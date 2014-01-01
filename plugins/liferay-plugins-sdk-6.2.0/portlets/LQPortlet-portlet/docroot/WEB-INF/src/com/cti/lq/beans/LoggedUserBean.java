package com.cti.lq.beans;

public class LoggedUserBean {
	
	private String firstName;
	private String lastName;
	private Boolean isSignedIn;
	
	
	public String getFirstName() {
		return firstName;
	}
	
	
	public Boolean getIsSignedIn() {
		return isSignedIn;
	}


	public void setIsSignedIn(Boolean isSignedIn) {
		this.isSignedIn = isSignedIn;
	}

	public void setSignedIn(Boolean isSignedIn) {
		this.isSignedIn = isSignedIn;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
