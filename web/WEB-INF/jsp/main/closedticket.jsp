<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<!-- Auto edit functionality -->
<script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
<script>
    var id = ${ticketObject.ticketId};
    var item = "ticket";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval); 
</script>

<div class="row">
    <div class="col-xs-12 col-sm-8 col-md-10">

        <h3><span class="label label-default">${ticketObject.ticketId}</span> ${ticketObject.shortDescription}</h3>

        <br />

        <ol class="breadcrumb">
            <li>
                Snapshot as of the ticket as of 
                <c:set var="now" value="<%=new java.util.Date()%>" />
                <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${now}" />
            </li>
        </ol>

        <div class="panel panel-primary">
            <a name="basic"></a>
            <div class="panel-heading">
                <h3 class="panel-title">Basic information</h3>
            </div>
            <div class="panel-body">
                Ticket ID: ${ticketObject.ticketId}

                <br />
                Ticket Description: ${ticketObject.shortDescription}

                <br /><br />

                Ticket Type ${ticketObject.applicationControl.ticketType.ticketTypeName}
                <br />
                Severity: ${ticketObject.applicationControl.severity.severityLevel} : ${ticketObject.applicationControl.severity.severityName}
                <br />
                Application: ${ticketObject.applicationControl.application.applicationName}
                <br />
                Workflow: ${ticketObject.applicationControl.workflow.workflowName}
                <br /><br />
                Current status: ${ticketObject.currentWorkflowStep.workflowStatus.workflowStatusName}<br />

                Current Queue: 
                <c:choose>
                    <c:when test="${ticketObject.currentWorkflowStep.queue.queueId == 1}">
                        <c:set var="basicInfoQueueName" value="Reporting User"/>
                    </c:when>
                    <c:otherwise>
                        <c:set var="basicInfoQueueName" value="${ticketObject.currentWorkflowStep.queue.queueName}"/>
                    </c:otherwise>
                </c:choose>

                ${basicInfoQueueName}

                <br />
            </div>
        </div>


        <div class="panel panel-primary">
            <a name="user"></a>
            <div class="panel-heading">
                <h3 class="panel-title">User information</h3>
            </div>
            <div class="panel-body">
                Created By: ${ticketObject.createdBy.firstName} ${ticketObject.createdBy.lastName}
                <br />
                Reported By: ${ticketObject.reportedBy.firstName} ${ticketObject.reportedBy.lastName}
                <br />
                Last Processing By: 
                ${ticketObject.changeLog.changeLog[fn:length(ticketObject.changeLog.changeLog)-1].user.firstName} 
                ${ticketObject.changeLog.changeLog[fn:length(ticketObject.changeLog.changeLog)-1].user.lastName}
            </div>
        </div>

        <div class="panel panel-primary">
            <a name="sla"></a>
            <div class="panel-heading">
                <h3 class="panel-title">SLA Clock</h3>
            </div>
            <div class="panel-body">
                <div class="progressbar-override progress">
                    <c:choose>
                        <c:when test="${((ticketObject.changeLog.timeElapsed/ticketObject.applicationControl.slaClock)*100) >= 100}">
                            <c:set var="sla" value="100"/>
                            <c:set var="slanote" value="Violated"/>
                        </c:when>
                        <c:otherwise>
                            <fmt:parseNumber var="sla" integerOnly="true" 
                                             type="number" value="${(ticketObject.changeLog.timeElapsed/ticketObject.applicationControl.slaClock)*100}" />
                            <c:set var="slanote" value="Not Violated"/>
                        </c:otherwise>
                    </c:choose>

                    <div class="progress-bar" role="progressbar" aria-valuenow="${sla}" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;  width: ${sla}%;">
                        ${sla}%
                    </div>
                </div>

                SLA: ${slanote}
            </div>


        </div>



        <div class="panel panel-primary">
            <a name="ticketlog"></a>
            <div class="panel-heading">
                <h3 class="panel-title">Ticket Log</h3>
            </div>
            <div class="panel-body">

                <div style="padding:10px;max-height:500px;width:100%;overflow:auto;">

                    <c:forEach var="ticketLogEntry" items="${ticketObject.ticketLog.entryList}">
                        <div class="media">
                            <div class="media-left">
                                <a href="#">
                                    <img class="media-picture media-object" src="<%=request.getContextPath()%>/profile/${ticketLogEntry.user.userId}.png" alt="...">
                                </a>
                            </div>
                            <div class="media-body">
                                <h4 class="media-heading">${ticketLogEntry.user.firstName} ${ticketLogEntry.user.lastName} 
                                    <small>
                                        <jsp:setProperty name="dateValue" property="time" value="${ticketLogEntry.stamp*1000}"/>
                                        <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${dateValue}" />

                                    </small></h4>
                                    ${ticketLogEntry.logEntry}
                            </div>
                        </div>

                        <hr class="style-three" />

                    </c:forEach>


                </div>
            </div>
        </div>

        <div class="panel panel-primary">
            <a name="attachments"></a>
            <div class="panel-heading">
                <h3 class="panel-title">Attachments</h3>
            </div>
            <div class="panel-body">

                <table class="gridtable">
                    <tr>
                        <td>Name</td>
                        <td>Description</td>
                        <td>File Name</td>
                        <td>Download</td>
                    </tr>
                    <c:set var="totalFiles" value="${fn:length(ticketObject.attachmentList)}"/>

                    <c:forEach var="ticketAttachment" items="${ticketObject.attachmentList}">
                        <tr>
                            <td>${ticketAttachment.name}</td>
                            <td>${ticketAttachment.description}</td>
                            <td>${ticketAttachment.fileName}</td>
                            <td><a href="<%=request.getContextPath()%>/home/file/${ticketAttachment.fileUploadId}/">Download</a></td>
                        </tr>
                    </c:forEach>

                    <c:if test="${totalFiles == 0}">
                        <tr>
                            <td colspan="4">No attachments</td>
                        </tr>
                    </c:if>
                </table>
            </div>
        </div>


        <div class="panel panel-primary">
            <a name="changelog"></a>
            <div class="panel-heading">
                <h3 class="panel-title">Change Log</h3>
            </div>
            <div class="panel-body">

                <table class="gridtable">
                    <tr>
                        <td>Ticket Type</td>
                        <td>Severity</td>
                        <td>Application</td>
                        <td>Workflow</td>
                        <td>Status</td>
                        <td>Queue</td>
                        <td>Time stamp</td>
                    </tr>
                    <c:forEach var="changeLog" items="${ticketObject.changeLog.changeLog}">
                        <tr>
                            <td>${changeLog.applicationControl.ticketType.ticketTypeName}</td>
                            <td>${changeLog.applicationControl.severity.severityName}</td>
                            <td>${changeLog.applicationControl.application.applicationName}</td>
                            <td>${changeLog.applicationControl.workflow.workflowName}</td>
                            <td>${changeLog.workflowStatus.workflowStatusName}</td>

                            <c:set var="queue" value="${changeLog.applicationControl.workflow.workflowMap.getWorkflowStageByStatus(changeLog.workflowStatus.workflowStatusId)}" />

                            <td>${queue.queue.queueName}</td>
                            <jsp:setProperty name="dateValue" property="time" value="${changeLog.stamp*1000}"/>
                            <td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${dateValue}" /></td>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
    </div>

    <div class="ticketnav col-xs-4 col-md-2" style="position:fixed; right:0">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Quick Navigation</h3>
            </div>
            <div class="panel-body">
                <a href="#basic">Basic information</a>
                <br />
                <a href="#sla">SLA Information</a>
                <br />
                <a href="#ticketlog">Ticket Log</a>
                <br />
                <a href="#attachments">Attachments</a>
                <br />
                <a href="#changelog">Change Log</a>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Ticket Control</h3>
            </div>
            <div class="panel-body">
                <form method="POST" id="editRequestForm" action="<%=request.getContextPath()%>/home/update/editticket/">
                    <input type="submit" value="Edit" class="btn btn-warning" />
                    <input type="hidden" name="ticketid" value="${ticketObject.ticketId}" />
                </form>
                <br />
                <input type="button" value="Refresh Ticket" class="btn btn-success"  onclick="location.reload();" />
            </div>
        </div>
    </div>
</div>


