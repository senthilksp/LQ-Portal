/**
 * 
 */
package com.cti.lq.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.cti.lq.Constants.LQPortalConstants;
import com.cti.lq.exceptions.LQPortalException;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

/**
 * @author senthil Date Created : 20/12/2013. Function : This class is for
 *         Utility functions used in LQPortal.
 */
public class LQPortalUtil {
	static final Log LOG = LogFactory.getLog(LQPortalUtil.class);

	public static void processException(Throwable ex, PortletRequest request) {
		SessionErrors.add(request, LQPortalException.class.getName());
	}

	public static Throwable getRootException(Throwable exception) {
		if (exception.getCause() != null) {
			return getRootException(exception.getCause());
		}
		return exception;
	}

	public static ResourceBundle getResourceBundle() {
		return ResourceBundle.getBundle("content/Language");
	}

	public static ResourceBundle getResourceBundle(HttpServletRequest request) {
		return ResourceBundle.getBundle("content/Language");
	}

	public static void sendPasswordResetEmail(ActionRequest request,
			String emailAddress, String firstName) throws LQPortalException {
		try {
			LOG.info("Sending password reset email to: " + emailAddress);
			HttpServletRequest httpRequest = PortalUtil
					.getHttpServletRequest(request);

			ResourceBundle rb = LQPortalUtil.getResourceBundle(httpRequest);
			String subject = rb.getString("forget-password-email-subject");

			StringBuffer passwordResetURL = constructPasswordResetURL(
					httpRequest, emailAddress);
			LOG.info("PasswordReset URL: " + passwordResetURL);

			String msgBody = constructEmailMessage(rb, firstName,
					passwordResetURL.toString());

			sendEmail(subject, msgBody, emailAddress, null);

		} catch (Exception e) {
			e.printStackTrace();
			// throw new LQPortalException(e.getLocalizedMessage());
		}

	}

	private static StringBuffer constructPasswordResetURL(
			HttpServletRequest httpRequest, String emailAddress) {
		String mailSenttime = String.valueOf(new Date().getTime());
		StringBuffer passwordResetURL = null;

		ThemeDisplay themeDisplay = (ThemeDisplay) httpRequest
				.getAttribute(WebKeys.THEME_DISPLAY);
		String completeURL;
		try {
			completeURL = PortalUtil.getLayoutFullURL(themeDisplay);
			String[] elements = completeURL.split("/");
			completeURL = elements[0] + "//" + elements[1] + elements[2];

			passwordResetURL = new StringBuffer(completeURL
					+ "/password-reset?p1=");
			passwordResetURL = passwordResetURL.append(emailAddress);
			passwordResetURL.append("&p2=");
			passwordResetURL.append(mailSenttime);

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return passwordResetURL;
	}

	private static String constructEmailMessage(ResourceBundle rb,
			String firstName, String URL) {
		StringBuffer msgbody = null;
		try {

			String[] textParagraphs = new String[5];
			textParagraphs[0] = rb.getString("forget-password-email-hi")
					.concat(" ").concat(firstName);
			textParagraphs[1] = rb.getString("forget-password-email-body-msg1");
			textParagraphs[2] = rb.getString("forget-password-email-body-msg2");
			textParagraphs[3] = rb.getString("forget-password-email-body-msg3");
			textParagraphs[4] = rb.getString("forget-password-email-signature");

			msgbody = new StringBuffer(textParagraphs[0] + "<br>" + "<br>");
			msgbody.append(textParagraphs[1]);
			msgbody.append(textParagraphs[2]);
			msgbody.append(textParagraphs[3]);
			msgbody.append(textParagraphs[4] + "<br>" + "<br>");
			msgbody.append(URL);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgbody.toString();
	}

	private static String constructPasswordSuccessMail(ResourceBundle rb,
			String firstName, String URL) {

		StringBuffer msgbody = null;
		try {

			String[] textParagraphs = new String[5];
			textParagraphs[0] = rb.getString("password-reset-email-hi")
					.concat(" ").concat(firstName);
			textParagraphs[1] = rb.getString("password-reset-email-msg1");
			textParagraphs[2] = rb.getString("password-reset-email-msg2");
			textParagraphs[3] = rb.getString("password-reset-email-msg3");

			msgbody = new StringBuffer(textParagraphs[0] + "<br>" + "<br>");
			msgbody.append(textParagraphs[1]);
			msgbody.append(textParagraphs[2] + "<br>" + "<br>");
			msgbody.append(textParagraphs[3] + "<br>" + "<br>");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return msgbody.toString();
	}

	public static void sendEmail(String msgSubject, String msgBody,
			String emailAddress, String senderEmailAddress) {
		LOG.debug("Entering sendEmail()");

		InternetAddress from;
		InternetAddress to;
		try {
			from = new InternetAddress(emailAddress);
			to = new InternetAddress(emailAddress);

			MailMessage message = new MailMessage(from, to, msgSubject,
					msgBody, true);
			MailServiceUtil.sendEmail(message);
		} catch (AddressException e) {
			LOG.error("Invalid email address");
			e.printStackTrace();
		}

		LOG.debug("Leaving sendEmail()");
	}

	public static Boolean uploadFile(String path,
			UploadPortletRequest uploadRequest, String fileName) {
		Boolean isFileUploaded = false;
		LOG.info("Entering into uploadFile()");

		try {
			path = path + LQPortalConstants.fileLocation;
			LOG.info("path================" + path);
			LOG.info("Size: " + uploadRequest.getSize(fileName));

			if (uploadRequest.getSize(fileName) == 0) {
				return isFileUploaded;
			} else {
				String sourceFileName = uploadRequest.getFileName(fileName);
				File file = uploadRequest.getFile(fileName);

				LOG.info("Name of the file:"
						+ uploadRequest.getFileName(fileName));
				File newFile = null;
				newFile = new File(path + "/" + sourceFileName);
				LOG.info("New file name: " + newFile.getName());
				LOG.info("New file path: " + newFile.getPath());

				InputStream in = new BufferedInputStream(
						uploadRequest.getFileAsStream(fileName));
				FileInputStream fis = new FileInputStream(file);
				FileOutputStream fos = new FileOutputStream(newFile);

				byte[] bytes_ = FileUtil.getBytes(in);
				int i = fis.read(bytes_);

				while (i != -1) {
					fos.write(bytes_, 0, i);
					i = fis.read(bytes_);
				}
				fis.close();
				fos.close();
				Float size = (float) newFile.length();
				System.out.println("file size bytes:" + size);
				System.out.println("file size Mb:" + size / 1048576);

				LOG.info("File created: " + newFile.getName());
				isFileUploaded = true;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return isFileUploaded;
	}

	public static Boolean fileMakerLeaderCheck(String email) {
		LOG.info("Authentication success.");
		Boolean isLeader = false;
		URL url;
		email = "ping@me.com";

		try {
			// get URL content //1 Leader 0-noleader
			// url = new
			// URL("http://crm.thecoaches.com/fmi-test/webcomp2_newFM/abletogetrecord.php?postkey=fjgh15t&em=ping@me.com");
			url = new URL(
					"http://crm.thecoaches.com/fmi-test/webcomp2_newFM/authenticate_leader.php?postkey=fjgh15t&em=ping@me.com");
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String inputLine;

			while ((inputLine = br.readLine()) != null) {
				LOG.info("inputLine=====" + inputLine);
				if (inputLine.equalsIgnoreCase("ok")) {
					isLeader = true;
					LOG.info("Authentication success.");
					break;
				}
			}

			br.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return isLeader;
	}

	public static void sendPasswordResetSuccessEmail(
			ActionRequest actionRequest, String emailAddress, String firstName) {

		LOG.info("Sending password reset email to: " + emailAddress);
		HttpServletRequest httpRequest = PortalUtil
				.getHttpServletRequest(actionRequest);

		ResourceBundle rb = LQPortalUtil.getResourceBundle(httpRequest);
		String subject = rb.getString("password-reset-email-subject");

		String URL = LQPortalConstants.LQ_PORTAL_LOGIN_URL;
		String msgBody = constructPasswordSuccessMail(rb, firstName, URL);

		sendEmail(subject, msgBody, emailAddress, null);

	}

	public static String getCurrentURL(HttpServletRequest request) {
		return PortalUtil.getCurrentURL(request);
	}

	public static void uploadFilesForTesting(String toLocation, String fromLocation,String fileName) {
		InputStream inStream = null;
		OutputStream outStream = null;
		try {

			String systemPath = System.getenv("LQ_HOME");

			File fromFile = new File(systemPath + "//" + fileName);
			File toFile   = new File(systemPath + "//lqfiles//" + fileName);

			inStream = new FileInputStream(fromFile);
			outStream = new FileOutputStream(toFile);

			byte[] buffer = new byte[1024];

			int length;
			// copy the file content in bytes
			while ((length = inStream.read(buffer)) > 0) {
				outStream.write(buffer, 0, length);
			}

			inStream.close();
			outStream.close();
			System.out.println("File is copied successful!");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
