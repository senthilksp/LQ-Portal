/**
 * 
 */
package com.cti.lq.controller;

import java.io.File;
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
import com.cti.lq.exceptions.LQPortalException;

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

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);

		LQPortletService lqServiceLayer = new LQPortletServiceImpl();

		try {
			lqServiceLayer.populateQuestEditPortlet(renderRequest);

		} catch (Exception le) {
			LQPortalUtil.processException(le, renderRequest);
		}

		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");

	}

	public void submitQuestDetails(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {

		LOG.info(" Entering submitQuestDetails()");

		QuestMasterBean questMaster = null;
		QuestTransactionBean transBean = null;
		String fileLocation = File.separator
				+ LQPortalConstants.portletLocatation + File.separator
				+ LQPortalConstants.fileLocation;
		LQQuestService questService = new LQQuestServiceImpl();
		Boolean transSaved = false;
		Boolean masterSaved = true;

		try {
			UploadPortletRequest uploadRequest = PortalUtil
					.getUploadPortletRequest(actionRequest);
			String path = getPortletContext().getRealPath("/");
			String fileName = null;
			if (uploadRequest.getParameter("questId") != null
					&& uploadRequest.getParameter("questDefinition") != null
					&& uploadRequest.getParameter("questName") != null) {
			
				questMaster = new QuestMasterBean();
				questMaster.setQuestId(Integer.valueOf(uploadRequest
						.getParameter("questId")));
				questMaster.setQuestDefinition(uploadRequest
						.getParameter("questDefinition"));
				questMaster.setQuestTitle(uploadRequest
						.getParameter("questName"));
			}
			
			Integer id = 0; 

			if (uploadRequest.getParameter("id1")!=null) {
				id = Integer.valueOf(uploadRequest.getParameter("id1"));
				fileName =  uploadRequest.getFileName(String.valueOf(id));
			}
			;
			if (!(fileName == null || "".equals(fileName))) {
				LQPortalUtil.uploadFile(path, uploadRequest, String.valueOf(id));
				transBean = new QuestTransactionBean();
				transBean.setId(id);
				transBean.setQuestLocation(fileLocation + File.separator
						+ fileName);

			}
			
			if (transBean != null) {
				transSaved = questService.updateQuestTransaction(transBean);
			}
			if (questMaster != null) {
				masterSaved = questService.updateQuestMaster(questMaster);
			}

			if (transSaved || masterSaved) {
				SessionMessages
						.add(actionRequest, "quest-edited-successfully");
			} else {
				SessionErrors.add(actionRequest, "quest-update-failed");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}