package com.cti.lq.dao;

import java.sql.SQLException;
import java.util.List;

import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.beans.QuestViewBean;

public interface LQQuestDAO {

	List<QuestMasterBean> getQuestMasterList(int userId) throws SQLException;

	Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) throws SQLException;

	Boolean saveQuestTransactions(List<QuestTransactionBean> qTransList, int userId, int questId) throws SQLException;

	Boolean updateQuestTransaction(QuestTransactionBean transBean) throws SQLException;

	Boolean updateQuestMaster(QuestMasterBean questMaster) throws SQLException;

	Boolean deleteQuestTransaction(int id1) throws SQLException;


}
