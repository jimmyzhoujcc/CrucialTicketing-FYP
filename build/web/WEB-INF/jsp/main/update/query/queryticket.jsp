<%-- 
    Document   : queryticket
    Created on : 07-Jan-2015, 01:02:05
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<div class="create-form-button-container">
    <form method="POST" action="<%=request.getContextPath()%>/home/update/ticket/">

        <div class="query_container">
            <div class="query_heading">
                Ticket ID:
            </div>
            <div class="query_box">
                <input type="text" name="ticketId" />
            </div>
        </div>

        <script>
            $(function () {
                $("#datepicker_from").datepicker({dateFormat: 'dd-mm-yy'});
            });
            $(function () {
                $("#datepicker_to").datepicker({dateFormat: 'dd-mm-yy'});
            });
        </script>    

        <div class="query_container">
            <div class="query_heading">
                Last Updated From: 
            </div>
            <div class="query_box">
                <input type="text" name="lastUpdatedFrom" id="datepicker_from">
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Last Updated To:
            </div>
            <div class="query_box">
                <input type="text" name="lastUpdatedTo" id="datepicker_to">
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
            <td>Short Description</td>
            <td>Workflow Status</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(ticketList) gt 0}">

                <c:forEach var="ticket" items="${ticketList}">
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.shortDescription}</td>
                    <td>${ticket.currentWorkflowStep.workflowStatus.workflowStatusName}</td>
                    <td><input type="button" onClick="javascript:updateHiddenIdentifier(${ticket.ticketId})" value="Select" /></td>
                    </c:forEach>
                </c:if>

            <c:if test="${fn:length(ticketList) == 0}">
                <td colspan="4">No records to display</td>
            </c:if>
    </table>

    <script>
        function updateHiddenIdentifier(id) {
            document.getElementById('hiddenIdentifier').value = id;
            $("#queryForSingle").submit();
        }
    </script>

    <form id="queryForSingle" action="<%=request.getContextPath()%>/home/update/ticket/update/" method="POST">
        <input id="hiddenIdentifier" type="hidden" name="ticketId" />
    </form>
</div>