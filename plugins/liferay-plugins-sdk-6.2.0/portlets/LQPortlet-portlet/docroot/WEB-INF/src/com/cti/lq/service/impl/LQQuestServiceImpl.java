/**
 * 
 */
package com.cti.lq.service.impl;
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
	public List<QuestMasterBean> getQuestMasterList(int userId) {
		 LQQuestDAO questDAO = new LQQuestDAOImpl();
		 return questDAO.getQuestMasterList(userId);
	}

	@Override
	public Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList) {
		LQQuestDAO questDAO = new LQQuestDAOImpl();
 		return questDAO.saveQuestDetails(questmaster,qTransList);
		
	}




}
