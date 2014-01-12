<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>
<%@ page import="com.cti.lq.util.LQPortalUtil" %>

<portlet:defineObjects />

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />
	
<% java.util.ResourceBundle rb= LQPortalUtil.getResourceBundle(request);%>
<% String LQ_LEADER_DETAIL_EDIT_PAGE = com.cti.lq.Constants.LQPortalConstants.LQ_LEADER_DETAIL_EDIT_PAGE; %>	

	<div class="contentWrapper_lq">
		<h2 style="width:50%"><%=rb.getString("leader-view-portlet-heading")%></h2>
		<table>
		<tr> <td width="40%"><img src="${leaderBean.photoURL}" alt="" ></img> </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-name")%> </b> : </td> <td> ${leaderBean.firstname}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-facultyrole")%> </b> : </td> <td> ${leaderBean.facultyRole}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-primaryphone")%> </b> : </td> <td> ${leaderBean.primaryPhone}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-businessname")%> </b> : </td> <td> ${leaderBean.businessName}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-emailaddress")%> </b> : </td> <td> ${leaderBean.emailAddress}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-country")%> </b> : </td> <td> ${leaderBean.country}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-city")%> </b> : </td> <td> ${leaderBean.city}  </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-website")%> </b> : </td> <td> ${leaderBean.website} </td> </tr>
		<tr> <td> <b><%=rb.getString("leader-view-portlet-biostatement")%> </b> : </td> <td> ${leaderBean.bioStatement} </td> </tr>
		</table>
		 <c:if test="${canEdit eq true}">
			<a href='<%=LQ_LEADER_DETAIL_EDIT_PAGE%>?userId=${userId}' style="float:right">Edit Leader</a> 
			<br>
		</c:if>	
	</div>