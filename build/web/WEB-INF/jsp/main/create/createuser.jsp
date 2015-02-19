<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="createuser-form-container">

    <h3>User creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create user</li>
        <li>System will send you a message with the new user's login credentials</li>
    </ul>

    Estimated Time:
    <ul>
        <li>To fill out form: 1 minute
        <li>System response: 5 seconds
    </ul>
</div>
<br class="clearfix" />
<br class="clearfix" />
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/user/create/" commandName="user">
    <div class="createuser-form-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="createuser-form-container">
        <div class="form-group">Username:
            <form:input type="text" path="username" class="form-control" placeholder="Enter desired username" />
            <p class="help-block">Used to login to this system</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="createuser-form-container">
        <div class="form-group">First name
            <form:input type="text" path="firstName" class="form-control" placeholder="Enter user's first name" required="required" />
            <p class="help-block">User's first name for account setup</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="createuser-form-container">
        <div class="form-group">Last name
            <form:input type="text" path="lastName" class="form-control" placeholder="Enter user's last name" required="required" />
            <p class="help-block">User's last name for account setup</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="createuser-form-container">
        <div class="form-group">Email Address
            <form:input type="text" path="emailAddress" class="form-control" placeholder="Enter user's email address" required="required" />
            <p class="help-block">User's email address for account setup</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="createuser-form-container">
        <div class="form-group">Contact Number
            <form:input type="text" path="contact" class="form-control" placeholder="Enter user's contact number" required="required" />
            <p class="help-block">User's contact number for account setup</p>
        </div>      
    </div>

    <br class="clearfix" />

    <script>
        $().ready(function () {


            $('#roleAdd').click(function () {
                removeAllRoles();
                
                var table = document.getElementById('roleTable');

                var rowLength = table.rows.length;

                for (var i = 1; i < rowLength; i += 1) {
                    var row = table.rows[i];

                    if (document.getElementById('role_check_' + row.cells[0].innerText).checked) {
                        roleList = document.getElementById('roleListSelected');
                        roleList.options[roleList.options.length] = new Option(row.cells[1].innerText, row.cells[0].innerText);
                    }
                }
            });
            $('#clearRoles').click(function () {
                removeAllRoles();
            });

            function removeAllRoles() {
                var selectBox = document.getElementById("roleListSelected");
                var i;
                for (i = selectBox.options.length - 1; i >= 0; i--)
                {
                    selectBox.remove(i);
                }
            }
        });
    </script>

    <div class="createuser-form-container">

        <div class="form-group">Existing roles
            <table class="gridtable" id="roleTable">
                <tr>
                    <td>Role ID</td>
                    <td>Role Name</td>
                    <td>Role Description</td>
                    <td>Choice</td>
                </tr>
                <c:forEach var="role" items="${roleList}">
                    <tr>
                        <td>${role.roleId}</td>
                        <td>${role.roleName}</td>
                        <td>${role.roleDescription}</td>
                        <td>
                            <input type="checkbox" id="role_check_${role.roleId}" />
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <br class="clearfix" />
    <div class="createuser-form-container">
        <input type="button" id="roleAdd" value="Update Roles" class="btn btn-success" />
        <input type="button" id="clearRoles" value="Clear Roles" class="btn btn-danger" />
    </div>

    <br class="clearfix" />

    <div class="createuser-form-container">
        <div class="form-group">Assigned roles
            <select multiple="true" size="5" id="roleListSelected" name="roleListSelected" class="form-control">
            </select>
        </div>
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="createuser-form-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>