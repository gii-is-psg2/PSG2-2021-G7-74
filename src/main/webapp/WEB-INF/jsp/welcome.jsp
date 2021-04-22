<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
	<div class = "welcome-body">
		<fmt:message key="welcome" var="ownerWelcome"/>
	    <h2 class = "welcome-message">${owner.firstName} <b> </b> ${ownerWelcome}</h2>
		<spring:url value="/resources/images/lince.png" htmlEscape="true" var="linceImage"/>
		<img class="bg-image" src="${linceImage}"/>

	</div>
</petclinic:layout>
