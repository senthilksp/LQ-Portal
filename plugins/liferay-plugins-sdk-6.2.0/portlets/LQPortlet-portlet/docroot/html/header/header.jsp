<%@ taglib uri="http://java.sun.com/portlet_2_0" prefix="portlet"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="liferay-ui" uri="/WEB-INF/tld/liferay-ui.tld"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<jsp:useBean id="LoggedUserBean" class="com.cti.lq.beans.LoggedUserBean"
	scope="request" />

<div class="contentWrapper_lq">
	<ul class="mainNav">
		<li class="level-1 here first" id="nav-sub-why-cti"><a
			href="/welcome">Home</a> <!--  Submenu  <ul>
					<li class="level-2 first" id="nav-sub-about-cti"><a href="http://www.thecoaches.com/why-cti/about-cti">About CTI</a></li>
					<li class="level-2" id="nav-sub-what-is-co-active"><a href="http://www.thecoaches.com/why-cti/what-is-co-active">What is Co-Active?</a></li>
				</ul> --></li>
		<c:if test="${roleName == null}">
			<li class="level-1" id="nav-sub-leadership"><a
			href="/web/guest/questdetails-no-login">Quest</a></li>
		</c:if>
		
		<c:if test="${roleName == null}">
			<li class="level-1" id="nav-sub-leadership"><a
			href="/web/guest/leaderdetails-no-login">Leader</a></li>
		</c:if>
		
		<c:if test="${roleName eq 'LEADER' || roleName eq 'LEADER_ADMIN'}">
			<li class="level-1" id="nav-sub-leadership"><a
			href="/web/guest/leaderdetails">Leader</a></li>
	    </c:if>
	    
		<li class="level-1" id="nav-sub-leadership" style="float: right"><a
			href="/c/portal/login" id="signinLink">Sign-in</a></li>
		<c:if test="${(isSignedIn == true)}">
			<li class="level-1" id="nav-sub-leadership1" style="float: right"><a
				href="/c/portal/logout">Sign-Out</a></li>
		</c:if>
	</ul>
	<div class="contentWrapper_lq">
		<div class="interiorBanner1">
			<h1>Leadership Quest</h1>
		</div>
	</div>
	<div class="contentWrapper_lq">
		<c:if test="${(isSignedIn == true)}">
			<h6 style="float: right">You are signed in as
				${LoggedUserBean.firstName} ${LoggedUserBean.lastName}</h6>
		</c:if>
	</div>
	<br>
	<br>
</div>

