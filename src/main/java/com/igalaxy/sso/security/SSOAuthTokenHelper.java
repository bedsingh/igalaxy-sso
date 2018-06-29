
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

import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/***********************************************************************************************************
 * Java File - SSOAuthTokenHelper.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

@Component
public class SSOAuthTokenHelper {
	
	private static Logger logger = LogManager.getLogger(SSOAuthTokenHelper.class);
	
	@Autowired
	private AuthTokenService authTokenService;
	
	public void addAuthentication(Map<String, String> accessTokenMap, String userEmpId, String userId) {
		Optional.ofNullable(accessTokenMap).ifPresent(tokenMap -> {
			final String accessToken = tokenMap.get("access_token");
			final String encodedTokenWithEmpId = Base64.getEncoder().encodeToString((accessToken+":"+userEmpId).getBytes());
			AuthenticationWithToken authentication = new AuthenticationWithToken(encodedTokenWithEmpId, null);
			authentication.setToken(encodedTokenWithEmpId);
			authentication.setAuthenticated(true);
			
			SSOAuthToken ssoAuthToken = new SSOAuthToken();
			ssoAuthToken.setAccessToken(accessToken);
			ssoAuthToken.setAuthentication(authentication);
			ssoAuthToken.setUserId(userId);
			ssoAuthToken.setExpireDate(tokenExpireDate(tokenMap.get("expires-in")));
			ssoAuthToken.setExpiresIn(tokenMap.get("expires-in"));
			ssoAuthToken.setRefreshToken(tokenMap.get("refresh-token"));
			ssoAuthToken.setTokenType(tokenMap.get("token-type"));
			ssoAuthToken.setUserEmployeeId(userEmpId);
			authTokenService.store(encodedTokenWithEmpId, ssoAuthToken);
			logger.info("Token Stored into Cache Token: "+encodedTokenWithEmpId+" UserId: "+userId);
			
		});
	}
	
	/**
	 * This method is use to add millis into current date and return the expire token date.
	 * input param value is in seconds.
	 * @param expiresIn
	 * @return
	 */
	private static Date tokenExpireDate(String expiresIn) {
		long expiresInMillis = 0;
		
		try {
			expiresInMillis = Long.valueOf(expiresIn);
			expiresInMillis = (expiresInMillis*1000);
		}
		catch(NumberFormatException numberExcepiton) {
			logger.error("Number format exception in tokenExpireDate method.");
		}
		
		Date tokenCreatedOn = new Date();
		Calendar tokenExpireDateCalendar = Calendar.getInstance();
		tokenExpireDateCalendar.setTimeInMillis(tokenCreatedOn.getTime()+expiresInMillis);
		
		return tokenExpireDateCalendar.getTime();
	}
}
