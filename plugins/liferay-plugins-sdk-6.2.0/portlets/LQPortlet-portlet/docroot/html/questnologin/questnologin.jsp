<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>


<portlet:defineObjects />
	
<div style="overflow: hidden" class="contentWrapper_lq">
	<h2>Quest Details</h2>
	<br> 
	
	<!-- AddThis Button BEGIN -->
		<div class="addthis_toolbox addthis_default_style ">
		<a class="addthis_button_facebook_like" fb:like:layout="button_count"></a>
		<a class="addthis_button_tweet"></a>
		<a class="addthis_button_pinterest_pinit" pi:pinit:layout="horizontal"></a>
		<a class="addthis_counter addthis_pill_style"></a>
		</div>
		<script type="text/javascript">var addthis_config = {"data_track_addressbar":true};</script>
		<script type="text/javascript" src="//s7.addthis.com/js/300/addthis_widget.js#pubid=ra-52ce7b2b414da24b"></script>
	<!-- AddThis Button END -->
	
	<table>
	<tr> <td>   </td>
	 	 <td>   </td>  </tr>
			<c:forEach items="${leaderList}" var="leader">
				<tr> 
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
						</ul> </td>
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
