/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
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
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestCommentBean;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
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
public class AddQuest extends MVCPortlet {

	private String viewJSP;
	final Log LOG = LogFactory.getLog(AddQuest.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest,	RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering AddQuest->doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(viewJSP);

		LeaderBean leaderBean = new LeaderBean();
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();
		String role = LQPortalUserServiceUtil.getRoleName(renderRequest);

		try {
			lqServiceLayer.populateLeaderLoginPortlet(leaderBean, renderRequest);
			renderRequest.setAttribute("roleName", role);

		} catch (LQPortalException le) {
			le.printStackTrace();
		}

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");
	}

	public void submitQuestDetails(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		
		LOG.info(" Entering AddQuest->submitQuestDetails()");
		
		QuestMasterBean questmaster = new QuestMasterBean();
		List<QuestTransactionBean> qTransList = new ArrayList<QuestTransactionBean>();
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		String path = "http://" + uploadRequest.getServerName() + ":" + uploadRequest.getServerPort() + "/lqfiles/";
		
		List<QuestCommentBean> questCommentBeanList = null;
		
		String addedBy = LQPortalUserServiceUtil.getUser(LQPortalUserServiceUtil.getUserId(actionRequest)).getFullName();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm");
		Date date = new Date();
		String addedOn = dateFormat.format(date);
		
		QuestCommentBean questCommentBean = new QuestCommentBean();
		questCommentBean.setAddedBy(addedBy);
		questCommentBean.setAddedOn(addedOn);
		
		try {
			String imageFileName = uploadRequest.getFileName("image_fileName");
			String imageComment = uploadRequest.getParameter("image_comment");
			String audioFileName = uploadRequest.getFileName("audio_fileName");
			String audioComment = uploadRequest.getParameter("audio_comment");
			String videoFileName = uploadRequest.getFileName("video_fileName");
			String videoComment = uploadRequest.getParameter("video_comment");
			
			if (! (imageFileName == null || "".equals(imageFileName))) {
				LQPortalUtil.uploadFile(uploadRequest, "image_fileName");
				QuestTransactionBean transBean = new QuestTransactionBean();
				transBean.setQuestType(LQPortalConstants.IMAGE_TYPE);
				transBean.setQuestLocation(path + "/" + imageFileName);
				
				if (imageComment != null && !imageComment.isEmpty()) {
					questCommentBeanList = new ArrayList<QuestCommentBean>();
					questCommentBean.setComment(imageComment);
					questCommentBeanList.add(questCommentBean);
					transBean.setQuestCommentBean(questCommentBeanList);
				}
				qTransList.add(transBean);
				
			}
			if (! (audioFileName == null || "".equals(audioFileName))) {
				LQPortalUtil.uploadFile(uploadRequest, "audio_fileName");
				QuestTransactionBean transBean = new QuestTransactionBean();
				transBean.setQuestType(LQPortalConstants.AUDIO_TYPE);
				transBean.setQuestLocation(path + "/" +  audioFileName);
				if (audioComment != null && !audioComment.isEmpty()) {
					questCommentBeanList = new ArrayList<QuestCommentBean>();
					questCommentBean.setComment(audioComment);
					questCommentBeanList.add(questCommentBean);
					transBean.setQuestCommentBean(questCommentBeanList);
				}
				qTransList.add(transBean);
				
			}
			if (! (videoFileName == null || "".equals(videoFileName))) {
				LQPortalUtil.uploadFile(uploadRequest, "video_fileName");
				QuestTransactionBean transBean = new QuestTransactionBean();
				transBean.setQuestType(LQPortalConstants.VIDEO_TYPE);
				transBean.setQuestLocation(path + "/"+ videoFileName);
				if (videoComment != null && !videoComment.isEmpty()) {
					questCommentBeanList = new ArrayList<QuestCommentBean>();
					questCommentBean.setComment(videoComment);
					questCommentBeanList.add(questCommentBean);
					transBean.setQuestCommentBean(questCommentBeanList);
				}
				qTransList.add(transBean);
			}

			questmaster.setUserId(LQPortalUserServiceUtil.getUserId(actionRequest));
			if (uploadRequest.getParameter("accessMode") != null) {
				if (uploadRequest.getParameter("accessMode").toUpperCase().equals("PUBLIC")) {
					questmaster.setAccessMode(true);					
				} else if (uploadRequest.getParameter("accessMode").toUpperCase().equals("PRIVATE")) {
					questmaster.setAccessMode(false);
				}
			}

			if (uploadRequest.getParameter("questDefinition") != null) {
				questmaster.setQuestDefinition(uploadRequest.getParameter("questDefinition"));
			}
			
			if (uploadRequest.getParameter("questName") != null) {
				questmaster.setQuestTitle(uploadRequest.getParameter("questName"));
			}
			
			LQQuestService questService = new LQQuestServiceImpl();
			Boolean isSaved = questService.saveQuestDetails(questmaster,qTransList);
			LOG.info("isSaved = " + isSaved);
			if(isSaved) {
				SessionMessages.add(actionRequest, "quest-added-successfully");
			} else {
				SessionErrors.add(actionRequest, "quest-add-failed");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}
}