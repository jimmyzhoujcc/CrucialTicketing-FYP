<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
    function goToUrl(inputUrl) {
        window.location.replace(inputUrl)
    }
</script>

<!-- Auto edit functionality -->
<script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
<script>
    var id = ${workflow.workflowId};
    var item = "workflow";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
</script>

<c:choose>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/workflow/save/" />
        <c:set var="formName" value="saveRequestForm" />
        <script>
            clearInterval(checkIdIsOpenInterval);
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="formLink" value="/home/update/workflow/edit/" />
        <c:set var="formName" value="editRequestForm" />
    </c:otherwise>
</c:choose>

<div class="create-form-button-container">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="queue">

        <c:choose>
            <c:when test="${edit}">
                <input type="submit" value="Save" />
                <input type="hidden" value="${workflow.workflowId}" name="workflowId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <input type="submit" value="Edit" />
                <input type="hidden" value="${workflow.workflowId}" name="workflowId" />
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

    </form:form>
</div>