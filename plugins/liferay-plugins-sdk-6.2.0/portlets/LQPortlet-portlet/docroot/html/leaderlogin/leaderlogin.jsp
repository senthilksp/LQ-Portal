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

<portlet:actionURL name="submitLeaderDetails" var="submitLeaderDetailsURL" />

<div class="Table">
	<div class="Row">
		<div class="Cell-a"><h2><%=rb.getString("leader-login-portlet-title")%></h2>
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-a1">
			<liferay-ui:success key="leader-edited-successfully" message='<%=rb.getString("leader-editleader-success-msg")%>'/>
			<liferay-ui:error  key="leader-edit-failed" message='<%=rb.getString("leader-editleader-failure-msg")%>'/>
		</div>
	</div>
</div>
<aui:form action="<%=submitLeaderDetailsURL.toString()%>" method="post" name="LeaderDetailsForm" 
		id="LeaderDetailsForm" enctype="multipart/form-data">
	<aui:input type="hidden" id="userId" name="userId" value="${userId}" readonly="readonly"/>
	<aui:input type="hidden" id="photoURL" name="photoURL" value="${leaderBean.photoURL}" readonly="readonly"/>
	<div class="Table">
		<div class="Row">
			<div class="Cell-a">
				<div class="Table">
					<div class="Row">
						<div class="Cell-a">
							<%=rb.getString("leader-view-portlet-firstname")%>
							<aui:input label="" name="firstname" min="2"
								max="60" id="firstname" type="text" maxlength="150" required="true"  
								value="${leaderBean.firstname}">
								<aui:validator name="minLength"> '2' </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-lastname")%>' name="lastname" min="2"
								required="true" max="60" id="lastname" type="text" maxlength="150"
								value="${leaderBean.lastname}">
								<aui:validator name="minLength"> '2' </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-biostatement")%>' name="biostatement" style="height:200px;width:400px"
								required="true" id="biostatement" type="textarea" maxlength="256" value="${leaderBean.bioStatement}">
								<aui:validator name="minLength"> '10' </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<p>
								<img src="${leaderBean.photoURL}" alt=""></img> <br>
								<input type="file" name="<portlet:namespace />image_fileName" id="image_fileName"/>
							</p>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-facultyrole")%>' name="facultyrole"
								required="true" id="facultyrole" type="text" maxlength="30"
								value="${leaderBean.facultyRole}">
								<aui:validator name="minLength"> '2' </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-primaryphone")%>' name="primaryphone"
								min="10" max="11" id="primaryphone" type="text" maxlength="11" 
								value="${leaderBean.primaryPhone}">
								<aui:validator name="minLength"> '10' </aui:validator>
								<aui:validator name="required">  </aui:validator>
							    <aui:validator name="digits">  </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-businessname")%>' name="businessname"
								required="true" id="businessname" type="text" maxlength="150"
								value="${leaderBean.businessName}">
								<aui:validator name="minLength"> '2' </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label = '<%=rb.getString("leader-view-portlet-emailaddress")%>' name="emailaddress" id="emailaddress" 
						    	type="text" value="${leaderBean.emailAddress}" maxlength="60" required="true">
						    	<aui:validator name="minLength"> '2' </aui:validator>
						    	<aui:validator name="email" />
						    </aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-city")%>' name="city" id="city" type="text"
								value="${leaderBean.city}" maxlength="30">
								<aui:validator name="minLength"> '2' </aui:validator>
								<aui:validator name="required" />
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-country")%>' name="country" id="country"
								required="true" type="text" value="${leaderBean.country}" maxlength="30">
								<aui:validator name="minLength"> '2' </aui:validator>
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:input label='<%=rb.getString("leader-view-portlet-website")%>' name="website" id="website"
								type="text" value="${leaderBean.website}" maxlength="60">
							</aui:input>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<aui:button-row>
								<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
									<aui:button type="submit" onClick="doSubmit();" value='<%=rb.getString("leader-login-portlet-btn1-caption")%>' />
								</c:if>
								<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
									<aui:button type="cancel" onClick="doCancel(${userId});" value='<%=rb.getString("leader-login-portlet-btn2-caption")%>' />
								</c:if>
							</aui:button-row>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<a href="#" onClick="window.open('http://www.coactivenetwork.com', '_blank')">CoActive Network</a>
							<br>
							<br>
						</div>
					</div>
				</div>
			</div>
			<div class="Cell-a">
				<div class="Table">
					<div class="Heading">
						<div class="Cell-a" style="padding-left:0px;">
							Quests Configuration
						</div>
					</div>
				</div>
				<div class="Table">
					<div class="Heading">
						<div class="Cell-heading" style="padding-left:0px;">Quest Title
						</div>
						<div class="Cell-heading">Quest Mode
						</div>
					</div>
			 		<c:forEach items="${leaderBean.questList}" var="quest">
						<div class="Row">
							<div class="Cell-b">${quest.questTitle}
							</div>
							<div class="Cell-b">
							    <c:if test="${quest.accessMode eq true}">
									 &nbsp; &nbsp;<aui:input type="radio" label="" name= "${quest.questId}" value="Public" checked="true" /> Public 
									 &nbsp; &nbsp;<aui:input type="radio" label="" name= "${quest.questId}" value="Private"/> Private
								</c:if>
								<c:if test="${quest.accessMode eq false}">
									 &nbsp; &nbsp;<aui:input type="radio" label="" name="${quest.questId}" value="Public" /> Public 
									 &nbsp; &nbsp;<aui:input type="radio" label="" name="${quest.questId}" value="Private" checked="true" /> Private
								</c:if>
								<br><br>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</div>
	</div>		

</aui:form>