<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>

<portlet:defineObjects />
<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>

<script type="text/javascript">
jQuery(document).ready(function() {
	jQuery("#questname-error").hide();
});

function doSubmit() {
   
/* 	if(jQuery("input[name=questName]").val()=='' ) {
		jQuery("#questname-error").show();
	} else {
		jQuery("#questname-error").hide();
		//document.questForm.submit();
	} */
}

</script>

	<div class="contentWrapper_lq">
		<h2><%=rb.getString("quest-addquest-title")%></h2>
		<portlet:actionURL name="submitQuestDetails" var="submitQuestDetailsURL" />
		
		<liferay-ui:success key="quest-added-successfully" message='<%=rb.getString("quest-addquest-success-msg")%>'/>
		<liferay-ui:error   key="quest-add-failed" message='<%=rb.getString("quest-addquest-failure-msg")%>'/>
		
		<form id="<portlet:namespace />questForm" name="<portlet:namespace />questForm" 
			action="<%=submitQuestDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
			<br>
			<div style="margin:0px 0px 0px 20px;">
				<%=rb.getString("quest-addquest-name")%> &nbsp;&nbsp;&nbsp;
				<input type="text" name="questName"  id="<portlet:namespace />questName" > 
				<span style="color: red; font: 12px/14px Arial" id="questname-error">This field is required</span>
				
				<br><%=rb.getString("quest-addquest-definition")%>
				<input type="text" name="<portlet:namespace />questDefinition" class="required" id="<portlet:namespace />questDefinition" > <br>
				
				<%=rb.getString("quest-addquest-accessmode")%>
				<input type="radio"name= "<portlet:namespace />accessMode" value="Public" checked="checked" /> Public 
				<input type="radio"name= "<portlet:namespace />accessMode" value="Public"  /> Private <br><br><br>
				
				<%=rb.getString("quest-addquest-image-type")%><br>
				<input type="file" name="<portlet:namespace />image_fileName" /> <br><br><br>
				
				<%=rb.getString("quest-addquest-audio-type")%><br>
				<input type="file" name="<portlet:namespace />audio_fileName" /> <br><br><br>
				
				<%=rb.getString("quest-addquest-video-type")%><br>
				<input type="file" name="<portlet:namespace />video_fileName" /> <br><br><br>
				<input type="submit" id="btnSubmit" name="btnSubmit" value='<%=rb.getString("quest-addquest-btn-save")%>' />
				<br>
		</div>	 
		</form>
	</div>	
		
		
