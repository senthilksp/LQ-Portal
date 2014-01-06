/**
 * 
 */
package com.cti.lq.service;

import java.util.List;

import javax.portlet.RenderRequest;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.LoggedUserBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.exceptions.LQPortalException;

/**
 * @author senthil
 * Date Created : 20/12/2013.
 * Function : This is service layer class for header portlet. 
 */
public interface LQPortletService {
	
	public LoggedUserBean populateHeaderPortlet(LoggedUserBean loggedUserBean, RenderRequest renderRequest) throws LQPortalException;

	public void populateLeaderNoLoginPortlet(List<LeaderBean> leaderList,
			RenderRequest renderRequest) throws LQPortalException;

	public void populateLeaderLoginPortlet(LeaderBean leaderBean,
			RenderRequest renderRequest) throws LQPortalException;

	public void saveLeaderDetails(LeaderBean leaderDetails);

	public void populateLeaderViewPortlet(LeaderBean leaderBean,
			RenderRequest renderRequest) throws LQPortalException;

	public void populateQuestViewPortlet(List<QuestViewBean> questList,
			RenderRequest renderRequest) throws LQPortalException;;
	
	public void populateQuestLoginPortlet(List<QuestViewBean> questList,
					RenderRequest renderRequest);



}
