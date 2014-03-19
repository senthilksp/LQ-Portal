<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil"%>
<%@ page import="com.liferay.portal.util.PortalUtil"%>

<portlet:defineObjects />

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

<%
	java.util.ResourceBundle rb = LQPortalUtil
			.getResourceBundle(request);
	PortalUtil.setPageTitle("Quests", request);
%>

<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean"
	scope="request" />

<div class="Table">
	<div class="Row">
		<div class="Cell-a">
			<h2><%=rb.getString("quest-view-portlet-heading")%></h2>
		</div>
		<div class="Cell-a">
		</div>
	</div>
	<div class="Row">
		<div class="Cell-a" style="padding-left:20px;">
			<p>
				<img src="${questBean.photoURL}" alt=""></img><br/>
				${questBean.firstName}
			</p>
		</div>
		<div class="Cell-a">
			<div class="Table">
				<div class="Row">
					<div class="Cell-b">
						<h5>Quest Details</h5>
					</div>
					<div class="Cell-b"></div>
				</div>
				<div class="Row">
					<div class="Cell-b">
						<h5>Quest Title:</h5>
					</div>
					<div class="Cell-b">
						<p style="margin-top:10px;">${questBean.quest_title}</p>
					</div>
				</div>
				<div class="Row">
					<div class="Cell-b">
						<h5>Quest Definition:</h5>
					</div>
					<div class="Cell-b">
						<p style="margin-top:10px;">${questBean.definition}</p>
					</div>
				</div>
			</div>
			<div class="Table">
				<c:if test="${not empty questList }">
					<div class="Row">
						<div class="Cell-a">
							<h5>List of Quests :</h5>
						</div>
					</div>
					
<!-- List of Pictures -->
					<%
					String displayTitle = "yes";
					%>
					<c:forEach items="${questList}" var="quest">
						<c:if test="${quest.questType eq 'IMAGE'}">
							<%
							if (displayTitle.equalsIgnoreCase("yes")) {
							%>
							<div class="Row">
								<div class="Cell-a">
									<h5>Pictures</h5>
								</div>
							</div>
							<%
								displayTitle = "";
							}
							%>
							<div class="Row">
								<div class="Cell-a">
									<img src="${quest.questLocation}" alt=""></img>
								</div>
							</div>
						</c:if>
					</c:forEach>

<!-- List of Audios -->
					<%
					displayTitle = "yes";
					%>
					<c:forEach items="${questList}" var="quest">
						<c:if test="${quest.questType eq 'AUDIO'}">
							<%
							if (displayTitle.equalsIgnoreCase("yes")) {
							%>
							<div class="Row">
								<div class="Cell-a">
									<h5>Audio</h5>
								</div>
							</div>
							<%
								displayTitle = "";
							}
							%>
							<div class="Row">
								<div class="Cell-a">
									<a href='#!' onclick="doDisplay('${quest.id}')">${quest.qlocationForDisplay}</a>
									<br>
									<div id="${quest.id}" class="quest">
										<video id="lq_video" class="video-js vjs-default-skin" controls
											preload="none" width="300" height="75" poster=""
											data-setup="{{}}">
											<source src="${quest.questLocation}" type='video/mp4' />
										</video>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>

<!-- List of Videos -->
					<%
					displayTitle = "yes";
					%>
					<c:forEach items="${questList}" var="quest">
						<c:if test="${quest.questType eq 'VIDEO'}">
							<%
							if (displayTitle.equalsIgnoreCase("yes")) {
							%>
							<div class="Row">
								<div class="Cell-a">
									<h5>Video</h5>
								</div>
							</div>
							<%
								displayTitle = "";
							}
							%>
							<div class="Row">
								<div class="Cell-a">
									<a href='#!' onclick="doDisplay('${quest.id}')">${quest.qlocationForDisplay}</a>
									<br>
									<div id="${quest.id}" class="quest">
										<video id="lq_video" class="video-js vjs-default-skin" controls
											preload="none" width="300" height="250" poster=""
											data-setup="{{}}">
											<source src="${quest.questLocation}" type='video/mp4' />
										</video>
									</div>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</c:if>
			</div>
		</div>		
	</div>
	<div class="Row">
		<div class="Cell-a"><br><br>
		</div>
		<div class="Cell-a"><br><br>
		</div>
	</div>
</div>