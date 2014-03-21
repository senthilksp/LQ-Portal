/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.beans.QuestTransactionBean;
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

		LOG.info("Entering doView()");
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

		LOG.info("Leaving doView()");

	}

	public void submitQuestDetails(ActionRequest actionRequest, ActionResponse actionResponse) throws IOException, PortletException {

		LOG.info(" Entering submitQuestDetails()");

		try {
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
			String action = uploadRequest.getParameter("delId");
			
			if (action.equalsIgnoreCase("DELETE")) {
				doDelete(uploadRequest, actionRequest);
			} else if (action.equalsIgnoreCase("MASTER")) {
				doMasterSave(uploadRequest, actionRequest);
			} else if (action.equalsIgnoreCase("DELETEALL")) {
				doMasterDelete(uploadRequest, actionRequest);
			} else if (action.equalsIgnoreCase("MODIFY")) {
				doUpdate(uploadRequest, actionRequest);
			}
			actionResponse.sendRedirect(LQPortalConstants.LQ_QUEST_DETAILS_URL);

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void doMasterSave(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		LOG.info("Update Method");
		QuestMasterBean questMaster = null;
		Boolean masterSaved = false;
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
					masterSaved = questService.updateQuestMaster(questMaster);
				}

				if (masterSaved) {
					SessionMessages.add(actionRequest, "quest-edited-successfully");
				} else {
					SessionErrors.add(actionRequest, "quest-update-failed");
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doUpdate(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		LOG.info("Update Method");
		String fileName = null;
		LQQuestService questService = new LQQuestServiceImpl();
		QuestTransactionBean transBean = null;
		String path = "http://" + uploadRequest.getServerName() + ":" + uploadRequest.getServerPort() + "/lqfiles/";
		Boolean transSaved = false;
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
				transSaved = questService.updateQuestTransaction(transBean);
			}

			if (transSaved) {
				SessionMessages.add(actionRequest, "quest-edited-successfully");
			} else {
				SessionErrors.add(actionRequest, "quest-update-failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doDelete(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		LOG.info("Delete Method");
		int id1 = Integer.valueOf(uploadRequest.getParameter("id1"));
		LQQuestService questService = new LQQuestServiceImpl();

		try {
			Boolean transSaved = questService.deleteQuestTransaction(id1);
			if (transSaved) {
				SessionMessages.add(actionRequest, "quest-edited-successfully");
			} else {
				SessionErrors.add(actionRequest, "quest-update-failed");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void doMasterDelete(UploadPortletRequest uploadRequest, ActionRequest actionRequest) {
		
		LOG.info("doMasterDelete");
		Boolean masterDeleted = false;
		LQQuestService questService = new LQQuestServiceImpl();

		try {
			if (uploadRequest.getParameter("questId") != null) {
				int questId = Integer.valueOf(uploadRequest.getParameter("questId"));

				masterDeleted = questService.deleteQuestMaster(questId);

				if (masterDeleted) {
					SessionMessages.add(actionRequest, "quest-edited-successfully");
				} else {
					SessionErrors.add(actionRequest, "quest-update-failed");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
