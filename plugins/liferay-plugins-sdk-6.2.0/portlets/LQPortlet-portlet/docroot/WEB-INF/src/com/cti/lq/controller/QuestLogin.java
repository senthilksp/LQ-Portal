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
import com.cti.lq.beans.QuestTransactionBean;
import com.cti.lq.beans.QuestViewBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.LQQuestService;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.service.impl.LQQuestServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil
 *
 */
public class QuestLogin extends MVCPortlet {

	private String viewJSP;
	final Log LOG = LogFactory.getLog(LeaderLogin.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(viewJSP);

		List<QuestViewBean> questList = new ArrayList<QuestViewBean>();
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();

		try {
			lqServiceLayer.populateQuestViewPortlet(questList, renderRequest);
		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");
	}
	
	
	public void submitQuestLoginDetails(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		
		LOG.info(" Entering submitLeaderDetails()");
		
		List<QuestTransactionBean> qTransList = new ArrayList<QuestTransactionBean>();
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String path = "http://" + uploadRequest.getServerName() + ":" + uploadRequest.getServerPort() + "/lqfiles/";
		
		try {
			String imageFileName = uploadRequest.getFileName("image_fileName");
			String audioFileName = uploadRequest.getFileName("audio_fileName");
			String videoFileName = uploadRequest.getFileName("video_fileName");
			
			int questId = Integer.valueOf(uploadRequest.getParameter("quest_id"));
			
			if (! (imageFileName == null || "".equals(imageFileName))) {
				LQPortalUtil.uploadFile(uploadRequest,"image_fileName");
				QuestTransactionBean transBean = new QuestTransactionBean();
				transBean.setQuestType(LQPortalConstants.IMAGE_TYPE);
				transBean.setQuestLocation(path + "/" + imageFileName);
				qTransList.add(transBean);
					
			}
			
			if (! (audioFileName == null || "".equals(audioFileName))) {
				LQPortalUtil.uploadFile(uploadRequest,"audio_fileName");
				QuestTransactionBean transBean = new QuestTransactionBean();
				transBean.setQuestType(LQPortalConstants.AUDIO_TYPE);
				transBean.setQuestLocation(path +"/" +  audioFileName);
				qTransList.add(transBean);
					
			}
			
			if (! (videoFileName == null || "".equals(videoFileName))) {
				LQPortalUtil.uploadFile(uploadRequest,"video_fileName");
				QuestTransactionBean transBean = new QuestTransactionBean();
				transBean.setQuestType(LQPortalConstants.VIDEO_TYPE);
				transBean.setQuestLocation(path + "/"+ videoFileName);
				qTransList.add(transBean);
			}
			
			int userId = LQPortalUserServiceUtil.getUserId(actionRequest);
			
			LQQuestService questService = new LQQuestServiceImpl();
			Boolean isSaved = questService.saveQuestTransactions(qTransList,userId,questId);
			if (isSaved) {
				SessionMessages.add(actionRequest, "quest-added-successfully");
			} else {
				SessionErrors.add(actionRequest, "quest-add-failed");
			}
			actionResponse.sendRedirect(LQPortalConstants.LQ_QUEST_DETAILS_URL);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
}
