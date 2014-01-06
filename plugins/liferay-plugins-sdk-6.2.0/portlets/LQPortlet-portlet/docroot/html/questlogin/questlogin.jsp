<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<portlet:defineObjects />

<script type="text/javascript">

</script>
<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>


<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean" scope="request" />


<div style="overflow: hidden" class="contentWrapper_lq">
	<h2 style="margin-left:20px"><%=rb.getString("quest-view-portlet-heading")%></h2>
	<br>
	<div class="contentWrapper_lq"
		style="width: 50%; float: left; overflow: hidden">
		<img src="${questBean.photoURL}"
			alt="" style="margin-left:20px"></img> 
			<p style="margin-left:20px"> ${questBean.firstName} </p>
	</div>
  
	<div class="contentWrapper_lq" style="width: 50%; float: right; overflow: hidden">
		 <c:forEach items="${questList}" var="quest">
			<c:if test="${quest.questType eq 'IMAGE'}">
			    <br><br>
			     <p style="margin-left:20px">Title: ${quest.quest_title} Type: ${quest.questType} </p> <br>
				<img src="${quest.questLocation}" alt=""></img>
				<input id="image-file" type="file" />
			</c:if>
			
			<c:if test="${quest.questType eq 'VIDEO' || quest.questType eq 'AUDIO'}">
				<br><br>
				 <p style="margin-left:20px">Title: ${quest.quest_title} Type: ${quest.questType} </p>
				<video width="320" height="240" controls>
		  		<source src="${quest.questLocation}" type="video/mp4">
		 		 <%=rb.getString("quest-view-portlet-video-msg")%>
			</video>
			<input id="image-file" type="file" />
			</c:if>
		   </c:forEach>
		   
		   <aui:button-row>
			<aui:button type="submit" onClick="doSubmit();" value='<%=rb.getString("quest-login-portlet-btn1-caption")%>' />
			</aui:button-row>
			<a href='/web/guest/addQuestPage'><%=rb.getString("quest-addquest-link-caption")%></a>
	</div>
  
</div>
