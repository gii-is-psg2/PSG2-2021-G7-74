<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
	<div class = "page-body">
	    <h2 class = "page-title" id = "nuevo-dueno-title">
	        <c:if test="${cause['new']}"><fmt:message key="createOrUpdateCausesForm.title.new"/> </c:if> <fmt:message key="createOrUpdateCausesForm.title.cause"/>
	    </h2>
	    <form:form modelAttribute="cause" class="form-horizontal" id="add-owner-form">
	        <div class="form-group has-feedback">
	        <fmt:message key="createOrUpdateCausesForm.label.name" var = "name"/>
	        	<fmt:message key="createOrUpdateCausesForm.label.description" var = "description"/>
	        	<fmt:message key="createOrUpdateCausesForm.label.budgetTarget" var = "budgetTarget"/>
	        	<fmt:message key="createOrUpdateCausesForm.label.organization" var = "organization"/>
	            <petclinic:inputField label="${name}" name="name"/>
	            <petclinic:inputField label="${description}" name="description"/>
	            <petclinic:inputField label="${budgetTarget}" name="budgetTarget"/>
	            <petclinic:inputField label="${organization}" name="organization"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <c:choose>
	                    <c:when test="${cause['new']}">
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateCausesForm.button.add"/></button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateCausesForm.button.update"/></button>
	                    </c:otherwise>
	                </c:choose>
	            </div>
	        </div>
	    </form:form>
    </div>
</petclinic:layout>
