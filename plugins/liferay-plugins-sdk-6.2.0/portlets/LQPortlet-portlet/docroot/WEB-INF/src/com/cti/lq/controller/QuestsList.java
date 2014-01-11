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
import com.cti.lq.beans.QuestMasterBean;
import com.cti.lq.exceptions.LQPortalException;
import com.cti.lq.service.LQPortletService;
import com.cti.lq.service.impl.LQPortletServiceImpl;
import com.cti.lq.util.LQPortalUserServiceUtil;
import com.cti.lq.util.LQPortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

public class QuestsList extends MVCPortlet {
	
	private String viewJSP;
	final Log LOG = LogFactory.getLog(QuestsList.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}
	
	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {
		LOG.info("Entering doView()--Leader No login");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);
		
		List<QuestMasterBean> questMasterList = new ArrayList<QuestMasterBean>();
		
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();
		String role = LQPortalUserServiceUtil.getRoleName(renderRequest);
		
		try {
			lqServiceLayer.populateQuestListPortlet(renderRequest);
			renderRequest.setAttribute("roleName", role);
			LOG.info("RoleName=========" + role);
		
		} catch (Exception le) {
			LQPortalUtil.processException(le, renderRequest);
		}
		
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		
		LOG.info("Leaving doView() -- Leader No login");
	}
	
}
