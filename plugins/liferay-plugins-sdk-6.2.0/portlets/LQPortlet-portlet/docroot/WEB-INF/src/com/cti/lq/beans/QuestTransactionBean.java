package com.cti.lq.beans;

import java.util.List;

public class QuestTransactionBean {

	private int id;
	
	private int QuestId;
	private String QuestType;
	private String QuestLocation;
	private List<QuestCommentBean> questCommentBean;
	
	public int getQuestId() {
		return QuestId;
	}
	public void setQuestId(int questId) {
		QuestId = questId;
	}
	public String getQuestType() {
		return QuestType;
	}
	public void setQuestType(String questType) {
		QuestType = questType;
	}
	public String getQuestLocation() {
		return QuestLocation;
	}
	public void setQuestLocation(String questLocation) {
		QuestLocation = questLocation;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the questCommentBean
	 */
	public List<QuestCommentBean> getQuestCommentBean() {
		return questCommentBean;
	}
	/**
	 * @param questCommentBean the questCommentBean to set
	 */
	public void setQuestCommentBean(List<QuestCommentBean> questCommentBean) {
		this.questCommentBean = questCommentBean;
	}

}
