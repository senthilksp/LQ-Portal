package com.cti.lq.service;

import java.sql.SQLException;
import java.util.List;

import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.beans.QuestViewBean;

public interface LQQuestService {

	List<QuestMasterBean> getQuestMasterList(int userId) throws SQLException;

	Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) throws SQLException;

	Boolean saveQuestTransactions(List<QuestTransactionBean> qTransList,int UserId) throws SQLException;

	List<QuestMasterBean> getMasterQuestList(int userId) throws SQLException;

	Boolean updateQuestTransaction(QuestTransactionBean transBean) throws SQLException;


	Boolean updateQuestMaster(QuestMasterBean questMaster) throws SQLException;

}
