<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>

<portlet:defineObjects />
<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean" scope="request" />

<script type="text/javascript">
$(document).ready(function () {
	
});

function doSubmit(x) {
		jQuery("input[name=id1]").val(x); 
	    document.<portlet:namespace />questForm.submit();
}

</script>

<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>

	<div style="overflow: hidden" class="contentWrapper_lq">
		<h2 style="margin-left:20px">Edit Quest</h2>
		<br>
		<div class="contentWrapper_lq"
		style="width: 50%; float: left; overflow: hidden">
		<img src="${questBean.photoURL}"
			alt="" style="margin-left:20px"></img> 
			<p style="margin-left:20px"> ${questBean.firstName} </p>
		</div>
	
	    <liferay-ui:success key="quest-edited-successfully" message='<%=rb.getString("quest-editquest-success-msg")%>'/>
		
	    <portlet:actionURL name="submitQuestDetails" var="submitQuestDetailsURL" />
	    	<form id="<portlet:namespace />questForm" name="<portlet:namespace />questForm" 
			action="<%=submitQuestDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
			
			<div class="contentWrapper_lq" style="width: 50%; float: right; overflow: hidden">
				  <c:forEach items="${questMasterList}" var="questMaster"><br>
				  	  <input type="text" name="questName"  id="<portlet:namespace />questName" 
				  		value = "${questMaster.questTitle}"  > <br>
				  	  <input type="text" name="questDefinition"  id="<portlet:namespace />questDefinition" 
				  		value = "${questMaster.questDefinition}"  > 
				  	  <input type="hidden" name="questId"  id="<portlet:namespace />questId" 
				  		value = "${questMaster.questId}"  >
				  		
				  	  <input type="submit" id="btnEdit" name="btnEdit" value='Edit' />	
				  		<br> <br>
				      <c:forEach items="${questListAll}" var="questTransaction">
				      	<c:if test="${questMaster.questId == questTransaction.quest_id}">
							<c:if test="${questTransaction.questType eq 'IMAGE'}">
						    	<br><br>
								<img src="${questTransaction.questLocation}" alt=""></img>
								<input type ="hidden" name="id1" id="<portlet:namespace />id1" />"
								<input type="file" name="${questTransaction.questTransId}" id="${questTransaction.questTransId}" /> 
								<input type="button" id="btnEdit" name="btnEdit" value='Edit' onclick="doSubmit('${questTransaction.questTransId}');" />
							</c:if>
							<c:if test="${questTransaction.questType eq 'AUDIO'}">
								<br><br>
								<video width="320" height="240" controls>
						  		<source src="${questTransaction.questLocation}" type="video/mp4">
						 		 <%=rb.getString("quest-view-portlet-video-msg")%>
							</video>
							<input type ="hidden" name="id1" id="<portlet:namespace />id1" />"
							<input type="file" name="<portlet:namespace />audio_fileName" id="<portlet:namespace />audio_fileName" /> 
							<input type="button" id="btnEdit" name="btnEdit" value='Edit' onclick="doSubmit('${questTransaction.questTransId}');" />
						</c:if>
						<c:if test="${questTransaction.questType eq 'VIDEO' }">
								<br><br>
								<video width="320" height="240" controls>
						  		<source src="${questTransaction.questLocation}" type="video/mp4">
						 		 <%=rb.getString("quest-view-portlet-video-msg")%>
							</video>
							<input type="file" name="<portlet:namespace />video_fileName" id="<portlet:namespace />video_fileName"/>
							<input type="button" id="btnEdit" name="btnEdit" value='Edit' onclick="doSubmit('${questTransaction.questTransId}');" />
							 <br><br><br>
						</c:if>
						
					 </c:if>	
				 	</c:forEach>
				 	<br>
				 	<br><br>
			  </c:forEach>
			</div>
		</form>	
	</div>
