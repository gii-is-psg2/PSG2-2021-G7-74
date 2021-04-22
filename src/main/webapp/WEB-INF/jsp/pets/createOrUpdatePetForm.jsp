<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="owners">
    <jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#birthDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    	<div class = "page-body">
	        <h2 class = "page-title">
	            <c:if test="${pet['new']}"><fmt:message key="createOrUpdatePetForm.title.new"/> </c:if> <fmt:message key="createOrUpdatePetForm.title.pet"/>
	        </h2>
	        <form:form modelAttribute="pet"
	                   class="form-horizontal">
	            <input type="hidden" name="id" value="${pet.id}"/>
	            <c:if test="${pet['new']}"><input type="hidden" name="adoptable" value="false"/></c:if>
	            <c:if test="${!pet['new']}">  <input type="hidden" name="adoptable" value="${pet.id}"/></c:if>  
	            <div class="form-group has-feedback">
	                <div class="form-group">
	                    <label class="col-sm-2 control-label"><fmt:message key="createOrUpdatePetForm.owner"/> </label>
	                    <div class="col-sm-10">
	                        <c:out value="${pet.owner.firstName} ${pet.owner.lastName}"/>
	                    </div>
	                </div>
	                <fmt:message key="createOrUpdatePetForm.pet.name" var ="petNameFmt"/> 
	                <fmt:message key="createOrUpdatePetForm.pet.birthDate" var = "petBirthDayFmt"/>
	                <fmt:message key="createOrUpdatePetForm.pet.type" var = "petTypeFmt"/>  
	                <petclinic:inputField label="${petNameFmt}" name="name"/>
	                <petclinic:inputField label="${petBirthDayFmt}" name="birthDate"/>
	                <div class="control-group" id = "select-pet-type">
	                    <petclinic:selectField name="type" label="${petTypeFmt}" names="${types}" size="5"/>
	                </div>
	            </div>
	            <div class="form-group">
	                <div class="col-sm-offset-2 col-sm-10">
	                    <c:choose>
	                        <c:when test="${pet['new']}">
	                            <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdatePetForm.button.add"/></button>
	                        </c:when>
	                        <c:otherwise>
	                            <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdatePetForm.button.update"/></button>
	                        </c:otherwise>
	                    </c:choose>
	                </div>
	            </div>
	        </form:form>
	        <c:if test="${!pet['new']}">
	        </c:if>
        </div>
    </jsp:body>
</petclinic:layout>
