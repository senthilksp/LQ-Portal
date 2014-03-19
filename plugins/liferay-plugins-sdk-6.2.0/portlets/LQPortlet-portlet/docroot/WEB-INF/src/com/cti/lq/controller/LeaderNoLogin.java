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

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.LQQuestService;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.service.impl.LQQuestServiceImpl;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil Date : 23/12/2013.
 */
public class LeaderNoLogin extends MVCPortlet {

	private String viewJSP;
	final Log LOG = LogFactory.getLog(LeaderNoLogin.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		LOG.info("Entering doView()--Leader No login");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);
		
		List<LeaderBean> leaderList = new ArrayList<LeaderBean>();
		
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();
		try {
			lqServiceLayer.populateLeaderNoLoginPortlet(leaderList, renderRequest);
			
		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}
		
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		
		LOG.info("Leaving doView() -- Leader No login");
	}
	
	public void submitQuestDetails(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {

		LOG.info(" Entering submitQuestDetails()");

		try {
			UploadPortletRequest uploadRequest = PortalUtil
					.getUploadPortletRequest(actionRequest);

				String action = uploadRequest.getParameter("delId");
				
				
				if (action.equalsIgnoreCase("DELETEALL")) {
					doMasterDelete(uploadRequest, actionRequest);
					actionResponse.sendRedirect(LQPortalConstants.LQ_LEADER_NO_LOGIN_URL);
				}

			

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private void doMasterDelete(UploadPortletRequest uploadRequest,
			ActionRequest actionRequest) {
		
		LOG.info("doMasterDelete");
		Boolean masterDeleted = false;
		LQQuestService questService = new LQQuestServiceImpl();

		try {
			String[] questIds = uploadRequest.getParameterValues("questId");
			for (String questIdStr: questIds) {
				int questId = Integer.valueOf(questIdStr);
				masterDeleted = questService.deleteQuestMaster(questId);
				if (!masterDeleted) {
					SessionErrors.add(actionRequest, "quest-update-failed");
				}				
			}
			if (masterDeleted) {
				SessionMessages.add(actionRequest, "quests-deleted-successfully");
			}
		} catch (Exception e) {
			SessionErrors.add(actionRequest, "quest-update-failed");
			e.printStackTrace();
		}

	}
	
}
