<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

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

<div class="row">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="workflow">

        <div id="subticketnav" class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">User Control</h3>
            </div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${edit}">
                        <input type="submit" value="Save" class="btn btn-success" />
                        <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/workflow/cancel/?workflowId=${workflow.workflowId}')"  value="Cancel (Exit)" class="btn btn-danger" />
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Edit" class="btn btn-warning" />
                        <input type="button" value="Refresh" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/workflow/update/?workflowId=${workflow.workflowId}')" />
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <div class="col-xs-12 col-sm-8 col-md-10">
            <h3><span class="label label-default">Workflow Record</span> ${workflow.workflowName}</h3>

            <br class="clearfix" />

            <ol class="breadcrumb">
                <li>
                    Snapshot as of the workflow as of 
                    <c:set var="now" value="<%=new java.util.Date()%>" />
                    <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${now}" />
                </li>
            </ol>

            <div class="create-form-button-container form-override">
                <div class="panel panel-primary">
                    <a name="basic"></a>
                    <div class="panel-heading">
                        <h3 class="panel-title">User information</h3>
                    </div>
                    <div class="panel-body">

                        <input type="hidden" value="${workflow.workflowId}" name="workflowId" />

                        <c:if test="${edit}">
                            <div class="ticketinfo_container">
                                <div class="ticketinfo_heading">
                                    Ticket ID:
                                </div>
                                <div class="ticketinfo_data">
                                    <input type="text" name="ticketId" /> (for request)
                                </div>
                            </div>

                            <br class="clearfix" />
                            <br class="clearfix" />
                        </c:if>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Workflow ID:
                            </div>
                            <div class="ticketinfo_data">
                                ${workflow.workflowId}
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Workflow Name:
                            </div>
                            <div class="ticketinfo_data">
                                ${workflow.workflowName}
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Status:
                            </div>
                            <div class="ticketinfo_data">
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
                        </div>

                        <br class="clearfix" />
                        <br class="clearfix" />

                        <div class="ticketinfo_heading">
                            Workflow Structure:
                        </div>

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
                    </div>
                </div>
            </div>
        </div>

        <div class="ticketnav col-xs-4 col-md-2">

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">User Control</h3>
                </div>
                <div class="panel-body">
                    <c:choose>
                        <c:when test="${edit}">
                            <input type="submit" value="Save" class="btn btn-success" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/workflow/cancel/?workflowId=${workflow.workflowId}')"  value="Cancel (Exit)" class="btn btn-danger" />
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Edit" class="btn btn-warning" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" value="Refresh" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/workflow/update/?workflowId=${workflow.workflowId}')" />
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </form:form>
</div>