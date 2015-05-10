<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Configuration</h3>

    <form action="<%=request.getContextPath()%>/home/update/configuration/" method="POST">

        <div class="query_container">
            <div class="query_heading">
                Configuration ID:
            </div>
            <div class="query_box">
                <input type="text" name="applicationControlId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Ticket Type: 
            </div>
            <div class="query_box">
                <select name="ticketType">
                    <option value="0">Select</option>
                    <c:forEach var="ticketType" items="${ticketTypeList}">
                        <option value="${ticketType.ticketTypeId}">${ticketType.ticketTypeName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Application: 
            </div>
            <div class="query_box">
                <select name="application">
                    <option value="0">Select</option>
                    <c:forEach var="application" items="${applicationList}">
                        <option value="${application.applicationId}">${application.applicationName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Severity: 
            </div>
            <div class="query_box">
                <select name="severity">
                    <option value="0">Select</option>
                    <c:forEach var="severity" items="${severityList}">
                        <option value="${severity.severityId}">${severity.severityName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Workflow: 
            </div>
            <div class="query_box">
                <select name="workflow">
                    <option value="0">Select</option>
                    <c:forEach var="workflow" items="${workflowList}">
                        <option value="${workflow.workflowId}">${workflow.workflowName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Role: 
            </div>
            <div class="query_box">
                <select name="role">
                    <option value="0">Select</option>
                    <c:forEach var="role" items="${roleList}">
                        <option value="${role.roleId}">${role.roleName}</option>
                    </c:forEach>
                </select>
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                SLA Clock: 
            </div>
            <div class="query_box">
                <input type="text" name="slaClock" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                <input type="submit" />
            </div>
        </div>
    </form>

    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <table class="table table-hover">
        <tr>
            <td>ID</td>
            <td>Ticket Type</td>
            <td>Application</td>
            <td>Severity</td>
            <td>Workflow</td>
            <td>Role</td>
            <td>SLA Clock</td>
            <td>Status Flag</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(applicationControlList) gt 0}">
                <c:forEach var="applicationControl" items="${applicationControlList}">

                    <td>${applicationControl.applicationControlId}</td>
                    <td>${applicationControl.ticketType.ticketTypeName}</td>
                    <td>${applicationControl.application.applicationName}</td>
                    <td>${applicationControl.severity.severityLevel}:${applicationControl.severity.severityName}</td>
                    <td>${applicationControl.workflow.workflowName}</td>
                    <td>${applicationControl.role.roleName}</td>
                    <td>${applicationControl.slaClock}</td>
                    <td>${applicationControl.activeFlag}</td>
                    <td><input type="button" onClick="javascript:updateConfigurationInput(${applicationControl.applicationControlId})" value="Select" /></td>
                    </c:forEach>
                </c:if>

            <c:if test="${fn:length(applicationControlList) == 0}">
                <td colspan="9">No records to display</td>
            </c:if>
        </tr>
    </table>

    <script>
        function updateConfigurationInput(applicationControlId) {
            document.getElementById('configurationHiddenInput').value = applicationControlId;
            $("#queryConfiguration").submit();
        }
    </script>
    <form id="queryConfiguration" action="<%=request.getContextPath()%>/home/update/configuration/update/" method="POST">
        <input id="configurationHiddenInput" type="hidden" name="applicationControlId" />
    </form>
</div>

