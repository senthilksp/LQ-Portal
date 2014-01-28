package com.cti.lq.dao;

import java.sql.SQLException;
import java.util.List;

import javax.portlet.RenderRequest;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.PasswordResetBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;

public interface LQLeaderDAO {
	
	public List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist, RenderRequest renderRequest) throws SQLException;

	public LeaderBean getLeaderDetails(RenderRequest renderRequest,
			LeaderBean leaderBean) throws SQLException;

	public Boolean saveLeaderDetails(LeaderBean leaderDetails) throws SQLException;

	public List<QuestViewBean> getQuestDetails(RenderRequest renderRequest,
			List<QuestViewBean> questList, int userid, int questId) throws SQLException;

	public Boolean addLeaderDetails(LeaderBean leaderDetails) throws SQLException;

	public List<QuestViewBean> getQuestMasterDetails(
			RenderRequest renderRequest, List<QuestViewBean> questList, int userId) throws SQLException;

	public Boolean resetPassword(PasswordResetBean resetBean) throws SQLException;

	public Boolean deleteLeader(LeaderBean lb) throws SQLException;


}
