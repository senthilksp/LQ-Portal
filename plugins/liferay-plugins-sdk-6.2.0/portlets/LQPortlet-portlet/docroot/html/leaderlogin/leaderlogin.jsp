<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />
<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Leaders", request);
%>

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />

<script type="text/javascript">
$(document).ready(function () {
});

function doSubmit() {
	document.LeaderDetailsForm.submit();
}

function doCancel(userId) {
	window.location = '/web/guest/leaderdetails-viewpage?userId=' + userId;
}

</script>

	<div class="contentWrapper_lq">
		<h2 style="width:50%"><%=rb.getString("leader-login-portlet-title")%></h2>
		
		<br>
		<br>
		<portlet:actionURL name="submitLeaderDetails" var="submitLeaderDetailsURL" />
		<liferay-ui:success key="leader-edited-successfully" message='<%=rb.getString("leader-editleader-success-msg")%>'/>
		<liferay-ui:error  key="leader-edit-failed" message='<%=rb.getString("leader-editleader-failure-msg")%>'/>
		
		<aui:form action="<%=submitLeaderDetailsURL.toString()%>" method="post" name="LeaderDetailsForm" id="LeaderDetailsForm">
		<div class="contentWrapper_lq"style="width: 60%; float: left; height:1270px;" >	
					<div style="margin:0px 0px 0px 20px;">
					<%=rb.getString("leader-view-portlet-firstname")%><aui:input label= "" name="firstname" min="2"
							max="60" id="firstname" type="text" maxlength="150" required="true"  
							value="${leaderBean.firstname}">
							<aui:validator name="minLength"> '2' </aui:validator>
					</aui:input>
					
					<aui:input label='<%=rb.getString("leader-view-portlet-lastname")%>' name="lastname" min="2"
							required="true" max="60" id="lastname" type="text" maxlength="150"
							value="${leaderBean.lastname}">
							<aui:validator name="minLength"> '2' </aui:validator>
						</aui:input>
					<aui:input label='<%=rb.getString("leader-view-portlet-biostatement")%>' name="biostatement" style="height:200px;width:500px"
							required="true" id="biostatement" type="textarea" maxlength="256" value="${leaderBean.bioStatement}">
							<aui:validator name="minLength"> '10' </aui:validator>
					</aui:input>	
					<br> <img src="${leaderBean.photoURL}" alt=""></img> <br>
					
					<aui:input label='<%=rb.getString("leader-login-portlet-photoURL")%>' name="photourl" min="2" max="60"
							style="width:300px" id="firstname" type="text" required="true" maxlength="60"
							value="${leaderBean.photoURL}">
							<aui:validator name="minLength"> '2' </aui:validator>
					</aui:input>
						<input id="image-file" type="file" />
					<aui:input label='<%=rb.getString("leader-view-portlet-facultyrole")%>' name="facultyrole"
							required="true" id="facultyrole" type="text" maxlength="30"
							value="${leaderBean.facultyRole}">
							<aui:validator name="minLength"> '2' </aui:validator>
					</aui:input>
					<aui:input label='<%=rb.getString("leader-view-portlet-primaryphone")%>' name="primaryphone"
							min="10" max="11" id="primaryphone" type="text" maxlength="11" 
							value="${leaderBean.primaryPhone}">
							<aui:validator name="minLength"> '10' </aui:validator>
							<aui:validator name="required">  </aui:validator>
						    <aui:validator name="digits">  </aui:validator>
						</aui:input>
					<aui:input label='<%=rb.getString("leader-view-portlet-businessname")%>' name="businessname"
							required="true" id="businessname" type="text" maxlength="150"
							value="${leaderBean.businessName}">
							<aui:validator name="minLength"> '2' </aui:validator>
						</aui:input>
					<aui:input label = '<%=rb.getString("leader-view-portlet-emailaddress")%>' name="emailaddress" id="emailaddress" 
					               type="text" value="${leaderBean.emailAddress}" maxlength="60" required="true">
					     <aui:validator name="minLength"> '2' </aui:validator>
					     <aui:validator name="email" />
					    </aui:input>
					<aui:input label='<%=rb.getString("leader-view-portlet-city")%>' name="city" id="city" type="text"
							   value="${leaderBean.city}" maxlength="30">
							 <aui:validator name="minLength"> '2' </aui:validator>
							 <aui:validator name="required" />
						</aui:input>
					<aui:input label='<%=rb.getString("leader-view-portlet-country")%>' name="country" id="country"
							required="true" type="text" value="${leaderBean.country}" maxlength="30">
							 <aui:validator name="minLength"> '2' </aui:validator>
						</aui:input>
					<aui:input label='<%=rb.getString("leader-view-portlet-website")%>' name="website" id="website"
							type="text" value="${leaderBean.website}" maxlength="60">
						</aui:input>
						 <aui:input type="hidden" id="userId" name="userId" value="${userId}" readonly="readonly"/>
					
					<aui:button-row>
							 <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
								<aui:button type="submit" onClick="doSubmit();" value='<%=rb.getString("leader-login-portlet-btn1-caption")%>' />
							</c:if>
							<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
								<aui:button type="cancel" onClick="doCancel(${userId});" value='<%=rb.getString("leader-login-portlet-btn2-caption")%>' />
							</c:if>
						</aui:button-row>
						<a href="#" onClick="window.open('http://www.coactivenetwork.com', '_blank')">CoActive Network</a> <br> <br>
					 <br><br>
			</div>		
		</div>
		<div class="contentWrapper_lq" style="width: 40%; float: right; height:1270px;">
			<h3> Quests Configuration </h3>
			<b>Quest Title</b>  &nbsp; &nbsp; <b>Quest Mode </b> <br>
			<c:forEach items="${leaderBean.questList}" var="quest">
			     ${quest.questTitle} 
			    
			    <c:if test="${quest.accessMode eq true}">
					 &nbsp; &nbsp;<aui:input type="radio" label="" name= "${quest.questId}" value="Public" checked="true" /> Public 
					 &nbsp; &nbsp;<aui:input type="radio" label="" name= "${quest.questId}" value="Private"/> Private
				<br> <br>
				</c:if>
				<c:if test="${quest.accessMode eq false}">
					 &nbsp; &nbsp;<aui:input type="radio" label="" name="${quest.questId}" value="Public" /> Public 
					 &nbsp; &nbsp;<aui:input type="radio" label="" name="${quest.questId}" checked="true"  value="Private"/> Private
				<br> <br>
				</c:if>
			 </c:forEach>
		</div>
	</aui:form>
	</div>
