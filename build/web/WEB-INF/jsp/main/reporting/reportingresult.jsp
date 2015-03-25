<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>
<div class="column1">
    <h3>Reporting</h3>

    <br class="clearfix" />

    <ul class="nav nav-pills">
        <li role="presentation" class="active"><a href="#">Back</a></li>
        <li role="presentation" class="active"><a href="#" id="exportToExcel">Export to Excel</a></li>
    </ul>

    <script>
        $("#exportToExcel").click(function () {
            $("#formExportToExcel").submit();
        });
    </script>

    <form id="formExportToExcel" action="<%=request.getContextPath()%>/home/reporting/crucialreport/" method="POST">
        <c:if test="${fn:length(ticketList) gt 0}">

            <c:forEach var="ticket" items="${ticketList}">
                <input type="hidden" name="ticketList" value="${ticket.ticketId}" />
            </c:forEach>
        </c:if>
    </form>

    <br class="clearfix" />
    <br class="clearfix" />

    <table class="table table-hover">
        <tr>
            <td>ID</td>
            <td>Ticket Type</td>
            <td>Short Description</td>
            <td>Application</td>
            <td>Severity</td>
            <td>Workflow</td>
            <td>Role</td>
            <td>Workflow Status</td>
            <td>SLA Clock</td>
            <td>SLA Elapsed</td>
            <td>SLA Status</td>
            <td>Created By</td>
            <td>Created On</td>
            <td>Reported By</td>
            <td>Reported On</td>
            <td>Last Updated By</td>
            <td>Last Updated On</td>
        </tr>

        <c:if test="${fn:length(ticketList) gt 0}">

            <c:forEach var="ticket" items="${ticketList}">
                <tr>
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.applicationControl.ticketType.ticketTypeName}</td>
                    <td>${ticket.shortDescription}</td>
                    <td>${ticket.applicationControl.application.applicationName}</td>
                    <td>${ticket.applicationControl.severity.severityLevel}:${ticket.applicationControl.severity.severityName}</td>
                    <td>${ticket.applicationControl.workflow.workflowName}</td>
                    <td>${ticket.applicationControl.role.roleName}</td>
                    <td>${ticket.currentWorkflowStep.workflowStatus.workflowStatusName}</td>
                    <td>${ticket.applicationControl.slaClock}</td>

                    <td>${ticket.changeLog.timeElapsed}</td>

                    <c:choose>
                        <c:when test="${ticket.applicationControl.slaClock >= ticket.changeLog.timeElapsed}">
                            <td>Not Violated</td>
                        </c:when>
                        <c:otherwise>
                            <td>Violated</td>
                        </c:otherwise>
                    </c:choose>

                    <td>${ticket.changeLog.changeLog[0].user.username}</td>

                    <jsp:setProperty name="dateValue" property="time" value="${ticket.changeLog.changeLog[0].stamp*1000}"/>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${dateValue}" /></td>

                    <td>${ticket.reportedBy.username}</td>

                    <jsp:setProperty name="dateValue" property="time" value="${ticket.changeLog.changeLog[0].stamp*1000}"/>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${dateValue}" /></td>

                    <td>${ticket.changeLog.changeLog[fn:length(ticket.changeLog.changeLog) - 1].user.username}</td>

                    <jsp:setProperty name="dateValue" property="time" value="${ticket.changeLog.changeLog[fn:length(ticket.changeLog.changeLog) - 1].stamp*1000}"/>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${dateValue}" /></td>
                </tr>
            </c:forEach>

        </c:if>

    </table>
</div>