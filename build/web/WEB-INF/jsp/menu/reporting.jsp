<%-- 
    Document   : reporting
    Created on : 23-Mar-2015, 21:30:11
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="column1">
    <h3>Reporting</h3>

    <form method="POST" action="<%=request.getContextPath()%>/home/reporting/execute/">
        
        <ul class="nav nav-pills">
            <li role="presentation" class="active"><input type="submit" class="btn btn-primary" /></li>
        </ul>

        <br class="clearfix" />
        <br class="clearfix" />

        <script>
            $(document).ready(function () {

                $('#moveTicketToSelected').click(function () {

                    var ticketId = document.getElementById('ticketId').value;

                    if ($("#ticketSelectedList option[value='" + ticketId + "']").length > 0) {
                        alert("Ticket already selected");
                    } else {
                        var option = $('<option/>');
                        option.attr({'value': ticketId}).text(ticketId);
                        $('#ticketSelectedList').append(option);

                        content = "<input type=\"hidden\" name=\"ticketList\" value=\"" + ticketId + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#ticketList")); //main div
                    }
                });

                $('#clearTicketToSelected').click(function () {
                    $('#ticketSelectedList').empty();
                });

                //

                $('#moveTicketTypeToSelected').click(function () {
                    var $options = $("#ticketTypeList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#ticketTypeSelectedList option[value='" + value + "']").length > 0) {
                        alert("Ticket Type already selected");
                    } else {
                        $('#ticketTypeSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"ticketTypeList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#ticketTypeList")); //main div
                    }
                });

                $('#clearTicketTypeToSelected').click(function () {
                    $('#ticketTypeSelectedList').empty();
                });

                //

                $('#moveApplicationToSelected').click(function () {
                    var $options = $("#applicationList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#applicationSelectedList option[value='" + value + "']").length > 0) {
                        alert("Application already selected");
                    } else {
                        $('#applicationSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"applicationList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#applicationList")); //main div
                    }
                });

                $('#clearApplicationToSelected').click(function () {
                    $('#applicationSelectedList').empty();
                });


                // 

                $('#moveSeverityToSelected').click(function () {
                    var $options = $("#severityList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#severitySelectedList option[value='" + value + "']").length > 0) {
                        alert("Severity already selected");
                    } else {
                        $('#severitySelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"severityList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#severityList")); //main div
                    }
                });

                $('#clearSeverityToSelected').click(function () {
                    $('#severitySelectedList').empty();
                });

                //

                $('#moveWorkflowToSelected').click(function () {
                    var $options = $("#workflowList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#workflowSelectedList option[value='" + value + "']").length > 0) {
                        alert("Workflow already selected");
                    } else {
                        $('#workflowSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"workflowList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#workflowList")); //main div
                    }
                });

                $('#clearWorkflowToSelected').click(function () {
                    $('#workflowSelectedList').empty();
                });


                //

                $('#moveWorkflowStatusToSelected').click(function () {
                    var $options = $("#workflowStatusList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#workflowStatusSelectedList option[value='" + value + "']").length > 0) {
                        alert("Workflow Status already selected");
                    } else {
                        $('#workflowStatusSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"workflowStatusList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#workflowStatusList")); //main div
                    }
                });

                $('#clearWorkflowStatusToSelected').click(function () {
                    $('#workflowStatusSelectedList').empty();
                });

                //

                $('#moveReportedByUserToSelected').click(function () {
                    var $options = $("#reportedByUserList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#reportedByUserSelectedList option[value='" + value + "']").length > 0) {
                        alert("Workflow Status already selected");
                    } else {
                        $('#reportedByUserSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"reportedByUserList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#reportedByUserList")); //main div
                    }
                });

                $('#clearReportedByUserToSelected').click(function () {
                    $('#reportedByUserSelectedList').empty();
                });

                //

                $('#moveCreatedByUserToSelected').click(function () {
                    var $options = $("#createdByUserList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#createdByUserSelectedList option[value='" + value + "']").length > 0) {
                        alert("Workflow Status already selected");
                    } else {
                        $('#createdByUserSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"createdByUserList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#createdByUserList")); //main div
                    }
                });

                $('#clearCreatedByUserToSelected').click(function () {
                    $('#createdByUserSelectedList').empty();
                });

                //

                $('#moveLastUpdatedByUserToSelected').click(function () {
                    var $options = $("#lastUpdatedByUserList > option:selected").clone();
                    var value = $options[0].value;

                    if ($("#lastUpdatedByUserSelectedList option[value='" + value + "']").length > 0) {
                        alert("Workflow Status already selected");
                    } else {
                        $('#lastUpdatedByUserSelectedList').append($options);

                        content = "<input type=\"hidden\" name=\"lastUpdatedByUserList\" value=\"" + value + "\" />";
                        newDiv = document.createElement('div');
                        $(newDiv).html(content)
                                .appendTo($("#lastUpdatedByUserList")); //main div
                    }
                });

                $('#clearLastUpdatedByUserToSelected').click(function () {
                    $('#lastUpdatedByUserSelectedList').empty();
                });
            });
        </script>

        <br class="clearfix" />
        <table width="100%">
            <tr>
                <td width="25%">
                    <table>
                        <tr>
                            <td colspan="3">
                                Ticket ID
                            </td>
                        </tr>
                        <tr>
                             <td width="40%">
                                <input type="text" id="ticketId" />
                            </td>
                            <td width="20%">
                                <input id="clearTicketToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveTicketToSelected" type="button" value="Move" />
                            </td>
                             <td width="40%">
                                <select id="ticketSelectedList" size="10">
                                </select>
                                <div id="ticketList"></div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <hr class="clearfix" />
                </td>
            </tr>

            <tr>
                <td width="25%">
                    <table>
                        <tr>
                            <td colspan="3">
                                Ticket Type
                            </td>
                        </tr>
                        <tr>
                           <td width="40%">
                                <select id="ticketTypeList">
                                    <option value="0">Select</option>
                                    <c:forEach var="ticketType" items="${ticketTypeList}">
                                        <option value="${ticketType.ticketTypeId}">${ticketType.ticketTypeName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearTicketTypeToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveTicketTypeToSelected" type="button" value="Move" />
                            </td>
                             <td width="40%">
                                <select id="ticketTypeSelectedList" size="10">
                                </select>
                                <div id="ticketTypeList"></div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td width="25%">
                    <table>
                        <tr>
                            <td colspan="3">Application</td>
                        </tr>
                        <tr>
                             <td width="40%">
                                <select id="applicationList">
                                    <option value="0">Select</option>
                                    <c:forEach var="application" items="${applicationList}">
                                        <option value="${application.applicationId}">${application.applicationName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearApplicationToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveApplicationToSelected" type="button" value="Move" />
                            </td>
                             <td width="40%">
                                <select id="applicationSelectedList" size="10" multiple>
                                </select>
                                <div id="applicationList"></div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td width="25%">
                    <table>
                        <tr>
                            <td colspan="3">Severity</td>
                        </tr>
                        <tr>
                            <td width="40%">
                                <select id="severityList">
                                    <option value="0">Select</option>
                                    <c:forEach var="severity" items="${severityList}">
                                        <option value="${severity.severityId}">${severity.severityLevel}:${severity.severityName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearSeverityToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveSeverityToSelected" type="button" value="Move" />
                            </td>
                            <td width="40%">
                                <select id="severitySelectedList" size="10" multiple>
                                </select>
                                <div id="severityList"></div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td width="25%">
                    <table>
                        <tr>
                            <td colspan="3">Workflow</td>
                        </tr>
                        <tr>
                            <td width="40%">
                                <select id="workflowList">
                                    <option value="0">Select</option>
                                    <c:forEach var="workflow" items="${workflowList}">
                                        <option value="${workflow.workflowId}">${workflow.workflowName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearWorkflowToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveWorkflowToSelected" type="button" value="Move" />
                            </td>
                            <td width="40%">
                                <select id="workflowSelectedList" size="10" multiple>
                                </select>
                                <div id="workflowList"></div>
                            </td>
                        </tr>
                    </table>
                </td>
            </tr>

            <tr>
                <td colspan="4">
                    <hr class="clearfix" />
                </td>
            </tr>

            <tr>
                <td width="25%">
                    <table>

                        <tr>
                            <td colspan="3">Workflow Status</td>
                        </tr>
                        <tr>
                            <td width="40%">
                                <select id="workflowStatusList">
                                    <option value="0">Select</option>
                                    <c:forEach var="workflowStatus" items="${workflowStatusList}">
                                        <option value="${workflowStatus.workflowStatusId}">${workflowStatus.workflowStatusName}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearWorkflowStatusToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveWorkflowStatusToSelected" type="button" value="Move" />
                            </td>
                            <td width="40%">
                                <select id="workflowStatusSelectedList" size="10" multiple>
                                </select>
                                <div id="workflowStatusList"></div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td width="25%">
                    <table>

                        <tr>
                            <td colspan="3">Created By User</td>
                        </tr>
                        <tr>
                            <td width="40%">
                                <select id="createdByUserList">
                                    <option value="0">Select</option>
                                    <c:forEach var="user" items="${userList}">
                                        <option value="${user.userId}">${user.username}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearCreatedByUserToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveCreatedByUserToSelected" type="button" value="Move" />
                            </td>
                            <td width="40%">
                                <select id="createdByUserSelectedList" size="10" multiple>
                                </select>
                                <div id="createdByUserList"></div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td width="25%">
                    <table>

                        <tr>
                            <td colspan="3">Reported By User</td>
                        </tr>
                        <tr>
                            <td width="40%">
                                <select id="reportedByUserList">
                                    <option value="0">Select</option>
                                    <c:forEach var="user" items="${userList}">
                                        <option value="${user.userId}">${user.username}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearReportedByUserToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveReportedByUserToSelected" type="button" value="Move" />
                            </td>
                            <td width="40%">
                                <select id="reportedByUserSelectedList" size="10" multiple>
                                </select>
                                <div id="reportedByUserList"></div>
                            </td>
                        </tr>
                    </table>
                </td>

                <td width="25%">
                    <table>

                        <tr>
                            <td colspan="3">Last Updated By User</td>
                        </tr>
                        <tr>
                            <td width="40%">
                                <select id="lastUpdatedByUserList">
                                    <option value="0">Select</option>
                                    <c:forEach var="user" items="${userList}">
                                        <option value="${user.userId}">${user.username}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <td width="20%">
                                <input id="clearLastUpdatedByUserToSelected" type="button" value="Clear" />
                                <br class="clearfix" />
                                <input id="moveLastUpdatedByUserToSelected" type="button" value="Move" />
                            </td>
                            <td width="40%">
                                <select id="lastUpdatedByUserSelectedList" size="10" multiple>
                                </select>
                                <div id="lastUpdatedByUserList"></div>
                            </td>
                        </tr>
                    </table>
                </td>
                <td width="25%"></td>
            </tr>

            <tr>
                <td colspan="4">
                    <hr class="clearfix" />
                </td>
            </tr>

            <tr>
                <td>
                    Date Created: 
                </td>
                <td>
                    <input type="text" name="dateCreatedFrom" placeholder="DD-MM-YYYY" /> (from date)
                </td>
                <td>
                    <input type="text" name="dateCreatedTo" placeholder="DD-MM-YYYY"  /> (to date)
                </td>
                <td></td>
            </tr>

            <tr>
                <td>
                    Date Last Updated: 
                </td>
                <td>
                    <input type="text" name="dateLastUpdatedFrom" placeholder="DD-MM-YYYY" /> (from date)
                </td>
                <td>
                    <input type="text" name="dateLastUpdatedTo" placeholder="DD-MM-YYYY"  /> (to date)
                </td>
                <td></td>
            </tr>
        </table>
    </form>
</div>