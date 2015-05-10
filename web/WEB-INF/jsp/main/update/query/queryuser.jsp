<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query User</h3>

    <form action="<%=request.getContextPath()%>/home/update/user/" method="POST">

        <div class="query_container">
            <div class="query_heading">
                User ID:
            </div>
            <div class="query_box">
                <input type="text" name="ticketId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Username:
            </div>
            <div class="query_box">
                <input type="text" name="username" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                First Name:
            </div>
            <div class="query_box">
                <input type="text" name="firstName" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Last Name:
            </div>
            <div class="query_box">
                <input type="text" name="lastName" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                <input type="submit" />
            </div>
        </div>
    </form>
    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <table class="table table-hover">
        <tr>
            <td>ID</td>
            <td>Username</td>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(userList) gt 0}">
                <c:forEach var="user" items="${userList}">
                    <td>${user.userId}</td>
                    <td>${user.username}</td>
                    <td>${user.firstName}</td>
                    <td>${user.lastName}</td>
                    <td><input type="button" onClick="javascript:updateUserInput(${user.userId})" value="Select" /></td>
                    </c:forEach>
                </c:if>

            <c:if test="${fn:length(userList) == 0}">
                <td colspan="5">No records to display</td>
            </c:if>
    </table>

    <script>
        function updateUserInput(userId) {
            document.getElementById('userhiddeninput').value = userId;
            $("#queryuser").submit();
        }
    </script>
    <form id="queryuser" action="<%=request.getContextPath()%>/home/update/user/update/" method="POST">
        <input id="userhiddeninput" type="hidden" name="userId" />
    </form>
</div>

