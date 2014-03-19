/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
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
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.LQQuestService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.service.impl.LQQuestServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil
 *
 */
public class LeaderView extends MVCPortlet{

	private String viewJSP;
	final Log LOG = LogFactory.getLog(LeaderLogin.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		SessionMessages.clear(renderRequest);
		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);

		LeaderBean leaderBean = new LeaderBean();
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();

		try {
			lqServiceLayer.populateLeaderViewPortlet(leaderBean, renderRequest);

		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}

		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");
	}
	
	public void submitDeleteLeader(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		LOG.info("Deleting Leader");
		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		Boolean masterDeleted = false;
		
		LQQuestService questService = new LQQuestServiceImpl();
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		
		try {
			int leaderId = Integer.valueOf(uploadRequest.getParameter("userId"));
			String leaderEmail = uploadRequest.getParameter("userEmail");
			List<QuestMasterBean> questList = questService.getQuestMasterList(leaderId);
			for (QuestMasterBean quest: questList) {
				masterDeleted = questService.deleteQuestMaster(quest.getQuestId());
				if (!masterDeleted) {
					SessionErrors.add(actionRequest, "leader-delete-failure", quest.getQuestTitle());
				}				
			}
			if (questList.isEmpty()) {
				LOG.info("No Quests found.");
				masterDeleted = true;
			}
			if (masterDeleted) {
				LeaderBean leaderBean = new LeaderBean();
				leaderBean.setUserid(leaderId);;
				LQLeaderService lqLeaderService = new LQLeaderServiceImpl();
				lqLeaderService.deleteLeader(leaderBean);
				User leader = LQPortalUserServiceUtil.getUser(leaderEmail, themeDisplay.getCompanyId());
 				UserLocalServiceUtil.deleteUser(leader);
 				SessionMessages.add(actionRequest, "leader-delete-success");
 				actionResponse.sendRedirect(LQPortalConstants.LQ_HOME_URL);
			}
		} catch (Exception e) {
			SessionErrors.add(actionRequest, "leader-delete-failure");
			e.printStackTrace();
		}
	}	
}
