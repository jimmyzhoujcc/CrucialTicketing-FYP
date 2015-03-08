<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>Workflow creation - Part 1</h3>


    Please fill in the form below.
    <br /><br />
    Process of creation:
    <br />
    <ul>
        <li>Fill in form</li>
        <li>Follow through each page</li>
        <li>Submit</li>
        <li>System will validate this is a unique request</li>
        <li>System will create the workflow</li>
        <li>System will send you a message confirming creation</li>
    </ul>

    Estimated Time:
    <ul>
        <li>To fill out form: 5 minutes
        <li>System response: 5 seconds
    </ul>
</div>
<br class="clearfix" />
<br class="clearfix" />
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/workflow/p2/" commandName="workflow">
    <div class="create-form-button-container">
        <div class="form-group"> Ticket ID
            <input type="text" name="ticketId" class="form-control" placeholder="Ticket ID for this request" />
            <p class="help-block">Ticket ID ongoing for this request</p>
        </div>      
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Workflow name:
            <form:input type="text" path="workflowName" class="form-control" placeholder="Enter workflow name" />
            <p class="help-block">Unique workflow identifier</p>
        </div>      
    </div>

    <br class="clearfix" />

    <script>
        $().ready(function () {

            $('#statusAdd').click(function () {
                clearStatusList();
                var table = document.getElementById('statusTable');
                var rowLength = table.rows.length;
                for (var i = 1; i < rowLength; i += 1) {
                    var row = table.rows[i];
                    if (document.getElementById(row.cells[0].innerText + '_status_checkbox').checked) {
                        statusList = document.getElementById('confirmedStatusList');
                        addNewHiddenStatus(row.cells[0].innerText);
                        statusList.options[statusList.options.length] = new Option(row.cells[1].innerText);
                    }
                }
            });
            $('#statusClear').click(function () {
                clearStatusList();
            });
            function clearStatusList() {
                document.getElementById('confirmedStatusList_hidden').innerHTML = "";
                var selectBox = document.getElementById("confirmedStatusList");
                var i;
                counter = 0;
                for (i = selectBox.options.length - 1; i >= 0; i--)
                {
                    selectBox.remove(i);
                }
            }
        });
    </script>

    <script>
        var counter = 0;
        function addNewHiddenStatus(statusId) {
            content = "<input type=\"hidden\" name=\"workflowMap.workflow[" + counter + "\].workflowStatus.workflowStatusId\" value=\"" + statusId + "\" />";

            newDiv = document.createElement('div');
            $(newDiv).html(content)
                    .appendTo($("#confirmedStatusList_hidden")); //main div
            counter++;
        }
    </script>

    <div class="create-form-button-container">

        <div class="form-group">Existing Status'
            <table class="gridtable" id="statusTable">
                <tr>
                    <td>Status ID</td>
                    <td>Status Name</td>
                    <td>Choice to use</td>
                    <td>SLA Activator</td>
                </tr>

                <c:forEach var="workflowStatus" items="${workflowStatusList}">
                    <tr>
                        <td>${workflowStatus.workflowStatusId}</td>
                        <td>${workflowStatus.workflowStatusName}</td>
                        <td><input type="checkbox" id="${workflowStatus.workflowStatusId}_status_checkbox" /></td>
                        <td><input type="checkbox" id="${workflowStatus.workflowStatusId}_sla_checkbox" /></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>

    <br class="clearfix" />
    <div class="create-form-button-container">
        <input type="button" id="statusAdd" value="Update Status List" class="btn btn-success" />
        <input type="button" id="statusClear" value="Clear Status List" class="btn btn-danger" />
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Confirmed Status'
            <select multiple="true" size="5" id="confirmedStatusList" class="form-control">
            </select>
        </div>
    </div>

    <div id="confirmedStatusList_hidden">
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <div class="create-form-button-container">
        <input type="submit" class="btn btn-primary btn-lg" value="Next" />
    </div>
</form:form>