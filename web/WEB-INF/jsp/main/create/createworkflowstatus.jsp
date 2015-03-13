<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@page import="com.crucialticketing.util.WorkflowStatusType"%>
<%@page import="com.crucialticketing.entities.WorkflowStatus"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>Workflow Status creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in the form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the workflow status</li>
        <li>System will send you a message confirming creation</li>
    </ul>

    Estimated Time:
    <ul>
        <li>To fill out form: 20 seconds
        <li>System response: 5 seconds
    </ul>
</div>
<br class="clearfix" />
<br class="clearfix" />
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/workflowstatus/create/" commandName="workflowStatus">
    <div class="create-form-button-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Workflow status name:
            <form:input type="text" path="workflowStatusName" class="form-control" placeholder="Enter workflow status name" />
            <p class="help-block">Unique workflow status identifier</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <c:set var="enumValues" value="<%=WorkflowStatusType.values()%>"/>

        <div class="form-group">Workflow status type:
            <br class="clearfix" />
            <br class="clearfix" />
            <c:forEach items="${enumValues}" var="enumValue">
                 ${enumValue}: <input type="radio" name="workflowStatusType" value="${enumValue}" />
                 <br class="clearfix" />
            </c:forEach>

            <p class="help-block">Type of workflow status</p>
        </div>      
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>