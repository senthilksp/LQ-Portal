<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />

<%
java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Add Leader", request);
%>

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />

<script type="text/javascript">
$(document).ready(function () {
	
});

function doSubmit() {
	document.LeaderDetailsForm.submit();
}

</script>

<portlet:actionURL name="submitLeaderDetails" var="submitLeaderDetailsURL" />

<div class="Table">
	<div class="Row">
		<div class="Cell-a"><h2><%=rb.getString("leader-view-portlet-heading")%></h2>
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-a1">
			<liferay-ui:success key="leader-added-successfully" message='<%=rb.getString("leader-addleader-success-msg")%>'/>
			<liferay-ui:error  key="leader-add-failed" message='<%=rb.getString("leader-addleader-failure-msg")%>'/>
		</div>
	</div>
</div>
<aui:form action="<%=submitLeaderDetailsURL.toString()%>"
	method="post" name="LeaderDetailsForm" id="LeaderDetailsForm" enctype="multipart/form-data">
	<div class="Table">
		<div class="Row">
			<div class="Cell-a2">
				Photo
				<input type="file" name="<portlet:namespace />image_fileName" id="image_fileName"/>
				<br><br>
			</div>
			<div class="Cell-a3">
			</div>
		</div>
		<div class="Row">
			<div class="Cell-a2">
				<aui:input label='<%=rb.getString("leader-view-portlet-firstname")%>' name="firstname" maxlength="150"
					       id="firstname" type="text" required="true"	value="">
					<aui:validator name="minLength"> '2' </aui:validator>
					<aui:validator name="maxLength"> '150' </aui:validator>
				</aui:input>
			</div>
			<div class="Cell-a3">
				<aui:input label='<%=rb.getString("leader-view-portlet-lastname")%>' name="lastname" maxlength="150"
					required="true" max="60" id="lastname" type="text"
					value="">
					<aui:validator name="minLength"> '2' </aui:validator>
					<aui:validator name="maxLength"> '150' </aui:validator>
				</aui:input>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-a2">
				<aui:input label='<%=rb.getString("leader-view-portlet-facultyrole")%>' name="facultyrole"
					required="true" id="facultyrole" type="text" value="" maxlength="30">
					<aui:validator name="minLength"> '2' </aui:validator>
					<aui:validator name="maxLength"> '30' </aui:validator>	
				</aui:input>
			</div>
			<div class="Cell-a3">
				<aui:input label='<%=rb.getString("leader-view-portlet-primaryphone")%>' name="primaryphone"
					required="true" min="10" max="11" id="primaryphone" type="text" maxlength="11"
					value="">
					<aui:validator name="minLength"> '10' </aui:validator>
					<aui:validator name="maxLength"> '11' </aui:validator>	
					<aui:validator name="digits">  </aui:validator>
				</aui:input>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-a2">
				<aui:input label='<%=rb.getString("leader-view-portlet-businessname")%>' name="businessname"
					required="true" id="businessname" type="text" min="2" max="150" maxlength="150"
					value="">
					<aui:validator name="minLength"> '2' </aui:validator>
					<aui:validator name="maxLength"> '150' </aui:validator>
				</aui:input>
			</div>
			<div class="Cell-a3">
				<aui:input label = '<%=rb.getString("leader-view-portlet-emailaddress")%>' name="emailaddress" id="emailaddress" 
				    type="text" value=""  required="true" maxlength="40"> 
				    <aui:validator name="email" />
			    </aui:input>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-a2">
				<aui:input label='<%=rb.getString("leader-view-portlet-city")%>' name="city" id="city" type="text"
					required="true" value="" min="2" max="30" maxlength="30">
					<aui:validator name="minLength"> '2' </aui:validator>
					<aui:validator name="maxLength"> '30' </aui:validator>
				</aui:input>
			</div>
			<div class="Cell-a3">
				<aui:input label='<%=rb.getString("leader-view-portlet-country")%>' name="country" id="country"
					required="true" type="text" value="" min="2" max="30" maxlength="30">
					<aui:validator name="minLength"> '2' </aui:validator>
					<aui:validator name="maxLength"> '30' </aui:validator>
				</aui:input>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-a2">
				<aui:input label='<%=rb.getString("leader-view-portlet-website")%>' name="website" id="website"
					type="text" value="" maxlength="60">
				</aui:input>
			</div>
			<div class="Cell-a3">
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-a1">
				<aui:input label='<%=rb.getString("leader-view-portlet-biostatement")%>' name="biostatement" style="height:120px;width:920px"
					required="true" id="biostatement" type="textarea" min="10" max="256" maxlength="256" value="">
					<aui:validator name="minLength"> '10' </aui:validator>
					<aui:validator name="maxLength"> '256' </aui:validator>
				</aui:input>
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-a2">
				<aui:button-row>
					<aui:button type="submit" onClick="doSubmit();" value='<%=rb.getString("leader-addleader-portlet-btn-caption")%>' />
				</aui:button-row>
			</div>
			<div class="Cell-a3">
			</div>
		</div>
	</div>
</aui:form>
