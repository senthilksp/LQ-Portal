/**
 * 
 */
package com.cti.lq.util;

import javax.portlet.PortletRequest;

import com.cti.lq.exceptions.LQPortalException;
import com.liferay.portal.kernel.servlet.SessionErrors;

/**
 * @author senthil
 * Date Created : 20/12/2013.
 * Function : This class is for Utility functions used in LQPortal.
 */
public class LQPortalUtil {

	public static void processException(Throwable ex, PortletRequest request) {
			SessionErrors.add(request, LQPortalException.class.getName());
	}
	
	
	public static Throwable getRootException(Throwable exception) {
		if(exception.getCause() != null) {
			return getRootException(exception.getCause());
		}
		return exception;
	}

}
