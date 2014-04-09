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
		$("textarea[id^=comment]").val('');
	});

	function doDisplay(divId) {
		$(".quest").hide();
		$("video").each(function() {
			this.pause();
		});
		$('#' + divId).show();
	}
	
	function openCommentBox(questid) {
		$("#comment_inputs_" + questid).css("display", "block");
	}

	function saveComment(questTransId){
		
		$("input#questTransId").val(questTransId);
		$("input#addedBy").val($("#addedBy_" + questTransId).val());
		$("input#comment").val($("#comment_" + questTransId).val());
		
		//alert("questTransId = " + $("input#questTransId").val() + "\naddedBy = " + $("input#addedBy").val() + "\ncomment = " + $("input#comment").val());
		$("form#questCommentForm").submit();
	}
	
	function closeComment(questid) {
		$("#comment_" + questid).val('');
		$("#addedBy_" + questid).val('');
		$("#comment_inputs_" + questid).css("display", "none");
	}
	
	function toggleComments(questid) {
		var linkTitle = $("#viewCommentsLink_" + questid).text();
		if (linkTitle == "View Comments") {
			$("#viewCommentsLink_" + questid).text("Close Comments");
			$("#viewComments_" + questid).css("display", "block");
		} else if (linkTitle == "Close Comments") {
			$("#viewCommentsLink_" + questid).text("View Comments");
			$("#viewComments_" + questid).css("display", "none");
		}
	}
</script>

<%
	java.util.ResourceBundle rb = LQPortalUtil.getResourceBundle(request);
	PortalUtil.setPageTitle("Quests", request);
%>

<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean" scope="request" />
<portlet:actionURL name="submitQuestComment" var="submitQuestCommentURL" />

<form id="questCommentForm" 
	name="questCommentForm" 
	action="<%=submitQuestCommentURL.toString()%>" 
	method="post"
	enctype="multipart/form-data">
	
	<input type="hidden" name="questTransId" id="questTransId" value ="" />
	<input type="hidden" name="addedBy" id="addedBy" value ="" />
	<input type="hidden" name="comment" id="comment" value ="" />
	<input type="hidden" name="questId" id="questId" value ="${questId}" />
	<input type="hidden" name="userId" id="userId" value ="${userId}" />
</form>

<div class="Table">
	<div class="Row">
		<div class="Cell-a">
			<h2><%=rb.getString("quest-view-portlet-heading")%></h2>
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
<!-- Displaying Comments -->
<%
String bgcolor = "background-color: lightyellow;";
%>
							<div class="Row">
								<c:if test="${not empty quest.commentList}">

								
<!-- 									<a id="viewCommentsLink_${quest.id}" href="#!" onclick="toggleComments(${quest.id})">View Comments</a>
									<div id="viewComments_${quest.id}" style="display:none;">
 -->
									<div>
										<strong>Comments:</strong>
										<hr/>
										<c:forEach items="${quest.commentList}" var="comment">
											<div class="Table">
												<div class="Row">
													<div class="Cell-a" style="<%=bgcolor%>">
														<strong>${comment.addedBy} : </strong>${comment.comment}
														<br/>
														<div style="font-size:0.75em;"><i>${comment.addedOn}</i></div>
													</div>
												</div>
											</div>
<%
if (bgcolor == "background-color: lightyellow;") {
	bgcolor = "background-color: lightgoldenrodyellow;";
} else {
	bgcolor = "background-color: lightyellow;";
}
%>
										</c:forEach>
									</div>
								</c:if>
							</div>
<!-- Adding Comment button -->
							<div class="Row">
								<div class="Cell-a">
									<input type="button" onclick="openCommentBox(${quest.id});" value="Add Comment"/>
								</div>
							</div>
<!-- Adding Comment inputs -->
							<div class="Row" id="comment_inputs_${quest.id}" style="display:none;">
								<div class="Cell-a">
									<label>Comment:</label>
									<textarea 
										id="comment_${quest.id}" 
										name="comment_${quest.id}" 
										required="required" 
										maxlength="1024" 
										style="height:100px;width:360px;background-color:white;">
									</textarea>
									<label>Your Name:</label>
									<input type="text" id="addedBy_${quest.id}" name="addedBy_${quest.id}" style="width:360px;" required="required"/>
									<br/>
									<input type="submit" value="Save Comment" onclick="saveComment(${quest.id});"/>
									<input type="button" value="Cancel" onclick="closeComment(${quest.id});" />
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
<!-- Displaying Comments -->
<%
String bgcolor = "background-color: lightyellow;";
%>
							<div class="Row">
								<c:if test="${not empty quest.commentList}">

								
<!-- 									<a id="viewCommentsLink_${quest.id}" href="#!" onclick="toggleComments(${quest.id})">View Comments</a>
									<div id="viewComments_${quest.id}" style="display:none;">
 -->
									<div>
										<strong>Comments:</strong>
										<hr/>
										<c:forEach items="${quest.commentList}" var="comment">
											<div class="Table">
												<div class="Row">
													<div class="Cell-a" style="<%=bgcolor%>">
														<strong>${comment.addedBy} : </strong>${comment.comment}
														<br/>
														<div style="font-size:0.75em;"><i>${comment.addedOn}</i></div>
													</div>
												</div>
											</div>
<%
if (bgcolor == "background-color: lightyellow;") {
	bgcolor = "background-color: lightgoldenrodyellow;";
} else {
	bgcolor = "background-color: lightyellow;";
}
%>
										</c:forEach>
									</div>
								</c:if>
							</div>
<!-- Adding Comment button -->
							<div class="Row">
								<div class="Cell-a">
									<input type="button" onclick="openCommentBox(${quest.id});" value="Add Comment"/>
								</div>
							</div>
<!-- Adding Comment inputs -->
							<div class="Row" id="comment_inputs_${quest.id}" style="display:none;">
								<div class="Cell-a">
									<label>Comment:</label>
									<textarea 
										id="comment_${quest.id}" 
										name="comment_${quest.id}" 
										required="required" 
										maxlength="1024" 
										style="height:100px;width:360px;background-color:white;">
									</textarea>
									<label>Your Name:</label>
									<input type="text" id="addedBy_${quest.id}" name="addedBy_${quest.id}" style="width:360px;" required="required"/>
									<br/>
									<input type="submit" value="Save Comment" onclick="saveComment(${quest.id});"/>
									<input type="button" value="Cancel" onclick="closeComment(${quest.id});" />
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
<!-- Displaying Comments -->
<%
String bgcolor = "background-color: lightyellow;";
%>
							<div class="Row">
								<c:if test="${not empty quest.commentList}">

								
<!-- 									<a id="viewCommentsLink_${quest.id}" href="#!" onclick="toggleComments(${quest.id})">View Comments</a>
									<div id="viewComments_${quest.id}" style="display:none;">
 -->
									<div>
										<strong>Comments:</strong>
										<hr/>
										<c:forEach items="${quest.commentList}" var="comment">
											<div class="Table">
												<div class="Row">
													<div class="Cell-a" style="<%=bgcolor%>">
														<strong>${comment.addedBy} : </strong>${comment.comment}
														<br/>
														<div style="font-size:0.75em;"><i>${comment.addedOn}</i></div>
													</div>
												</div>
											</div>
<%
if (bgcolor == "background-color: lightyellow;") {
	bgcolor = "background-color: lightgoldenrodyellow;";
} else {
	bgcolor = "background-color: lightyellow;";
}
%>
										</c:forEach>
									</div>
								</c:if>
							</div>
<!-- Adding Comment button -->
							<div class="Row">
								<div class="Cell-a">
									<input type="button" onclick="openCommentBox(${quest.id});" value="Add Comment"/>
								</div>
							</div>
<!-- Adding Comment inputs -->
							<div class="Row" id="comment_inputs_${quest.id}" style="display:none;">
								<div class="Cell-a">
									<label>Comment:</label>
									<textarea 
										id="comment_${quest.id}" 
										name="comment_${quest.id}" 
										required="required" 
										maxlength="1024" 
										style="height:100px;width:360px;background-color:white;">
									</textarea>
									<label>Your Name:</label>
									<input type="text" id="addedBy_${quest.id}" name="addedBy_${quest.id}" style="width:360px;" required="required"/>
									<br/>
									<input type="submit" value="Save Comment" onclick="saveComment(${quest.id});"/>
									<input type="button" value="Cancel" onclick="closeComment(${quest.id});" />
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