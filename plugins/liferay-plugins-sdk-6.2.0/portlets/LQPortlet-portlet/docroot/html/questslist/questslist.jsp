
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>

<% String LQ_QUEST_DETAIL_VIEW_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_VIEW_PAGE; %>
<% String LQ_QUEST_DETAIL_EDIT_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_PAGE; %>
<% String LQ_LEADER_DETAIL_VIEW_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_VIEW_PAGE; %>
<% String LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD; %>


<% java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>
<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean" scope="request" />


<portlet:defineObjects />

<div class="Table">
	<div class="Row">
		<div class="Cell-a">
			<h2>Quest Details</h2>
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-b3">
			<p>
				<img src="${leaderBean.photoURL}" alt="" ></img>
				<br>
				<a href='<%=LQ_LEADER_DETAIL_VIEW_PAGE%>?userId=${leaderBean.userid}'>${leaderBean.firstname} ${leaderBean.lastname}</a>
			</p>
		</div>
		<div class="Cell-b4">
			<div class="Table">
				<ul>
					<c:forEach items="${leaderBean.questList}" var="quest">
						<div class="Row">
							<div class="Cell-b4">
						    	 <li>
						    	 	<a href='<%=LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD%>?userId=${userId}&questId=${quest.questId}'>
						    	 		${quest.questTitle}
						    	 	</a>
						    	 </li> 
							</div>
						</div>
					</c:forEach>
				</ul>
			</div>
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3">
			<br>
		</div>
		<div class="Cell-b4">
		</div>
	</div>
	<div class="Row">
		<div class="Cell-b3">
			<a href='/web/guest/addQuestPage'>
				<%=rb.getString("quest-addquest-link-caption")%>
			</a>
			<br>
			<br>
		</div>
		<div class="Cell-b4">
		</div>
	</div>
</div>