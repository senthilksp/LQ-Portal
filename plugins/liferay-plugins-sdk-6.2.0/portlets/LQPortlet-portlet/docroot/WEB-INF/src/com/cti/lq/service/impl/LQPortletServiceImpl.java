/**
 * 
 */
package com.cti.lq.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.LoggedUserBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.controller.Header;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.LQQuestService;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;

/**
 * @author senthil Date Created : 20/12/2013.
 */
public class LQPortletServiceImpl implements LQPortletService {

	final Log LOG = LogFactory.getLog(Header.class);

	@Override
	public LoggedUserBean populateHeaderPortlet(LoggedUserBean lbean,
			RenderRequest renderRequest) throws LQPortalException {

		LOG.info("Entering into populateHeaderPortlet");
		int userId = LQPortalUserServiceUtil.getUserId(renderRequest);
		if (userId != 0) {
			User user = LQPortalUserServiceUtil.getUser(userId);
			lbean.setFirstName(user.getFirstName());
			lbean.setLastName(user.getLastName());
			lbean.setSignedIn(true);
			renderRequest.setAttribute("userId", userId);
		}
		
		LOG.info("Leaving from populateHeaderPortlet");
		return lbean;
	}

	@Override
	public void populateLeaderLoginPortlet(LeaderBean leaderBean,
			RenderRequest renderRequest) throws LQPortalException {
		LOG.info("populateLeaderLoginPortlet");

		HttpServletRequest httpRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(renderRequest));
		LQLeaderService leaderService = new LQLeaderServiceImpl();

		leaderBean.setUserid(httpRequest.getParameter("userId") == null ? 0
				: Integer.valueOf(httpRequest.getParameter("userId")));

		try {
			int userId = LQPortalUserServiceUtil.getUserId(renderRequest);
			User user = UserLocalServiceUtil.getUser(userId);

			Boolean isLeader = LQPortalUtil.fileMakerLeaderCheck(user
					.getEmailAddress());
			LOG.info("isLeader====" + isLeader);

			leaderBean = leaderService.getLeaderDetails(leaderBean,
					renderRequest);
			renderRequest.setAttribute("leaderBean", leaderBean);
			renderRequest.setAttribute("isLeader", isLeader);
			renderRequest.setAttribute("userId", leaderBean.getUserid());

		} catch (Exception le) {
			throw new LQPortalException(le.getMessage());
		}

	}

	@Override
	public void populateQuestLoginPortlet(List<QuestViewBean> questList,
			RenderRequest renderRequest) {

	}

	@Override
	public void saveLeaderDetails(LeaderBean leaderDetails) {

	}

	@Override
	public void populateLeaderNoLoginPortlet(List<LeaderBean> leaderList,
			RenderRequest renderRequest) throws LQPortalException {
		LOG.info("Entering into populateLeaderNoLoginPortlet");
		LQLeaderService leaderService = new LQLeaderServiceImpl();
		
		try {
			String role = LQPortalUserServiceUtil.getRoleName(renderRequest);
			
			leaderList = leaderService.getLeaderList(leaderList, renderRequest);
			renderRequest.setAttribute("leaderList", leaderList);
						
			renderRequest.setAttribute("roleName", role);
			renderRequest.setAttribute("userId", LQPortalUserServiceUtil.getUserId(renderRequest));

		} catch (Exception ex) {
			throw new LQPortalException(ex.getMessage());
		}

	}

	@Override
	public void populateLeaderViewPortlet(LeaderBean leaderBean,
			RenderRequest renderRequest) throws LQPortalException {

		HttpServletRequest httpRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(renderRequest));
		LQLeaderService leaderService = new LQLeaderServiceImpl();

		leaderBean.setUserid(httpRequest.getParameter("userId") == null ? 0
				: Integer.valueOf(httpRequest.getParameter("userId")));

		Boolean editRights = false;
		Boolean deleteRights = false;
		int loginUserId = LQPortalUserServiceUtil.getUserId(renderRequest);

		try {
			String role = LQPortalUserServiceUtil.getRoleName(renderRequest);

			if (LQPortalConstants.LQ_LEADER_ROLE.equalsIgnoreCase(role)
					&& httpRequest.getParameter("userId") != null) {
				if (Integer.valueOf(httpRequest.getParameter("userId")) == loginUserId) {
					editRights = true;
				}
			}

			if (LQPortalConstants.LQ_LEADER_ADMIN.equalsIgnoreCase(role)) {
				editRights = true;
				if (Integer.valueOf(httpRequest.getParameter("userId")) != loginUserId) {
					deleteRights = true;
				}
			}

			renderRequest.setAttribute("canEdit", editRights);
			renderRequest.setAttribute("canDelete", deleteRights);

			leaderBean = leaderService.getLeaderDetails(leaderBean,
					renderRequest);
			renderRequest.setAttribute("leaderBean", leaderBean);
			renderRequest.setAttribute("roleName", role);
			renderRequest.setAttribute("userId", httpRequest.getParameter("userId"));

		} catch (Exception le) {
			throw new LQPortalException(le.getMessage());
		}

	}

	@Override
	public void populateQuestViewPortlet(List<QuestViewBean> questList,
			RenderRequest renderRequest) throws LQPortalException {
		int userId = 0;
		int questId = 0;

		QuestViewBean questBean = new QuestViewBean();

		HttpServletRequest httpRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(renderRequest));
		LQLeaderService leaderService = new LQLeaderServiceImpl();

		userId = httpRequest.getParameter("userId") == null ? 0 : Integer
				.valueOf(httpRequest.getParameter("userId"));

		questId = httpRequest.getParameter("questId") == null ? 0 : Integer
				.valueOf(httpRequest.getParameter("questId"));

		try {
			// This method is for getting questlists when nologin
			if (questId != 0) {
				questList = leaderService.getQuestDetails(questList,
						renderRequest, userId, questId);
			} else { // This method is for getting questlists when login
				questList = leaderService.getQuestMasterDetails(questList,
						renderRequest,userId);

			}

			renderRequest.setAttribute("questList", questList);
			renderRequest.setAttribute("questId", questId);
			renderRequest.setAttribute("userId", userId);
			
			if (questList.size() > 0) {
				questBean.setFirstName(questList.get(0).getFirstName());
				questBean.setPhotoURL(questList.get(0).getPhotoURL());
				questBean.setQuest_title(questList.get(0).getQuest_title());
				questBean.setDefinition(questList.get(0).getDefinition());
				renderRequest.setAttribute("questBean", questBean);
			}

		} catch (Exception le) {
			throw new LQPortalException(le.getMessage());
		}

	}

	@Override
	public void populateQuestEditPortlet(RenderRequest renderRequest) {

		LQLeaderService leaderService = new LQLeaderServiceImpl();

		List<QuestMasterBean> questList = new ArrayList<QuestMasterBean>();
		List<QuestViewBean> questListAll = new ArrayList<QuestViewBean>();
		
		List<QuestMasterBean> questListFiltered = new ArrayList<QuestMasterBean>();
		List<QuestViewBean> questListAllFiltered = new ArrayList<QuestViewBean>();

		//int userId = LQPortalUserServiceUtil.getUserId(renderRequest);
		questList = new ArrayList<QuestMasterBean>();
		
		HttpServletRequest httpRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(renderRequest));
		
		int questId = Integer.valueOf(httpRequest.getParameter("questId"));
		int userId = Integer.valueOf(httpRequest.getParameter("userId"));

		try {
			LQQuestService questService = new LQQuestServiceImpl();
			questList = questService.getQuestMasterList(userId);
			questListAll = leaderService.getQuestMasterDetails(questListAll,
					renderRequest,userId);

			QuestViewBean questBean = new QuestViewBean();
			if (questList.size() > 0 && questListAll.size() > 0) {
				questBean.setFirstName(questListAll.get(0).getFirstName());
				questBean.setPhotoURL(questListAll.get(0).getPhotoURL());
				renderRequest.setAttribute("questBean", questBean);
			}
			
			for(QuestMasterBean qb:questList) {
				if(qb.getQuestId() == questId) {
					questListFiltered.add(qb);
				}
			}
			
			for(QuestViewBean qb1:questListAll) {
				if(qb1.getQuest_id() == questId) {
					questListAllFiltered.add(qb1);
				}
			}

			renderRequest.setAttribute("questMasterList", questListFiltered);
			renderRequest.setAttribute("questListAll", questListAllFiltered);
			renderRequest.setAttribute("questId", questId);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void populateQuestListPortlet(RenderRequest renderRequest) {
		LOG.info("Entering into populateQuestListPortlet");
		
		LQLeaderService leaderService = new LQLeaderServiceImpl();
		LeaderBean leaderBean = new LeaderBean();
		try {
			int userId = LQPortalUserServiceUtil.getUserId(renderRequest);
			renderRequest.setAttribute("userId", userId);
			
			leaderBean = leaderService.getLeaderDetails(leaderBean,
					renderRequest);
			renderRequest.setAttribute("leaderBean", leaderBean);

		} catch (Exception le) {
			le.printStackTrace();
		}

		
		
	}


}
