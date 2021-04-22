<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="vets">

<div class="page-body">
		<h2 class="page-title">
		 <c:if test="${vet['new']}"><fmt:message key="vetForm.title"/> </c:if>
		
		</h2>
	
	<form:form modelAttribute="vet" class="form-horizontal" id="add-vet-form">
	
	
		<div class="form-group has-feedback">
		
		<fmt:message var="firstName" key="vetForm.firstName"/>
		<fmt:message var="lastName" key="vetForm.lastName"/>
		<fmt:message var="specialty" key="vetForm.specialties"/>
		<petclinic:inputField label="${firstName}" name="firstName"/>
	    <petclinic:inputField label="${lastName}" name="lastName"/>
		
		<div class="control-group">
			<petclinic:selectField name="specialties" label="${specialty}" names="${specialties}" size="5" multiple="true"/>
		</div>
		
		
		</div>
		
		<div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <c:choose>
	                    <c:when test="${vet['new']}">
	                        <button class="btn btn-default" type="submit"><fmt:message key="vetForm.add"/></button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit"><fmt:message key="vet.editVet"/></button>
	                    </c:otherwise>
	                </c:choose>
	            </div>
		
		</div>
	
	
	
	</form:form>
</div>

</petclinic:layout>