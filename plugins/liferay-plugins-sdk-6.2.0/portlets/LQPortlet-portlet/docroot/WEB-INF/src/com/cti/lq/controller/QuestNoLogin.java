/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil
 * Date Created : 12/31/2013.
 */
public class QuestNoLogin  extends MVCPortlet{

	private String viewJSP;
	final Log LOG = LogFactory.getLog(QuestNoLogin.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		
		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);
		
		List<LeaderBean> leaderList = new ArrayList<LeaderBean>();
		
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();
		String role = LQPortalUserServiceUtil.getRoleName(renderRequest);
		
		try {
			lqServiceLayer.populateLeaderNoLoginPortlet(leaderList, renderRequest);
			renderRequest.setAttribute("roleName", role);
		
		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		
		LOG.info("Leaving doView()");
	}	
	
}
