<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">

	<div class = "page-body">
	    <h2 class = "page-title"><fmt:message key="ownerList.title"/></h2>
	
	    <table id="ownersTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th style="width: 150px;"><fmt:message key="ownerList.name"/></th>
	            <th style="width: 200px;"><fmt:message key="ownerList.address"/></th>
	            <th><fmt:message key="ownerList.city"/></th>
	            <th style="width: 120px"><fmt:message key="ownerList.telephone"/></th>
	            <th><fmt:message key="ownerList.pets"/></th>
	            <sec:authorize access="hasAuthority('admin')"><th><fmt:message key="header.acciones"/></th></sec:authorize>
	        </tr>
	        </thead>
	        <tbody>
	        <c:forEach items="${selections}" var="owner">
	            <tr>
	                <td>
	                    <spring:url value="/owners/{ownerId}" var="ownerUrl">
	                        <spring:param name="ownerId" value="${owner.id}"/>
	                    </spring:url>
	                    <a href="${fn:escapeXml(ownerUrl)}"><c:out value="${owner.firstName} ${owner.lastName}"/></a>
	                </td>
	                <td>
	                    <c:out value="${owner.address}"/>
	                </td>
	                <td>
	                    <c:out value="${owner.city}"/>
	                </td>
	                <td>
	                    <c:out value="${owner.telephone}"/>
	                </td>
	                <td>
	                    <c:forEach var="pet" items="${owner.pets}">
	                        <c:out value="${pet.name} "/>
	                    </c:forEach>
	                </td>
	                 <sec:authorize access="hasAuthority('admin')">
        	        <td>
                		<spring:url value="/owners/delete/${owner.id}" var="deleteOwner"></spring:url>
               			<a href="${fn:escapeXml(deleteOwner)}" class="btn btn-default"><fmt:message key="owner.deleteOwner"/></a>
                	</td>
	                </sec:authorize>
	      
	<!--
	                <td> 
	                    <c:out value="${owner.user.username}"/> 
	                </td>
	                <td> 
	                   <c:out value="${owner.user.password}"/> 
	                </td> 
	-->
	                
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </div>
</petclinic:layout>
