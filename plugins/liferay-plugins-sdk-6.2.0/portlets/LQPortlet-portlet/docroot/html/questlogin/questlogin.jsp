<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<script type="text/javascript">
$(document).ready(function() {
	(".quest").hide();
});

function doDisplay(divId) {
	$(".quest").hide();
	$("video").each(function() {
		this.pause();
	});
	$('#' + divId).show();
	$('#' + divId).next().play();
}
</script>


<portlet:defineObjects />


<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Quests", request);
%>
<div class="content_wrap">
	<div class="content_wrap">
		<h2>Quest Details</h2>
		<br>
	</div>

	<div class="content_wrap">
		<liferay-ui:success key="quest-added-successfully"
		message='<%=rb.getString("quest-editquest-success-msg")%>' />
		
		<portlet:actionURL name="submitQuestLoginDetails" var="submitQuestLoginDetailsURL" />
		<div class="left_column">
			<img src="${questBean.photoURL}" alt="" style="margin-left:20px"></img>
			<br/> 
			<p style="margin-left:20px">${questBean.firstName} </p>
		</div>
		<div class="right_column">
			<form id="<portlet:namespace />questLoginForm" name="<portlet:namespace />questLoginForm" 
				action="<%=submitQuestLoginDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
				
				<h5>Pictures</h5>
				<c:forEach items="${questList}" var="quest">
					<c:if test="${quest.questType eq 'IMAGE'}">
					<img src="${quest.questLocation}" alt=""></img>
					</c:if>
				</c:forEach>
				<br/><br/>
				<input type="file" name="<portlet:namespace />image_fileName" />
				<input type="submit" id="btnSubmit1" name="btnSubmit1" value='Upload' />
				<br/>
				<h5>Audios</h5>
				<c:forEach items="${questList}" var="quest">
				<a href='#' onclick="showAudio();">${quest.questLocation}</a>	
					
					<div id="audiodiv" >
						<c:if test="${quest.questType eq 'AUDIO'}">
							<br><br>
							<a href='#!' onclick="doDisplay('${quest.id}')">${quest.questLocation}</a><br>
							<div id="${quest.id}" class="quest">
								<video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="50" poster="" data-setup="{{}}">
									<source src="${quest.questLocation}" type='video/mp4' />
								</video>
							</div>
						</c:if>
					</div>
				
				</c:forEach>
				<br/>
				<input type="file" name="<portlet:namespace />audio_fileName" />
				<input type="hidden" name="<portlet:namespace />quest_id" id="<portlet:namespace />quest_id" value ="${questId}" />
				<input type="submit" id="btnSubmit2" name="btnSubmit2" value='Upload' />	
				<br><br>
				<h5>Videos</h5>
				<c:forEach items="${questList}" var="quest">
				<a href='#' onclick="showVideo();">${quest.questLocation}</a>		
					
					<div id="videodiv" >
					<c:if test="${quest.questType eq 'VIDEO'}">
						<br><br>
						<a href='#!' onclick="doDisplay('${quest.id}')">${quest.questLocation}</a><br>
						<div id="${quest.id}" class="quest">
							<video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="264" poster="" data-setup="{{}}">
						    	<source src="${quest.questLocation}" type='video/mp4' />
							</video>
						</div>	
					</c:if>
					</div>
					
				</c:forEach>
				<br/>
				<input type="file" name="<portlet:namespace />video_fileName" />
				<input type="submit" id="btnSubmit3" name="btnSubmit3" value='upload' />
				<br><br>
				<a href='/web/guest/editQuestPage?userId=${userId}&questId=${questId}'>Edit Quest</a>
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<a href='/web/guest/addQuestPage'><%=rb.getString("quest-addquest-link-caption")%></a>
				<br><br>
			</form>
		</div>
	</div>
</div>
