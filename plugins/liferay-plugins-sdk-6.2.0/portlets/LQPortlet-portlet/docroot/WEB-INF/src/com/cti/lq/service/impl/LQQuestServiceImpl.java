/**
 * 
 */
package com.cti.lq.service.impl;
import java.sql.SQLException;
import java.util.List;

import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.dao.LQQuestDAO;
import com.cti.lq.dao.impl.LQQuestDAOImpl;
import com.cti.lq.service.LQQuestService;

/**
 * @author senthil
 *
 */
public class LQQuestServiceImpl implements LQQuestService {

	@Override
	public List<QuestMasterBean> getQuestMasterList(int userId) throws SQLException {
		 LQQuestDAO questDAO = new LQQuestDAOImpl();
		 return questDAO.getQuestMasterList(userId);
	}

	@Override
	public Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) throws SQLException{
		LQQuestDAO questDAO = new LQQuestDAOImpl();
 		return questDAO.saveQuestDetails(questmaster,qTransList);
		
	}

	@Override
	public Boolean saveQuestTransactions(List<QuestTransactionBean> qTransList,int userId,int questId) throws SQLException {
		LQQuestDAO questDAO = new LQQuestDAOImpl();
 		return questDAO.saveQuestTransactions(qTransList,userId,questId);
	}

	@Override
	public Boolean updateQuestTransaction(QuestTransactionBean transBean)
			throws SQLException {
		LQQuestDAO questDAO = new LQQuestDAOImpl();
		return questDAO.updateQuestTransaction(transBean);
	}

	@Override
	public Boolean updateQuestMaster(QuestMasterBean questMaster)
			throws SQLException {
		LQQuestDAO questDAO = new LQQuestDAOImpl();
		return questDAO.updateQuestMaster(questMaster);
	}

	@Override
	public Boolean deleteQuestTransaction(int id1) throws SQLException {
		LQQuestDAO questDAO = new LQQuestDAOImpl();
		return questDAO.deleteQuestTransaction(id1);
	}

	@Override
	public Boolean deleteQuestMaster(int questId) throws SQLException{
		LQQuestDAO questDAO = new LQQuestDAOImpl();
		return questDAO.deleteQuestMaster(questId);
	}

	@Override
	public int findQuestId(QuestMasterBean questmaster) throws SQLException {
		LQQuestDAO questDAO = new LQQuestDAOImpl();
		return questDAO.findQuestId(questmaster);
	}

}
