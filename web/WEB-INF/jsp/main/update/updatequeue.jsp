<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Auto edit functionality -->
<script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
<script>
    var id = ${queue.queueId};
    var item = "queue";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
</script>

<c:choose>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/queue/save/" />
        <c:set var="formName" value="saveRequestForm" />
        <script>
            clearInterval(checkIdIsOpenInterval);
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="formLink" value="/home/update/queue/edit/" />
        <c:set var="formName" value="editRequestForm" />
    </c:otherwise>
</c:choose>

<div class="create-form-button-container">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="queue">

        <c:choose>
            <c:when test="${edit}">
                <input type="submit" value="Save" />
                <input type="hidden" value="${queue.queueId}" name="queueId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <input type="submit" value="Edit" />
                <input type="hidden" value="${queue.queueId}" name="queueId" />
        </c:otherwise>
    </c:choose>

    <br class="clearfix" />
    <br class="clearfix" />

    Queue ID: ${queue.queueId}

    <br class="clearfix" />

    Queue Name: ${queue.queueName}

    <br class="clearfix" />
    <br class="clearfix" />
    Status:
    <c:choose>
        <c:when test="${edit}">
            Online: <input type="radio" name="activeFlag" value="${activeFlagOnline}" <c:if test="${queue.activeFlag.activeFlag == activeFlagOnline.activeFlag}">checked</c:if> /> 
            Offline: <input type="radio" name="activeFlag" value="${activeFlagOffline}" <c:if test="${queue.activeFlag.activeFlag == activeFlagOffline.activeFlag}">checked</c:if> />  
        </c:when>
        <c:otherwise>
            ${queue.activeFlag}
        </c:otherwise>
    </c:choose>

    <br class="clearfix" />
    <br class="clearfix" />

    User List
    <br class="clearfix" />
    <table class="table table-hover">
        <tr>
            <td>ID</td>
            <td>Username</td>
            <td>First Name</td>
            <td>Last Name</td>
            <td>Status Flag</td>
        </tr>

        <c:set var="counter" value="0" />
        <c:forEach var="userQueueCon" items="${queue.userQueueConList}">
            <tr>
                <td>${userQueueCon.user.userId}</td>
                <td>${userQueueCon.user.username}</td>
                <td>${userQueueCon.user.firstName}</td>
                <td>${userQueueCon.user.lastName}</td>

                <c:choose>
                    <c:when test="${edit}">
                    <input type="hidden" name="userQueueConList[${counter}].user.userId" value="${userQueueCon.user.userId}" />

                    <td>
                        Online: <input type="radio" value="${activeFlagOnline}" name="userQueueConList[${counter}].activeFlag" 
                                       <c:if test="${queue.userQueueConList[counter].activeFlag == activeFlagOnline}">checked</c:if> /> 
                                       <br class="clearfix" />
                                       Offline: <input type="radio" value="${activeFlagOffline}" name="userQueueConList[${counter}].activeFlag" 
                                        <c:if test="${queue.userQueueConList[counter].activeFlag == activeFlagOffline}">checked</c:if> /> 
                        </td>
                </c:when>
                <c:otherwise>
                    <td>${role.activeFlag}</td>
                </c:otherwise>
            </c:choose>

            </tr>

            <c:set var="counter" value="${counter+1}" />

        </c:forEach>

    </table>
</form:form>
</div>