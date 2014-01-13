<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>

<script type="text/javascript">


function doSubmit() {
	alert('inside');
	document.forgetpasswordForm.submit();
}
</script>

<portlet:defineObjects />

<div class="white-shadowed">
<h3 style="lq-h1"> Forgot Password </h3>

	<portlet:actionURL name="submitForgetPassword"
		var="submitForgetPasswordURL" />
	<aui:form action="<%= submitForgetPasswordURL.toString() %>" method="post"
		name="forgetpasswordForm" id="forgetpasswordForm"> 
		
		<c:choose>
			<c:when test="${(validUser== true)}">
				<br>
				<h4>Please check your email and follow instructions to change
					your password.</h4>
			</c:when>
			
			<c:when test="${(validUser== false)}">
				<br>
				<h4>Not a valid email. Please check.<br>
				    Or, call our Customer Care to access your account:</h4>
			</c:when>
			<c:otherwise>

			</c:otherwise>
		</c:choose>
		
		<c:if test="${(validUser== false || validUser==null)}">
			<aui:input label="Email Address" name="emailId" id="emailId" type="text">
			</aui:input>
		</c:if>	
		<aui:button-row>
				<aui:button type="submit" value="Next" />
			</aui:button-row>
	</aui:form>

</div>