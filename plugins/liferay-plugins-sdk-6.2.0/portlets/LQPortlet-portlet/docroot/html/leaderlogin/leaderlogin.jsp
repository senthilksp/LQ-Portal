<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>

<portlet:defineObjects />

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />
<script type="text/javascript">
$(document).ready(function () {
	
});


function doSubmit() {
	var emailId = jQuery("input[id=emailaddress]").val();
		document.LeaderDetailsForm.submit();
}


function checkValidEmail(emailval) {
	return true;
}


</script>


	<div class="contentWrapper_lq">
		<h3>Leader Details</h3>
		<portlet:actionURL name="submitLeaderDetails"
			var="submitLeaderDetailsURL" />
		<aui:form action="<%=submitLeaderDetailsURL.toString()%>"
			method="post" name="LeaderDetailsForm" id="LeaderDetailsForm">
			<table>
				<tr>
					<td><img src="${leaderBean.photoURL}" alt=""></img>
					</td>
					<td><aui:input label="URL" name="photourl" min="2" max="60"
							style="width:300px" id="firstname" type="text" required="true"
							value="${leaderBean.photoURL}">
						</aui:input></td>
				</tr>
				<tr>
					<td><aui:input label="First Name" name="firstname" min="2"
							max="60" id="firstname" type="text" required="true"
							value="${leaderBean.firstname}">
						</aui:input></td>
					<td><aui:input label="Last Name" name="lastname" min="2"
							required="true" max="60" id="lastname" type="text"
							value="${leaderBean.lastname}">
						</aui:input></td>
				</tr>

				<tr>
					<td><aui:input label="Faculty Role" name="facultyrole"
							required="true" id="facultyrole" type="text"
							value="${leaderBean.facultyRole}">
						</aui:input></td>
					<td><aui:input label="Primary Phone" name="primaryphone"
							required="true" min="10" max="11" id="primaryphone" type="text"
							value="${leaderBean.primaryPhone}">
						</aui:input></td>
				</tr>
				<tr>
					<td><aui:input label="Business Name" name="businessname"
							required="true" id="businessname" type="text"
							value="${leaderBean.businessName}">
						</aui:input></td>
					<td><aui:input label = "Email Address" name="emailaddress" id="emailaddress" 
					               type="text" value="${leaderBean.emailAddress}"  required="true"> 
					    <aui:validator name="email" />
					    </aui:input>
					</td>
				</tr>
				<tr>
					<td><aui:input label="City" name="city" id="city" type="text"
							required="true" value="${leaderBean.city}">
						</aui:input></td>
					<td><aui:input label="Country" name="country" id="country"
							required="true" type="text" value="${leaderBean.country}">
						</aui:input></td>

				</tr>
				<tr>
					<td><aui:input label="Web site" name="website" id="website"
							type="text" value="${leaderBean.website}">
						</aui:input></td>
					<td><aui:input label="Bio Statement" name="biostatement" style="height:200px;width:500px"
							required="true" id="biostatement" type="textarea"
							value="${leaderBean.bioStatement}">
						</aui:input></td>
				</tr>
				<tr>
					<td><aui:button-row>
							<aui:button type="submit" value="Edit" onClick="doSubmit();"/>
							<c:if test="${roleName eq'LEADER_ADMIN'}">
								<aui:button type="submit" value="Addleader" />
							</c:if>
						</aui:button-row></td>

				</tr>

			</table>


		</aui:form>

	</div>
