<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />
<%
PortalUtil.setPageTitle("Quests", request);
%>

<div class="Table">
    <div class="Row">
        <div class="Cell-b" style="border:none;">
            <!-- Column 2 start -->
            <h2>Quests</h2>
        </div>
	</div>
</div>
<%
String select_column="Row";
String bgcolor = "background-color: lightyellow;";
%>
<div class="Table">
	<c:forEach items="${leaderList}" var="leader">
		<c:if test="${not empty leader.questList}">
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
						<c:forEach items="${leader.questList}" var="quest">
							<c:if test="${roleName == null}">
						    	 <p><a href="/web/guest/questdetails-viewpage?userId=${leader.userid}&questId=${quest.questId}">${quest.questTitle}</a></p> 
						    </c:if>
						    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
						    	  <p><a href="/web/guest/questdetails-editpage">${quest.questTitle}</a></p> 
						    </c:if>
						</c:forEach>
					</div>
					<div class="Cell-b">
						<c:if test="${roleName == null}">
					    	<p><a href="/web/guest/leaderdetails-viewpage?userId=${leader.userid}">${leader.firstname} ${leader.lastname}</a></p> 
					    </c:if>
					    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
					    	<p><a href="/web/guest/leaerdetails-editpage">${leader.firstname} ${leader.lastname}</a></p> 
					    </c:if>	
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
		</c:if>
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
