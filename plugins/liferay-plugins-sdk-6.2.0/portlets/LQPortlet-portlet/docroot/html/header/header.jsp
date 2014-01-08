<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="liferay-ui" uri="/WEB-INF/tld/liferay-ui.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>

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
	

<jsp:useBean id="LoggedUserBean" class="com.cti.lq.beans.LoggedUserBean"
	scope="request" />
	
<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>	

<div class="contentWrapper_lq">
	<ul class="mainNav">
		<li class="level-1 here first" id="home">
			<a href='<%=LQ_HOME_URL%>'><%=rb.getString("header-portlet-Home")%></a> </li>
			
		<c:if test="${roleName == null}">
			<li class="level-1" id="nav-sub-leadership"><a
			href='<%=LQ_QUEST_NO_LOGIN_URL%>'><%=rb.getString("header-portlet-Quest")%></a></li>
		</c:if>
		<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
			<li class="level-1" id="nav-sub-leadership"><a
			href='<%=LQ_QUEST_DETAIL_EDIT_PAGE%>'><%=rb.getString("header-portlet-Quest")%></a></li>
	    </c:if>
		
		<c:if test="${roleName == null}">
			<li class="level-1" id="nav-sub-leadership"><a
			href='<%=LQ_LEADER_NO_LOGIN_URL%>'><%=rb.getString("header-portlet-leader")%></a></li>
		</c:if>
		
		<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
			<li class="level-1" id="nav-sub-leadership"><a
			href='<%=LQ_LEADER_DETAIL_EDIT_PAGE%>'><%=rb.getString("header-portlet-leader")%></a></li>
	    </c:if>
	    <c:if test="${(!isSignedIn == true)}">
			<li class="level-1" id="nav-sub-leadership" style="float: right"><a
			href='<%=LQ_PORTAL_LOGIN_URL%>' id="signinLink"><%=rb.getString("header-portlet-Signin")%></a></li>
		</c:if>
		<c:if test="${(isSignedIn == true)}">
			<li class="level-1" id="nav-sub-leadership1" style="float: right"><a
				href='<%=LQ_PORTAL_LOGOUT_URL%>'><%=rb.getString("header-portlet-Signout")%></a></li>
		</c:if>
	</ul>
	<div class="contentWrapper_lq">
		<div class="interiorBanner1">
			<h1><%=rb.getString("header-portlet-heading")%></h1>
		</div>
	</div>
	<div class="contentWrapper_lq">
		<c:if test="${(isSignedIn == true)}">
			<h6 style="float: right"><%=rb.getString("header-portlet-signin-caption")%>
				${LoggedUserBean.firstName} ${LoggedUserBean.lastName}</h6>
		</c:if>
	</div>
	<br>
	<br>
</div>

