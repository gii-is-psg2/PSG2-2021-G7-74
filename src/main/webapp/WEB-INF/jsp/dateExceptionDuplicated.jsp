<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<petclinic:layout pageName="errorpage">
	
	<div class = "page-body">
	    <spring:url value="/resources/images/oops.jpg" var="petsImage"/>
	    <img src="${petsImage}" width="300" height="300" />
	
	    <h2><fmt:message key="error.message.date"/></h2>
	
	    <p>${exception.message}</p>
	    
	    <spring:url value="/owners?lastName=" var="comeback"></spring:url>
           	<a href="${fn:escapeXml(comeback)}" class="btn btn-default"><fmt:message key="error.message.comeback"/></a>
				      
			
	</div>
</petclinic:layout>
