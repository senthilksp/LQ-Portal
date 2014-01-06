package com.cti.lq.service;

import java.util.List;

import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.beans.QuestViewBean;

public interface LQQuestService {

	List<QuestMasterBean> getQuestMasterList(int userId);

	Boolean saveQuestDetails(QuestMasterBean questmaster,
			List<QuestTransactionBean> qTransList);

}
