<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<portlet:defineObjects />

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />
	
<% 
java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
String LQ_LEADER_DETAIL_EDIT_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_EDIT_PAGE;
String LQ_LEADER_DETAIL_VIEW_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_VIEW_PAGE;
%>
<script type="text/javascript">
	function deleteLeader() {
		document.<portlet:namespace />DeleteLeaderForm.submit();
	}
</script>


<portlet:actionURL name="submitDeleteLeader" var="submitDeleteLeader" />
<liferay-ui:success key="leader-delete-success" message='<%=rb.getString("leader-deleteleader-success-msg")%>'/>
<liferay-ui:error  key="leader-delete-failure" message='<%=rb.getString("leader-deleteleader-failure-msg")%>'/>

<aui:form action="<%=submitDeleteLeader.toString()%>" method="post" name="DeleteLeaderForm" id="DeleteLeaderForm" enctype="multipart/form-data">
	<aui:input type="hidden" id="userId" name="userId" value="${userId}" readonly="readonly"/>
	<aui:input type="hidden" id="userEmail" name="userEmail" value="${leaderBean.emailAddress}" readonly="readonly"/>
</aui:form>

<div class="Table">
	<div class="Row">
		<div class="Cell-a">
			<h2><%=rb.getString("leader-view-portlet-heading")%></h2>
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-b3"><br>
		</div>
		<div class="Cell-b4"><br>
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3">
			<img src="${leaderBean.photoURL}" alt="" ></img>
		</div>
		<div class="Cell-b4">
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><br>
		</div>
		<div class="Cell-b4"><br>
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-name")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.firstname} ${leaderBean.lastname}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-facultyrole")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.facultyRole}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-primaryphone")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.primaryPhone}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-businessname")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.businessName}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-emailaddress")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.emailAddress}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-country")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.country}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-city")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.city}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-website")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.website}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><b><%=rb.getString("leader-view-portlet-biostatement")%></b>
		</div>
		<div class="Cell-b4">${leaderBean.bioStatement}
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><br>
		</div>
		<div class="Cell-b4"><br>
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><br>
		</div>
		<div class="Cell-b4">
			<div class="Table">
				<div class="Row">
					<c:if test="${roleName eq'LEADER_ADMIN'}">
						<div class="Cell-b3" style="padding-left:0px;">
							<strong><a href='/web/guest/addLeaderPage'><%=rb.getString("leader-login-portlet-btn3-caption")%></a></strong>
						</div>
					</c:if>
					<c:if test="${canEdit eq true}">
						<div class="Cell-b3" style="padding-left:0px;">
							<strong><a href='<%=LQ_LEADER_DETAIL_EDIT_PAGE%>?userId=${userId}'><%=rb.getString("leader-login-portlet-btn4-caption")%></a></strong> 
						</div>
					</c:if>
					<c:if test="${canDelete eq true}">
						<div class="Cell-b3" style="padding-left:0px;">
							<strong><a href="javascript:void();" onClick="javascript:deleteLeader();"><%=rb.getString("leader-login-portlet-btn5-caption")%></a></strong>
						</div>
					</c:if>
				</div>
			</div>
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3"><br><br>
		</div>
		<div class="Cell-b4"><br><br>
		</div>
	</div>
</div>