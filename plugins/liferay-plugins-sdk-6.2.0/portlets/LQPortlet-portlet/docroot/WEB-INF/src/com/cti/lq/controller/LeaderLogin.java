/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class LeaderLogin extends MVCPortlet {

	private String viewJSP;
	final Log LOG = LogFactory.getLog(LeaderLogin.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);

		LeaderBean leaderBean = new LeaderBean();
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();
		String role = LQPortalUserServiceUtil.getRoleName(renderRequest);
		
		try {
			lqServiceLayer
					.populateLeaderLoginPortlet(leaderBean, renderRequest);
			renderRequest.setAttribute("roleName", role);

		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}


		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");
	}

	public void submitLeaderDetails(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		LOG.info(" Entering submitLeaderDetails()");

		LeaderBean leaderDetails = new LeaderBean();
		int userId = LQPortalUserServiceUtil.getUserId(actionRequest);

		try {
			leaderDetails.setFirstname(actionRequest.getParameter("firstname")); 
			leaderDetails.setLastname(actionRequest.getParameter("lastname"));
			leaderDetails.setEmailAddress(actionRequest.getParameter("emailaddress"));
			leaderDetails.setFacultyRole(actionRequest.getParameter("facultyrole")); 
			leaderDetails.setPrimaryPhone(actionRequest.getParameter("primaryphone"));
			leaderDetails.setWebsite(actionRequest.getParameter("website"));
			leaderDetails.setBusinessName(actionRequest.getParameter("businessname")); 
			leaderDetails.setCountry(actionRequest.getParameter("country"));
			leaderDetails.setCity(actionRequest.getParameter("city"));
			
			leaderDetails.setBioStatement(actionRequest.getParameter("biostatement"));
			leaderDetails.setPhotoURL(actionRequest.getParameter("photourl"));
			leaderDetails.setUserid(userId);

			List<QuestMasterBean> questMasterList = new ArrayList<QuestMasterBean>();
			List<QuestMasterBean> questChangesList = new ArrayList<QuestMasterBean>();
			
			
			LQQuestService questService = new LQQuestServiceImpl(); 
			questMasterList = questService.getQuestMasterList(userId);
			for(QuestMasterBean qb:questMasterList) {
				QuestMasterBean newBean = new QuestMasterBean();
				newBean.setUserId(qb.getUserId());
				newBean.setQuestId(qb.getQuestId());
				if( actionRequest.getParameter(String.valueOf(qb.getQuestId())) != null) {
					newBean.setUserId(userId);
					if("Private".equalsIgnoreCase(actionRequest.getParameter(String.valueOf(qb.getQuestId())))) {
						newBean.setAccessMode(false);
					} else {
						newBean.setAccessMode(true);
					}
				}
				questChangesList.add(newBean);
			}
			
			leaderDetails.setQuestList(questChangesList);
			
			LQLeaderService lqServiceLayer = new LQLeaderServiceImpl();
			saveSuccess = lqServiceLayer.saveLeaderDetails(leaderDetails);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (saveSuccess) {
			SessionMessages.add(actionRequest, "leader-edited-successfully");
		} else {
			SessionErrors.add(actionRequest, "leader-edit-failed");
		}

	}
}
