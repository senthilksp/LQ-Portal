/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.cti.lq.beans.QuestCommentBean;
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
		
		LOG.info(" Entering submitQuestLoginDetails()");
		
		LQQuestService questService = new LQQuestServiceImpl();
		List<QuestTransactionBean> qTransList = new ArrayList<QuestTransactionBean>();
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String path = "http://" + uploadRequest.getServerName() + ":" + uploadRequest.getServerPort() + "/lqfiles/";
		
		try {
			String imageFileName = uploadRequest.getFileName("image_fileName");
			String audioFileName = uploadRequest.getFileName("audio_fileName");
			String videoFileName = uploadRequest.getFileName("video_fileName");
			
			int userId = LQPortalUserServiceUtil.getUserId(actionRequest);
			int questId = Integer.valueOf(uploadRequest.getParameter("quest_id"));

			String redirectUrl = LQPortalConstants.LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD + "?userId=" + userId + "&questId=" + questId;
			
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
			
			Boolean isSaved = questService.saveQuestTransactions(qTransList,userId,questId);
			if (isSaved) {
				SessionMessages.add(actionRequest, "quest-added-successfully");
			} else {
				SessionErrors.add(actionRequest, "quest-add-failed");
			}
			actionResponse.sendRedirect(PortalUtil.escapeRedirect(redirectUrl));
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public void submitQuestComment(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		
		LOG.info("Entering submitQuestComment()");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		int userId = LQPortalUserServiceUtil.getUserId(actionRequest);
		
		LQQuestService questService = new LQQuestServiceImpl();
		String questTransId = uploadRequest.getParameter("questTransId");
		String addedBy = uploadRequest.getParameter("addedBy");
		String comment = uploadRequest.getParameter("comment");
		String questId = uploadRequest.getParameter("questId");
		String redirectUrl = LQPortalConstants.LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD + "?userId=" + userId + "&questId=" + questId;

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = new Date();
		String addedOn = dateFormat.format(date);
		QuestCommentBean questCommentBean = new QuestCommentBean();
		questCommentBean.setAddedBy(addedBy);
		questCommentBean.setAddedOn(addedOn);
		questCommentBean.setComment(comment);
		questCommentBean.setQuestTransId(Integer.parseInt(questTransId));
		
		Boolean isSaved = false;
		try {
			isSaved = questService.saveQuestComment(questCommentBean);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if (isSaved) {
			SessionMessages.add(actionRequest, "quest-comment-added-successfully");
		} else {
			SessionErrors.add(actionRequest, "quest-comment-add-failed");
		}
		LOG.info("Leaving submitQuestComment()");
		actionResponse.sendRedirect(PortalUtil.escapeRedirect(redirectUrl));
	}
}
