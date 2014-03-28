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
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.beans.LeaderBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

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
		try {
			lqServiceLayer.populateLeaderNoLoginPortlet(leaderList, renderRequest);
			
		} catch (LQPortalException le) {
			LQPortalUtil.processException(le, renderRequest);
		}
		
		if(renderRequest.getPortletSession().getAttribute("LQ_SEARCH_STRING") != null) {
			renderRequest.getPortletSession().removeAttribute("LQ_SEARCH_STRING"); 
		}
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		
		LOG.info("Leaving doView() -- Leader No login");
	}
	
	
	public void submitSearchDeails(ActionRequest actionRequest,
			ActionResponse actionResponse) throws PortletException {
		
		LOG.info("Entering into submitSearchDeails");
		UploadPortletRequest uploadRequest = PortalUtil.getUploadPortletRequest(actionRequest);
	
		String searchText  =  uploadRequest.getParameter("txtSearch");
		
		actionRequest.getPortletSession().setAttribute("LQ_SEARCH_STRING", searchText,
				PortletSession.APPLICATION_SCOPE);
		
		
	}
	
}
