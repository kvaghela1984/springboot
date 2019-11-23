package com.example.springboot.user.app.models;

import java.io.Serializable;

public class AuthenticateRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8394769370670597130L;
	
	private String userName;
	private String passWord;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
	

}
