<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />
<%
PortalUtil.setPageTitle("Quests", request);
%>


<div class="content_wrap">
	<div class="content_wrap">
		<div class="left_column">
			<h2>Quests</h2>
		</div>
		<div class="right_column">
			<a class="addthis_button" style="padding-top:20px;" href="http://www.addthis.com/bookmark.php?v=300&amp;pubid=ra-52ce7b2b414da24b">
			<img src="http://s7.addthis.com/static/btn/v2/lg-share-en.gif" width="125" height="16" alt="Bookmark and Share" style="border:0"/></a>
		    <script type="text/javascript">var addthis_config = {"data_track_addressbar":true};</script>
			<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-52ce7b2b414da24b"></script>
		</div>
		<div style="clear:both;"></div>
	</div>
	<br/>
<c:forEach items="${leaderList}" var="leader">
	<c:if test="${not empty leader.questList}">
	<div class="content_wrap">
		<div class="left_column">
			<div class="column_wrap">
				<div class="left_quests_column">
					<c:forEach items="${leader.questList}" var="quest">
					    <c:if test="${roleName == null}">
				    	 <li><a href="/web/guest/questdetails-viewpage?userId=${leader.userid}&questId=${quest.questId}">${quest.questTitle}</a></li> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	  <li><a href="/web/guest/questdetails-editpage">${quest.questTitle}</a></li> 
				    </c:if>
					</c:forEach>
				</div>
				<div id="right_leader_column">
					<c:if test="${roleName == null}">
				    	<a href="/web/guest/leaderdetails-viewpage?userId=${leader.userid}">${leader.firstname}${leader.lastname}</a> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	<a href="/web/guest/leaerdetails-editpage">${leader.firstname} ${leader.lastname}</a> 
				    </c:if>	
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
		<div class="right_column" >
			<div class="column_wrap">
				<div class="left_quests_column">
					<c:forEach items="${leader.questList}" var="quest">
					    <c:if test="${roleName == null}">
				    	 <li><a href="/web/guest/questdetails-viewpage?userId=${leader.userid}&questId=${quest.questId}">${quest.questTitle}</a></li> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	  <li><a href="/web/guest/questdetails-editpage">${quest.questTitle}</a></li> 
				    </c:if>
					</c:forEach>
				</div>
				<div id="right_leader_column">
					<c:if test="${roleName == null}">
				    	<a href="/web/guest/leaderdetails-viewpage?userId=${leader.userid}">${leader.firstname}${leader.lastname}</a> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	<a href="/web/guest/leaerdetails-editpage">${leader.firstname} ${leader.lastname}</a> 
				    </c:if>	
				</div>
				<div style="clear:both;"></div>
			</div>
		</div>
	</div>
	</c:if>
</c:forEach>
<br/><br/><br/><br/><br/><br/>
</div>