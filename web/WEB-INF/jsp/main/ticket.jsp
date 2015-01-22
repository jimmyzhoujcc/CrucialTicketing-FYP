<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<div class="row">
    <div class="col-xs-12 col-sm-6 col-md-10">
        <h3><span class="label label-default">${ticketObject.ticketId}</span> ${ticketObject.shortDescription}</h3>

        <br />

        <ol class="breadcrumb">
            <li>
                <c:if test="${ticketObject.status.statusId == 1}">
                <u>
                </c:if>
                New
                <c:if test="${ticketObject.status.statusId == 1}">
                </u>
            </c:if>
            </li>
            <c:forEach var="workflowItem" items="${ticketObject.applicationControl.workflow.workflow}">
                <li>
                    <c:if test="${workflowItem.status.statusId == ticketObject.status.statusId}">
                    <u>
                    </c:if>

                    ${workflowItem.status.statusName}

                    <c:if test="${workflowItem.status.statusId == ticketObject.status.statusId}">
                    </u>
                </c:if>

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
                Current status: ${ticketObject.status.statusName}<br />
                Current Queue: <br />
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
                <div style="padding:10px;max-height:300px;width:100%;overflow:auto;">

                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-picture media-object" src="<%=request.getContextPath()%>/profile/1.PNG" alt="...">
                            </a>
                        </div>
                        <div class="media-body">
                            <h4 class="media-heading">Test Test <small>15th October 2014 15:30</small></h4>
                            Message added to log for test
                        </div>
                    </div>

                    <div class="media">
                        <div class="media-left">
                            <a href="#">
                                <img class="media-picture media-object" src="<%=request.getContextPath()%>/profile/system.png" alt="...">
                            </a>
                        </div>
                        <div class="media-body">
                            <h4 class="media-heading">System message <small>15th October 2014 15:30</small></h4>
                            Status has been updated to 'New'
                        </div>
                    </div>

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
                <button type="button" class="btn btn-warning">Edit</button>
                <button type="button" class="btn btn-success">Save Ticket</button>

                <br /><br />
                <!-- Single button -->
                <div class="btn-group">
                    <button type="button" class="btn btn-info dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
                        Change ticket status<span class="caret"></span>
                    </button>
                    <ul class="dropdown-menu" role="menu">
                        <li><a href="#">Action</a></li>
                        <li><a href="#">Another action</a></li>
                        <li><a href="#">Something else here</a></li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>


