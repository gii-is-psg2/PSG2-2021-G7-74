<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!--  >%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%-->

<petclinic:layout pageName="causes">
	
	<div class = "page-body" id = "find-causes-body">
		<div class = "title-and-button">
		    <h2 class = "page-title"><fmt:message key="findCauses.title"/></h2>
			<sec:authorize access="permitAll">
				<a class="btn btn-default" href='<spring:url value="/causes/new" htmlEscape="true"/>'>
					<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					<span class="addCause-btn-text" aria-hidden="true"><fmt:message key="findCauses.button.add"/></span>
				</a>
			</sec:authorize>
		</div>
	    
	    <form:form modelAttribute="cause" action="/causes" method="get" class="form-horizontal"
	               id="search-cause-form">
	        <div class="form-group" id = "name-group">
	            <div class="control-group" id="name">
	                <label class="col-sm-2 control-label"><fmt:message key="findCauses.name"/></label>
	                <div class="col-sm-10">
	                    <form:input class="form-control" path="name" size="30" maxlength="80"/>
	                    <span class="help-inline"><form:errors path="*"/></span>
	                </div>
	            </div>
	        </div>
	        <div class="form-group" id = "name-submit-group">
	            <div class="col-sm-offset-2 col-sm-10">
	                <button type="submit" class="btn btn-default">
	                	<span class="glyphicon glyphicon-search" aria-hidden="true"></span>
	                	<span class = "findCause-btn-text"><fmt:message key="findCause.button"/></span>
	                </button>
	            </div>
	        </div>
	
	    </form:form>
	
	    
	</div>
	
</petclinic:layout>
