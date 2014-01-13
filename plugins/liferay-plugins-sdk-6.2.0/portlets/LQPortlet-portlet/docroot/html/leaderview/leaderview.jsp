<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<portlet:defineObjects />

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />
	
<% java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>
<% String LQ_LEADER_DETAIL_EDIT_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_EDIT_PAGE; %>	

<div class="content_wrap">
	<div class="content_wrap">
		<div class="left_column">
			<h2><%=rb.getString("leader-view-portlet-heading")%></h2>
		</div>
		<div class="right_column">
		</div>
		<div style="clear:both;"></div>
	</div>
	<div class="content_wrap" style="font-size: 12px; font-weight: normal; padding-left: 10px; padding-top: 20px; width:480px; float: left;">
		<div class="left_column">
			<img src="${leaderBean.photoURL}" alt="" ></img>
		</div>
		<div class="right_column">

		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-name")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.firstname} ${leaderBean.lastname}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-facultyrole")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.facultyRole}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-primaryphone")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.primaryPhone}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-businessname")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.businessName}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-emailaddress")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.emailAddress}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-country")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.country}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-city")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.city}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-website")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.website}
		</div>
		<div style="clear:both;"></div>
		
		<div class="left_column">
			<b><%=rb.getString("leader-view-portlet-biostatement")%> </b> :
		</div>
		<div class="right_column">
			${leaderBean.bioStatement}
		</div>
		<div style="clear:both;"></div>
		<br/><br/>
		<div class="left_column">
		</div>
		<div class="right_column">
			<c:if test="${roleName eq'LEADER_ADMIN'}">
				<strong><a style="btn-primary; float:left; width:75px;" href='/web/guest/addLeaderPage'><%=rb.getString("leader-login-portlet-btn3-caption")%></a></strong>
			</c:if>
			<c:if test="${canEdit eq true}">
				<strong><a style="float:right; width:100px;" href='<%=LQ_LEADER_DETAIL_EDIT_PAGE%>?userId=${userId}'>Edit Leader</a></strong> 
			</c:if>	
		</div>
		<div style="clear:both;"></div>
		<br/><br/><br/><br/>
	</div>
</div>