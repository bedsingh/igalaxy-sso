
/***********************************************************************************************************
 * Module Name - igalaxy-sso
 * Version Control Block
 * 
 * Date			Version	   Author			Reviewer			Change Description
 * -----------  ---------  --------------	----------------	-------------------------------------------
 * Jun 18, 2018  1.0		   Singh, Bed		XXXXXXXX			Created.
 * -----------  ---------  --------------	----------------	-------------------------------------------
 * 
 ***********************************************************************************************************/

package com.igalaxy.sso.security;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

/***********************************************************************************************************
 * Java File - AuthenticationService.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - Token class which save the token in cache.
 ***********************************************************************************************************/

@Component
public class AuthTokenService {

	private static Logger logger = LogManager.getLogger(AuthTokenService.class);

	private static ConcurrentMap<String, SSOAuthToken> tokenCache = new ConcurrentHashMap<>();
	private static final int FIVE_MINUTE_IN_MILLIS = 5*60*1000;

	@Scheduled(fixedRate=FIVE_MINUTE_IN_MILLIS)
	public void evictExpireTokens() {
		logger.info("Evicting expired tokens.");

		expireTokenAndRemoveFromCache();
	}

	private void expireTokenAndRemoveFromCache() {
		tokenCache.entrySet()
		.removeIf(authToken -> {
			final Date currentDateTime = new Date();
			boolean isTokenExpired = authToken.getValue().getExpireDate().before(currentDateTime);
			logger.info("Is Token expired -> "+isTokenExpired+" For UserId: "+authToken.getValue().getUserId());
			return isTokenExpired;
		});
	}

	public void store(String encodeTokenWithEmpId, SSOAuthToken ssoAuthToken) {
		tokenCache.put(encodeTokenWithEmpId, ssoAuthToken);
		logger.info("Total tokens in cache: "+tokenCache.size());
	}

	public boolean contains(String encodeTokenWithEmpId) {
		return Optional.ofNullable(tokenCache.get(encodeTokenWithEmpId)).isPresent();
	}

	public synchronized boolean isActiveToken(String encodedTokenWithEmpId) {
		boolean isActiveToken = false;
		if(contains(encodedTokenWithEmpId)) {
			SSOAuthToken ssoAuthToken = tokenCache.get(encodedTokenWithEmpId);
			final Date currentDate = new Date();
			isActiveToken = true;
			if(ssoAuthToken.getExpireDate().before(currentDate)) {
				isActiveToken = false;
				tokenCache.remove(encodedTokenWithEmpId);
			}
		}
		return isActiveToken;
	}

	public Authentication retrieve(String encodedTokenWithEmpId) 
	{
		if(isActiveToken(encodedTokenWithEmpId)) {
			return tokenCache.get(encodedTokenWithEmpId).getAuthentication();
		}
		return null;
	}


}
