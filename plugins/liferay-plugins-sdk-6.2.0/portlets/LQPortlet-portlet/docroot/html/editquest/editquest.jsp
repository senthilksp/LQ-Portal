<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil"%>
<%@ page import="com.cti.lq.Constants.LQPortalConstants"%>

<portlet:defineObjects />
<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean"
	scope="request" />

<script type="text/javascript">
	$(document).ready(function() {

	});

	function doSubmit(x) {
		jQuery("input[name=id1]").val(x);
		document.<portlet:namespace />questForm.submit();
	}
	
	function doDelete(x) {
		jQuery("input[name=id1]").val(x);
		jQuery("input[name=delId]").val("DELETE");
		document.<portlet:namespace />questForm.submit();
	}	
	
	function btnMasterSave() {
		jQuery("input[name=delId]").val("MASTER");
		document.<portlet:namespace />questForm.submit();
	}	
</script>

<%
	java.util.ResourceBundle rb = LQPortalUtil
			.getResourceBundle(request);
%>

<div style="overflow: hidden" class="contentWrapper_lq" >
	<br>
	<div class="contentWrapper_lq"
		style="width: 50%; float: left; overflow: hidden">
		<br> <img src="${questBean.photoURL}" alt=""
			style="margin-left: 20px"></img>
		<p style="margin-left: 20px">${questBean.firstName}</p>
	</div>

	<liferay-ui:success key="quest-edited-successfully"
		message='<%=rb.getString("quest-editquest-success-msg")%>' />

	<portlet:actionURL name="submitQuestDetails"
		var="submitQuestDetailsURL" />
	<form id="<portlet:namespace />questForm"
		name="<portlet:namespace />questForm"
		action="<%=submitQuestDetailsURL.toString()%>" method="post"
		enctype="multipart/form-data">

		<div class="contentWrapper_lq"
			style="width: 50%; float: right; overflow: hidden">
			<h2>Edit Quest</h2>
			<c:forEach items="${questMasterList}" var="questMaster">
				<br>
				Quest Title : <br>
				<input type="text" name="questName" required="required"
					id="<portlet:namespace />questName"
					value="${questMaster.questTitle}">
				<br>
				Quest Definition : <br>
				<input type="text" name="questDefinition" required="required"
					id="<portlet:namespace />questDefinition"
					value="${questMaster.questDefinition}">
				<input type="hidden" name="questId"
					id="<portlet:namespace />questId" value="${questMaster.questId}">

				<input type="submit" id="btnMasterSave" name="btnMasterSave" value=<%=rb.getString("quest-edit-button-caption")%> onclick="doMasterSave();" />
				<c:forEach items="${questListAll}" var="questTransaction">
					<c:if test="${questMaster.questId == questTransaction.quest_id}">
						<input type="hidden" name="delId" id="<portlet:namespace />delId" />
						<c:if test="${questTransaction.questType eq 'IMAGE'}">
							<br>
							<br>
							<img src="${questTransaction.questLocation}" alt=""></img>
							<br>
							<input type="hidden" name="id1" id="<portlet:namespace />id1" />
							<input type="file" name="${questTransaction.questTransId}"
								id="${questTransaction.questTransId}" accept="image/*"/>
							<input type="button" id="btnEdit" name="btnEdit" value=<%=rb.getString("quest-edit-button-caption")%>
								onclick="doSubmit('${questTransaction.questTransId}');" />
							<input type="button" id="btnDelete" name="btnDelete" value=<%=rb.getString("quest-delete-button-caption")%>
								onclick="doDelete('${questTransaction.questTransId}');" />	
						</c:if>
						<c:if test="${questTransaction.questType eq 'AUDIO'}">
							<video id="lq_video" class="video-js vjs-default-skin" controls
								preload="none" width="300" height="264"
								poster=""
								data-setup="{{}}">
								<source src="${questTransaction.questLocation}" type='video/mp4' />
							</video>
							<input type="hidden" name="id1" id="<portlet:namespace />id1" />"
							<input type="file" name="${questTransaction.questTransId}"
								id="${questTransaction.questTransId}" />
							<input type="button" id="btnEdit" name="btnEdit" value=<%=rb.getString("quest-edit-button-caption")%>
								onclick="doSubmit('${questTransaction.questTransId}');" />
							<input type="button" id="btnDelete" name="btnDelete" value=<%=rb.getString("quest-delete-button-caption")%>
								onclick="doSubmit('${questTransaction.questTransId}'');" />		
						</c:if>
						<c:if test="${questTransaction.questType eq 'VIDEO' }">
							<br>
							<br>
							<video id="lq_video" class="video-js vjs-default-skin" controls
								preload="none" width="300" height="264"
								poster=""
								data-setup="{{}}">
								<source src="${questTransaction.questLocation}" type='video/mp4' />
							</video>
							<input type="file" name="${questTransaction.questTransId}"
								id="${questTransaction.questTransId}" />
							<input type="button" id="btnEdit" name="btnEdit" value=<%=rb.getString("quest-edit-button-caption")%>
								onclick="doSubmit('${questTransaction.questTransId}');" />
							<input type="button" id="btnDelete" name="btnDelete" value=<%=rb.getString("quest-delete-button-caption")%>
								onclick="doSubmit('${questTransaction.questTransId}');" />		
							<br>
							<br>
							<br>
						</c:if>
					</c:if>
				</c:forEach>
				<h2></h2>
			</c:forEach>
			<a href='/web/guest/addQuestPage'><%=rb.getString("quest-addquest-link-caption")%></a>  <br>
		</div><br>
	</form>
</div>


