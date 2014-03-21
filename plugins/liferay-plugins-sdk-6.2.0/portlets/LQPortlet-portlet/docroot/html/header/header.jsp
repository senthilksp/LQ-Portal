<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="liferay-ui" uri="/WEB-INF/tld/liferay-ui.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<script type="text/javascript">
jQuery(document).ready(function() {
	
});

function doSubmit() {
   
}

</script>


    <%  String LQ_HOME_URL 			  	  = com.cti.lq.Constants.LQPortalConstants.LQ_HOME_URL; %>
	<%  String LQ_LEADER_NO_LOGIN_URL 	  = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_NO_LOGIN_URL; %>
	<%  String LQ_QUEST_NO_LOGIN_URL  	  =	 com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_NO_LOGIN_URL; %>
	<%  String LQ_QUEST_DETAILS_URL   	  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAILS_URL; %>
	<%  String LQ_LEADER_DETAIL_EDIT_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_EDIT_PAGE; %>
	<%  String LQ_QUEST_DETAIL_EDIT_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_PAGE; %>
	<%  String LQ_PORTAL_LOGOUT_URL       = com.cti.lq.Constants.LQPortalConstants.LQ_PORTAL_LOGOUT_URL; %>
	<%  String LQ_PORTAL_LOGIN_URL        = com.cti.lq.Constants.LQPortalConstants.LQ_PORTAL_LOGIN_URL; %>
	<%  String LQ_LEADER_ROLE 		      = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_ROLE; %>
	<%  String LQ_LEADER_ADMIN_ROLE	      = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_ADMIN; %>
	<%  String LQ_LEADER_DETAIL_VIEW_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_VIEW_PAGE; %>
	<%  String LQ_QUEST_DETAILS_PAGE      = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAILS_URL; %>
	

<jsp:useBean id="LoggedUserBean" class="com.cti.lq.beans.LoggedUserBean" scope="request" />

	
<%
PortalUtil.setPageTitle("Quests", request);
java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
String currentUrl = LQPortalUtil.getCurrentURL(request);
String styleClass = "level-1";
String styleClass_active = " parent-here first";
String headers_home = styleClass;
String headers_quest = styleClass;
String headers_leader = styleClass;

if (currentUrl.contains("welcome") || currentUrl.equalsIgnoreCase("/")) {
	headers_home += styleClass_active;
} else if (currentUrl.contains("quest")) {
	headers_quest += styleClass_active;
} else if (currentUrl.contains("leader")) {
	headers_leader += styleClass_active;
}
%>	

<div class="content_wrap">
	<ul class="mainNav">
		<li class="<%=headers_home %>" id="nav-home">
			<a href='<%=LQ_HOME_URL%>'><%=rb.getString("header-portlet-Home")%></a> 
		</li>
			
		<c:if test="${roleName == null}">
			<li class="<%=headers_quest %>" id="nav-quest">
				<a href='<%=LQ_QUEST_NO_LOGIN_URL%>'><%=rb.getString("header-portlet-Quest")%></a>
			</li>
		</c:if>
		<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
			<li class="<%=headers_quest %>" id="nav-quest">
				<a href='<%=LQ_QUEST_DETAILS_PAGE%>'><%=rb.getString("header-portlet-Quest")%></a>
			</li>
	    </c:if>
		
		<c:if test="${roleName == null}">
			<li class="<%=headers_leader %>" id="nav-leadership">
				<a href='<%=LQ_LEADER_NO_LOGIN_URL%>'><%=rb.getString("header-portlet-leader")%></a>
			</li>
		</c:if>
		
		<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
			<li class="<%=headers_leader %>" id="nav-leadership">
				<a href='<%=LQ_LEADER_DETAIL_VIEW_PAGE%>?userId=${userId}'><%=rb.getString("header-portlet-leader")%></a>
			</li>
	    </c:if>
	    <c:if test="${(!isSignedIn == true)}">
			<li style="float: right; padding-right:10px; padding-top:20px;">
				<a href='<%=LQ_PORTAL_LOGIN_URL%>' id="signinLink"><%=rb.getString("header-portlet-Signin")%></a>
			</li>
		</c:if>
		<c:if test="${(isSignedIn == true)}">
			<li style="float: right; padding-right:10px; padding-top:20px;">
				<a href='<%=LQ_PORTAL_LOGOUT_URL%>'><%=rb.getString("header-portlet-Signout")%></a>
			</li>
		</c:if>
	</ul>
	<div class="content_wrap">
		<div class="interiorBanner1">
			<h1><%=rb.getString("header-portlet-heading")%></h1>
		</div>
	</div>
	<c:if test="${(isSignedIn == true)}">
		<div class="content_wrap">
			<h6 style="float:right; padding-right:10px;font-weight:normal;"><%=rb.getString("header-portlet-signin-caption")%> <strong>${LoggedUserBean.firstName} ${LoggedUserBean.lastName}</strong></h6></p>
		</div>
	</c:if>
	<br>
	<br>
</div>
<div class="content_wrap">
	<a class="addthis_button" style="padding-right:10px;float:right;" href="http://www.addthis.com/bookmark.php?v=300&amp;pubid=ra-52ce7b2b414da24b">
	<img src="http://s7.addthis.com/static/btn/v2/lg-share-en.gif" width="125" height="16" alt="Bookmark and Share" style="border:0"/></a>
    <script type="text/javascript">var addthis_config = {"data_track_addressbar":true};</script>
	<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-52ce7b2b414da24b"></script>
</div>
