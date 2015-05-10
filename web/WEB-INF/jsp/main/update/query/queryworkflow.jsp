<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Workflow</h3>

    <form action="<%=request.getContextPath()%>/home/update/workflow/" method="POST">
        <div class="query_container">
            <div class="query_heading">
                Workflow ID:
            </div>
            <div class="query_box">
                <input type="text" name="workflowId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Workflow Name:
            </div>
            <div class="query_box">
                <input type="text" name="workflowName" />
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
            <td>Workflow Name</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(workflowList) gt 0}">
                <c:forEach var="workflow" items="${workflowList}">
                    <td>${workflow.workflowId}</td>
                    <td>${workflow.workflowName}</td>
                    <td><input type="button" onClick="javascript:updateWorkflowInput(${workflow.workflowId})" value="Select" /></td>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(workflowList) == 0}">
                <td colspan="3">No records to display</td>
            </c:if>
    </table>

    <script>
        function updateWorkflowInput(workflowId) {
            document.getElementById('workflowHiddenInput').value = workflowId;
            $("#queryWorkflow").submit();
        }
    </script>
    <form id="queryWorkflow" action="<%=request.getContextPath()%>/home/update/workflow/update/" method="POST">
        <input id="workflowHiddenInput" type="hidden" name="workflowId" />
    </form>
</div>

