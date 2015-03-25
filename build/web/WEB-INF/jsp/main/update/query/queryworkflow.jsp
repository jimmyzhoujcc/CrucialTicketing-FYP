<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Workflow</h3>

    <form action="<%=request.getContextPath()%>/home/update/workflow/" method="POST">
        Workflow ID: <input type="text" name="workflowId" />
        <br class="clearfix" />
        Workflow Name: <input type="text" name="workflowName" />
        <br class="clearfix" />
        <br class="clearfix" />
        <input type="submit" />
    </form>
        
    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(workflowList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Workflow Name</td>
                <td>Select</td>
            </tr>

            <c:forEach var="workflow" items="${workflowList}">
                <tr>
                    <td>${workflow.workflowId}</td>
                    <td>${workflow.workflowName}</td>
                    <td><input type="button" onClick="javascript:updateWorkflowInput(${workflow.workflowId})" value="Select" /></td>
                </tr>
            </c:forEach>
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
    </c:if>
</div>

