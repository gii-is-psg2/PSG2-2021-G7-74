<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="owners">
	
	<div class = "page-body" id = "find-owners-body">
		<div class = "title-and-button">
		    <h2 class = "page-title"><fmt:message key="findOwners.title"/></h2>
			<sec:authorize access="hasAuthority('admin')">
				<a class="btn btn-default" href='<spring:url value="/owners/new" htmlEscape="true"/>'>
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span class="addOwner-btn-text" aria-hidden="true"><fmt:message key="findOwners.button.add"/></span>
				</a>
			</sec:authorize>
		</div>
	    
	    <form:form modelAttribute="owner" action="/owners" method="get" class="form-horizontal"
	               id="search-owner-form">
	        <div class="form-group" id = "lastName-group">
	            <div class="control-group" id="lastName">
	                <label class="col-sm-2 control-label"><fmt:message key="findOwners.lastName"/></label>
	                <div class="col-sm-10">
	                    <form:input class="form-control" path="lastName" size="30" maxlength="80"/>
	                    <span class="help-inline"><form:errors path="*"/></span>
	                </div>
	            </div>
	        </div>
	        <div class="form-group" id = "lastName-submit-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <button type="submit" class="btn btn-default">
	                	<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
	                	<span class = "findOwner-btn-text"><fmt:message key="findOwners.button"/></span>
	                </button>
	            </div>
	        </div>
	
	    </form:form>
	
	    
	</div>
	
</petclinic:layout>
