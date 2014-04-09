<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil"%>
<%@ page import="com.cti.lq.Constants.LQPortalConstants"%>

<portlet:defineObjects />
<jsp:useBean id="questBean" class="com.cti.lq.beans.QuestViewBean"
	scope="request" />

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
	
	function doSubmit(x) {
		
		var fileChosen = jQuery("input[type=file][name=" + x + "]").val();
		
		if (fileChosen == "") {
			alert("Please choose a file.");
			return false;
		}
		jQuery("input[name=id1]").val(x);
		jQuery("input[name=delId]").val("MODIFY");
		
		document.<portlet:namespace />questForm.submit();
	}
	
	function doDelete(x) {
		jQuery("input[name=id1]").val(x);
		jQuery("input[name=delId]").val("DELETE");
		document.<portlet:namespace />questForm.submit();
	}	
	
	function doMasterSave() {
		jQuery("input[name=delId]").val("MASTER");
		document.<portlet:namespace />questForm.submit();
	}
	
	function doMasterDelete() {
		jQuery("input[name=delId]").val("DELETEALL");
		document.<portlet:namespace />questForm.submit();
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
	java.util.ResourceBundle rb = LQPortalUtil
			.getResourceBundle(request);
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

<portlet:actionURL name="submitQuestDetails" var="submitQuestDetailsURL" />
<div class="Table">
	<div class="Row">
		<div class="Cell-a">
			<h2>Edit Quest</h2>
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-a1">
			<liferay-ui:success key="quest-edited-successfully"	message='<%=rb.getString("quest-editquest-success-msg")%>' />
		</div>
	</div>
</div>
<div class="Table">
	<div class="Row">
		<div class="Cell-a">
			<p>
				<img src="${questBean.photoURL}" alt=""></img>
				<br>
				${questBean.firstName}
			</p>
		</div>
		<form id="<portlet:namespace />questForm"
			name="<portlet:namespace />questForm"
			action="<%=submitQuestDetailsURL.toString()%>" method="post"
			enctype="multipart/form-data">
			<input type="hidden" name="delId" id="<portlet:namespace />delId" />
			<div class="Cell-a">
				<div class="Table">
					<c:forEach items="${questMasterList}" var="questMaster">
						<input type="hidden" name="questId" id="<portlet:namespace />questId" value="${questMaster.questId}">
						<input type="hidden" name="accessMode" id="<portlet:namespace />questId" value="${questMaster.accessMode}">
						<div class="Row">
							<div class="Cell-a">
								Quest Title :
								<br>
								<input type="text" name="questName" required="required"
									id="<portlet:namespace />questName"
									value="${questMaster.questTitle}"
									style="width:260px;"> 
							</div>
						</div>
						<div class="Row">
							<div class="Cell-a">
								Quest Definition :
								<br>
								<textarea id="<portlet:namespace />questDefinition" name="<portlet:namespace />questDefinition" required="required" maxlength="1024" style="height:100px;width:260px;background-color:white;">
									value="${questMaster.questDefinition}"
								</textarea>
							</div>
						</div>
						<div class="Row">
							<div class="Cell-a">
								<input type="submit" id="btnMasterSave" name="btnMasterSave"
									value=<%=rb.getString("quest-edit-button-caption")%>
									onclick="doMasterSave();" />
								<input type="button" id="btnMasterDelete" name="btnMasterDelete"
									value=<%=rb.getString("quest-del-button-caption")%>
									onclick="" />
								<div id="dialog" title="Confirmation">
									Are you sure to delete this entire Quest?
								</div>
								<script>
									$( "#dialog" ).dialog({ autoOpen: false });
									$( "#dialog" ).dialog( "option", "buttons", [ { text: "Yes", click: function() { doMasterDelete(); } }, { text: "No", click: function() { $( this ).dialog( "close" ); } }  ] );
									$( "#btnMasterDelete" ).click(function() {
										$( "#dialog" ).dialog( "open" );
									});
								</script>
							</div>
						</div>
						<div class="Row">
							<div class="Cell-a">
								<h5>Pictures</h5>
							</div>
						</div>
						<c:forEach items="${questListAll}" var="questTransaction">
							<c:if test="${questMaster.questId == questTransaction.quest_id}">
								<c:if test="${questTransaction.questType eq 'IMAGE'}">
									<div class="Row">
										<div class="Cell-a">
											<br>
											<img src="${questTransaction.questLocation}" alt=""></img>
											<br>
											<input type="hidden" name="id1" id="<portlet:namespace />id1" />
											<input type="file" name="${questTransaction.id}"
												id="${questTransaction.questTransId}" accept="image/*" />
											<input type="button" id="btnEdit" name="btnEdit"
												value=<%=rb.getString("quest-edit-button-caption")%>
												onclick="doSubmit('${questTransaction.id}');" />
											<input type="button" id="btnDelete" name="btnDelete"
												value=<%=rb.getString("quest-delete-button-caption")%>
												onclick="doDelete('${questTransaction.id}');" />
										</div>
									</div>
												
<!-- Displaying Comments -->
<%
String bgcolor = "background-color: lightyellow;";
%>
									<div class="Row">
										<c:if test="${not empty questTransaction.commentList}">
		
										
<!-- 									<a id="viewCommentsLink_${quest.id}" href="#!" onclick="toggleComments(${quest.id})">View Comments</a>
									<div id="viewComments_${quest.id}" style="display:none;">
 -->
											<div>
												<strong>Comments:</strong>
												<hr/>
												<c:forEach items="${questTransaction.commentList}" var="comment">
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
											<input type="button" onclick="openCommentBox(${questTransaction.id});" value="Add Comment"/>
										</div>
									</div>
<!-- Adding Comment inputs -->
									<div class="Row" id="comment_inputs_${questTransaction.id}" style="display:none;">
										<div class="Cell-a">
											<label>Comment:</label>
											<textarea 
												id="comment_${questTransaction.id}" 
												name="comment_${questTransaction.id}" 
												required="required" 
												maxlength="1024" 
												style="height:100px;width:360px;background-color:white;">
											</textarea>
											<label>Your Name:</label>
											<input type="text" id="addedBy_${questTransaction.id}" name="addedBy_${questTransaction.id}" style="width:360px;" required="required"/>
											<br/>
											<input type="button" value="Save Comment" onclick="saveComment(${questTransaction.id});"/>
											<input type="button" value="Cancel" onclick="closeComment(${questTransaction.id});" />
										</div>
									</div>
												
								</c:if>
						  	</c:if>	
						</c:forEach>
						<div class="Row">
							<div class="Cell-a">
								<h5>Audios</h5>
							</div>
						</div>
						<c:forEach items="${questListAll}" var="questTransaction">
							<c:if test="${questMaster.questId == questTransaction.quest_id}">	
								<c:if test="${questTransaction.questType eq 'AUDIO'}">
									<div class="Row">
										<div class="Cell-a">
											<a href='#!' onclick="doDisplay('${questTransaction.id}')">${questTransaction.qlocationForDisplay}</a>
											<div id="${questTransaction.id}" class="quest">
												<video id="lq_video" class="video-js vjs-default-skin" controls
													preload="none" width="300" height="50" poster=""
													data-setup="{{}}">
													<source src="${questTransaction.questLocation}"
														type='video/mp4' />
												</video>
												<input type="hidden" name="id1" id="<portlet:namespace />id1" />
												<input type="file" name="${questTransaction.id}"
													id="${questTransaction.id}" />
												<input type="button"
													id="btnEdit" name="btnEdit"
													value=<%=rb.getString("quest-edit-button-caption")%>
													onclick="doSubmit('${questTransaction.id}');" />
												<input
													type="button" id="btnDelete" name="btnDelete"
													value=<%=rb.getString("quest-delete-button-caption")%>
													onclick="doDelete('${questTransaction.id}');" />	
											</div>
										</div>
									</div>
<!-- Displaying Comments -->
<%
String bgcolor = "background-color: lightyellow;";
%>
									<div class="Row">
										<c:if test="${not empty questTransaction.commentList}">

								
<!-- 									<a id="viewCommentsLink_${quest.id}" href="#!" onclick="toggleComments(${quest.id})">View Comments</a>
									<div id="viewComments_${quest.id}" style="display:none;">
 -->
											<div>
												<strong>Comments:</strong>
												<hr/>
												<c:forEach items="${questTransaction.commentList}" var="comment">
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
											<input type="button" onclick="openCommentBox(${questTransaction.id});" value="Add Comment"/>
										</div>
									</div>
<!-- Adding Comment inputs -->
									<div class="Row" id="comment_inputs_${questTransaction.id}" style="display:none;">
										<div class="Cell-a">
											<label>Comment:</label>
											<textarea 
												id="comment_${questTransaction.id}" 
												name="comment_${questTransaction.id}" 
												required="required" 
												maxlength="1024" 
												style="height:100px;width:360px;background-color:white;">
											</textarea>
											<label>Your Name:</label>
											<input type="text" id="addedBy_${questTransaction.id}" name="addedBy_${questTransaction.id}" style="width:360px;" required="required"/>
											<br/>
											<input type="button" value="Save Comment" onclick="saveComment(${questTransaction.id});"/>
											<input type="button" value="Cancel" onclick="closeComment(${questTransaction.id});" />
										</div>
									</div>
									<br>	
								</c:if>
							</c:if>	
						</c:forEach>
						<div class="Row">
							<div class="Cell-a">
								<h5>Videos</h5>
							</div>
						</div>
						<c:forEach items="${questListAll}" var="questTransaction">
							<c:if test="${questMaster.questId == questTransaction.quest_id}">
								<c:if test="${questTransaction.questType eq 'VIDEO' }">
									<div class="Row">
										<div class="Cell-a">
											<br>
											<a href='#!' onclick="doDisplay('${questTransaction.id}')">${questTransaction.qlocationForDisplay}</a>
											<div id="${questTransaction.id}" class="quest">
												<video id="lq_video" class="video-js vjs-default-skin" controls
													preload="none" width="300" height="264" poster=""
													data-setup="{{}}">
													<source src="${questTransaction.questLocation}"
														type='video/mp4' />
												</video>
												<input type="file" name="${questTransaction.questTransId}"
													id="${questTransaction.questTransId}" />
												<input type="button"
													id="btnEdit" name="btnEdit"
													value=<%=rb.getString("quest-edit-button-caption")%>
													onclick="doSubmit('${questTransaction.id}');" />
												<input
													type="button" id="btnDelete" name="btnDelete"
													value=<%=rb.getString("quest-delete-button-caption")%>
												    onclick="doDelete('${questTransaction.id}');" />
											</div>
											<br>
										</div>
									</div>
<!-- Displaying Comments -->
<%
String bgcolor = "background-color: lightyellow;";
%>
									<div class="Row">
										<c:if test="${not empty questTransaction.commentList}">
		
										
<!-- 									<a id="viewCommentsLink_${quest.id}" href="#!" onclick="toggleComments(${quest.id})">View Comments</a>
									<div id="viewComments_${quest.id}" style="display:none;">
 -->
											<div>
												<strong>Comments:</strong>
												<hr/>
												<c:forEach items="${questTransaction.commentList}" var="comment">
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
											<input type="button" onclick="openCommentBox(${questTransaction.id});" value="Add Comment"/>
										</div>
									</div>
<!-- Adding Comment inputs -->
									<div class="Row" id="comment_inputs_${questTransaction.id}" style="display:none;">
										<div class="Cell-a">
											<label>Comment:</label>
											<textarea 
												id="comment_${questTransaction.id}" 
												name="comment_${questTransaction.id}" 
												required="required" 
												maxlength="1024" 
												style="height:100px;width:360px;background-color:white;">
											</textarea>
											<label>Your Name:</label>
											<input type="text" id="addedBy_${questTransaction.id}" name="addedBy_${questTransaction.id}" style="width:360px;" required="required"/>
											<br/>
											<input type="button" value="Save Comment" onclick="saveComment(${questTransaction.id});"/>
											<input type="button" value="Cancel" onclick="closeComment(${questTransaction.id});" />
										</div>
									</div>
								</c:if>
							</c:if>
						</c:forEach>
						<div class="Row">
							<div class="Cell-a">
								<a href='/web/guest/addQuestPage'><%=rb.getString("quest-addquest-link-caption")%></a>
							</div>
						</div>
					</c:forEach>
				</div>
			</div>
		</form>
	</div>
</div>