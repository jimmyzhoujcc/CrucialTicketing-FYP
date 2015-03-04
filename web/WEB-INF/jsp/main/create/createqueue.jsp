<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>Queue creation</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the queue</li>
        <li>System will send you a message with the queue's details</li>
    </ul>

    Estimated Time:
    <ul>
        <li>To fill out form: 20 seconds
        <li>System response: 5 seconds
    </ul>
</div>
<br class="clearfix" />
<br class="clearfix" />
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/queue/create/" commandName="queue">
    <div class="create-form-button-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" required="required" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Queue Name:
            <form:input type="text" path="queueName" class="form-control" placeholder="Enter desired queue name"  required="required" />
            <p class="help-block">Name of this queue</p>
        </div>      
    </div>

    <br class="clearfix" />

    <script>
        $().ready(function () {

            $('#userAdd').click(function () {
                removeAllUsers();
                var table = document.getElementById('userTable');
                var rowLength = table.rows.length;
                for (var i = 1; i < rowLength; i += 1) {
                    var row = table.rows[i];
                    if (document.getElementById(row.cells[0].innerText + '_user_check').checked) {
                        userList = document.getElementById('userListSelected');
                        addNewHiddenUser(row.cells[0].innerText);
                        userList.options[userList.options.length] = new Option(row.cells[2].innerText + " " + row.cells[3].innerText, row.cells[0].innerText);
                    }
                }
            });
            $('#clearUsers').click(function () {
                removeAllUsers();
                document.getElementById('confirmed_users').innerHTML = "";
            });
            function removeAllUsers() {
                var selectBox = document.getElementById("userListSelected");
                var i;
                for (i = selectBox.options.length - 1; i >= 0; i--)
                {
                    selectBox.remove(i);
                }
            }
        });
    </script>

    <script>
<!-- Adding new hidden users -->
        var counter = 0;
        function addNewHiddenUser(userId) {
            var pattern = /(\d{2})\.(\d{2})\.(\d{4})/;

            content = "<input type=\"hidden\" name=\"userList[" + counter + "\].user.userId\" value=\"" + userId + "\" />";

            newDiv = document.createElement('div');
            $(newDiv).html(content)
                    .appendTo($("#confirmed_users")); //main div
            counter++;
        }
    </script>

    <div class="create-form-button-container">

        <div class="form-group">Existing Users
            <table class="gridtable" id="userTable">
                <tr>
                    <td>User ID</td>
                    <td>Username</td>
                    <td>First Name</td>
                    <td>Last Name</td>
                    <td>Email Address</td>
                    <td>Contact</td>
                    <td>Choice</td>
                </tr>

                <c:forEach var="user" items="${userList}">
                    <tr>
                        <td>${user.userId}</td>
                        <td>${user.username}</td>
                        <td>${user.firstName}</td>
                        <td>${user.lastName}</td>
                        <td>${user.emailAddress}</td>
                        <td>${user.contact}</td>
                        <td><input type="checkbox" id="${user.userId}_user_check" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <br class="clearfix" />
    <div class="create-form-button-container">
        <input type="button" id="userAdd" value="Update Users" class="btn btn-success" />
        <input type="button" id="clearUsers" value="Clear Users" class="btn btn-danger" />
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Confirmed Users
            <select multiple="true" size="5" id="userListSelected" class="form-control">
            </select>
        </div>
    </div>

    <div id="confirmed_users">
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>