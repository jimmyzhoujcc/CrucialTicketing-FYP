<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>Configuration creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the configuration</li>
        <li>System will send you a message with the configuration's details</li>
    </ul>

    Estimated Time:
    <ul>
        <li>To fill out form: 20 seconds
        <li>System response: 15 seconds
    </ul>

</div>
<br class="clearfix" />
<br class="clearfix" />
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/configuration/create/" commandName="applicationControl">
    <div class="create-form-button-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" required="required" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Ticket type:
            <select name="ticketType.ticketTypeId">
                <c:forEach var="ticketType" items="${ticketTypeList}">
                    <option value="${ticketType.ticketTypeId}">${ticketType.ticketTypeName}</option>
                </c:forEach>
            </select>
            <p class="help-block">Select ticket type</p>
        </div>      
    </div>

    <br class="clearfix" />
    
    <div class="create-form-button-container">
        <div class="form-group">Severity: 
            <select name="severity.severityId">
                <c:forEach var="severity" items="${severityList}">
                    <option value="${severity.severityId}">${severity.severityLevel} : ${severity.severityName}</option>
                </c:forEach>
            </select>
            <p class="help-block">Select severity</p>
        </div>      
    </div>
    
    <br class="clearfix" />
    
    <div class="create-form-button-container">
        <div class="form-group">Application:  
            <select name="application.applicationId">
                <c:forEach var="application" items="${applicationList}">
                    <option value="${application.applicationId}">${application.applicationName}</option>
                </c:forEach>
            </select>
            <p class="help-block">Select severity</p>
        </div>      
    </div>
    
    <br class="clearfix" />
    
    <div class="create-form-button-container">
        <div class="form-group">Workflow:   
            <select name="workflow.workflowId">
                <c:forEach var="workflow" items="${workflowList}">
                    <option value="${workflow.workflowId}">${workflow.workflowName}</option>
                </c:forEach>
            </select>
            <p class="help-block">Select workflow</p>
        </div>      
    </div>
    
    <br class="clearfix" />
    
    
    <div class="create-form-button-container">

        <div class="form-group">Maintenance role to raise tickets against this configuration
            <table class="gridtable" id="roleTable">
                <tr>
                    <td>Select</td>
                    <td>Role ID</td>
                    <td>Role Name</td>
                    <td>Role Description</td>
                </tr>

                <c:forEach var="role" items="${roleList}">
                    <tr>
                        <td><input type="radio" name="role.roleId" class="form-control" value="${role.roleId}" /></td>
                        <td>${role.roleId}</td>
                        <td>${role.roleName}</td>
                        <td>${role.roleDescription}</td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
    
    

    <br class="clearfix" />
    
    <div class="create-form-button-container">
        <div class="form-group">SLA Clock:
            <form:input type="text" path="slaClock" class="form-control" placeholder="Enter SLA clock" />
            <p class="help-block">SLA clock for configuration</p>
        </div>      
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>