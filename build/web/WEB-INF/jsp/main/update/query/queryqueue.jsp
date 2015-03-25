<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Queue</h3>

    <form action="<%=request.getContextPath()%>/home/update/queue/" method="POST">
        Queue ID: <input type="text" name="queueId" />
        <br class="clearfix" />
        Queue Name: <input type="text" name="queueName" />
        <br class="clearfix" />
        <br class="clearfix" />
        <input type="submit" />
    </form>
        
    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(queueList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Queue Name</td>
                <td>Select</td>
            </tr>

            <c:forEach var="queue" items="${queueList}">
                <tr>
                    <td>${queue.queueId}</td>
                    <td>${queue.queueName}</td>
                    <td><input type="button" onClick="javascript:updateQueueInput(${queue.queueId})" value="Select" /></td>
                </tr>
            </c:forEach>
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
    </c:if>
</div>

