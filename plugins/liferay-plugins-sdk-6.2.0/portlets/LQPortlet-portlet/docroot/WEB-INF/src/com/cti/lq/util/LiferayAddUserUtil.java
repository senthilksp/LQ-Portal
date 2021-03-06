package com.cti.lq.util;

import java.util.Calendar;
import java.util.Locale;

import javax.portlet.ActionRequest;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.beans.LeaderBean;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;

public class LiferayAddUserUtil {

	public static long insertLiferayUser(ActionRequest actionRequest, LeaderBean leader) throws PortalException, SystemException {
		long userId = 0;
		String firstName = leader.getFirstname();
		String lastName = leader.getLastname();
		String emailAddress = leader.getEmailAddress();

		ThemeDisplay themeDisplay = (ThemeDisplay) actionRequest.getAttribute(WebKeys.THEME_DISPLAY);
		long companyId = themeDisplay.getCompanyId();

		long creatorUserId = 0;
		boolean autoPassword = false;
		boolean autoScreenName = false;
		Locale locale = Locale.US;
		String password = LQPortalConstants.ADD_LR_USER_PASSWD;
		String userScreenName = firstName + "-" + lastName;

		long facebookId = 99;
		long[] ids;
		ids = new long[0];

		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		boolean sendEmail = false;

		//Getting Leader Role.
		long roleIds[] = new long[1];
		roleIds[0] = leader.getRoleid();
		
		User user = UserLocalServiceUtil.addUser(creatorUserId, companyId, autoPassword, password, password, autoScreenName, userScreenName, emailAddress, facebookId, "", locale, firstName, "", lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay, birthdayYear,	LQPortalConstants.LQ_LEADER_ROLE, ids, ids, roleIds, null, sendEmail, null);

		userId = user.getUserId();
		return userId;
	}
}
