<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
	<div class = "page-body">
	    <h2 class = "page-title" id = "nuevo-dueno-title">
	        <c:if test="${owner['new']}"><fmt:message key="createOrUpdateOwnerForm.title.new"/> </c:if> <fmt:message key="createOrUpdateOwnerForm.title.owner"/>
	    </h2>
	    <form:form modelAttribute="owner" class="form-horizontal" id="add-owner-form">
	        <div class="form-group has-feedback">
	        <fmt:message key="createOwnerForm.label.firstName" var = "firstNameFmt"/>
	        	<fmt:message key="createOrUpdateOwnerForm.label.lastName" var = "lastNameFmt"/>
	        	<fmt:message key="createOrUpdateOwnerForm.label.address" var = "addressFmt"/>
	        	<fmt:message key="createOrUpdateOwnerForm.label.city" var = "cityFmt"/>
	        	<fmt:message key="createOrUpdateOwnerForm.label.telephone" var = "telephoneFmt"/>
	        	<fmt:message key="createOrUpdateOwnerForm.label.username" var = "usernameFmt"/>
	        	<fmt:message key="createOrUpdateOwnerForm.label.password" var = "passwordFmt"/>
	            <petclinic:inputField label="${firstNameFmt}" name="firstName"/>
	            <petclinic:inputField label="${lastNameFmt}" name="lastName"/>
	            <petclinic:inputField label="${addressFmt}" name="address"/>
	            <petclinic:inputField label="${cityFmt}" name="city"/>
	            <petclinic:inputField label="${telephoneFmt}" name="telephone"/>
	            <petclinic:inputField label="${usernameFmt}" name="user.username"/>
	            <petclinic:inputField label="${passwordFmt}" name="user.password"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <c:choose>
	                    <c:when test="${owner['new']}">
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateOwnerForm.button.add"/></button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateOwnerForm.button.update"/></button>
	                    </c:otherwise>
	                </c:choose>
	            </div>
	        </div>
	    </form:form>
    </div>
</petclinic:layout>
