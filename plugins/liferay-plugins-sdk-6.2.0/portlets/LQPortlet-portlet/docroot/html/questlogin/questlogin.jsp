<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />

<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Quests", request);
%>

	<div class="contentWrapper_lq" style="overflow: hidden" >
		<h2>Quest Details</h2> <br>
		
		<liferay-ui:success key="quest-added-successfully"
		message='<%=rb.getString("quest-editquest-success-msg")%>' />
		
		<portlet:actionURL name="submitQuestLoginDetails" var="submitQuestLoginDetailsURL" />

		<div class="contentWrapper_lq" style="width: 50%; float: left; overflow: hidden">
			<img src="${questBean.photoURL}"
				alt="" style="margin-left:20px"></img> 
				<p style="margin-left:20px"> ${questBean.firstName} </p>
		</div>
		
		<form id="<portlet:namespace />questLoginForm" name="<portlet:namespace />questLoginForm" 
			action="<%=submitQuestLoginDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
			
			<div class="contentWrapper_lq" style="width: 50%; float: right; overflow: hidden" >
			 <h5> Quest Pictures </h5>
			 <c:forEach items="${questList}" var="quest">
				<c:if test="${quest.questType eq 'IMAGE'}">
					<img src="${quest.questLocation}" alt=""></img>
				</c:if>
			  </c:forEach>
			  <input type="file" name="<portlet:namespace />image_fileName" />
			  <input type="submit" id="btnSubmit1" name="btnSubmit1" value='Upload' />
			  	
			  	<br><br><h5> Quest Audios </h5>
			  	<c:forEach items="${questList}" var="quest">
					<c:if test="${quest.questType eq 'AUDIO'}">
						<br><br>
						 <video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="264"
								      poster=""
								      data-setup="{{}}">
								    <source src="${quest.questLocation}" type='video/mp4' />
								  </video>
					</c:if>
				</c:forEach>
				<input type="file" name="<portlet:namespace />audio_fileName" />
				<input type="hidden" name="<portlet:namespace />quest_id" id="<portlet:namespace />quest_id" value ="${questId}" />
				<input type="submit" id="btnSubmit2" name="btnSubmit2" value='Upload' />	
					
				<br><br><h5> Quest Videos </h5>
				<c:forEach items="${questList}" var="quest">	
					<c:if test="${quest.questType eq 'VIDEO'}">
						<br><br>
						 <video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="264"
								      poster=""
								      data-setup="{{}}">
								    <source src="${quest.questLocation}" type='video/mp4' />
								  </video>
					</c:if>
                  </c:forEach>
                  <input type="file" name="<portlet:namespace />video_fileName" />
                  <input type="submit" id="btnSubmit3" name="btnSubmit3" value='upload' />
                  
			   <br><br>
			 
			   <a href='/web/guest/editQuestPage?userId=${userId}&questId=${questId}'>EditQuest</a>
			  <a href='/web/guest/addQuestPage'><%=rb.getString("quest-addquest-link-caption")%></a>  <br><br>
		</div>
	</form>
</div>
