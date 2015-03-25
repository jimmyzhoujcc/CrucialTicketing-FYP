<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">
    <c:choose>
        <c:when test="${edit}">
            <form action="<%=request.getContextPath()%>/home/update/workflow/save/" method="POST">
                <input type="submit" value="Save" />
                <input type="hidden" value="${workflow.workflowId}" name="workflowId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <form action="<%=request.getContextPath()%>/home/update/workflow/edit/" method="POST">
                    <input type="submit" value="Edit" />
                    <input type="hidden" value="${workflow.workflowId}" name="workflowId" />
                </form>
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        <br class="clearfix" />

        Workflow ID: ${workflow.workflowId}

        <br class="clearfix" />

        Workflow Name: ${workflow.workflowName}

        <br class="clearfix" />
        <br class="clearfix" />

        Workflow Structure:

        <br class="clearfix" />

        <table class="table table-hover">
            <tr>
                <td>Status From</td>
                <td>Status To</td>
                <td>Role</td>
                <td>Queue</td>
                <td>SLA Active</td>
            </tr>

            <c:forEach var="workflowStep" items="${workflow.workflowMap.workflow}">
                <tr>
                    <c:choose>
                        <c:when test="${fn:length(workflowStep.nextWorkflowStep) gt 0}">
                            <c:forEach var="nextWorkflowStep" items="${workflowStep.nextWorkflowStep}">
                                <td>${workflowStep.workflowStatus.workflowStatusName}</td>
                                
                                <td>${nextWorkflowStep.workflowStatus.workflowStatusName}</td>
                                <td>${nextWorkflowStep.role.roleName}</td>
                                <td>${nextWorkflowStep.queue.queueName}</td>

                                <td>
                                    <c:choose>
                                        <c:when test="${workflowStep.clockActive == 1}">Yes</c:when>
                                        <c:otherwise>No</c:otherwise>
                                    </c:choose>
                                </td>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <td>${workflowStep.workflowStatus.workflowStatusName}</td>
                            <td>none</td>
                            <td>none</td>
                            <td>none</td>
                            <td>none</td>
                        </c:otherwise>
                    </c:choose>

                </tr>
            </c:forEach>
        </table>

        <br class="clearfix" />
        <br class="clearfix" />
        Status:
        <c:choose>
            <c:when test="${edit}">
                Online: <input type="radio" value="${activeFlagOnline}" name="activeFlag" <c:if test="${workflow.activeFlag.activeFlag == activeFlagOnline}">checked</c:if> /> 
                Offline: <input type="radio" value="${activeFlagOffline}" name="activeFlag" <c:if test="${workflow.activeFlag.activeFlag == activeFlagOffline}">checked</c:if> />  
            </c:when>
            <c:otherwise>
                ${workflow.activeFlag}
            </c:otherwise>
        </c:choose>
</div>