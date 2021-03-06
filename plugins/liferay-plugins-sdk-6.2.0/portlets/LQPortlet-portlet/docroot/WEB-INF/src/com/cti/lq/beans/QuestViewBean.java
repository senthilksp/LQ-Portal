/**
 * 
 */
package com.cti.lq.beans;

import java.util.List;

/**
 * @author senthil
 *
 */
public class QuestViewBean {


	private int id;
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
	private String qlocationForDisplay;
	private List<QuestCommentBean> commentList;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
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
	
	public String getQlocationForDisplay() {
		return qlocationForDisplay;
	}
	public void setQlocationForDisplay(String qlocationForDisplay) {
		this.qlocationForDisplay = qlocationForDisplay;
	}
	/**
	 * @return the commentsList
	 */
	public List<QuestCommentBean> getCommentList() {
		return commentList;
	}
	/**
	 * @param commentsList the commentsList to set
	 */
	public void setCommentList(List<QuestCommentBean> commentList) {
		this.commentList = commentList;
	}
}


