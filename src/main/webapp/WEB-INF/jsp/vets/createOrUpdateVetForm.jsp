<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName=vets>

<h2>
 <c:if test="${vet['new']}"><fmt:message key="newVet"/> </c:if>

</h2>

<form:form modelAttribute="vet" class="form-horizontal" id=add-vet-form>


	<div class="form-group has-feedback">
	
	<fmt:message var="firstName" key="firstName"/>
	<fmt:message var="lastName" key="lastName"/>
	<fmt:message var="specialty" key="specialties"/>
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
                        <button class="btn btn-default" type="submit"><fmt:message key="addVet"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="updateVet"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
	
	



</form:form>


</petclinic:layout>