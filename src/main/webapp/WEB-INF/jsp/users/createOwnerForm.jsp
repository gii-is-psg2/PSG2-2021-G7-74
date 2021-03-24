<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>



<petclinic:layout pageName="owners">
	<div class = "page-body">
	    <h2 class = "page-title">
	        <c:if test="${owner['new']}"> <fmt:message key="createOwnerForm.new"/> </c:if> <fmt:message key="createOwnerForm.owner"/>
	    </h2>
	    <form:form modelAttribute="owner" class="form-horizontal" id="add-owner-form">
	        <div class="form-group has-feedback">
	        	<fmt:message key="createOwnerForm.label.firstName" var = "firstNameFmt"/>
	        	<fmt:message key="createOwnerForm.label.lastName" var = "lastNameFmt"/>
	        	<fmt:message key="createOwnerForm.label.address" var = "addressFmt"/>
	        	<fmt:message key="createOwnerForm.label.city" var = "cityFmt"/>
	        	<fmt:message key="createOwnerForm.label.telephone" var = "telephoneFmt"/>
	        	<fmt:message key="createOwnerForm.label.username" var = "usernameFmt"/>
	        	<fmt:message key="createOwnerForm.label.password" var = "passwordFmt"/>
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
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOwnerForm.button.add"/></button>
	                    </c:when>
	                    <c:otherwise>
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOwnerForm.button.update"/></button>
	                    </c:otherwise>
	                </c:choose>
	            </div>
	        </div>
	    </form:form>
    </div>
</petclinic:layout>
