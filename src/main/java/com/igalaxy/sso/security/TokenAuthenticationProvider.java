
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

import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/***********************************************************************************************************
 * Java File - TokenAuthenticationProvider.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

@Component
public class TokenAuthenticationProvider implements AuthenticationProvider {

	private static Logger logger = LogManager.getLogger(TokenAuthenticationProvider.class);

	@Autowired
	private AuthTokenService authTokenService;
	
	public TokenAuthenticationProvider(AuthTokenService authTokenService) {
		this.authTokenService = authTokenService;
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		Optional<?> token = (Optional<?>)authentication.getPrincipal();
		if(!token.isPresent()) {
			throw new BadCredentialsException("Invalid SSO Auth Token");
		}
		
		if(!authTokenService.contains(token.get().toString())) {
			throw new BadCredentialsException("Invalid SSO Token or token expired.");
		}
		
		logger.info("TokenAuthenticationProvide - authenticate token.get() is Null ? "+(token.get()==null));
		
		return authTokenService.retrieve(token.get().toString());
	}

	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> authentication) {
		logger.info("authentication.equals(AuthenticationWithToken.class) -> "+authentication.equals(AuthenticationWithToken.class));
		return authentication.equals(AuthenticationWithToken.class);
	}

}
