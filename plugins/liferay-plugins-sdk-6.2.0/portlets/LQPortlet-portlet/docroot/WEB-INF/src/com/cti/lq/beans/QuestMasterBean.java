/**
 * 
 */
package com.cti.lq.beans;

/**
 * @author senthil
 *
 */
public class QuestMasterBean {

	private int questId;
	private String questTitle;
	private Boolean accessMode;
	private String questDefinition;
	private int userId;
	
	public int getQuestId() {
		return questId;
	}
	public void setQuestId(int questId) {
		this.questId = questId;
	}
	public String getQuestTitle() {
		return questTitle;
	}
	public void setQuestTitle(String questTitle) {
		this.questTitle = questTitle;
	}
	public Boolean getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(Boolean accessMode) {
		this.accessMode = accessMode;
	}
	public String getQuestDefinition() {
		return questDefinition;
	}
	public void setQuestDefinition(String questDefinition) {
		this.questDefinition = questDefinition;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
}
