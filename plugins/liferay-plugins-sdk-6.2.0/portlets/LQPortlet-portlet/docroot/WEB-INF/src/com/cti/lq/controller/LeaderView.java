/**
 * 
 */
package com.cti.lq.controller;

import java.io.IOException;

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
import com.cti.lq.util.LQPortalUtil;
import com.liferay.util.bridges.mvc.MVCPortlet;

/**
 * @author senthil
 *
 */
public class LeaderView extends MVCPortlet{

	private String viewJSP;
	final Log LOG = LogFactory.getLog(LeaderLogin.class);

	public void init() throws PortletException {
		viewJSP = getInitParameter("view-template");
	}

	public void doView(RenderRequest renderRequest,
			RenderResponse renderResponse) throws IOException, PortletException {

		LOG.info("Entering doView()");
		PortletRequestDispatcher portletRequestDispatcher = getPortletContext()
				.getRequestDispatcher(viewJSP);

		LeaderBean leaderBean = new LeaderBean();
		LQPortletService lqServiceLayer = new LQPortletServiceImpl();

		try {
			lqServiceLayer
					.populateLeaderViewPortlet(leaderBean, renderRequest);

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
