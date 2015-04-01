<%-- 
    Document   : queryticket
    Created on : 07-Jan-2015, 01:02:05
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"  %>

<div class="create-form-button-container">
    <form method="POST" action="<%=request.getContextPath()%>/home/update/ticket/">
        Select by
        <br />
        Ticket ID: <input type="text" name="ticketId" />

        <script>
            $(function () {
                $("#datepicker_from").datepicker({dateFormat: 'dd-mm-yy'});
            });
            $(function () {
                $("#datepicker_to").datepicker({dateFormat: 'dd-mm-yy'});
            });
        </script>    

        <br class="clearfix" />

        Last Updated From: 

        <input type="text" name="lastUpdatedFrom" id="datepicker_from">

        <br class="clearfix" />

        Last Updated To:

        <input type="text" name="lastUpdatedTo" id="datepicker_to">

        <br class="clearfix" />

        <input type="submit" />
    </form>

    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(ticketList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Short Description</td>
                <td>Workflow Status</td>
                <td>Select</td>
            </tr>

            <c:forEach var="ticket" items="${ticketList}">
                <tr>
                    <td>${ticket.ticketId}</td>
                    <td>${ticket.shortDescription}</td>
                    <td>${ticket.currentWorkflowStep.workflowStatus.workflowStatusName}</td>
                    <td><input type="button" onClick="javascript:updateHiddenIdentifier(${ticket.ticketId})" value="Select" /></td>
                </tr>
            </c:forEach>
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
    </c:if>
</div>