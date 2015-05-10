<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Role</h3>

    <form action="<%=request.getContextPath()%>/home/update/role/" method="POST">
        <div class="query_container">
            <div class="query_heading">
                Role ID:
            </div>
            <div class="query_box">
                <input type="text" name="roleId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Role Name:
            </div>
            <div class="query_box">
                <input type="text" name="roleName" />
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
            <td>Role Name</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(roleList) gt 0}">
                <c:forEach var="role" items="${roleList}">

                    <td>${role.roleId}</td>
                    <td>${role.roleName}</td>
                    <td><input type="button" onClick="javascript:updateRoleInput(${role.roleId})" value="Select" /></td>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(roleList) == 0}">
                <td colspan="3">No records to display</td>
            </c:if>
        </tr>
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
</div>

