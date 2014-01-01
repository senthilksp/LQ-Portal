package com.cti.lq.dao;

import java.util.List;

import javax.portlet.RenderRequest;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;

public interface LQLeaderDAO {
	
	public List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist, RenderRequest renderRequest);

	public LeaderBean getLeaderDetails(RenderRequest renderRequest,
			LeaderBean leaderBean);

	public void saveLeaderDetails(LeaderBean leaderDetails);

	public List<QuestViewBean> getQuestDetails(RenderRequest renderRequest,
			List<QuestViewBean> questList, int userid, int questId);


}
