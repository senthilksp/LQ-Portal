<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>
<%@ page import="com.cti.lq.Constants.LQPortalConstants" %>
<%@ page import="com.liferay.portal.util.PortalUtil" %>

<script type="text/javascript">
$(document).ready(function() {
		$(".quest").hide();	
});

function doDisplay(divId) {
	$(".quest").hide();
	$("video").each(function() {
		this.pause();
	});
	$('#' + divId).show();
}
</script>


<portlet:defineObjects />


<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Quests", request);
%>
<portlet:actionURL name="submitQuestLoginDetails" var="submitQuestLoginDetailsURL" />

<form id="<portlet:namespace />questLoginForm" name="<portlet:namespace />questLoginForm" 
	action="<%=submitQuestLoginDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >

	<input type="hidden" name="<portlet:namespace />quest_id" id="<portlet:namespace />quest_id" value ="${questId}" />
	<div class="Table">
		<div class="Row">
			<div class="Cell-a">
				<h2>Quest Details</h2>
			</div>
		</div>
	</div>
	<div class="Table">
		<div class="Row">
			<div class="Cell-a1">
				<liferay-ui:success key="quest-added-successfully" message='<%=rb.getString("quest-editquest-success-msg")%>' />
			</div>
		</div>
	</div>

	<div class="Table">
		<div class="Row">
			<div class="Cell-a" style="padding-left:25px;">
				<p>
					<img src="${questBean.photoURL}" alt=""></img>
					<br/> 
					${questBean.firstName}
				</p>
			</div>
			<div class="Cell-a">
				<div class="Table">
					<div class="Row">
						<div class="Cell-a"><h5>Pictures</h5>
						</div>
					</div>
					<c:forEach items="${questList}" var="quest">
						<c:if test="${quest.questType eq 'IMAGE'}">
							<div class="Row">
								<div class="Cell-a">
									<img src="${quest.questLocation}" alt=""></img>
								</div>
							</div>
						</c:if>
					</c:forEach>
					<div class="Row">
						<div class="Cell-a">
							<input type="file" name="<portlet:namespace />image_fileName" />
							<input type="submit" id="btnSubmit1" name="btnSubmit1" value='Upload' />
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a"><h5>Audios</h5>
						</div>
					</div>
					<c:forEach items="${questList}" var="quest">
						<div class="Row">
							<div class="Cell-a">
								<c:if test="${quest.questType eq 'AUDIO'}">
									<div id="audiodiv" >
										<a href='#!' onclick="doDisplay('${quest.id}')">${quest.qlocationForDisplay}</a><br>
										<div id="${quest.id}" class="quest">
											<video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="50" poster="" data-setup="{{}}">
												<source src="${quest.questLocation}" type='video/mp4' />
											</video>
										</div>
									</div>
								</c:if>
							</div>
						</div>
					</c:forEach>
					<div class="Row">
						<div class="Cell-a">
							<input type="file" name="<portlet:namespace />audio_fileName" />
							<input type="submit" id="btnSubmit2" name="btnSubmit2" value='Upload' />
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a"><h5>Videos</h5>
						</div>
					</div>
					
					<c:forEach items="${questList}" var="quest">
						<c:if test="${quest.questType eq 'VIDEO'}">
							<div class="Row">
								<div class="Cell-a">
									<div id="videodiv" >
										<a href='#!' onclick="doDisplay('${quest.id}')">${quest.qlocationForDisplay}</a><br>
										<div id="${quest.id}" class="quest">
											<video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="264" poster="" data-setup="{{}}">
										    	<source src="${quest.questLocation}" type='video/mp4' />
											</video>
										</div>	
									</div>
								</div>
							</div>
						</c:if>
						
					</c:forEach>
					<div class="Row">
						<div class="Cell-a">
							<input type="file" name="<portlet:namespace />video_fileName" />
							<input type="submit" id="btnSubmit3" name="btnSubmit3" value='upload' />
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a"><br></div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<div class="Table">
								<div class="Row">
									<div class="Cell-b3" style="padding-left:0px;">
										<a href='/web/guest/editQuestPage?userId=${userId}&questId=${questId}'>Edit Quest</a>
									</div>
									<div class="Cell-b3">
										<a href='/web/guest/addQuestPage'><%=rb.getString("quest-addquest-link-caption")%></a>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>