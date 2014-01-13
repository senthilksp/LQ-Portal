/**
 * 
 */
package com.cti.lq.controller;

import java.io.File;
import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil Date : 03-JAN-2014
 */
public class AddLeader extends MVCPortlet {

	private String viewJSP;
	final Log LOG = LogFactory.getLog(AddLeader.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);

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

		HttpServletRequest httpRequest = PortalUtil
				.getHttpServletRequest(actionRequest);
		HttpSession session = httpRequest.getSession();
		ThemeDisplay themeDisplay = (ThemeDisplay) httpRequest
				.getAttribute(WebKeys.THEME_DISPLAY);

		UploadPortletRequest uploadRequest = PortalUtil
				.getUploadPortletRequest(actionRequest);

		String path = getPortletContext().getRealPath("/");

		try {

			leaderDetails.setFirstname(uploadRequest.getParameter("firstname"));
			leaderDetails.setLastname(uploadRequest.getParameter("lastname"));
			leaderDetails.setEmailAddress(uploadRequest
					.getParameter("emailaddress"));
			leaderDetails.setFacultyRole(uploadRequest
					.getParameter("facultyrole"));
			leaderDetails.setPrimaryPhone(uploadRequest
					.getParameter("primaryphone"));
			leaderDetails.setWebsite(uploadRequest.getParameter("website"));
			leaderDetails.setBusinessName(uploadRequest
					.getParameter("businessname"));
			leaderDetails.setCountry(uploadRequest.getParameter("country"));
			leaderDetails.setCity(uploadRequest.getParameter("city"));
			System.out.println("Bio Statement"
					+ uploadRequest.getParameter("biostatement"));

			leaderDetails.setBioStatement(uploadRequest
					.getParameter("biostatement"));
			leaderDetails.setUserid(userId);

			// upload photo.

			String imageFileName = uploadRequest.getFileName("image_fileName");
			String fileLocation = LQPortalConstants.LQ_FILE_LOCATION;
			fileLocation = fileLocation + "/" + imageFileName;

			if (!(imageFileName == null || "".equals(imageFileName))) {
				LQPortalUtil.uploadFile(path, uploadRequest, "image_fileName");

				leaderDetails.setPhotoURL(fileLocation);

				LQLeaderService lqServiceLayer = new LQLeaderServiceImpl();
				Boolean isAdded = lqServiceLayer
						.addLeaderDetails(leaderDetails);

				LOG.info("insertion done");

				if (isAdded) {
					SessionMessages.add(actionRequest,
							"leader-added-successfully");
				} else {
					SessionErrors.add(actionRequest, "leader-add-failed");
				}

			}
			// upload Ends.
			User u = LQPortalUserServiceUtil.getUser(
					leaderDetails.getEmailAddress(),
					themeDisplay.getCompanyId());
			if (u != null) {
				session.setAttribute("validUser", true);
				LQPortalUtil.sendPasswordResetEmail(actionRequest,
						leaderDetails.getEmailAddress(), u.getFirstName());
				LOG.info("Mail has sent");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
