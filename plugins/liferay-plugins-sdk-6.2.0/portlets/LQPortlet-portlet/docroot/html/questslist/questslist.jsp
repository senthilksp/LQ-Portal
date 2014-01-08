<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>


<portlet:defineObjects />
	<div class="contentWrapper_lq">
		<h3>Leaders and Quests</h3>
		<table>
			<c:forEach items="${leaderList}" var="leader">
				<tr>
					<td><img src="${leader.photoURL}" alt="" ></img>
					<td>
				<tr>
					<td>${leader.firstname}</td>
				</tr>
			</c:forEach>
		</table>
	
	</div>
