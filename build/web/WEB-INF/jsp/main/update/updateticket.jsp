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

<script>
    function goToUrl(inputUrl) {
        window.location.replace(inputUrl)
    }
</script>

<c:choose>
    <c:when test="${newTicket}">
        <c:set var="formLink" value="/home/create/ticket/save/" />
        <c:set var="formName" value="editRequestForm" />
    </c:when>
    <c:when test="${view}">
        <c:set var="formLink" value="/home/update/ticket/edit/" />
        <c:set var="formName" value="editRequestForm" />

        <!-- Auto edit functionality -->
        <script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
        <script>
    var id = ${ticket.ticketId};
    var item = "ticket";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
        </script>
    </c:when>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/ticket/save/" />
        <c:set var="formName" value="saveRequestForm" />
    </c:when>

</c:choose>

<div class="row">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" modelAttribute="uploadedfilelog" enctype="multipart/form-data"> 

        <div id="subticketnav" class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">Ticket Control</h3>
            </div>
            <div class="panel-body">

                <c:choose>
                    <c:when test="${view}">
                        <input type="submit" value="Edit" class="btn btn-warning" />
                        <br class="clearfix" />
                        <br class="clearfix" />
                        <input type="button" value="Refresh Ticket" class="btn btn-success"  onclick="location.reload();" />
                    </c:when>
                    <c:when test="${newTicket || edit}">

                        <div class="btn-group">

                            <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                Change ticket status<span class="caret"></span>
                            </button>

                            <c:set var="totalNextNodes" value="${fn:length(ticket.currentWorkflowStep.nextWorkflowStep)}"/>

                            <script type="text/javascript">
                                function changeStatus(inputStatus) {
                                    var elem = document.getElementById("workflowStatus");
                                    elem.value = inputStatus;
                                }
                            </script>

                            <ul class="dropdown-menu" role="menu">
                                <c:forEach var="nextNode" items="${ticket.currentWorkflowStep.nextWorkflowStep}">
                                    <li>
                                        <a href="javascript:changeStatus('${nextNode.workflowStatus.workflowStatusId}');">${nextNode.workflowStatus.workflowStatusName}</a>
                                    </li>
                                </c:forEach>
                                <c:if test="${totalNextNodes == 0}">
                                    <li><a href="#">No further actions</a></li>
                                    </c:if>
                            </ul>

                            <input type="hidden" value="" id="workflowStatus" name="workflowStatus" />


                        </div>

                        <br class="clearfix" />
                        <br class="clearfix" />

                        <input type="submit" value="Save Ticket" class="btn btn-success" />

                        <br class="clearfix" />
                        <br class="clearfix" />

                        <input type="button" id="cancelEdit" value="Cancel (Exit)" class="btn btn-danger" />
                        <script>
                            $('#cancelEdit').on('click', function () {
                                window.history.back();
                            });
                        </script>
                    </c:when>
                </c:choose>

            </div>
        </div>

        <div class="col-xs-12 col-sm-8 col-md-10">

            <c:choose>
                <c:when test="${newTicket}">
                    <h3>Ticket Creation</h3>
                    <h3><span class="label label-default">Remember to save to create the ticket</h3>

                    <br class="clearfix" />
                </c:when>
                <c:otherwise>
                    <h3><span class="label label-default">${ticket.ticketId}</span> ${ticket.shortDescription}</h3>

                    <br class="clearfix" />

                    <ol class="breadcrumb">
                        <li>
                            Snapshot as of the ticket as of 
                            <c:set var="now" value="<%=new java.util.Date()%>" />
                            <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${now}" />
                        </li>
                    </ol>
                </c:otherwise>
            </c:choose>

            <div class="panel panel-primary">
                <a name="basic"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Basic information</h3>
                </div>
                <div class="panel-body">
                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Ticket ID: 
                        </div>
                        <div class="ticketinfo_data">
                            <c:choose>
                                <c:when test="${newTicket}">
                                    Will be issued upon creation
                                </c:when>
                                <c:otherwise>
                                    ${ticket.ticketId}
                                    <input type="hidden" value="${ticket.ticketId}" name="ticketId" />
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Ticket Description: 
                        </div>
                        <div class="ticketinfo_data">
                            <c:choose>
                                <c:when test="${view}">
                                    ${ticket.shortDescription}
                                </c:when>
                                <c:when test="${newTicket || edit}">
                                    <input type="input" value="${ticket.shortDescription}" name="shortDescription" />
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Severity: 
                        </div>
                        <div class="ticketinfo_data">
                            <c:choose>
                                <c:when test="${newTicket || view}">
                                    ${ticket.applicationControl.severity.severityLevel}: ${ticket.applicationControl.severity.severityName} 
                                </c:when>
                                <c:when test="${edit}">
                                    <select name="severity">
                                        <c:forEach var="severity" items="${severityList}">
                                            <option 
                                                <c:if test="${ticket.applicationControl.severity.severityId == severity.severityId}">
                                                    selected
                                                </c:if>
                                                value="${severity.severityId}">${severity.severityLevel}: ${severity.severityName}</option>
                                        </c:forEach>
                                    </select>

                                    <span class="severity-note"><small>Note: Changing severity overrides any changes made to the ticket's status</small></span>
                                </c:when>
                            </c:choose>
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Ticket Type: 
                        </div>
                        <div class="ticketinfo_data">
                            ${ticket.applicationControl.ticketType.ticketTypeName}
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Application:
                        </div>
                        <div class="ticketinfo_data">
                            ${ticket.applicationControl.application.applicationName}
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Workflow:
                        </div>
                        <div class="ticketinfo_data">
                            ${ticket.applicationControl.workflow.workflowName}
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Current status:
                        </div>
                        <div class="ticketinfo_data">
                            ${ticket.currentWorkflowStep.workflowStatus.workflowStatusName}
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Current Queue:
                        </div>
                        <div class="ticketinfo_data">
                            ${ticket.currentWorkflowStep.queue.queueName}
                        </div>
                    </div>

                    <c:if test="${newTicket}">
                        <input type="hidden" name="applicationControlId" value="${ticket.applicationControl.applicationControlId}" />
                        <input type="hidden" name="ticketTypeId" value="${ticket.applicationControl.ticketType.ticketTypeId}" />
                        <input type="hidden" name="applicationId" value="${ticket.applicationControl.application.applicationId}" />
                        <input type="hidden" name="severityId" value="${ticket.applicationControl.severity.severityId}" />
                    </c:if>
                </div>
            </div>


            <div class="panel panel-primary">
                <a name="user"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">User information</h3>
                </div>
                <div class="panel-body">
                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Created By: 
                        </div>
                        <div class="ticketinfo_data">
                            <c:choose>
                                <c:when test="${newTicket}">
                                    ${user.firstName} ${user.lastName}
                                </c:when>
                                <c:otherwise>
                                    ${ticket.changeLog.changeLog[0].user.firstName} 
                                    ${ticket.changeLog.changeLog[0].user.lastName}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Reported By:
                        </div>
                        <div class="ticketinfo_data">
                            <c:choose>
                                <c:when test="${newTicket}">
                                    <select name="reportedByUserId">
                                        <c:forEach var="reportedByUser" items="${userList}">
                                            <option 
                                                <c:if test="${user.userId == reportedByUser.userId}">
                                                    selected
                                                </c:if>
                                                value="${reportedByUser.userId}">${reportedByUser.firstName} ${reportedByUser.lastName}</option>
                                        </c:forEach>
                                    </select>
                                </c:when>
                                <c:otherwise>
                                    ${ticket.reportedBy.firstName} 
                                    ${ticket.reportedBy.lastName}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                    <div class="ticketinfo_container">
                        <div class="ticketinfo_heading">
                            Last Processing By: 
                        </div>
                        <div class="ticketinfo_data">
                            <c:choose>
                                <c:when test="${newTicket}">
                                    ${user.firstName} ${user.lastName}
                                </c:when>
                                <c:otherwise>
                                    ${ticket.changeLog.changeLog[fn:length(ticket.changeLog.changeLog)-1].user.firstName} 
                                    ${ticket.changeLog.changeLog[fn:length(ticket.changeLog.changeLog)-1].user.lastName}
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>

                </div>
            </div>

            <c:if test="${view || edit}">
                <div class="panel panel-primary">
                    <a name="sla"></a>
                    <div class="panel-heading">
                        <h3 class="panel-title">SLA Clock</h3>
                    </div>
                    <div class="panel-body">
                        <div class="progressbar-override progress">
                            <c:choose>
                                <c:when test="${((ticket.changeLog.timeElapsed/ticket.applicationControl.slaClock)*100) >= 100}">
                                    <c:set var="sla" value="100"/>
                                    <c:set var="slanote" value="Violated"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:parseNumber var="sla" integerOnly="true" 
                                                     type="number" value="${(ticket.changeLog.timeElapsed/ticket.applicationControl.slaClock)*100}" />
                                    <c:set var="slanote" value="Not Violated"/>
                                </c:otherwise>
                            </c:choose>
                            <div class="progress-bar" role="progressbar" aria-valuenow="${sla}" aria-valuemin="0" aria-valuemax="100" style="min-width: 2em;  width: ${sla}%;">
                                ${sla}%
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                SLA:
                            </div>
                            <div class="ticketinfo_data">
                                ${slanote}
                            </div>
                        </div>
                    </div>

                </div>
            </c:if>

            <div class="panel panel-primary">
                <a name="ticketlog"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Ticket Log</h3>
                </div>
                <div class="panel-body">
                    <c:choose>
                        <c:when test="${edit || newTicket}">     
                            <textarea class="logentrytextarea" name="logentry"></textarea>

                            <br class="clearfix" />
                            (Remember: saving the ticket submits the message)
                            <br class="clearfix" />
                            <hr class="style-one" />
                        </c:when>
                    </c:choose>

                    <div style="padding:10px;max-height:500px;width:100%;overflow:auto;">

                        <c:forEach var="ticketLogEntry" items="${ticket.ticketLog.entryList}">
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

                    <c:choose>
                        <c:when test="${edit || newTicket}">

                            <div id="file-table">
                                <div class="file-media media">
                                    <div class="media-body">
                                        <h4 class="media-heading">File 1</h4>
                                        <input name="files[0].file" type="file" />
                                        <br class="clearfix" />
                                        Name: <input name="files[0].name" type="input" />
                                        Description: <input name="files[0].description" type="input" />
                                    </div>
                                </div>
                            </div>

                            <br class="clearfix" />
                            <input id="addFile" type="button" value="Add File" />

                            <script>
                                var counter = 1;
                                var limit = 10;

                                $('#addFile').on('click', function () {
                                    if (counter === limit) {
                                        alert("You have reached the limit of files");
                                    } else {
                                        content = "";
                                        content += "<h4 class=\"media-heading\">File " + (counter + 1) + "</h4>";
                                        content += "<input name=\"files[" + counter + "].file\" type=\"file\" />";
                                        content += "<br class=\"clearfix\" />";
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

                            <hr class="style-three" /> 
                        </c:when>
                    </c:choose>

                    <h4>File List</h4>

                    <table class="gridtable">
                        <tr>
                            <td>Name</td>
                            <td>Description</td>
                            <td>File Name</td>
                            <td>Download</td>
                        </tr>
                        <c:set var="totalFiles" value="${fn:length(ticket.attachmentList)}"/>

                        <c:forEach var="ticketAttachment" items="${ticket.attachmentList}">
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
                <a name="linkedtickets"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Linked Tickets</h3>
                </div>
                <div class="panel-body">

                    <c:choose>
                        <c:when test="${edit || newTicket}">

                            <div id="file-table">
                                <div class="file-media media">
                                    <div class="media-body">
                                        <h4 class="media-heading">Link To Ticket</h4>
                                        Ticket ID: <input type="text" id="linkTicketId" />
                                    </div>
                                </div>
                            </div>

                            <br class="clearfix" />
                            <input id="addLink" type="button" value="Add New Link" />

                            <br class="clearfix" />
                            <br class="clearfix" />

                            Confirmed to add: 
                            <select id="confirmedTicketLinks">
                                <option value="0">Links</option>
                            </select>
                            <script>
                                var counter = 0;
                                var confirmedLinkArray = {};

                                $('#addLink').on('click', function () {

                                    found = false;
                                    var linkTicketId = document.getElementById("linkTicketId").value;

                                    $.each(confirmedLinkArray, function (key, value) {
                                        if (value === linkTicketId) {
                                            found = true;
                                        }
                                    });

                                    if (found) {
                                        alert("Ticket has already been linked");
                                        return;
                                    }

                                    ticketLinks = document.getElementById('confirmedTicketLinks');

                                    ticketLinks.options[ticketLinks.options.length] = new Option(linkTicketId);
                                    confirmedLinkArray[counter] = linkTicketId;

                                    content = "<input type=\"hidden\" name=\"linkedTicketList\" value=\"" + linkTicketId + "\" />";

                                    newDiv = document.createElement('div');
                                    $(newDiv).html(content)
                                            .appendTo($("#hiddenLinkTable")); //main div
                                    counter++;
                                });
                            </script>

                            <hr class="style-three" /> 
                        </c:when>
                    </c:choose>

                    <!-- For hidden items -->
                    <div id="hiddenLinkTable"></div>

                    <table class="gridtable">
                        <tr>
                            <td>Ticket ID</td>
                            <td>Description</td>
                            <td>Workflow Status</td>
                        </tr>
                        <c:set var="totalLinks" value="${fn:length(ticket.ticketLinkList)}"/>

                        <c:forEach var="link" items="${ticket.ticketLinkList}">
                            <tr>
                                <td><a href="<%=request.getContextPath()%>/home/update/ticket/update/?ticketId=${link.toTicket.ticketId}">${link.toTicket.ticketId}</a></td>
                                <td>${link.toTicket.shortDescription}</td>
                                <td>${link.toTicket.currentWorkflowStep.workflowStatus.workflowStatusName}</td>
                            </tr>

                            <script>
                                ticketLinks = document.getElementById('confirmedTicketLinks');

                                ticketLinks.options[ticketLinks.options.length] = new Option(${link.toTicket.ticketId});
                                confirmedLinkArray[counter] = "${link.toTicket.ticketId}";

                                content = "<input type=\"hidden\" name=\"linkedTicketList\" value=\"" + ${link.toTicket.ticketId} + "\" />";

                                newDiv = document.createElement('div');
                                $(newDiv).html(content)
                                        .appendTo($("#hiddenLinkTable")); //main div

                                counter++;
                            </script>
                        </c:forEach>

                        <c:if test="${totalLinks == 0}">
                            <tr>
                                <td colspan="4">No Linked Tickets</td>
                            </tr>
                        </c:if>
                    </table>
                </div>
            </div>

            <c:if test="${view || edit}">
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
                            <c:forEach var="changeLog" items="${ticket.changeLog.changeLog}">
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
            </c:if>

        </div>

        <div class="ticketnav col-xs-4 col-md-2">
            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Quick Navigation</h3>
                </div>
                <div class="panel-body">
                    <a href="#basic">Basic information</a>
                    <br class="clearfix" />
                    <a href="#user">User/SLA Information</a>
                    <br class="clearfix" />
                    <a href="#ticketlog">Ticket Log</a>
                    <br class="clearfix" />
                    <a href="#attachments">Attachments</a>
                    <br class="clearfix" />
                    <a href="#linkedtickets">Linked Tickets</a>
                    <br class="clearfix" />
                    <a href="#changelog">Change Log</a>
                </div>
            </div>

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Ticket Control</h3>
                </div>
                <div class="panel-body">

                    <c:choose>
                        <c:when test="${view}">
                            <input type="submit" value="Edit" class="btn btn-warning" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" value="Refresh Ticket" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/ticket/update/?ticketId=${ticket.ticketId}')" />
                        </c:when>
                        <c:when test="${newTicket || edit}">

                            <div class="btn-group">

                                <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                                    Change ticket status<span class="caret"></span>
                                </button>

                                <c:set var="totalNextNodes" value="${fn:length(ticket.currentWorkflowStep.nextWorkflowStep)}"/>

                                <script type="text/javascript">
                                    function changeStatus(inputStatus) {
                                        var elem = document.getElementById("workflowStatus");
                                        elem.value = inputStatus;
                                    }
                                </script>

                                <ul class="dropdown-menu" role="menu">
                                    <c:forEach var="nextNode" items="${ticket.currentWorkflowStep.nextWorkflowStep}">
                                        <li>
                                            <a href="javascript:changeStatus('${nextNode.workflowStatus.workflowStatusId}');">${nextNode.workflowStatus.workflowStatusName}</a>
                                        </li>
                                    </c:forEach>
                                    <c:if test="${totalNextNodes == 0}">
                                        <li><a href="#">No further actions</a></li>
                                        </c:if>
                                </ul>

                                <input type="hidden" value="" id="workflowStatus" name="workflowStatus" />


                            </div>

                            <br class="clearfix" />
                            <br class="clearfix" />

                            <input type="submit" value="Save Ticket" class="btn btn-success" />

                            <br class="clearfix" />
                            <br class="clearfix" />

                            <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/ticket/cancel/?ticketId=${ticket.ticketId}')"  value="Cancel (Exit)" class="btn btn-danger" />

                        </c:when>
                    </c:choose>

                </div>
            </div>
        </div>

    </form:form>  
</div>


