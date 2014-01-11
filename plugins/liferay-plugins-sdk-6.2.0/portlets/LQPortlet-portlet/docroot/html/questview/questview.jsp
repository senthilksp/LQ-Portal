<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil"%>

<portlet:defineObjects />

<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>


<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean"
	scope="request" />


<div style="overflow: hidden" class="contentWrapper_lq">
	<h2 style="margin-left: 20px"><%=rb.getString("quest-view-portlet-heading")%></h2>
	<br> <br>
	
	<div class="contentWrapper_lq"
		style="width: 50%; float: left; overflow: hidden">
		<img src="${questBean.photoURL}" alt="" style="margin-left: 20px"></img>
		<p style="margin-left: 20px">${questBean.firstName}</p>
	</div>

	<div class="contentWrapper_lq"
		style="width: 50%; float: right; overflow: hidden">
		<h5> Quest Details </h5>
		<p> Quest Title       : ${questBean.quest_title} </p> 
		<p> Quest Definition  : ${questBean.definition} </p> 
		
		<h6>  List of Quests :</h6>
		<c:forEach items="${questList}" var="quest">
			<c:if test="${quest.questType eq 'IMAGE'}">
				<img src="${quest.questLocation}" alt=""></img>
			</c:if>
		</c:forEach>	
		
		<c:forEach items="${questList}" var="quest">
			<c:if
				test="${quest.questType eq 'VIDEO' || quest.questType eq 'AUDIO'}">
				<br>
				<br>
				<video id="lq_video" class="video-js vjs-default-skin" controls
					preload="none" width="300" height="264"
					poster=""
					data-setup="{{}}">
					<source src="${quest.questLocation}" type='video/mp4' />
				</video>
			</c:if>
		</c:forEach>
	</div>
	<br>
	<br>
</div>
