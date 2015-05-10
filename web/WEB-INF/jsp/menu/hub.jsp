<%-- 
    Document   : home
    Created on : 06-Jan-2015, 14:15:39
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<h3>The Hub</h3>
<br class="clearfix" />
<h5>All tickets below are in "Queue(s)" which you belong to</h5>
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
        <td>Workflow Status</td>
        <td>Queue</td>
        <td>SLA Clock</td>
        <td>SLA Elapsed</td>
        <td>SLA Status</td>
        <td>Last Updated By</td>
        <td>Last Updated On</td>
        <td>Select</td>
    </tr>

    <c:choose>
        <c:when test="${fn:length(ticketList) gt 0}">

            <c:forEach var="ticket" items="${ticketList}">
                <tr>
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.applicationControl.ticketType.ticketTypeName}</td>
                    <td>${ticket.shortDescription}</td>
                    <td>${ticket.applicationControl.application.applicationName}</td>
                    <td>${ticket.applicationControl.severity.severityLevel}:${ticket.applicationControl.severity.severityName}</td>
                    <td>${ticket.applicationControl.workflow.workflowName}</td>
                    <td>${ticket.currentWorkflowStep.workflowStatus.workflowStatusName}</td>
                    <td>${ticket.currentWorkflowStep.queue.queueName}</td>
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

                    <td>${ticket.changeLog.changeLog[fn:length(ticket.changeLog.changeLog) - 1].user.username}</td>

                    <jsp:setProperty name="dateValue" property="time" value="${ticket.changeLog.changeLog[fn:length(ticket.changeLog.changeLog) - 1].stamp*1000}"/>
                    <td><fmt:formatDate pattern="dd-MM-yyyy" value="${dateValue}" /></td>
                    
                    <td><a href="<%=request.getContextPath()%>/home/update/ticket/update/?ticketId=${ticket.ticketId}">View</a></td>
                </tr>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <tr>
                <td colspan="13">None to display</td>
            </tr>
        </c:otherwise>
    </c:choose>

</table>

<script>
    function getTicket(ticketId) {
        document.getElementById('hiddenTicketInput').value = ticketId;
        $("#ticketForm").submit();
    }
</script>
<form id="ticketForm" action="<%=request.getContextPath()%>/home/update/viewticket/" method="POST">
    <input id="hiddenTicketInput" type="hidden" name="ticketId" />
</form>

