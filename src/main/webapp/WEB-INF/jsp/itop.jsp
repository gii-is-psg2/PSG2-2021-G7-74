<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="itop">

	<div class = "page-body">
		<div class = "title-and-button" id = "vetList-title">
	    	<h2 class = "page-title"><fmt:message key="itop.title"/></h2>
		</div>
	

	    <table id="vetsTable" class="table table-striped">
	        <thead>
	        <tr>
	            <th><fmt:message key="itop.name"/></th>
	            <th><fmt:message key="itop.phone"/></th>
	          	<th><fmt:message key="itop.email"/></th>
	            
	        </tr>
	        </thead>
	        <tbody>
	        	<c:if test = "${contacts.isEmpty()}">
					<br><h3><fmt:message key="itop.noContacts"/></h3>
				</c:if>
	        <c:forEach items="${contacts}" var="contact">
	            <tr>
	                <td>
	                    <c:out value="${contact.name} "/>
	                </td>
	                <td>
	                   <c:out value="${contact.phone} "/>
	                </td>
					 <td>
	                   <c:out value="${contact.email} "/>
	                </td>
	            </tr>
	        </c:forEach>
	        </tbody>
	    </table>
    </div>
</petclinic:layout>