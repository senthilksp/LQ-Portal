<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil"%>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />

<%
java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Quests", request);
%>

<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean" scope="request" />

<div class="content_wrap">
	<div class="content_wrap">
		<div class="left_column">
			<h2><%=rb.getString("quest-view-portlet-heading")%></h2>
		</div>
		<div class="right_column">
		</div>
		<div style="clear:both;"></div>
	</div>
	<div class="content_wrap">
		<div class="left_column">
			<img src="${questBean.photoURL}" alt=""></img>
			<p>${questBean.firstName}</p>
		</div>
		<div class="right_column">
			<h5>Quest Details</h5>
			<table>
				<col width="30%">
				<col width="70%">
				<tr>
					<td>Quest Title:</td>
					<td>${questBean.quest_title}</td>
				</tr>
				<tr>
					<td>Quest Definition:</td>
					<td>${questBean.definition}</td>
				</tr>
			</table>
			<c:if test="${not empty questList }">
<%
String displayTitle = "yes";
%>
				<h5>List of Quests :</h5>
				
				<c:forEach items="${questList}" var="quest">
					<c:if test="${quest.questType eq 'IMAGE'}">
<%
						if (displayTitle.equalsIgnoreCase("yes")) {
%>
						<h5>Pictures</h5>
<%
							displayTitle = "";
						}
%>
						<img src="${quest.questLocation}" alt=""></img>
					</c:if>
				</c:forEach>	
<%
displayTitle = "yes";
%>
				<c:forEach items="${questList}" var="quest">
					<c:if test="${quest.questType eq 'AUDIO'}">
<%
						if (displayTitle.equalsIgnoreCase("yes")) {
%>
						<h5>Audio</h5>
<%
							displayTitle = "";
						}
%>
						<video id="lq_video" class="video-js vjs-default-skin" controls
							preload="none" width="300" height="264"
							poster=""
							data-setup="{{}}">
							<source src="${quest.questLocation}" type='video/mp4' />
						</video>
					</c:if>
				</c:forEach>
<%
displayTitle = "yes";
%>
				<c:forEach items="${questList}" var="quest">
					<c:if test="${quest.questType eq 'VIDEO'}">
<%
						if (displayTitle.equalsIgnoreCase("yes")) {
%>
						<h5>Video</h5>
<%
							displayTitle = "";
						}
%>
						<video id="lq_video" class="video-js vjs-default-skin" controls
							preload="none" width="300" height="264"
							poster=""
							data-setup="{{}}">
							<source src="${quest.questLocation}" type='video/mp4' />
						</video>
					</c:if>
				</c:forEach>
			</c:if>
		</div>
		<div style="clear:both;"></div>
		<br/><br/><br/><br/>
	</div>
</div>