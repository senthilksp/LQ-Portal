package com.cti.lq.dao;

import java.sql.SQLException;
import java.util.List;

import com.cti.lq.beans.QuestCommentBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;

public interface LQQuestDAO {

	List<QuestMasterBean> getQuestMasterList(int userId) throws SQLException;

	Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) throws SQLException;

	Boolean saveQuestTransactions(List<QuestTransactionBean> qTransList, int userId, int questId) throws SQLException;

	Boolean saveQuestComment(QuestCommentBean questCommentBean) throws SQLException;
	
	Boolean updateQuestTransaction(QuestTransactionBean transBean) throws SQLException;

	Boolean updateQuestMaster(QuestMasterBean questMaster) throws SQLException;

	Boolean deleteQuestTransaction(int questTransId) throws SQLException;

	Boolean deleteQuestMaster(int questId) throws SQLException;

	int findQuestId(QuestMasterBean questmaster) throws SQLException;


}
