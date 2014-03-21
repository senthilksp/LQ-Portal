<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<%
PortalUtil.setPageTitle("Forgot Password", request);
java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
%>

<script type="text/javascript">


function doSubmit() {
	document.forgetpasswordForm.submit();
}
</script>

<portlet:defineObjects />

<div class="contentWrapper_lq">
	<br>
	<div class="interiorBanner1">
		<h1><%=rb.getString("header-portlet-heading")%></h1>
	</div>
	<br>
	<br>
</div>
<div class="contentWrapper_lq" style="width:800px;padding-left:20px;">
<h2 >Forgot Password</h2>
<br>
<br>

	<portlet:actionURL name="submitForgetPassword"
		var="submitForgetPasswordURL" />
	<aui:form action="<%= submitForgetPasswordURL.toString() %>" method="post"
		name="forgetpasswordForm" id="forgetpasswordForm"> 
		
		<c:choose>
			<c:when test="${(validUser== true)}">
				<br>
				<h6>Please check your email and follow instructions to change
					your password.</h6>
			</c:when>
			
			<c:when test="${(validUser== false)}">
				<br>
				<h6>Email does not exists in our database.<br>
				    Or, call our Customer Care to access your account</h6>
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