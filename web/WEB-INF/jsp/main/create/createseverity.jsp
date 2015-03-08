<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>Severity creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the severity</li>
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
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/severity/create/" commandName="severity">
    <div class="create-form-button-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Severity level:
            <form:input type="text" path="severityLevel" class="form-control" placeholder="Enter severity level" />
            <p class="help-block">Unique severity identifier</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Severity name:
            <form:input type="text" path="severityName" class="form-control" placeholder="Enter severity name" />
            <p class="help-block">Severity name</p>
        </div>      
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>