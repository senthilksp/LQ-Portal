/**
 * 
 */
package com.cti.lq.service;

import java.util.List;

import javax.portlet.RenderRequest;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;

/**
 * @author senthil
 * Date : 23/12/2013.
 */
public interface LQLeaderService {

	List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist,
			RenderRequest renderRequest);

	LeaderBean getLeaderDetails(LeaderBean leaderBean,
			RenderRequest renderRequest);

	void saveLeaderDetails(LeaderBean leaderDetails);

	List<QuestViewBean> getQuestDetails(List<QuestViewBean> questList,
			RenderRequest renderRequest, int userId, int questId);

	

}
