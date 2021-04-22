<%@page import="java.time.LocalDate"%>
<%@page import="java.util.Date"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<petclinic:layout pageName="owners">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#date").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
    
    <jsp:body>
	
	<div class="page-body">
		<h2 class = "page-title"><fmt:message key="adoptions.title"/></h2>
		
		<table class="table table-striped">
	        <c:forEach var="pet" items="${adoptablePet}">	
			     <tr>
			     	<form:form modelAttribute="adoption" id="add-adoption">
		                <td valign="top">
		                    <dl class="dl-horizontal">
		                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.name"/></dt>
		                        <dd><c:out value="${pet.name}"/></dd>
		                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.birthDate"/></dt>
		                        <dd><petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd"/></dd>
		                        <dt><fmt:message key="ownerDetails.petsAndVisits.pet.type"/></dt>
		                        <dd><c:out value="${pet.type.name}"/></dd>
		                    </dl>
		                </td>
		                             
		                 <td valign="top">
		                    <table class="table-condensed">
		                        <thead>
		                        <tr>
		                            <th><fmt:message key="header.description"/></th>
		                        </tr>
		                        </thead>
		                        <tr>
	                                <td>
	                                	<fmt:message key="adoptions.textarea.placeholder" var="placeholder"/>
	                               		<textarea 
	                               			rows=3 
	                               			style="resize: none; overflow: auto; width: 43vw; padding: 10px" 
	                               			placeholder="${placeholder}"
	                               			name="description"
	                               		></textarea>
	                               		<input type="hidden" name="petId" value="${pet.id}"/>
	                               		<input type="hidden" name="status" value="EN_PROCESO"/>                               		    
	                                </td>
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
	                        	<tr>
		                          	<td>
		                        		<button class="btn btn-default" type="submit"><fmt:message key="adoptions.submit"/></button>
		                            </td>
	                            </tr>
	                        </table>
	                    </td>
                    </form:form>	                
				</tr>			
	       </c:forEach>
		</table>
	</div>
	</jsp:body>
</petclinic:layout>