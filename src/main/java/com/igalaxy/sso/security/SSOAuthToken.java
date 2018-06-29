
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

import java.io.Serializable;
import java.util.Date;

import org.springframework.security.core.Authentication;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/***********************************************************************************************************
 * Java File - SSOAuthToken.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

//@Getter(value=AccessLevel.PUBLIC)
//@Setter(value=AccessLevel.PUBLIC)
public class SSOAuthToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String userId;
	private String userEmployeeId;
	private String accessToken;
	private String expiresIn;
	private String refreshToken;
	private String tokenType;
	private Date expireDate;
	private Authentication authentication;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserEmployeeId() {
		return userEmployeeId;
	}
	public void setUserEmployeeId(String userEmployeeId) {
		this.userEmployeeId = userEmployeeId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getExpiresIn() {
		return expiresIn;
	}
	public void setExpiresIn(String expiresIn) {
		this.expiresIn = expiresIn;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getTokenType() {
		return tokenType;
	}
	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}
 
	public Date getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(Date expireDate) {
		this.expireDate = expireDate;
	}
	public Authentication getAuthentication() {
		return authentication;
	}
	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

	
}
