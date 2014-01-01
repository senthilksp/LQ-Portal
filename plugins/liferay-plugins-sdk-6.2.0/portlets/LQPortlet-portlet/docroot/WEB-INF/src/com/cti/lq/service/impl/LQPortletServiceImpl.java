/**
 * 
 */
package com.cti.lq.service.impl;

import java.util.List;

import javax.portlet.RenderRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.LoggedUserBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.controller.Header;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.liferay.portal.model.User;
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
		}
		LOG.info("Leaving from populateHeaderPortlet");
		return lbean;
	}

	@Override
	public void populateLeaderLoginPortlet(LeaderBean leaderBean,
			RenderRequest renderRequest) throws LQPortalException {

		LQLeaderService leaderService = new LQLeaderServiceImpl();
		leaderBean.setUserid(0);

		try {
			leaderBean = leaderService.getLeaderDetails(leaderBean,
					renderRequest);
			renderRequest.setAttribute("leaderBean", leaderBean);

		} catch (Exception le) {
			throw new LQPortalException(le.getMessage());
		}

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

		try {
			leaderBean = leaderService.getLeaderDetails(leaderBean,
					renderRequest);
			renderRequest.setAttribute("leaderBean", leaderBean);

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

		userId  = httpRequest.getParameter("userId") == null ? 0 : Integer
				.valueOf(httpRequest.getParameter("userId"));
		
		questId = httpRequest.getParameter("questId") == null ? 0 : Integer
				.valueOf(httpRequest.getParameter("questId"));

		try {
			questList = leaderService.getQuestDetails(questList, renderRequest,
					userId,questId);
			renderRequest.setAttribute("questList", questList);
			if(questList.size()>0) {
				questBean.setFirstName(questList.get(0).getFirstName());
				questBean.setPhotoURL(questList.get(0).getPhotoURL());
				renderRequest.setAttribute("questBean", questBean);
			}

		} catch (Exception le) {
			throw new LQPortalException(le.getMessage());
		}

	}

}
