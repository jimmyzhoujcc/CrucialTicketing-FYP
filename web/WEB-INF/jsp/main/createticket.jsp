<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row">
    <form:form method="POST" action="${pageContext.servletContext.contextPath}/home/create/saveticket/" modelAttribute="uploadedfilelog" enctype="multipart/form-data">  

        <div class="col-xs-12 col-sm-8 col-md-10">
            <c:if test="${fn:length(alert)>0}">
                <div class="alert alert-danger" role="alert">${alert}</div>
                <% request.removeAttribute("alert");%>
            </c:if>
            <h3><span class="label label-default">Remember to save to create the ticket</h3>

            <br />

            <div class="panel panel-primary">
                <a name="basic"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Basic information</h3>
                </div>
                <div class="panel-body">
                    Ticket Description: 
                    <input type="input" value="${ticketObject.shortDescription}" name="shortdescription" />

                    <br /><br />

                    <input type="hidden" value="${ticketObject.applicationControl.applicationControlId}" name="applicationControlId" />
                    <input type="hidden" value="${ticketObject.applicationControl.ticketType.ticketTypeId}" name="ticketTypeId" />
                     <input type="hidden" value="${ticketObject.applicationControl.application.applicationId}" name="applicationId" />
                    <input type="hidden" value="${ticketObject.applicationControl.severity.severityId}" name="severityId" />
                    
                    Ticket Type ${ticketObject.applicationControl.ticketType.ticketTypeName}
                    <br />
                    Application: ${ticketObject.applicationControl.application.applicationName}
                    <br />
                    Severity: ${ticketObject.applicationControl.severity.severityLevel}:${ticketObject.applicationControl.severity.severityName}

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
                    Created By: ${user.firstName} ${user.lastName}
                    <br />
                    Reported By: ${ticketObject.reportedBy.firstName} ${ticketObject.reportedBy.lastName}
                    <select name="reportedByUser">
                        <c:forEach var="reportedByUser" items="${userList}">
                            <option 
                                <c:if test="${user.userId == reportedByUser.userId}">
                                    selected
                                </c:if>
                                value="${reportedByUser.userId}">${reportedByUser.firstName} ${reportedByUser.lastName}</option>
                        </c:forEach>
                    </select>
                    <br />
                    Last Processing By: ${user.firstName} ${user.lastName}
                </div>
            </div>

            <div class="panel panel-primary">
                <a name="ticketlog"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Ticket Log</h3>
                </div>
                <div class="panel-body">
                    <textarea class="logentrytextarea" name="logentry"></textarea>
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
                    <a href="#ticketlog">Ticket Log</a>
                    <br />
                    <a href="#attachments">Attachments</a>
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


