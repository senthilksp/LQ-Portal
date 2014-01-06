<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>

<% String LQ_QUEST_DETAIL_VIEW_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_VIEW_PAGE; %>
<% String LQ_QUEST_DETAIL_EDIT_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_PAGE; %>
<% String LQ_LEADER_DETAIL_VIEW_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_VIEW_PAGE; %>
<% String LQ_LEADER_DETAIL_EDIT_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_EDIT_PAGE; %>


<% java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>

<portlet:defineObjects />
	<div class="contentWrapper_lq">
		<h2><%=rb.getString("leader-no-login-portlet-heading")%></h2>
		<br>
		<table>
			<c:forEach items="${leaderList}" var="leader">
				<tr>
					<td><img src="${leader.photoURL}" alt="" ></img>
					<td>
					<ul>
					<c:forEach items="${leader.questList}" var="quest">
					    <c:if test="${roleName == null}">
				    	 <li><a href='<%=LQ_QUEST_DETAIL_VIEW_PAGE%>?userId=${leader.userid}&questId=${quest.questId}'>${quest.questTitle}</a></li> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	  <li><a href='<%=LQ_QUEST_DETAIL_EDIT_PAGE%>'>${quest.questTitle}</a></li> 
				    </c:if>
					</c:forEach>
					</ul>
				</tr>
				<tr>
				    <td> 
				    <c:if test="${roleName == null}">
				    	<a href='<%=LQ_LEADER_DETAIL_VIEW_PAGE%>?userId=${leader.userid}'>${leader.firstname}</a> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	<a href='<%=LQ_LEADER_DETAIL_EDIT_PAGE%>?userId=${leader.userid}'>${leader.firstname}</a> 
				    </c:if>	
				    </td>
				</tr>	
				
			</c:forEach>
		</table>
	
	</div>

