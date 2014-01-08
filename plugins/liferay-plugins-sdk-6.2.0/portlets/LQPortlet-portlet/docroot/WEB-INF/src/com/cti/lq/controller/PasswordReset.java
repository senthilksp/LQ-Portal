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
		
		if (portletRequestDispatcher == null) {
		} else {
			portletRequestDispatcher.include(renderRequest, renderResponse);
		}

		LOG.info("Leaving doView()");
	}


}
