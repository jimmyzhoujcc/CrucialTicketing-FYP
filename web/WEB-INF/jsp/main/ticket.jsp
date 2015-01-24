<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<div class="row">
    <div class="col-xs-12 col-sm-6 col-md-10">
        <c:if test="${fn:length(alert)>0}">
            <div class="alert alert-danger" role="alert">${alert}</div>
        </c:if>
        <h3><span class="label label-default">${ticketObject.ticketId}</span> ${ticketObject.shortDescription}</h3>

        <br />

        <ol class="breadcrumb">
            <li>

            </li>
            <c:forEach var="workflowItem" items="${ticketObject.applicationControl.workflow.workflow}">
                <li>

                </li>
            </c:forEach>
        </ol>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Basic information</h3>
            </div>
            <div class="panel-body">
                Ticket ID: ${ticketObject.ticketId}
                <br />
                Ticket Description: 
                <c:if test="${editMode==true}">
                    <input type="input" id="holder_new_shortdescription" value="${ticketObject.shortDescription}" />
                </c:if>
                <c:if test="${editMode==false}">
                    ${ticketObject.shortDescription}
                </c:if>

                <br /><br />

                Ticket Type ${ticketObject.applicationControl.ticketType.ticketTypeName}
                <br />
                Severity: ${ticketObject.applicationControl.severity.severityLevel} : ${ticketObject.applicationControl.severity.severityName}
                <br />
                Application: ${ticketObject.applicationControl.application.applicationName}
                <br />
                Workflow: ${ticketObject.applicationControl.workflow.workflowName}
                <br /><br />
                Current status: ${ticketObject.currentWorkflowStage.status.statusName}<br />
                Current Queue: 


                <c:if test="${ticketObject.currentWorkflowStage.queue.queueId == -1}">
                    Reporting user
                </c:if>
                <c:if test="${ticketObject.currentWorkflowStage.queue.queueId > 0}">
                    ${ticketObject.currentWorkflowStage.queue.queueName}
                </c:if>
                <br />
            </div>
        </div>


        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">User information</h3>
            </div>
            <div class="panel-body">
                Created By: ${ticketObject.createdBy.firstName} ${ticketObject.createdBy.lastName}
                <br />
                Reported By: ${ticketObject.reportedBy.firstName} ${ticketObject.reportedBy.lastName}
                <br />
                Processing By: ${ticketObject.messageProcessor.firstName} ${ticketObject.messageProcessor.lastName}
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">SLA & KPI Clocks</h3>
            </div>
            <div class="panel-body">
                <div class="progressbar-override progress">
                    <div class="progress-bar" role="progressbar" aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;">
                        0%
                    </div>
                </div>
                <div class="progressbar-override progress">
                    <div class="progress-bar" role="progressbar" aria-valuenow="2" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em; width: 2%;">
                        2%
                    </div>
                </div>
            </div>
        </div>



        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Involvement Indicator</h3>
            </div>
            <div class="panel-body">
                <div class="progressbar-override progress">
                    <div class="progress-bar progress-bar-success" style="width: 35%">
                        <span class="sr-only">35% Complete (success)</span>
                    </div>
                    <div class="progress-bar progress-bar-warning progress-bar-striped" style="width: 20%">
                        <span class="sr-only">20% Complete (warning)</span>
                    </div>
                    <div class="progress-bar progress-bar-danger" style="width: 10%">
                        <span class="sr-only">10% Complete (danger)</span>
                    </div>
                </div>
            </div>
        </div>



        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Ticket Log</h3>
            </div>
            <div class="panel-body">
                <c:if test="${editMode==true}">
                    <textarea class="logentrytextarea" id="holder_logentry"></textarea>

                    <br />
                    (Remember: saving the ticket submits the message)
                    <br />
                    <hr class="style-one" />

                </c:if>

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
                                        <jsp:useBean id="dateValue" class="java.util.Date"/>
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
    </div>

    <div class="ticketnav col-xs-6 col-md-2" style="position:fixed; right:0">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Quick Navigation</h3>
            </div>
            <div class="panel-body">
                <a href="#">Basic information</a>
                <br />
                <a href="#">SLA & KPI Information</a>
                <br />
                <a href="#">Involvement Indicator</a>
                <br />
                <a href="#">Ticket Log</a>
            </div>
        </div>

        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Ticket Control</h3>
            </div>
            <div class="panel-body">
                <c:if test="${editMode==false}">
                    <form method="POST" action="<%=request.getContextPath()%>/home/update/editticket/">
                        <input type="submit" value="Edit" class="btn btn-warning" />
                        <input type="hidden" name="ticketid" value="${ticketObject.ticketId}" />
                    </form>
                </c:if>

                <c:if test="${editMode==true}">
                    <div class="btn-group">
                        <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            Change ticket status<span class="caret"></span>
                        </button>

                        <c:set var="totalNextNodes" value="${fn:length(ticketObject.currentWorkflowStage.nextWorkflowStage)}"/>

                        <script type="text/javascript">
                            function changeStatus(inputStatus) {
                                var elem = document.getElementById("newstatus");
                                elem.value = inputStatus
                            }
                        </script>

                        <ul class="dropdown-menu" role="menu">
                            <c:forEach var="nextNode" items="${ticketObject.currentWorkflowStage.nextWorkflowStage}">
                                <li>
                                    <a href="javascript:changeStatus('${nextNode.status.statusId}');">${nextNode.status.statusName}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${totalNextNodes == 0}">
                                <li><a href="#">No further actions</a></li>
                                </c:if>
                        </ul>
                    </div>

                    <br /><br />
                </c:if>

                <c:if test="${editMode==true}">
                    <script>
                        function modify_value() {
                            document.getElementById('logentry').value = document.getElementById('holder_logentry').value;
                            document.getElementById('new_shortdescription').value = document.getElementById('holder_new_shortdescription').value;
                        }
                        </script>
                    <form method="POST" action="<%=request.getContextPath()%>/home/update/saveticket/">
                        <input type="submit" value="Save Ticket" class="btn btn-success" onclick="javascript:modify_value();" />
                        <input type="hidden" value="${ticketObject.ticketId}" name="ticketid" />
                        <input type="hidden" value="${ticketObject.shortDescription}" name="old_shortdescription" />
                        <input type="hidden" value="" id="new_shortdescription" name="new_shortdescription" />
                        <input type="hidden" value="" id="newstatus" name="newstatus" />
                        <input type="hidden" value="" id="logentry" name="logentry" />
                    </form>
                </c:if>
            </div>
        </div>
    </div>
</div>


