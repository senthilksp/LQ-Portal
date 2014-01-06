<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.theme.PortletDisplay" %>


<portlet:defineObjects />

<script type="text/javascript">
	$("document").ready(function(){
		 alert('changed1!');
		 $('input[type=file]').change(function(){
		        alert('changed2');
		        var val=$('input[type=file]').val(); 
		        val = val.replace("c:\fakepath","");
		        document.getElementById("<portlet:namespace />destination").value = val;
		  });
	});
</script>


<div>
            <h3> Choose File to Upload in Server </h3>
            <portlet:actionURL name="submitUploadDetails" var="submitUploadDetailsURL" />
            <form id="<portlet:namespace />uploadForm" name="<portlet:namespace />uploadForm" action="<%=submitUploadDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
            	 Destination11 : 
            	<input type="text" name="<portlet:namespace />destination"  id="<portlet:namespace />destination" >
            	<input type="text" name="<portlet:namespace />questType"  id="<portlet:namespace />questType" >
            	<input type="text" name="<portlet:namespace />questName"  id="<portlet:namespace />questName" > 
                <input type="file" name="<portlet:namespace />fileName" />
                <input type="submit" value="upload" name="sbtn"  id = "sbtn" />
            </form>
        </div>


