/**
 * 
 */
package com.cti.lq.beans;

/**
 * @author senthil
 *
 */
public class PasswordResetBean {

	int userId;
	String emailAddress;
	String newPassword;
	String cNewPassword;
	
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
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
	
}
