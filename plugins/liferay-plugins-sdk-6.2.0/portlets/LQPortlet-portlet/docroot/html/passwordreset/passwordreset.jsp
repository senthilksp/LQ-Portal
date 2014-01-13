<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>

<script type="text/javascript">

$(document).ready(function() {
	jQuery("#password-error1").hide();
	jQuery("#password-error2").hide();
	jQuery("#password-error3").hide();
});


function doSubmit() {
	
	if(($('#newpass').val() == '') || $('#cnewpass').val()=='') {
		jQuery("#password-error1").show();
		jQuery("#password-error2").hide();
		jQuery("#password-error3").hide();
	}
	
	 else if(($('#newpass').val().length < 6) || $('#cnewpass').val().length< 6) {
		jQuery("#password-error1").hide();
		jQuery("#password-error2").show();
		jQuery("#password-error3").hide();
	}
	
	else if(($('#newpass').val() != $('#cnewpass').val())) {
		alert('inside');
		jQuery("#password-error1").hide();
		jQuery("#password-error2").hide()
		jQuery("#password-error3").show();
	} 
	
	else {
		alert('insise else');
		jQuery("#password-error1").hide();
		jQuery("#password-error2").hide();
		jQuery("#password-error3").hide();

		document.<portlet:namespace />passwordresetForm.submit();
	}
	    
}




</script>

<portlet:defineObjects />

<div class="white-shadowed">
<h2> Password Reset</h2>

	<portlet:actionURL name="submitPasswordReset"
		var="submitPasswordResetURL" />
	<form action="<%= submitPasswordResetURL.toString() %>" method="post"
		name="<portlet:namespace />passwordresetForm" id="<portlet:namespace />passwordresetForm" action="<%=submitPasswordResetURL.toString()%>" enctype="multipart/form-data" >
		 	<span style="color: red; font: 18px/20px Arial" id="password-error1">Password Fields Should be Blank</span> <br> <br> 	 
	        <span style="color: red; font: 18px/20px Arial" id="password-error2">Password Fields Should have minimum 6 Characters</span> <br> <br>
	        <span style="color: red; font: 18px/20px Arial" id="password-error3">Both Password should be equal</span> <br> <br>
	   	<table>
	   	<tr>
		   	<td> Password </td>
		   	<td> <input name="newpass" id="newpass" type="password" maxlength="30"/> </td>
		</tr>   	
		<tr>
		   	<td> Confirm Password </td>
		   	<td> <input name="cnewpass" id="cnewpass" type="password" maxlength="30"/> </td>
		   	<td> <input type="hidden" id="email" name="email" value="${email}" > </td>
		</tr>  
	   	</table>
	   	
		<aui:button-row>
				<aui:button id="btnSubmit"  value="Save"   onclick="doSubmit();"/>
				<aui:button id="btnCancel"  value="Cancel" onclick="doCancel();"/>
			</aui:button-row>
	</form>

</div>