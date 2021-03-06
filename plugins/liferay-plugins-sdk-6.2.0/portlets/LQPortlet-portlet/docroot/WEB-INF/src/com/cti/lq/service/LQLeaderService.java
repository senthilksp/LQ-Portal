/**
 * 
 */
package com.cti.lq.service;

import java.sql.SQLException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.PasswordResetBean;
import com.cti.lq.beans.QuestViewBean;

/**
 * @author senthil
 * Date : 23/12/2013.
 */
public interface LQLeaderService {

	List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist,
			RenderRequest renderRequest) throws SQLException;

	LeaderBean getLeaderDetails(LeaderBean leaderBean,
			RenderRequest renderRequest)throws SQLException;

	Boolean saveLeaderDetails(LeaderBean leaderDetails)throws SQLException;

	List<QuestViewBean> getQuestDetails(List<QuestViewBean> questList,
			RenderRequest renderRequest, int userId, int questId)throws SQLException;

	Boolean addLeaderDetails(LeaderBean leaderDetails, ActionRequest actionRequest)throws SQLException;

	List<QuestViewBean> getQuestMasterDetails(List<QuestViewBean> questList,
			RenderRequest renderRequest, int userId)throws SQLException;

	Boolean resetPassword(PasswordResetBean resetBean) throws SQLException;

	Boolean deleteLeader(LeaderBean lb) throws SQLException;

}
