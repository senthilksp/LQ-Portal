package com.cti.lq.util;

import java.util.List;

import javax.portlet.PortletRequest;

import com.cti.lq.Constants.LQPortalConstants;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;

public class LQPortalUserServiceUtil {

	public static int getUserId(PortletRequest req) {
		if (req.getUserPrincipal() != null) {
			return new Integer(req.getUserPrincipal().getName()).intValue();
		} else {
			return 0;
		}
	}

	public static User getUser(long userId) {
		User user = null;
		try {
			user = UserLocalServiceUtil.getUser(userId);
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static User getUser(String emailAddress, long companyId) {
		User user = null;
		try {
			user = UserLocalServiceUtil.getUserByEmailAddress(companyId,
					emailAddress);
		} catch (PortalException e) {
			e.printStackTrace();
		} catch (SystemException e) {
			e.printStackTrace();
		}
		return user;

	}

	public static String getRoleName(PortletRequest req) {
		String roleName = null;
		int userId = getUserId(req);
		if (userId != 0) {
			try {
				User user = UserLocalServiceUtil.getUser(userId);
				List<Role> roleList = user.getRoles();
				for (Role role : roleList) {
					if (role.getName().equalsIgnoreCase(
							LQPortalConstants.LQ_LEADER_ROLE)) {
						roleName = LQPortalConstants.LQ_LEADER_ROLE;
					} else if (role.getName().equalsIgnoreCase(
							LQPortalConstants.LQ_LEADER_ADMIN)) {
						roleName = LQPortalConstants.LQ_LEADER_ADMIN;
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return roleName;
	}

}
