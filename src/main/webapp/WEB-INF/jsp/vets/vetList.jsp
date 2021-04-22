<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="vets">

	<div class = "page-body">
		<div class = "title-and-button" id = "vetList-title">
	    	<h2 class = "page-title"><fmt:message key="vetList.title"/></h2>
	    	
	    	<sec:authorize access="hasAuthority('admin')">
				<a class="btn btn-default" href='<spring:url value="/vets/new" htmlEscape="true"/>'>
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
						<span class="addOwner-btn-text" aria-hidden="true"><fmt:message key="vetList.button.add"/></span>
				</a>
			</sec:authorize>
		</div>
	
	
	    <table id="vetsTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th><fmt:message key="vetList.name"/></th>
	            <th><fmt:message key="vetList.specialties"/></th>
              	<sec:authorize access="hasAuthority('admin')"><th><fmt:message key="header.acciones"/></th></sec:authorize>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${vets.vetList}" var="vet">
	            <tr>
	                <td>
	                    <c:out value="${vet.firstName} ${vet.lastName}"/>
	                </td>
	                <td>
	                    <c:forEach var="specialty" items="${vet.specialties}">
	                        <c:out value="${specialty.name} "/>
	                    </c:forEach>
	                    <c:if test="${vet.nrOfSpecialties == 0}">none</c:if>
	                </td>
	                <sec:authorize access="hasAuthority('admin')">
	                <td>
                		<spring:url value="/vets/delete/${vet.id}" var="deleteVet"></spring:url>
               			<a href="${fn:escapeXml(deleteVet)}" class="btn btn-default"><fmt:message key="vet.deleteVet"/></a>
               			
               			<spring:url value="/vets/${vet.id}/edit" var="editVet"></spring:url>
               			<a href="${fn:escapeXml(editVet)}" class="btn btn-default"><fmt:message key="vet.editVet"/></a>
                	</td>
                	</sec:authorize>
                	
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
	
	    <table class="table-buttons">
	        <tr>
	            <td>
	                <a href="<spring:url value="/vets.xml" htmlEscape="true" />"><fmt:message key="vestList.button"/></a>
	            </td>            
	            
	        </tr>
	    </table>
    </div>
</petclinic:layout>
