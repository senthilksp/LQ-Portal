/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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

import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQLeaderService;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.impl.LQLeaderServiceImpl;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.cti.lq.beans.LeaderBean;
import com.cti.lq.beans.QuestMasterBean;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;
import com.cti.lq.exceptions.LQPortalException;

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
		String role = LQPortalUserServiceUtil.getRoleName(renderRequest);
		
		try {
			lqServiceLayer.populateLeaderNoLoginPortlet(leaderList, renderRequest);
			renderRequest.setAttribute("roleName", role);
			LOG.info("RoleName=========" + role);
		
		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}
		
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		
		LOG.info("Leaving doView() -- Leader No login");
	}
	
}
