<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<portlet:defineObjects />

<%
java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Add Quest", request);
%>

<script type="text/javascript">
jQuery(document).ready(function() {
	jQuery("#questname-error1").hide();
	jQuery("#questname-error2").hide();
});

function doSubmit() {
	
	if(($('#questName').val() == '') || $('#questDefinition').val()=='') {
		jQuery("#questname-error1").show();
	}
	else if(($('#image_fileName').val() == '') && $('#audio_fileName').val()=='' && $('#video_fileName').val()=='') {
		jQuery("#questname-error1").hide();
		jQuery("#questname-error2").show();
	
	} else {
		jQuery("#questname-error1").hide();
		jQuery("#questname-error2").hide();
		document.<portlet:namespace />questForm.submit();
	} 
}

</script>
<portlet:actionURL name="submitQuestDetails" var="submitQuestDetailsURL" />
<div class="Table">
	<div class="Row">
		<div class="Cell-a"><h2><%=rb.getString("quest-addquest-title")%></h2>
		</div>
		<div class="Cell-a">
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-a1">
			<liferay-ui:success key="quest-added-successfully" message='<%=rb.getString("quest-addquest-success-msg")%>'/>
			<liferay-ui:error   key="quest-add-failed" message='<%=rb.getString("quest-addquest-failure-msg")%>'/>
		</div>
	</div>
</div>
<form id="<portlet:namespace />questForm" name="<portlet:namespace />questForm" 
	action="<%=submitQuestDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
	<div class="Table">
		<div class="Row">
			<div class="Cell-b3"><%=rb.getString("quest-addquest-name")%>
			</div>
			<div class="Cell-b4"><input type="text" name="questName"  id="questName" >
			</div>
		</div>
		<div class="Row">
			<div class="Cell-b3"><%=rb.getString("quest-addquest-definition")%>
			</div>
			<div class="Cell-b4">
				<input type="text" name="<portlet:namespace />questDefinition" required="required" id="questDefinition" >
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-a1">
				<span style="color: red; font: 12px/14px Arial" id="questname-error1">Quest Name or Definition Should not be Blank</span>
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-b3"><%=rb.getString("quest-addquest-accessmode")%>
			</div>
			<div class="Cell-b4">
				<div class="Table">
					<div class="Row">
						<div class="Cell-b3" style="padding-left:0px; width:75px"><input type="radio" name= "<portlet:namespace />accessMode" value="PUBLIC" checked="checked" />&nbsp;&nbsp;Public
						</div>
						<div class="Cell-b3" style="padding-left:0px; width:75px"><input type="radio" name= "<portlet:namespace />accessMode" value="PRIVATE" />&nbsp;&nbsp;Private<br><br>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-a1">
				<span style="color: red; font: 12px/14px Arial" id="questname-error2">Either Audio or Video Or Image Should be Selected</span>
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-b3"><%=rb.getString("quest-addquest-image-type")%>
			</div>
			<div class="Cell-b4"><input type="file" name="<portlet:namespace />image_fileName" id="image_fileName"/><br><br>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-b3"><%=rb.getString("quest-addquest-audio-type")%>
			</div>
			<div class="Cell-b4"><input type="file" name="<portlet:namespace />audio_fileName" id="audio_fileName"/><br><br>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-b3"><%=rb.getString("quest-addquest-video-type")%>
			</div>
			<div class="Cell-b4"><input type="file" name="<portlet:namespace />video_fileName" id="video_fileName" /><br><br>
			</div>
		</div>
		<div class="Row">
			<div class="Cell-b3">
				<input type="button" id="btnSubmit" name="btnSubmit" value='<%=rb.getString("quest-addquest-btn-save")%>' onclick="doSubmit();" />
			</div>
			<div class="Cell-b4">
			</div>
		</div>
	</div>
</form>