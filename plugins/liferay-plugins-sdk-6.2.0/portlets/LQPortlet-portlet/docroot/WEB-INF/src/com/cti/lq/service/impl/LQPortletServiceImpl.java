/**
 * 
 */
package com.cti.lq.service.impl;

import java.sql.SQLException;
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
			leaderList = leaderService.getLeaderList(leaderList, renderRequest);
			renderRequest.setAttribute("leaderList", leaderList);

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
		int loginUserId = LQPortalUserServiceUtil.getUserId(renderRequest);

		try {
			String role = LQPortalUserServiceUtil.getRoleName(renderRequest);

			if (LQPortalConstants.LQ_LEADER_ROLE.equalsIgnoreCase(role)
					&& httpRequest.getParameter("userId") != null) {
				if (Integer.valueOf(httpRequest.getParameter("userId")) == loginUserId) {
					editRights = true;
				}
			}

			if (LQPortalConstants.LQ_LEADER_ADMIN.equalsIgnoreCase(role))
				editRights = true;

			renderRequest.setAttribute("canEdit", editRights);

			leaderBean = leaderService.getLeaderDetails(leaderBean,
					renderRequest);
			renderRequest.setAttribute("leaderBean", leaderBean);
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
						renderRequest);

			}

			renderRequest.setAttribute("questList", questList);
			if (questList.size() > 0) {
				questBean.setFirstName(questList.get(0).getFirstName());
				questBean.setPhotoURL(questList.get(0).getPhotoURL());
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

		int userId = LQPortalUserServiceUtil.getUserId(renderRequest);
		questList = new ArrayList<QuestMasterBean>();

		try {
			LQQuestService questService = new LQQuestServiceImpl();
			questList = questService.getMasterQuestList(userId);
			questListAll = leaderService.getQuestMasterDetails(questListAll,
					renderRequest);

			QuestViewBean questBean = new QuestViewBean();
			if (questList.size() > 0) {
				questBean.setFirstName(questListAll.get(0).getFirstName());
				questBean.setPhotoURL(questListAll.get(0).getPhotoURL());
				renderRequest.setAttribute("questBean", questBean);
			}

			renderRequest.setAttribute("questMasterList", questList);
			renderRequest.setAttribute("questListAll", questListAll);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
