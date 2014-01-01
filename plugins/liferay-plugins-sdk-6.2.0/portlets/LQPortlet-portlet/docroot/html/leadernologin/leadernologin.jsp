<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>


<portlet:defineObjects />
	<div class="contentWrapper_lq">
		<h2>Leaders and Quests</h2>
		<br>
		<table>
			<c:forEach items="${leaderList}" var="leader">
				<tr>
					<td><img src="${leader.photoURL}" alt="" ></img>
					<td>
					<ul>
					<c:forEach items="${leader.questList}" var="quest">
					    <c:if test="${roleName == null}">
				    	 <li><a href="/web/guest/questdetails-viewpage?userId=${leader.userid}&questId=${quest.questId}">${quest.questTitle}</a></li> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	  <li><a href="/web/guest/questdetails-editpage">${quest.questTitle}</a></li> 
				    </c:if>
					</c:forEach>
					</ul>
				</tr>
				<tr>
				    <td> 
				    <c:if test="${roleName == null}">
				    	<a href="/web/guest/leaderdetails-viewpage?userId=${leader.userid}">${leader.firstname}</a> 
				    </c:if>
				    <c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
				    	<a href="/web/guest/leaerdetails-editpage">${leader.firstname}</a> 
				    </c:if>	
				    </td>
				</tr>	
				
			</c:forEach>
		</table>
	
	</div>

