<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">
	
	<div class = "page-body">
		<h2 class = "page-title"><fmt:message key="petAdoptions.title.name"/></h2>
		
		<table class="table table-striped">
			<c:forEach var="adoption" items="${pet.adoptions}">
				<tr>
					<td valign="top">
						<table>
							<thead>
							<tr>
								<th><fmt:message key="header.applicant"/></th>
							</tr>
							</thead>
							<tr>
                                <td><c:out value="${adoption.applicant.firstName} ${adoption.applicant.lastName}"/></td>
                            </tr>
						</table>
					</td>
					<td valign="top">
						<table>
							<thead>
							<tr>
								<th><fmt:message key="header.description"/></th>
							</tr>
							</thead>
							<tr>
                                <td><c:out value="${adoption.description}"/></td>
                            </tr>
						</table>
					</td>
					<td valign="top">
						<table>
							<thead>
							<tr>
								<th><fmt:message key="header.date"/></th>
							</tr>
							</thead>
							<tr>
                                <td><c:out value="${adoption.date}"/></td>
                            </tr>
						</table>
					</td>
					<td valign="top">
						<table>
							<thead>
							<tr>
								<th><fmt:message key="header.status"/></th>
							</tr>
							</thead>
							<tr>
                                <td><c:out value="${adoption.status}"/></td>
                            </tr>
						</table>
					</td>
					<td valign="top">
						<table>
		                   	<thead>
		                   	<tr>
		                   		<th><fmt:message key="header.acciones"/></th>
		                   	</tr>
		                   	</thead>
		                   	<c:if test="${adoption.status == 'EN_PROCESO'}">
	                           	<tr>
		                            <td>
		                                <spring:url value="/owners/{ownerId}/adoptions/{adoptionId}/accept" var="acceptAdoption">
		                                    <spring:param name="adoptionId" value="${adoption.id}"/>
		                                </spring:url>
		                                <a href="${fn:escapeXml(acceptAdoption)}"><fmt:message key="petAdoptions.accept"/></a>
		                           	</td>
	                           	</tr>
	                           	<tr>
		                            <td>
		                                <spring:url value="/owners/{ownerId}/adoptions/{adoptionId}/deny" var="denyAdoption">
		                                    <spring:param name="adoptionId" value="${adoption.id}"/>
		                                </spring:url>
		                                <a href="${fn:escapeXml(denyAdoption)}"><fmt:message key="petAdoptions.deny"/></a>
		                           	</td>
	                           	</tr>
                           	</c:if>
		                </table>
	                </td>
                </tr>
			</c:forEach>
		</table>
	</div>

</petclinic:layout>