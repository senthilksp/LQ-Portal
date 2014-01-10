<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<portlet:defineObjects />

<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>


<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean" scope="request" />


<div style="overflow: hidden" class="contentWrapper_lq">
	<h2 style="margin-left:20px"><%=rb.getString("quest-view-portlet-heading")%></h2>
	<br>
	<br>		
	
	<!-- AddThis Button BEGIN -->
		<a class="addthis_button" href="http://www.addthis.com/bookmark.php?v=300&amp;pubid=ra-52ce7b2b414da24b"><img src="http://s7.addthis.com/static/btn/v2/lg-share-en.gif" width="125" height="16" alt="Bookmark and Share" style="border:0"/></a>
			    <script type="text/javascript">var addthis_config = {"data_track_addressbar":true};</script>
				<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-52ce7b2b414da24b"></script>
	<!-- AddThis Button END -->
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
				<img src="${quest.questLocation}" alt=""></img>
			</c:if>
			<c:if test="${quest.questType eq 'VIDEO' || quest.questType eq 'AUDIO'}">
				<br><br>
				 <video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="264"
								      poster="/LQTheme-theme/images/cti/bkgds/oceans-clip.png"
								      data-setup="{{}}">
								    <source src="${quest.questLocation}" type='video/mp4' />
								  </video>
			</c:if>
		 </c:forEach>
	</div>
<br><br>  
</div>
