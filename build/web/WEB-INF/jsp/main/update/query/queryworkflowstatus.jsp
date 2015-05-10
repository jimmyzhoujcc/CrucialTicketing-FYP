<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Workflow Status</h3>

    <form action="<%=request.getContextPath()%>/home/update/workflowstatus/" method="POST">

        <div class="query_container">
            <div class="query_heading">
                Workflow Status ID:
            </div>
            <div class="query_box">
                <input type="text" name="workflowStatusId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Workflow Status Name:
            </div>
            <div class="query_box">
                <input type="text" name="workflowStatusName" />
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
            <td>Workflow Status Name</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(workflowStatusList) gt 0}">
                <c:forEach var="workflowStatus" items="${workflowStatusList}">
                    <td>${workflowStatus.workflowStatusId}</td>
                    <td>${workflowStatus.workflowStatusName}</td>
                    <td><input type="button" onClick="javascript:updateWorkflowStatusInput(${workflowStatus.workflowStatusId})" value="Select" /></td>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(workflowStatusList) == 0}">
                <td colspan="3">No records to display</td>
            </c:if>
    </table>

    <script>
        function updateWorkflowStatusInput(workflowStatusId) {
            document.getElementById('workflowStatusHiddenInput').value = workflowStatusId;
            $("#queryWorkflowStatus").submit();
        }
    </script>
    <form id="queryWorkflowStatus" action="<%=request.getContextPath()%>/home/update/workflowstatus/update/" method="POST">
        <input id="workflowStatusHiddenInput" type="hidden" name="workflowStatusId" />
    </form>
</div>

