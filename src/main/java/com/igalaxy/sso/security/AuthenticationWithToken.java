
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

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

/***********************************************************************************************************
 * Java File - AuthenticationWithToken.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {

	private static final long serialVersionUID = 1L;

	public AuthenticationWithToken(Object aPrincipal, Object aCredentials) {
		super(aPrincipal, aCredentials);

	}

	public AuthenticationWithToken(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
		super(aPrincipal, aCredentials, anAuthorities);

	}

	public void setToken(String token) {
		setDetails(token);
	}

	public String getToken() {
		return getDetails().toString();
	}

}
