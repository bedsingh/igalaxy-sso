
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

import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.igalaxy.sso.exception.IGalaxyException;

/***********************************************************************************************************
 * Java File - AuthenticationFilter.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

public class AuthenticationFilter extends GenericFilterBean {

	private static Logger logger = LogManager.getLogger(AuthenticationFilter.class);

	private AuthenticationManager authenticationManager;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	/* (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;

		Optional<String> authToken = Optional.ofNullable(httpRequest.getHeader("x-auth-token")).filter(token -> token.trim().length()>0);
		Optional<String> userId = Optional.ofNullable(httpRequest.getHeader("user-id")).filter(usrId -> usrId.trim().length()>0);

		if(!authToken.isPresent() || !userId.isPresent()) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			throw new IGalaxyException(HttpServletResponse.SC_UNAUTHORIZED, "Passing sso token or user details are not valid.");
		}

		try {
			final String encodeToken = Base64.getEncoder().encodeToString((authToken.get()+":"+userId.get()).getBytes());
			Optional<String> encodedTokenWithUserId = Optional.ofNullable(encodeToken);
			if(processTokenAuthentication(encodedTokenWithUserId)) {

			}

		}
		catch(Exception exception) {

		}
	}

	private boolean processTokenAuthentication(Optional<String> token) {
		AuthenticationWithToken requestAuthentication = new AuthenticationWithToken(token, null);
		Authentication authentication = authenticationManager.authenticate(requestAuthentication);
		logger.info("Is Authentication null: "+(authentication ==  null));

		if(authentication == null || !authentication.isAuthenticated()) {
			throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials.");
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		logger.info("User Authenticated ?  "+authentication.isAuthenticated());
		
		return authentication.isAuthenticated();
	}
}
