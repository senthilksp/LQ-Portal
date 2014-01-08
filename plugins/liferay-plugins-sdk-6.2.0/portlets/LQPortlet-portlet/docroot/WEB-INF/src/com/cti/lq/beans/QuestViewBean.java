/**
 * 
 */
package com.cti.lq.beans;

/**
 * @author senthil
 *
 */
public class QuestViewBean {

	private int quest_id;
	private String quest_title;
	private Boolean access_mode;
	private String definition;
	private String questType;
	private String questLocation;
	private int userId;
	private String firstName;
	private String photoURL;
	private int questTransId;
	
	public int getQuestTransId() {
		return questTransId;
	}
	public void setQuestTransId(int questTransId) {
		this.questTransId = questTransId;
	}
	public int getQuest_id() {
		return quest_id;
	}
	public String getPhotoURL() {
		return photoURL;
	}
	public void setPhotoURL(String photoURL) {
		this.photoURL = photoURL;
	}
	public void setQuest_id(int quest_id) {
		this.quest_id = quest_id;
	}
	public String getQuest_title() {
		return quest_title;
	}
	public void setQuest_title(String quest_title) {
		this.quest_title = quest_title;
	}
	public Boolean getAccess_mode() {
		return access_mode;
	}
	public void setAccess_mode(Boolean access_mode) {
		this.access_mode = access_mode;
	}
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getQuestType() {
		return questType;
	}
	public void setQuestType(String questType) {
		this.questType = questType;
	}
	public String getQuestLocation() {
		return questLocation;
	}
	public void setQuestLocation(String questLocation) {
		this.questLocation = questLocation;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	

}


