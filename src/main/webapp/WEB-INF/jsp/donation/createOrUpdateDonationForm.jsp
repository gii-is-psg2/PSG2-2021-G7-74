<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="donation">
	<div class = "page-body">
	    <h2 class = "page-title" id = "nuevo-dueno-title">
	        <c:if test="${donation['new']}"><fmt:message key="createOrUpdateDonationForm.title.new"/> </c:if> <fmt:message key="createOrUpdateDonationForm.title.donation"/>
	    </h2>
	    <form:form modelAttribute="donation" class="form-horizontal" id="add-donation-form">
	        <div class="form-group has-feedback">
	        <fmt:message key="createOrUpdateDonationForm.label.amount" var = "amount"/>
	        	<fmt:message key="createOrUpdateDonationForm.label.client" var = "client"/>
	            <petclinic:inputField label="${amount}" name="amount"/>
	            <petclinic:inputField label="${client}" name="client"/>
	        </div>
	        <div class="form-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <c:choose>
	                    <c:when test="${donation['new']}">
	                        <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateDonationForm.button.add"/></button>
	                    </c:when>
	                </c:choose>
	            </div>
	        </div>
	    </form:form>
    </div>
</petclinic:layout>
