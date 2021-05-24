<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
	
	<div class = "page-body">
		
	    <h2 class = "page-title"><fmt:message key="causesDetails.title.name"/></h2>
	
	
	    <table class="table table-striped">
	        <tr>
	            <th><fmt:message key="causeDetails.name"/></th>
	            <td><b><c:out value="${cause.name}"/></b></td>
	        </tr>
	        <tr>
	            <th><fmt:message key="causeDetails.description"/></th>
	            <td><c:out value="${cause.description}"/></td>
	        </tr>
	        <tr>
	            <th><fmt:message key="causeDetails.budgetTarget"/></th>
	            <td><c:out value="${cause.budgetTarget}"/></td>
	        </tr>

	        <tr>
	            <th><fmt:message key="causeDetails.organization"/></th>
	            <td><c:out value="${cause.organization}"/></td>
	        </tr>
	        
   	        <tr>
	            <th><fmt:message key="causes.progressBar"/></th>
	            <td><progress max="${cause.budgetTarget}" value="${cause.achievedTarget}"> </progress></td>
	        </tr>
	        
	    </table>
		<c:if test="${cause.causeActive}">
	    <spring:url value="/donation/{causeId}/donation/new" var="addUrl">
	        <spring:param name="causeId" value="${cause.id}"/>
	    </spring:url>
	    <a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><fmt:message key="causeDetails.donation.button.add"/></a>
	   	</c:if>
	    <br/>
	    <br/>	      
	    
		<c:if test="${cause.donations.size() == 0}">
		<h2><fmt:message key="causesDetails.donations.notFound"/></h2>
		</c:if>
		
		<c:if test="${cause.donations.size() >= 1}">
		<h2><fmt:message key="causesDetails.title.donations"/></h2>
	    	<table class="table table-striped">
	        <c:forEach var="donation" items="${cause.donations}">
			
	            <tr>
	                <td valign="top">
	                    <dl class="dl-horizontal">
	                        <dt><fmt:message key="donationDetails.amount"/></dt>
	                        <dd><c:out value="${donation.amount}"/></dd>
	                        <dt><fmt:message key="donationDetails.donation_date"/></dt>
	                        <dd><petclinic:localDate date="${donation.donationDate}" pattern="yyyy-MM-dd"/></dd>
	                        <dt><fmt:message key="donationDetails.client"/></dt>
	                        <dd><c:out value="${donation.client}"/></dd>
	                    </dl>
	                </td>
	                
	            </tr>
				
	        </c:forEach>
	    </table>
	   </c:if>
	</div>
</petclinic:layout>
