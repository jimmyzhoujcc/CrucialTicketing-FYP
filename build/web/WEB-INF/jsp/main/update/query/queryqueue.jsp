<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Queue</h3>

    <form action="<%=request.getContextPath()%>/home/update/queue/" method="POST">
        <div class="query_container">
            <div class="query_heading">
                Queue ID:
            </div>
            <div class="query_box">
                <input type="text" name="queueId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Queue Name:
            </div>
            <div class="query_box">
                <input type="text" name="queueName" />
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
            <td>Queue Name</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(queueList) gt 0}">
                <c:forEach var="queue" items="${queueList}">

                    <td>${queue.queueId}</td>
                    <td>${queue.queueName}</td>
                    <td><input type="button" onClick="javascript:updateQueueInput(${queue.queueId})" value="Select" /></td>

                </c:forEach>
            </c:if>
            <c:if test="${fn:length(queueList) == 0}">
                <td colspan="3">No records to display</td>
            </c:if>
        </tr>
    </table>

    <script>
        function updateQueueInput(queueId) {
            document.getElementById('queueHiddenInput').value = queueId;
            $("#queryQueue").submit();
        }
    </script>
    <form id="queryQueue" action="<%=request.getContextPath()%>/home/update/queue/update/" method="POST">
        <input id="queueHiddenInput" type="hidden" name="queueId" />
    </form>
</div>

