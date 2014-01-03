<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil"%>
<%@ page import="com.cti.lq.Constants.LQPortalConstants"%>

<portlet:defineObjects />
<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />

<script type="text/javascript">
$(document).ready(function () {
	
});

function doSubmit() {
	document.LeaderDetailsForm.submit();
}

</script>

<div class="contentWrapper_lq">
	<h2><%=rb.getString("leader-view-portlet-heading")%></h2>
	<portlet:actionURL name="submitLeaderDetails"
		var="submitLeaderDetailsURL" />
	<aui:form action="<%=submitLeaderDetailsURL.toString()%>" method="post"
		name="LeaderDetailsForm" id="LeaderDetailsForm">
		<table>
			<tr>
				<td><img src="${leaderBean.photoURL}" alt=""></img></td>
				<td><aui:input
						label='<%=rb.getString("leader-login-portlet-photoURL")%>'
						name="photourl" min="2" max="60" style="width:300px"
						id="firstname" type="text" required="true"
						value="${leaderBean.photoURL}">
					</aui:input> <input id="image-file" type="file" /></td>
			</tr>
			<tr>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-firstname")%>'
						name="firstname" min="2" max="60" id="firstname" type="text"
						required="true" value="${leaderBean.firstname}">
					</aui:input></td>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-lastname")%>'
						name="lastname" min="2" required="true" max="60" id="lastname"
						type="text" value="${leaderBean.lastname}">
					</aui:input></td>
			</tr>

			<tr>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-facultyrole")%>'
						name="facultyrole" required="true" id="facultyrole" type="text"
						value="${leaderBean.facultyRole}">
					</aui:input></td>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-primaryphone")%>'
						name="primaryphone" required="true" min="10" max="11"
						id="primaryphone" type="text" value="${leaderBean.primaryPhone}">
					</aui:input></td>
			</tr>
			<tr>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-businessname")%>'
						name="businessname" required="true" id="businessname" type="text"
						value="${leaderBean.businessName}">
					</aui:input></td>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-emailaddress")%>'
						name="emailaddress" id="emailaddress" type="text"
						value="${leaderBean.emailAddress}" required="true">
						<aui:validator name="email" />
					</aui:input></td>
			</tr>
			<tr>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-city")%>' name="city"
						id="city" type="text" required="true" value="${leaderBean.city}">
					</aui:input></td>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-country")%>'
						name="country" id="country" required="true" type="text"
						value="${leaderBean.country}">
					</aui:input></td>

			</tr>
			<tr>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-website")%>'
						name="website" id="website" type="text"
						value="${leaderBean.website}">
					</aui:input></td>
				<td><aui:input
						label='<%=rb.getString("leader-view-portlet-biostatement")%>'
						name="biostatement" style="height:200px;width:500px"
						required="true" id="biostatement" type="textarea"
						value="${leaderBean.bioStatement}">
					</aui:input></td>
			</tr>
			<tr>
				<td><aui:button-row>
						<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
							<aui:button type="submit" onClick="doSubmit();"
								value='<%=rb.getString("leader-login-portlet-btn1-caption")%>' />
						</c:if>
					</aui:button-row></td>
				<td><c:if test="${roleName eq'LEADER_ADMIN'}">
						<a style="" href='/web/guest/addLeaderPage'><%=rb.getString("leader-login-portlet-btn2-caption")%></a>
					</c:if></td>
			</tr>
		</table>
	</aui:form>

</div>
