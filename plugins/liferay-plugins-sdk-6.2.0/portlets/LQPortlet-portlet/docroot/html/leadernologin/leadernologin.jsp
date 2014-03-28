
<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>

<% String LQ_QUEST_DETAIL_VIEW_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_VIEW_PAGE; %>
<% String LQ_QUEST_DETAIL_EDIT_PAGE  = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_PAGE; %>
<% String LQ_LEADER_DETAIL_VIEW_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_VIEW_PAGE; %>
<% String LQ_LEADER_DETAIL_EDIT_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_EDIT_PAGE; %>
<% String LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD; %>


<% java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Leaders", request);
%>

<script type="text/javascript">
$(document).ready(function () {
	var pathname = window.location.pathname;

	if(pathname.indexOf('leader') != -1){
		$("#welcomediv").hide();
	    $("#leaderdiv").show();
	} else {
		$("#welcomediv").show();
		$("#leaderdiv").hide();
	}
});

function searchSubmit() {
    document.leaderNoLoginForm.submit();
}

</script>


<portlet:defineObjects />
<portlet:actionURL name="submitSearchDeails" var="submitSearchDeailsURL" />

<div class="Table">
    <div class="Row">
        <div class="Cell-a" style="border:none;background-color: white;">
			<div id="welcomediv" >
				<h2><%=rb.getString("leader-welcome-msg1")%></h2>
				<ul style = "margin-left: 55px"> 
					<li><%=rb.getString("leader-welcome-msg2")%> </li> 
					<li><%=rb.getString("leader-welcome-msg3")%> </li> 
					<li><%=rb.getString("leader-welcome-msg4")%> </li> 
					<li><%=rb.getString("leader-welcome-msg5")%> </li>  
				</ul>
			</div>
			<div id="leaderdiv" > 
				<h2><%=rb.getString("leader-welcome-msg6")%></h2>
			</div>
		</div>
	</div>
</div>

<div id="searchDiv">
    <form id="leaderNoLoginForm" name="leaderNoLoginForm" action="<%=submitSearchDeailsURL.toString()%>" method="post" >
		<input type="text"  name="txtSearch" id="txtSearch" style="" autocomplete="off" placeholder="Search.." />
	    <input type ="button" id="searchBtn" name="searchBtn" onclick="searchSubmit();" value="Go" />
	</form>    
</div> <br><br><br>

<%
String select_column="Row";
String bgcolor = "background-color: lightyellow;";
%>
<div class="Table">
	<c:forEach items="${leaderList}" var="leader">
<% 
if (select_column == "Row") { 
%>
	<div class="Row">
<%
} 
%>
		<div class="Cell-a" style="<%=bgcolor%>">
			<div class="Table">
				<div class="Row">
					<div class="Cell-b" style="<%=bgcolor%>">
						<p>
						<div width="108" height="115"><img src="${leader.photoURL}" alt=""></div>
						<a href='<%=LQ_LEADER_DETAIL_VIEW_PAGE%>?userId=${leader.userid}'>${leader.firstname} ${leader.lastname}</a>
						</p>
					</div>
					<div class="Cell-b" style="<%=bgcolor%>">
						<div class="Table">
							<c:forEach items="${leader.questList}" var="quest">	
								<div class="Row">
									<div class="Cell-b1">
									    <c:if test="${roleName eq 'LEADER_ADMIN'}">
								           <li><a href='<%=LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD%>?userId=${leader.userid}&questId=${quest.questId}'>${quest.questTitle}</a></li> 
								    	</c:if>
									    <c:if test="${roleName == null}">
								    	 	<li><a href='<%=LQ_QUEST_DETAIL_VIEW_PAGE%>?userId=${leader.userid}&questId=${quest.questId}'>${quest.questTitle}</a></li> 
								    	</c:if>
								     	<c:if test="${roleName eq 'LEADER' && quest.userId != userId}">
											<li><a href='<%=LQ_QUEST_DETAIL_VIEW_PAGE%>?userId=${leader.userid}&questId=${quest.questId}'>${quest.questTitle}</a></li>
										</c:if>
									
										<c:if test="${roleName eq 'LEADER' && quest.userId == userId}">
											 <li><a href='<%=LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD%>?userId=${leader.userid}&questId=${quest.questId}'>${quest.questTitle}</a></li>
										</c:if>
									</div>
								</div>
							</c:forEach>
						</div>
					</div>
				</div>
			</div>
		</div>
<%
if (select_column == "Row") {
	select_column = "";
} else if (select_column == "") {
	select_column = "Row";
	if (bgcolor == "background-color: lightyellow;") {
		bgcolor = "background-color: lightgoldenrodyellow;";
	} else {
		bgcolor = "background-color: lightyellow;";
	}
%>
	</div>
<%
} 
%>
	</c:forEach>
<%
if (select_column == "") {
%>
		<div class="Cell-a" style="<%=bgcolor%>">
		</div>
	</div>
<%
} 
%>
</div>
<br/><br/><br/><br/><br/><br/>