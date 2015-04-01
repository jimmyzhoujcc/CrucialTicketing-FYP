<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>User Role Connection creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the configuration</li>
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
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/userrolecon/create/" commandName="userRoleCon">
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
        <div class="form-group">Role List: 
            <select name="role.roleId">
                <c:forEach var="role" items="${roleList}">
                    <option value="${role.roleId}">${role.roleName}</option>
                </c:forEach>
            </select>
            <p class="help-block">Select role</p>
        </div>      
    </div>

    <script>
        $(function () {
            $("#datepicker_to").datepicker({dateFormat: 'dd-mm-yy'});
        });
    </script>    

    <br class="clearfix" />
    
    <div class="create-form-button-container">
        <div class="form-group">Validity date from: ${userRoleCon.validFromStr} 
            <input type="hidden" value="${userRoleCon.validFromStr}" name="validFromStr" />
            <p class="help-block">Date the role should be active from</p>
        </div>      
    </div>
    
    <br class="clearfix" />
    
    <div class="create-form-button-container">
        <div class="form-group">Validity date to:  
            <input type="text" name="validToStr" id="datepicker_to">
            <p class="help-block">Date the role should be active to</p>
        </div>      
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>