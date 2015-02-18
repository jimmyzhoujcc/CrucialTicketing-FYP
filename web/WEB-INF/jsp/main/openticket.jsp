<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<div class="row">
    <form:form method="POST" action="${pageContext.servletContext.contextPath}/home/update/saveticket/" modelAttribute="uploadedfilelog" enctype="multipart/form-data">  

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
                    <input type="hidden" value="${ticketObject.ticketId}" name="ticketid" />

                    <br />
                    Ticket Description: 
                    <input type="hidden" value="${ticketObject.shortDescription}" name="old_shortdescription" />
                    <input type="input" value="${ticketObject.shortDescription}" name="new_shortdescription" />

                    <br /><br />

                    Severity: 
                    <select name="severity">
                        <c:forEach var="severity" items="${severityList}">
                            <option 
                                <c:if test="${ticketObject.applicationControl.severity.severityId == severity.severityId}">
                                    selected
                                </c:if>
                                value="${severity.severityId}">${severity.severityLevel}: ${severity.severityName}</option>
                        </c:forEach>
                    </select>
                    <span class="severity-note"><small>Note: Changing severity overrides any changes made to the ticket's status</small></span>
                    <br /><br />

                    Ticket Type ${ticketObject.applicationControl.ticketType.ticketTypeName}
                    <br />
                    Application: ${ticketObject.applicationControl.application.applicationName}
                    <br />
                    Workflow: ${ticketObject.applicationControl.workflow.workflowName}
                    <br /><br />
                    Current status: ${ticketObject.currentWorkflowStep.status.statusName}<br />

                    Current Queue: 
                    <c:if test="${ticketObject.currentWorkflowStep.queue.queueId == -1}">
                        Reporting user
                    </c:if>
                    <c:if test="${ticketObject.currentWorkflowStep.queue.queueId > 0}">
                        ${ticketObject.currentWorkflowStep.queue.queueName}
                    </c:if>
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
                </div>
            </div>

            <div class="panel panel-primary">
                <a name="ticketlog"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Ticket Log</h3>
                </div>
                <div class="panel-body">
                    <textarea class="logentrytextarea" name="logentry"></textarea>

                    <br />
                    (Remember: saving the ticket submits the message)
                    <br />
                    <hr class="style-one" />

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


                    <div id="file-table">
                        <div class="file-media media">
                            <div class="media-body">
                                <h4 class="media-heading">File 1</h4>
                                <input name="files[0].file" type="file" />
                                <br />
                                Name: <input name="files[0].name" type="input" />
                                Description: <input name="files[0].description" type="input" />
                            </div>
                        </div>




                    </div>

                    <br />
                    <input id="addFile" type="button" value="Add File" />

                    <script>
                        var counter = 1;
                        var limit = 10;

                        $('#addFile').on('click', function () {
                            if (counter == limit) {
                                alert("You have reached the limit of files");
                            } else {
                                content = "";
                                content += "<h4 class=\"media-heading\">File " + (counter + 1) + "</h4>";
                                content += "<input name=\"files[" + counter + "].file\" type=\"file\" />";
                                content += "<br />";
                                content += "Name: <input name=\"files[" + counter + "].name\" type=\"input\" />";
                                content += "Description: <input name=\"files[" + counter + "].description\" type=\"input\" />";

                                newDiv = document.createElement('div');
                                $(newDiv).addClass("file-media media")
                                        .html(content)
                                        .appendTo($("#file-table")); //main div
                                counter++;
                            }
                        });
                    </script>

                    <hr class="style-three"> 

                    <h4>File List</h4>

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
                                <td>${changeLog.workflowStatus.statusName}</td>

                                <c:set var="queue" value="${changeLog.applicationControl.workflow.workflowMap.getWorkflowStageByStatus(changeLog.workflowStatus.statusId)}" />

                                <c:choose>
                                    <c:when test="${queue.queue.queueId == -1}">
                                        <c:set var="queueName" value="Reporting User"/>
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="queueName" value="${queue.queue.queueName}"/>
                                    </c:otherwise>
                                </c:choose>

                                <td>${queueName}</td>
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


                    <div class="btn-group">
                        <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                            Change ticket status<span class="caret"></span>
                        </button>

                        <c:set var="totalNextNodes" value="${fn:length(ticketObject.currentWorkflowStep.nextWorkflowStep)}"/>

                        <script type="text/javascript">
                            function changeStatus(inputStatus) {
                                var elem = document.getElementById("newstatus");
                                elem.value = inputStatus
                            }
                        </script>

                        <ul class="dropdown-menu" role="menu">
                            <c:forEach var="nextNode" items="${ticketObject.currentWorkflowStep.nextWorkflowStep}">
                                <li>
                                    <a href="javascript:changeStatus('${nextNode.status.statusId}');">${nextNode.status.statusName}</a>
                                </li>
                            </c:forEach>
                            <c:if test="${totalNextNodes == 0}">
                                <li><a href="#">No further actions</a></li>
                                </c:if>
                        </ul>

                        <input type="hidden" value="" id="newstatus" name="newstatus" />
                    </div>

                    <br /><br />

                    <input type="submit" value="Save Ticket" class="btn btn-success" />
                    <br /><br />
                    <input type="button" id="cancelEdit" value="Cancel (Exit)" class="btn btn-danger" />
                    <script>
                        $('#cancelEdit').on('click', function () {
                            window.history.back();
                        });
                    </script>

                </div>
            </div>
        </div>

    </form:form>  
</div>


