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
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    <jsp:body>
        <h2><c:if test="${visit['new']}"><fmt:message key="createOrUpdateVisitForm.title.new"/></c:if><fmt:message key="createOrUpdateVisitForm.title.visit"/></h2>

        <b><fmt:message key="createOrUpdateVisitForm.title.pet"/></b>
        <table class="table table-striped">
            <thead>
            <tr>
                <th><fmt:message key="createOrUpdateVisitForm.pet.name"/></th>
                <th><fmt:message key="createOrUpdateVisitForm.pet.birthDate"/></th>
                <th><fmt:message key="createOrUpdateVisitForm.pet.type"/></th>
                <th><fmt:message key="createOrUpdateVisitForm.pet.owner"/></th>
            </tr>
            </thead>
            <tr>
                <td><c:out value="${visit.pet.name}"/></td>
                <td><petclinic:localDate date="${visit.pet.birthDate}" pattern="yyyy/MM/dd"/></td>
                <td><c:out value="${visit.pet.type.name}"/></td>
                <td><c:out value="${visit.pet.owner.firstName} ${visit.pet.owner.lastName}"/></td>
            </tr>
        </table>

        <form:form modelAttribute="visit" class="form-horizontal">
            <div class="form-group has-feedback">
            <fmt:message key="createOrUpdateVisitForm.visit.date" var = "dateFmt"/>
            <fmt:message key="createOrUpdateVisitForm.visit.description" var = "descriptionFmt"/>
                <petclinic:inputField label="${dateFmt}" name="date"/>
                <petclinic:inputField label="${descriptionFmt}" name="description"/>
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="petId" value="${visit.pet.id}"/>
                    <button class="btn btn-default" type="submit"><fmt:message key="createOrUpdateVisitForm.visit.button.add"/></button>
                </div>
            </div>
        </form:form>

        <br/>
        <b><fmt:message key="createOrUpdateVisitForm.visit.previousVisit"/></b>
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
    </jsp:body>

</petclinic:layout>
