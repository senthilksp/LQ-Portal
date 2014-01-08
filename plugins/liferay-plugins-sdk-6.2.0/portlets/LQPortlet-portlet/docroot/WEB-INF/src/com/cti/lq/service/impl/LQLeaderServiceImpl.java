/**
 * 
 */
package com.cti.lq.service.impl;

import java.sql.SQLException;
import java.util.List;

import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.dao.LQLeaderDAO;
import com.cti.lq.dao.impl.LQLeaderDAOImpl;

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
	public Boolean addLeaderDetails(LeaderBean leaderDetails) throws SQLException{
		LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		return leaderDAO.addLeaderDetails(leaderDetails);
		
	}

	@Override
	public List<QuestViewBean> getQuestMasterDetails(
			List<QuestViewBean> questList, RenderRequest renderRequest) throws SQLException {
		    LOG.info("Entering Leader Service-Layer");
		
		    LQLeaderDAO leaderDAO = new LQLeaderDAOImpl();
		    questList = leaderDAO.getQuestMasterDetails(renderRequest, questList); 
		
		    return questList;
	}

	
	
}
