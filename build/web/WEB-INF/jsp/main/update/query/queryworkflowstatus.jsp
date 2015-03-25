<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Workflow Status</h3>

    <form action="<%=request.getContextPath()%>/home/update/workflowstatus/" method="POST">
        Workflow Status ID: <input type="text" name="workflowStatusId" />
        <br class="clearfix" />
        Workflow Status Name: <input type="text" name="workflowStatusName" />
        <br class="clearfix" />
        <br class="clearfix" />
        <input type="submit" />
    </form>
        
    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(workflowStatusList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Workflow Status Name</td>
                <td>Select</td>
            </tr>

            <c:forEach var="workflowStatus" items="${workflowStatusList}">
                <tr>
                    <td>${workflowStatus.workflowStatusId}</td>
                    <td>${workflowStatus.workflowStatusName}</td>
                    <td><input type="button" onClick="javascript:updateWorkflowStatusInput(${workflowStatus.workflowStatusId})" value="Select" /></td>
                </tr>
            </c:forEach>
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
    </c:if>
</div>

