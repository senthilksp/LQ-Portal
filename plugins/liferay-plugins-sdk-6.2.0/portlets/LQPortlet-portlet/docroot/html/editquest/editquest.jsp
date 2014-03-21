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
	});
	
	function doDisplay(divId) {
		$(".quest").hide();
		$("video").each(function() {
			this.pause();
		});
		$('#' + divId).show();
	}
	

	function doSubmit(x) {
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
</script>

<%
	java.util.ResourceBundle rb = LQPortalUtil
			.getResourceBundle(request);
%>
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
								<c:forEach items="${questListAll}" var="questTransaction">
									<c:if test="${questMaster.questId == questTransaction.quest_id}">
										<c:if test="${questTransaction.questType eq 'IMAGE'}">
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
										</c:if>
								  	</c:if>	
								</c:forEach>
							</div>
						</div>
						<div class="Row">
							<div class="Cell-a">
								<h5>Audios</h5>
								<c:forEach items="${questListAll}" var="questTransaction">
									<c:if test="${questMaster.questId == questTransaction.quest_id}">	
										<c:if test="${questTransaction.questType eq 'AUDIO'}">
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
											<br>	
										</c:if>
								  </c:if>	
							</c:forEach>
							</div>
						</div>
						<div class="Row">
							<div class="Cell-a">
								<h5>Videos</h5>
								<c:forEach items="${questListAll}" var="questTransaction">
									<c:if test="${questMaster.questId == questTransaction.quest_id}">
										<c:if test="${questTransaction.questType eq 'VIDEO' }">
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
										</c:if>
									</c:if>
								</c:forEach>
							</div>
						</div>
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