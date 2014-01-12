
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
<% String LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD = com.cti.lq.Constants.LQPortalConstants.LQ_QUEST_DETAIL_EDIT_FOR_UPLOAD; %>


<% java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>

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

</script>


<portlet:defineObjects />
	<div class="contentWrapper_lq" style="overflow: hidden" >
	<br>
		<div class="contentWrapper_lq" style="width: 50%; float: left; ">
			<div id="welcomediv" >
				 <h2><%=rb.getString("leader-welcome-msg1")%></h2> <br>
						<ul style = "margin-left: 55px"> 
							<li><%=rb.getString("leader-welcome-msg2")%> </li>
							<li><%=rb.getString("leader-welcome-msg2")%> </li> 
							<li><%=rb.getString("leader-welcome-msg3")%> </li> 
							<li><%=rb.getString("leader-welcome-msg4")%> </li> 
							<li><%=rb.getString("leader-welcome-msg5")%> </li>  
						</ul>
				  <br>
				</div> 
				 <div id="leaderdiv" > 
					  <h2> <%=rb.getString("leader-welcome-msg6")%> </h2> <br>
				 </div>
				  
				   <table>
			<c:forEach items="${leaderList}" var="leader">
			
				<tr>
					<td><img src="${leader.photoURL}" alt="" ></img>
					<td>
					<ul>
					<c:forEach items="${leader.questList}" var="quest">
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
					</c:forEach>
					</ul>
				</tr>
				<tr>
				    <td> 
				    	<a href='<%=LQ_LEADER_DETAIL_VIEW_PAGE%>?userId=${leader.userid}'>${leader.firstname} ${leader.lastname}</a> 
				    </td>
				</tr>	
				
			</c:forEach>
		</table>
				</div>
				<div class="contentWrapper_lq" style="width: 50%; float: right; overflow: hidden" >
					<!-- AddThis Button BEGIN -->
					<a class="addthis_button" href="http://www.addthis.com/bookmark.php?v=300&amp;pubid=ra-52ce7b2b414da24b">
					<img src="http://s7.addthis.com/static/btn/v2/lg-share-en.gif" width="125" height="16" alt="Bookmark and Share" style="border:0"/></a>
				    <script type="text/javascript">var addthis_config = {"data_track_addressbar":true};</script>
					<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-52ce7b2b414da24b"></script>
				<!-- AddThis Button END -->
				</div>	
			<br>
		
	
	</div>

