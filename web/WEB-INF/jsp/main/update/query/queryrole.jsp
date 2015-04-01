<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Role</h3>

    <form action="<%=request.getContextPath()%>/home/update/role/" method="POST">
        Role ID: <input type="text" name="roleId" />
        <br class="clearfix" />
        Role Name: <input type="text" name="roleName" />
        <br class="clearfix" />
        <br class="clearfix" />
        <input type="submit" />
    </form>

    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(roleList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Role Name</td>
            </tr>

            <c:forEach var="role" items="${roleList}">
                <tr>
                    <td>${role.roleId}</td>
                    <td>${role.roleName}</td>
                    <td><input type="button" onClick="javascript:updateRoleInput(${role.roleId})" value="Select" /></td>
                </tr>
            </c:forEach>
        </table>

        <script>
            function updateRoleInput(roleId) {
                document.getElementById('roleHiddenInput').value = roleId;
                $("#queryRole").submit();
            }
        </script>
        <form id="queryRole" action="<%=request.getContextPath()%>/home/update/role/update/" method="POST">
            <input id="roleHiddenInput" type="hidden" name="roleId" />
        </form>
    </c:if>
</div>

