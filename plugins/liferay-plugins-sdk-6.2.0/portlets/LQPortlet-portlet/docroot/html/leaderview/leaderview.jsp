<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="/WEB-INF/tld/liferay-ui.tld" prefix="liferay-ui"%>
<%@ taglib uri="http://alloy.liferay.com/tld/aui" prefix="aui"%>

<portlet:defineObjects />

<jsp:useBean id="leaderBean" class="com.cti.lq.beans.LeaderBean"
	scope="request" />

	<div class="contentWrapper_lq">
		<h2>Leader Details</h2>
		<table>
		<tr> <td width="40%"><img src="${leaderBean.photoURL}" alt="" ></img> </td> </tr>
		<tr> <td> <b> Name : </b> </td> <td> ${leaderBean.firstname}  </td> </tr>
		<tr> <td> <b>Faculty Role </b>  : </td> <td> ${leaderBean.facultyRole}  </td> </tr>
		<tr> <td> <b>Primary Phone </b> : </td> <td> ${leaderBean.primaryPhone}  </td> </tr>
		<tr> <td> <b>Business Name </b> : </td> <td> ${leaderBean.businessName}  </td> </tr>
		<tr> <td> <b>EmailAdderss </b>  : </td> <td> ${leaderBean.emailAddress}  </td> </tr>
		<tr> <td> <b>Country </b>       : </td> <td> ${leaderBean.country}  </td> </tr>
		<tr> <td> <b>City </b>          : </td> <td> ${leaderBean.city}  </td> </tr>
		<tr> <td> <b> Website </b>      : </td> <td> ${leaderBean.website} </td> </tr>
		<tr> <td> <b>Biostatement </b> : </td>  <td> ${leaderBean.bioStatement} </td> </tr>
		</table>
	</div>