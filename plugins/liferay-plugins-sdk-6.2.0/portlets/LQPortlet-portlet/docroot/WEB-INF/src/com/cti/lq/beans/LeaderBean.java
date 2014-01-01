package com.cti.lq.beans;

import java.util.List;

public class LeaderBean {
	
	private int userid;
	private int roleid;
	private String facultyRole;
	private String primaryPhone;
	private String website;
	private String city;
	private String country;
	private String state;
	private String businessName;
	private String photoURL;
	private String bioStatement;
	private String firstname;
	private String lastname;
	private String emailAddress;
	private List<QuestMasterBean> questList;
	
	
	public String getEmailAddress() {
		return emailAddress;
	}
	public List<QuestMasterBean> getQuestList() {
		return questList;
	}
	public void setQuestList(List<QuestMasterBean> questList) {
		this.questList = questList;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getRoleid() {
		return roleid;
	}
	public void setRoleid(int roleid) {
		this.roleid = roleid;
	}
	public String getFacultyRole() {
		return facultyRole;
	}
	public void setFacultyRole(String facultyRole) {
		this.facultyRole = facultyRole;
	}
	public String getPrimaryPhone() {
		return primaryPhone;
	}
	public void setPrimaryPhone(String primaryPhone) {
		this.primaryPhone = primaryPhone;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public String getBioStatement() {
		return bioStatement;
	}
	public void setBioStatement(String bioStatement) {
		this.bioStatement = bioStatement;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
