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


<portlet:defineObjects />


<%java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);
PortalUtil.setPageTitle("Quests Details", request);
%>

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
</form>

<portlet:actionURL name="submitQuestLoginDetails" var="submitQuestLoginDetailsURL" />
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
				<liferay-ui:success key="quest-added-successfully" message='<%=rb.getString("quest-addquest-success-msg")%>' />
			</div>
		</div>
		<div class="Row">
			<div class="Cell-a1">
				<liferay-ui:success key="quest-comment-added-successfully" message='<%=rb.getString("quest-comment-added-success-msg")%>' />
				<liferay-ui:error key="quest-comment-add-failed" message='<%=rb.getString("quest-comment-add-failed-msg")%>' />
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
						<div class="Cell-a">
							<strong>Quest Title :</strong>
							<br>
							${questBean.quest_title}
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a">
							<strong>Quest Definition :</strong>
							<br>
							${questBean.definition}
						</div>
					</div>
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
					<div class="Row">
						<div class="Cell-a">
							<label>Upload Picture :</label>
							<form id="<portlet:namespace />questLoginForm" name="<portlet:namespace />questLoginForm" 
								action="<%=submitQuestLoginDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
							
								<input type="hidden" name="<portlet:namespace />quest_id" id="<portlet:namespace />quest_id" value ="${questId}" />
								<input type="file" name="<portlet:namespace />image_fileName" />
								<input type="submit" id="btnSubmit1" name="btnSubmit1" value='Upload' />
							</form>
						</div>
					</div>
					<div class="Row">
						<div class="Cell-a"><h5>Audios</h5>
						</div>
					</div>
					<c:forEach items="${questList}" var="quest">
						<c:if test="${quest.questType eq 'AUDIO'}">
							<div class="Row">
								<div class="Cell-a">
									<div id="audiodiv" >
										<a href='#!' onclick="doDisplay('${quest.id}')">${quest.qlocationForDisplay}</a><br>
										<div id="${quest.id}" class="quest">
											<video id="lq_video" class="video-js vjs-default-skin" controls preload="none" width="300" height="50" poster="" data-setup="{{}}">
												<source src="${quest.questLocation}" type='video/mp4' />
											</video>
										</div>
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
									<input type="button" value="Cancel" onclick="closecomment(${quest.id});" />
								</div>
							</div>
						</c:if>
					</c:forEach>
					<div class="Row">
						<div class="Cell-a">
							<label>Upload Audio :</label>
							<form id="<portlet:namespace />questLoginForm" name="<portlet:namespace />questLoginForm" 
								action="<%=submitQuestLoginDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
							
								<input type="hidden" name="<portlet:namespace />quest_id" id="<portlet:namespace />quest_id" value ="${questId}" />
								<input type="file" name="<portlet:namespace />audio_fileName" />
								<input type="submit" id="btnSubmit2" name="btnSubmit2" value='Upload' />
							</form>
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
									<input type="button" value="Cancel" onclick="closecomment(${quest.id});" />
								</div>
							</div>
						</c:if>
					</c:forEach>
					<div class="Row">
						<div class="Cell-a">
							<label>Upload Video :</label>
							<form id="<portlet:namespace />questLoginForm" name="<portlet:namespace />questLoginForm" 
								action="<%=submitQuestLoginDetailsURL.toString()%>" method="post" enctype="multipart/form-data" >
							
								<input type="hidden" name="<portlet:namespace />quest_id" id="<portlet:namespace />quest_id" value ="${questId}" />
								<input type="file" name="<portlet:namespace />video_fileName" />
								<input type="submit" id="btnSubmit3" name="btnSubmit3" value='Upload' />
							</form>
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
