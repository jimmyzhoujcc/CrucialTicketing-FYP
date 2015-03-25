<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="create-form-button-container">
    <c:choose>
        <c:when test="${edit}">
            <form action="<%=request.getContextPath()%>/home/update/queue/save/" method="POST">
                <input type="submit" value="Save" />
                <input type="hidden" value="${queue.queueId}" name="queueId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <form action="<%=request.getContextPath()%>/home/update/queue/edit/" method="POST">
                    <input type="submit" value="Edit" />
                    <input type="hidden" value="${queue.queueId}" name="queueId" />
                </form>
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
                Online: <input type="radio" value="${activeFlagOnline}" name="activeFlag" <c:if test="${user.activeFlag.activeFlag == activeFlagOnline}">checked</c:if> /> 
                Offline: <input type="radio" value="${activeFlagOffline}" name="activeFlag" <c:if test="${user.activeFlag.activeFlag == activeFlagOffline}">checked</c:if> />  
            </c:when>
            <c:otherwise>
                ${user.activeFlag}
            </c:otherwise>
        </c:choose>
</div>