<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">
	
	<div class = "page-body">
		
	    <h2 class = "page-title"><fmt:message key="ownerDetails.title.name"/></h2>
	
	
	    <table class="table table-striped">
	        <tr>
	            <th><fmt:message key="ownerDetails.name"/></th>
	            <td><b><c:out value="${owner.firstName} ${owner.lastName}"/></b></td>
	        </tr>
	        <tr>
	            <th><fmt:message key="ownerDetails.address"/></th>
	            <td><c:out value="${owner.address}"/></td>
	        </tr>
	        <tr>
	            <th><fmt:message key="ownerDetails.city"/></th>
	            <td><c:out value="${owner.city}"/></td>
	        </tr>
	        <tr>
	            <th><fmt:message key="ownerDetails.telephone"/></th>
	            <td><c:out value="${owner.telephone}"/></td>
	        </tr>
	    </table>
	
	    <sec:authorize access="hasAuthority('admin')">
	    	<spring:url value="{ownerId}/edit" var="editUrl">
	        	<spring:param name="ownerId" value="${owner.id}"/>
	    	</spring:url>
	    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="ownerDetails.petsAndVisits.owner.button.update"/></a>
	
	    	<spring:url value="{ownerId}/pets/new" var="addUrl">
	       	 <spring:param name="ownerId" value="${owner.id}"/>
	   		</spring:url>
	   		<a href="${fn:escapeXml(addUrl)}" class="btn btn-default"><fmt:message key="ownerDetails.petsAndVisits.pet.button.add"/></a>
	        	      
   			<spring:url value="/owners/delete/${owner.id}" var="deleteOwner"></spring:url>
        		<a href="${fn:escapeXml(deleteOwner)}" class="btn btn-default"><fmt:message key="owner.deleteOwner"/></a>
	    	<br/>
	    	<br/>
	    	<br/>
	    	</sec:authorize>
	    
	    <h2><fmt:message key="ownerDetails.title.petsAndVisits"/></h2>
	
	    <table class="table table-striped">
	        <c:forEach var="pet" items="${owner.pets}">
	
	            <tr>
	                <td valign="top">
	                    <dl class="dl-horizontal">
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.name"/></dt>
	                        <dd><c:out value="${pet.name}"/></dd>
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.birthDate"/></dt>
	                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.type"/></dt>
	                        <dd><c:out value="${pet.type.name}"/></dd>
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.adoptable"/></dt>
	                        <dd><c:out value="${pet.adoptable}"/></dd>
	                    </dl>
	                </td>
	                <td valign="top">
	                    <table class="table-condensed">
	                        <thead>
	                        <tr>
	                            <th><fmt:message key="ownerDetails.petsAndVisits.pet.visit.date"/></th>
	                            <th><fmt:message key="ownerDetails.petsAndVisits.pet.visit.description"/></th>
	                        </tr>
	                        </thead>
	                        <c:forEach var="visit" items="${pet.visits}">
	                            <tr>
	                                <td><petclinic:localDate date="${visit.date}" pattern="yyyy-MM-dd"/></td>
	                                <td><c:out value="${visit.description}"/></td>
	                            </tr>
	                        </c:forEach>
	                    </table>
	                </td>
	                
	                <td valign="top">
	                    <table class="table-condensed">
	                        <thead>
	                        <tr>
	                            <th><fmt:message key="ownerDetails.petsAndVisits.pet.hotelBook.startDate"/></th>
	                            <th><fmt:message key="ownerDetails.petsAndVisits.pet.hotelBook.endDate"/></th>
	                        </tr>
	                        </thead>
	                        <c:forEach var="hotelBook" items="${pet.hotelBooks}">
	                            <tr>
	                                <td><petclinic:localDate date="${hotelBook.startDate}" pattern="yyyy-MM-dd"/></td>
	                                <td><petclinic:localDate date="${hotelBook.endDate}" pattern="yyyy-MM-dd"/></td>
	                            </tr>
	                        </c:forEach>
	                        
	                    </table>
	                </td>
	                <td>
                        <table>
                        	<thead>
                        	<tr>
                        		<th><fmt:message key="header.acciones"/></th>
                        	</tr>
                        	</thead>
                            <tr>
	                          	<td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/hotelBooks/new" var="hotelBookUrl">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(hotelBookUrl)}"><fmt:message key="ownerDetails.petsAndVisits.pet.hotelBook.button.add"/></a>
	                            </td>
                            </tr>
                            <tr>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/edit" var="petUrl">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(petUrl)}"><fmt:message key="ownerDetails.petsAndVisits.pet.visit.edit.pet"/></a>
	                            </td>
                            </tr>
                            <tr>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/visits/new" var="visitUrl">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(visitUrl)}"><fmt:message key="ownerDetails.petsAndVisits.pet.visit.button.add"/></a>
	                            </td>
                            </tr>
                            <tr>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/pets/delete/{petId}" var="deletePet">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(deletePet)}"><fmt:message key="owner.deletePet"/></a>
	                           	</td>
                           	</tr>
                           	<tr>
	                            <td>
	                                <spring:url value="/owners/{ownerId}/pets/{petId}/toogleAdoptable" var="togglePetAdoptable">
	                                    <spring:param name="ownerId" value="${owner.id}"/>
	                                    <spring:param name="petId" value="${pet.id}"/>
	                                </spring:url>
	                                <a href="${fn:escapeXml(togglePetAdoptable)}">
	                                	<c:if test="${!pet.adoptable}"><fmt:message key="owner.setAdoptable"/></c:if>
	                                	<c:if test="${pet.adoptable}"><fmt:message key="owner.cancelAdoptable"/></c:if>                        	
	                                </a>
	                           	</td>
                           	</tr>
                           	<c:if test="${pet.adoptable}">
	                           	<tr>
		                            <td>
		                                <spring:url value="/owners/{ownerId}/adoptions/pets/{petId}" var="adoptionRequests">
		                                    <spring:param name="ownerId" value="${owner.id}"/>
		                                    <spring:param name="petId" value="${pet.id}"/>
		                                </spring:url>
		                                <a href="${fn:escapeXml(adoptionRequests)}"><fmt:message key="owner.petAdoptionRequest"/></a>
		                           	</td>
	                           	</tr>
                           	</c:if>
                     	</table>
                    </td>
	            </tr>
				
	        </c:forEach>
	    </table>
	    
	    
	    
	    
	    
	
		<h2><fmt:message key="ownerDetails.title.adoptions"/></h2>
		<table class="table table-striped">
	        <c:forEach var="adoption" items="${owner.adoptions}">	
			     <tr>
	                <td valign="top">
	                    <dl class="dl-horizontal">
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.name"/></dt>
	                        <dd><c:out value="${adoption.pet.name}"/></dd>
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.birthDate"/></dt>
	                        <dd><petclinic:localDate date="${adoption.pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
	                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.type"/></dt>
	                        <dd><c:out value="${adoption.pet.type.name}"/></dd>
	                    </dl>
	                </td>
	                
	                <td valign="top">
	                     <table class="table-condensed">
	                        <thead>
	                        <tr>
	                            <th><fmt:message key="ownerDetails.petsAndVisits.adoptions.date"/></th>
	                        </tr>
	                        </thead>
                            <tr>
                                <td><petclinic:localDate date="${adoption.date}" pattern="yyyy-MM-dd"/></td>
                            </tr>
	                    </table>
	                   
	                </td>
	                
	                 <td valign="top">
	                    <table class="table-condensed">
	                        <thead>
	                        <tr>
	                            <th><fmt:message key="ownerDetails.petsAndVisits.adoptions.status"/></th>
	                        </tr>
	                        </thead>
	                        <tr>
                                <td><c:out value="${adoption.status}"/></td>
                            </tr>
	                    </table>
	                </td>
	                
	                <td valign="top">
	                    <table class="table-condensed">
                        	<thead>
                        	<tr>
                        		<th><fmt:message key="header.acciones"/></th>
                        	</tr>
                        	</thead>
                        	<c:if test="${adoption.status == 'EN_PROCESO'}">
	                        	<tr>
		                          	<td>
		                                <spring:url value="/owners/{ownerId}/adoptions/{adoptionId}/delete" var="adoptionDeleteUrl">
		                                    <spring:param name="adoptionId" value="${adoption.id}"/>
		                                    <spring:param name="ownerId" value="${owner.id}"/>     
		                                </spring:url>
		                                <a href="${fn:escapeXml(adoptionDeleteUrl)}"><fmt:message key="ownerDetails.petsAndVisits.pet.adoption.button.delete"/></a>
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
