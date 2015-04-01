<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>User Queue Connection creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the User Queue Connection</li>
        <li>System will send you a message to confirm creation</li>
    </ul>

    Estimated Time:
    <ul>
        <li>To fill out form: 15 seconds
        <li>System response: 15 seconds
    </ul>

</div>
<br class="clearfix" />
<br class="clearfix" />
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/userqueuecon/create/" commandName="userQueueCon">
    <div class="create-form-button-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" required="required" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">User List: 
            <select name="user.userId">
                <c:forEach var="user" items="${userList}">
                    <option value="${user.userId}">${user.username} (${user.firstName} ${user.lastName})</option>
                </c:forEach>
            </select>
            <p class="help-block">Select user</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Queue List: 
            <select name="queue.queueId">
                <c:forEach var="queue" items="${queueList}">
                    <option value="${queue.queueId}">${queue.queueName}</option>
                </c:forEach>
            </select>
            <p class="help-block">Select queue</p>
        </div>      
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>