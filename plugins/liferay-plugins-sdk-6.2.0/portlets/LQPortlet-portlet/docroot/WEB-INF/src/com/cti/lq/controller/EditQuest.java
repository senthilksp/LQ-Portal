/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

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
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
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
public class EditQuest extends MVCPortlet {

	private String viewJSP;
	final Log LOG = LogFactory.getLog(EditQuest.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest,	RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering doView");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext().getRequestDispatcher(viewJSP);

		LQPortletService lqServiceLayer = new LQPortletServiceImpl();

		try {
			lqServiceLayer.populateQuestEditPortlet(renderRequest);

		} catch (Exception le) {
			le.printStackTrace();
		}

		if (portletRequestDispatcher != null) {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView");

	}

	public void submitQuestDetails(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {

		LOG.info(" Entering submitQuestDetails");
		Boolean saved = false;
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
		int userId = LQPortalUserServiceUtil.getUserId(actionRequest);
		int questId = Integer.valueOf(uploadRequest.getParameter("questId"));
		String redirectUrl = LQPortalConstants.LQ_QUEST_DETAIL_EDIT_PAGE + "?userId=" + userId + "&questId=" + questId;
		
		try {
			String action = uploadRequest.getParameter("delId");
			
			if (action.equalsIgnoreCase("DELETE")) {
				saved = doDelete(uploadRequest, actionRequest);
				if (saved) {
					SessionMessages.add(actionRequest, "quest-deleted-successfully");
				} else {
					SessionErrors.add(actionRequest, "quest-delete-failed");
				}
			} else if (action.equalsIgnoreCase("MASTER")) {
				saved = doMasterSave(uploadRequest, actionRequest);
				if (saved) {
					SessionMessages.add(actionRequest, "quest-edited-successfully");
				} else {
					SessionErrors.add(actionRequest, "quest-update-failed");
				}
			} else if (action.equalsIgnoreCase("DELETEALL")) {
				saved = doMasterDelete(uploadRequest, actionRequest);
				if (saved) {
					SessionMessages.add(actionRequest, "quest-deleted-successfully");
				} else {
					SessionErrors.add(actionRequest, "quest-delete-failed");
				}
			} else if (action.equalsIgnoreCase("MODIFY")) {
				saved = doUpdate(uploadRequest, actionRequest);
				if (saved) {
					SessionMessages.add(actionRequest, "quest-edited-successfully");
				} else {
					SessionErrors.add(actionRequest, "quest-update-failed");
				}
			}
			actionResponse.sendRedirect(PortalUtil.escapeRedirect(redirectUrl));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private boolean doMasterSave(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		LOG.info("Entering into doMasterSave");
		QuestMasterBean questMaster = null;
		LQQuestService questService = new LQQuestServiceImpl();

		try {
			if (uploadRequest.getParameter("questId") != null
					&& uploadRequest.getParameter("questDefinition") != null
					&& uploadRequest.getParameter("questName") != null) {

				questMaster = new QuestMasterBean();
				questMaster.setQuestId(Integer.valueOf(uploadRequest.getParameter("questId")));
				questMaster.setQuestDefinition(uploadRequest.getParameter("questDefinition"));
				questMaster.setQuestTitle(uploadRequest.getParameter("questName"));
				questMaster.setAccessMode(Boolean.valueOf(uploadRequest.getParameter("accessMode")));

				if (questMaster != null) {
					return questService.updateQuestMaster(questMaster);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	private boolean doUpdate(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		LOG.info("Entering into doUpdate");
		String fileName = null;
		LQQuestService questService = new LQQuestServiceImpl();
		QuestTransactionBean transBean = null;
		String path = "http://" + uploadRequest.getServerName() + ":" + uploadRequest.getServerPort() + "/lqfiles/";
		Integer id = 0;

		try {
			if (uploadRequest.getParameter("id1") != null) {
				id = Integer.valueOf(uploadRequest.getParameter("id1"));
				fileName = uploadRequest.getFileName(String.valueOf(id));
			}
			if (!(fileName == null || "".equals(fileName))) {
				LQPortalUtil.uploadFile(uploadRequest, String.valueOf(id));
				transBean = new QuestTransactionBean();
				transBean.setId(id);
				transBean.setQuestLocation(path + "/" + fileName);
			}

			if (transBean != null) {
				return questService.updateQuestTransaction(transBean);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean doDelete(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		LOG.info("Entering into doDelete");
		int id1 = Integer.valueOf(uploadRequest.getParameter("id1"));
		LOG.info("id1 = " + id1);
		LQQuestService questService = new LQQuestServiceImpl();

		try {
			return questService.deleteQuestTransaction(id1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private boolean doMasterDelete(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		
		LOG.info("Entering into doMasterDelete");
		LQQuestService questService = new LQQuestServiceImpl();

		try {
			if (uploadRequest.getParameter("questId") != null) {
				int questId = Integer.valueOf(uploadRequest.getParameter("questId"));
				return questService.deleteQuestMaster(questId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void submitQuestComment(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {
		
		LOG.info("Entering into submitQuestComment");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);

		int userId = LQPortalUserServiceUtil.getUserId(actionRequest);
		
		LQQuestService questService = new LQQuestServiceImpl();
		String questTransId = uploadRequest.getParameter("questTransId");
		String addedBy = uploadRequest.getParameter("addedBy");
		String comment = uploadRequest.getParameter("comment");
		String questId = uploadRequest.getParameter("questId");
		String redirectUrl = LQPortalConstants.LQ_QUEST_DETAIL_EDIT_PAGE + "?userId=" + userId + "&questId=" + questId;

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
		LOG.info("Leaving submitQuestComment");
		actionResponse.sendRedirect(PortalUtil.escapeRedirect(redirectUrl));
	}
}
