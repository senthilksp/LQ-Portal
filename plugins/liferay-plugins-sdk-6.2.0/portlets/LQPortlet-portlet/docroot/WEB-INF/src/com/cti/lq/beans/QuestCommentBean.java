package com.cti.lq.beans;


public class QuestCommentBean {
	private int id;
	private int questTransId;
	private String comment;
	private String addedBy;
	private String addedOn;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the quest_id
	 */
	public int getQuestTransId() {
		return questTransId;
	}
	/**
	 * @param quest_id the quest_id to set
	 */
	public void setQuestTransId(int questTransId) {
		this.questTransId = questTransId;
	}
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the added_by
	 */
	public String getAddedBy() {
		return addedBy;
	}
	/**
	 * @param added_by the added_by to set
	 */
	public void setAddedBy(String addedBy) {
		this.addedBy = addedBy;
	}
	/**
	 * @return the added_at
	 */
	public String getAddedOn() {
		return addedOn;
	}
	/**
	 * @param added_at the added_at to set
	 */
	public void setAddedOn(String addedOn) {
		this.addedOn = addedOn;
	}
}