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
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.PasswordResetBean;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil
 * Date created. 02-JAN-2014.
 */
public class PasswordReset extends MVCPortlet {
	
	private String viewJSP;
	final Log LOG = LogFactory.getLog(PasswordReset.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		LOG.info("Entering doView()");

		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);
		
		HttpServletRequest httpRequest = PortalUtil
				.getOriginalServletRequest(PortalUtil
						.getHttpServletRequest(renderRequest));
		
		renderRequest.setAttribute("email", httpRequest.getParameter("p1"));
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");
	}
	
	public void submitPasswordReset(ActionRequest actionRequest,
			ActionResponse actionResponse) throws IOException, PortletException {
		
		LOG.info("Inside submit password method");
		HttpServletRequest httpRequest = PortalUtil.getHttpServletRequest(actionRequest);
		ThemeDisplay themeDisplay = (ThemeDisplay) httpRequest.getAttribute(WebKeys.THEME_DISPLAY);
		
		try {
			UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
			PasswordResetBean resetBean = new PasswordResetBean();
			
			resetBean.setNewPassword(uploadRequest.getParameter("newpass"));
			resetBean.setcNewPassword(uploadRequest.getParameter("cnewpass"));
			resetBean.setEmailAddress(uploadRequest.getParameter("email"));
			
			LQLeaderService leaderService = new LQLeaderServiceImpl();
			Boolean isReset = leaderService.resetPassword(resetBean);
			
			User u = LQPortalUserServiceUtil.getUser(uploadRequest.getParameter("email"),themeDisplay.getCompanyId());
			resetBean.setUserId(u.getUserId());
			
			if(isReset) {
				LQPortalUtil.sendPasswordResetSuccessEmail(actionRequest, uploadRequest.getParameter("email"),u.getFirstName());
				LOG.info("Mail has sent");
			}
			
			actionResponse.sendRedirect(LQPortalConstants.LQ_HOME_LOGIN);
		
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}


}
