<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">

    <h3>Workflow creation - Part 2</h3>


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
<form:form method="POST" action="${pageContext.request.contextPath}/home/create/workflow/create/" commandName="workflow">

    From: 
    <select id="workflowFromSelection">
        <c:forEach var="workflow" items="${workflow.workflowMap.workflow}">
            <option value="${workflow.workflowStatus.workflowStatusId}">
                ${workflow.workflowStatus.workflowStatusName}
            </option>
        </c:forEach>
    </select>

    To: 
    <select id="workflowToSelection">
        <c:forEach var="workflow" items="${workflow.workflowMap.workflow}">
            <option value="${workflow.workflowStatus.workflowStatusId}">
                ${workflow.workflowStatus.workflowStatusName}
            </option>
        </c:forEach>
    </select>

    <br class="clearfix" />
    <br class="clearfix" />

    Required role for change: 
    <select id="roleSelection">
        <c:forEach var="role" items="${roleList}">
            <option value="${role.roleId}">
                ${role.roleName}
            </option>
        </c:forEach>
    </select>

    <br class="clearfix" />
    <br class="clearfix" />

    <script>
        $().ready(function () {

            Object.size = function (obj) {
                var size = 0, key;
                for (key in obj) {
                    if (obj.hasOwnProperty(key))
                        size++;
                }
                return size;
            };


            var MyArray = {};
            var indexArray = {};
            var counter = 0;

            $('#workflowAdd').click(function () {
                var workflowFromId = $("#workflowFromSelection option:selected").val();
                var workflowToId = $("#workflowToSelection option:selected").val();

                if (workflowFromId === workflowToId) {
                    alert("A workflow status cannot move to the same workflow status");
                    return;
                }

                var roleId = $("#roleSelection option:selected").val();
                var found = false;
                var size = 0;

                if (!(workflowFromId in MyArray)) {
                    alert("doesnt exist");
                    addNewBaseWorkflowStep(counter, workflowFromId);
                    MyArray[workflowFromId] = {0: workflowToId};
                    indexArray[workflowFromId] = counter;
                    counter++;
                } else {
                    size = Object.size(MyArray[workflowFromId]);

                    $.each(MyArray[workflowFromId], function (key, value) {
                        if (value === workflowToId) {
                            found = true;
                        }
                    });

                    if (found) {
                        alert("Already exists");
                    } else {
                        MyArray[workflowFromId][size] = workflowToId;
                    }

                }

                if (!found) {
                    workflowList = document.getElementById('confirmedWorkflowList');
                    addNewHiddenWorkflowStep(indexArray[workflowFromId], size, workflowToId, roleId);
                    workflowList.options[workflowList.options.length] = new Option(
                            $("#workflowFromSelection option:selected").text()
                            + " --> "
                            + $("#workflowToSelection option:selected").text()
                            + " (" + $("#roleSelection option:selected").text() + ")");
                }
            });

            $('#workflowClear').click(function () {
                clearWorkflowList();
            });

            function clearWorkflowList() {
                document.getElementById('confirmedWorkflowList_hidden').innerHTML = "";
                var selectBox = document.getElementById("confirmedWorkflowList");
                var i;
                MyArray = {};
                indexArray = {};
                for (i = selectBox.options.length - 1; i >= 0; i--)
                {
                    selectBox.remove(i);
                }
            }
        });
    </script>

    <script>
        function addNewBaseWorkflowStep(index, workflowFromId) {
            content = "<input type=\"hidden\" name=\"workflowMap.workflow[" + index + "].workflowStatus.workflowStatusId\" value=\"" + workflowFromId + "\" />";
            content += "<input type=\"hidden\" name=\"workflowMap.workflow[" + index + "].clockActive\" value=\"" + document.getElementById(workflowFromId + "_sla_flag").innerText + "\" />";
            content += "<input type=\"hidden\" name=\"workflowMap.workflow[" + index + "].queue.queueId\" value=\"" + document.getElementById(workflowFromId + "_queue").innerText + "\" />";

            newDiv = document.createElement('div');
            $(newDiv).html(content)
                    .appendTo($("#confirmedWorkflowList_hidden")); //main div
        }

        function addNewHiddenWorkflowStep(index, count, workflowToId, roleId) {

            content = "<input type=\"hidden\" name=\"workflowMap.workflow[" + index + "].nextWorkflowStep[" + count + "].workflowStatus.workflowStatusId\" value=\"" + workflowToId + "\" />";
            content += "<input type=\"hidden\" name=\"workflowMap.workflow[" + index + "].nextWorkflowStep[" + count + "].role.roleId\" value=\"" + roleId + "\" />";

            newDiv = document.createElement('div');
            $(newDiv).html(content)
                    .appendTo($("#confirmedWorkflowList_hidden")); //main div
        }
    </script>

    <br class="clearfix" />
    <div class="create-form-button-container">
        <input type="button" id="workflowAdd" value="Add workflow step" class="btn btn-success" />
        <input type="button" id="workflowClear" value="Clear workflow step List" class="btn btn-danger" />
    </div>

    <br class="clearfix" />

    <div class="create-form-button-container">
        <div class="form-group">Workflow steps
            <select multiple="true" size="5" id="confirmedWorkflowList" class="form-control">
            </select>
        </div>
    </div>

    <div id="confirmedWorkflowList_hidden">
    </div>

    <br class="clearfix" />
    <br class="clearfix" />

    <c:forEach var="workflowStep" items="${workflow.workflowMap.workflow}">
        <div style="display:none" id="${workflowStep.workflowStatus.workflowStatusId}_sla_flag">${workflowStep.clockActive}</div>
        <div style="display:none" id="${workflowStep.workflowStatus.workflowStatusId}_queue">${workflowStep.queue.queueId}</div>
    </c:forEach>

    <div class="create-form-button-container">
        <input type="hidden" name="ticketId" value="${ticketId}" />
        <input type="hidden" name="workflowName" value="${workflow.workflowName}" />
        <input type="submit" class="btn btn-primary btn-lg" value="Submit for system review" />
    </div>
</form:form>