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
                $("#startDate").datepicker({dateFormat: 'yy/mm/dd'});
                $("#endDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
    	
    	<div class = "page-body">
        <h2 class = "page-title"><c:if test="${hotelBook['new']}"><fmt:message key="createOrUpdateHotelBookForm.title.new"/></c:if><fmt:message key="createOrUpdateHotelBookForm.title.hotelBook"/></h2>

	        <b><fmt:message key="createOrUpdateHotelBookForm.title.pet"/></b>
	        <table class="table table-striped">
	            <thead>
	            <tr>
	                <th><fmt:message key="createOrUpdateHotelBookForm.pet.name"/></th>
	                <th><fmt:message key="createOrUpdateHotelBookForm.pet.birthDate"/></th>
	                <th><fmt:message key="createOrUpdateHotelBookForm.pet.type"/></th>
	                <th><fmt:message key="createOrUpdateHotelBookForm.pet.owner"/></th>
	            </tr>
	            </thead>
	            <tr>
	                <td><c:out value="${hotelBook.pet.name}"/></td>
	                <td><petclinic:localDate date="${hotelBook.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
	                <td><c:out value="${hotelBook.pet.type.name}"/></td>
	                <td><c:out value="${hotelBook.pet.owner.firstName} ${hotelBook.pet.owner.lastName}"/></td>
	            </tr>
	        </table>
	
	        <form:form modelAttribute="hotelBook" class="form-horizontal">
	            <div class="form-group has-feedback">
	            <fmt:message key="createOrUpdateHotelBookForm.hotelBook.startDate" var = "startDate"/>
	            <fmt:message key="createOrUpdateHotelBookForm.hotelBook.endDate" var = "endDate"/>
	                <petclinic:inputField label="${startDate}" name="startDate"/>
	                <petclinic:inputField label="${endDate}" name="endDate"/>
	            </div>
	
	            <div class="form-group">
	                <div class="col-sm-offset-2 col-sm-10">
	                    <input type="hidden" name="petId" value="${hotelBook.pet.id}"/>
	                    <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateHotelBookForm.hotelBook.button.add"/></button>
	                </div>
	            </div>
	        </form:form>
	
	        <br/>
	        <b><fmt:message key="createOrUpdateHotelBookForm.hotelBook.previousHotelBooks"/></b>
	        <%--
	        <table class="table table-striped">
	            <tr>
	                <th><fmt:message key="createOrUpdateVisitForm.visit.previousVisit.date"/></th>
	                <th><fmt:message key="createOrUpdateVisitForm.visit.previousVisit.description"/></th>
	            </tr>
	            <c:forEach var="visit" items="${visit.pet.visits}">
	                <c:if test="${!visit['new']}">
	                    <tr>
	                        <td><petclinic:localDate date="${visit.date}" pattern="yyyy/MM/dd"/></td>
	                        <td><c:out value="${visit.description}"/></td>
	                    </tr>
	                </c:if>
	            </c:forEach>
	        </table>
	        --%>
        </div>
    </jsp:body>

</petclinic:layout>
