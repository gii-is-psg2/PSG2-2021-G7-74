<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">

	<div class = "page-body">
	    <h2 class = "page-title"><fmt:message key="causeList.title"/></h2>
	
	    <table id="causesTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th style="width: 400px;"><fmt:message key="causesList.name"/></th>
	            <th style="width: 600px;"><fmt:message key="causesList.description"/></th>
	            <th><fmt:message key="causesList.budgetTarget"/></th>
	           	<th><fmt:message key="causesList.achievedTarget"/></th>
	            <th style="width: 120px"><fmt:message key="causesList.organization"/></th>
	            <th><fmt:message key="causesList.createDonation"/></th>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${selections}" var="cause">
	            <tr>
	                <td>
	                    <spring:url value="/causes/{causeId}" var="causeUrl">
	                        <spring:param name="causeId" value="${cause.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(causeUrl)}"><c:out value="${cause.name}"/></a>
	                </td>
	                <td>
	                    <c:out value="${cause.description}"/>
	                </td>
	                <td>
	                    <c:out value="${cause.budgetTarget}"/>
	                </td>
	               	<td>
	                     <c:out value="${cause.achievedTarget}"/>
	                </td>
	                <td>
	                    <c:out value="${cause.organization}"/>
	                </td>
	                <c:if test="${cause.causeActive}">
	                  <td>
	                    <spring:url value="/donation/{causeId}/donation/new" var="addUrl">
	        				<spring:param name="causeId" value="${cause.id}"/>
	    				</spring:url>
	    				<a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><fmt:message key="causeDetails.donation.button.add"/></a>
	                </td>
	                </c:if>
  	                <c:if test="${!cause.causeActive}">
  	                 <td>
  	                	<p style="color: #04c735;"><fmt:message key="causeDetails.causeClosed"/></p>
  	                </td>
	               	</c:if>
	                
	              

	                               
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </div>
</petclinic:layout>
