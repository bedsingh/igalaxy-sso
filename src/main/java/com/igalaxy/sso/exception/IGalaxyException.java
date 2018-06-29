
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

package com.igalaxy.sso.exception;

/***********************************************************************************************************
 * Java File - IGalaxyException.java
 * Author - Bed Singh
 * Date   - Jun 18, 2018
 * Description - 
 ***********************************************************************************************************/

public class IGalaxyException extends RuntimeException 
{

	private static final long serialVersionUID = 1L;
	private String message;
	private Integer status;

	public IGalaxyException() {

	}

	public IGalaxyException(Integer status, String message) {
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	
	

}
