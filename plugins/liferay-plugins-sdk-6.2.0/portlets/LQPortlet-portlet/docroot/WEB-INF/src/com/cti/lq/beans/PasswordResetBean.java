/**
 * 
 */
package com.cti.lq.beans;

/**
 * @author senthil
 *
 */
public class PasswordResetBean {

	long userId;
	
	String emailAddress;
	String newPassword;
	String cNewPassword;
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getcNewPassword() {
		return cNewPassword;
	}
	public void setcNewPassword(String cNewPassword) {
		this.cNewPassword = cNewPassword;
	}
	
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	
}
