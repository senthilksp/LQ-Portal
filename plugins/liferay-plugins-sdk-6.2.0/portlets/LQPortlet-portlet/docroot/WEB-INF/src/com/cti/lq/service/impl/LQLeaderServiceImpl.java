/**
 * 
 */
package com.cti.lq.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.PasswordResetBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.dao.LQLeaderDAO;
import com.cti.lq.dao.impl.LQLeaderDAOImpl;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

/**
 * @author senthil
 *
 */
public class LQLeaderServiceImpl implements LQLeaderService  {
	final Log LOG = LogFactory.getLog(LQLeaderServiceImpl.class);

	@Override
	public List<LeaderBean> getLeaderList(List<LeaderBean> leaderlist,
			RenderRequest renderRequest) throws SQLException{
		
	 LOG.info("Entering Leader Service-Layer");
	 
	 LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
	 leaderlist = leaderDAO.getLeaderList(leaderlist, renderRequest);
	
	 return leaderlist;
	}

	@Override
	public LeaderBean getLeaderDetails(LeaderBean leaderBean,
			RenderRequest renderRequest) throws SQLException {
		LOG.info("Entering Leader Service-Layer");
		
		LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		leaderBean = leaderDAO.getLeaderDetails(renderRequest, leaderBean);
		
		return leaderBean;
	}

	@Override
	public Boolean saveLeaderDetails(LeaderBean leaderDetails) throws SQLException {
		LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		return leaderDAO.saveLeaderDetails(leaderDetails);
		
	}

	@Override
	public List<QuestViewBean> getQuestDetails(List<QuestViewBean> questList,
			RenderRequest renderRequest, int userId, int questId) throws SQLException {
		LOG.info("Entering Leader Service-Layer");
		
		LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		questList = leaderDAO.getQuestDetails(renderRequest, questList,userId,questId);
		
		return questList;
	}

	@Override
	public Boolean addLeaderDetails(LeaderBean leaderDetails,ActionRequest actionRequest) throws SQLException{
		LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		return leaderDAO.addLeaderDetails(leaderDetails,actionRequest);
		
	}


	@Override
	public Boolean resetPassword(PasswordResetBean resetBean) throws SQLException {
		User u = null;
		
		try {
			u = UserLocalServiceUtil.updatePassword(resetBean.getUserId(),
					resetBean.getNewPassword(), resetBean.getcNewPassword(),
					false);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (u != null) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public List<QuestViewBean> getQuestMasterDetails(
			List<QuestViewBean> questList, RenderRequest renderRequest,
			int userId) throws SQLException {
		
		    LOG.info("Entering Leader Service-Layer");
		
		    LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		    questList = leaderDAO.getQuestMasterDetails(renderRequest, questList,userId);
		
		    return questList;
	}

	@Override
	public Boolean deleteLeader(LeaderBean lb) throws SQLException {
		LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		return leaderDAO.deleteLeader(lb);
	}

	
}
