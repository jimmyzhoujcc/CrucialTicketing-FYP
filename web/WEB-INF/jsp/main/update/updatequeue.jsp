<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<script>
    function goToUrl(inputUrl) {
        window.location.replace(inputUrl)
    }
</script>

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

<div class="row">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="queue">

        <div id="subticketnav" class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">User Control</h3>
            </div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${edit}">
                        <input type="submit" value="Save" class="btn btn-success" />
                        <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/queue/cancel/?queueId=${queue.queueId}')"  value="Cancel (Exit)" class="btn btn-danger" />
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Edit" class="btn btn-warning" />
                        <input type="button" value="Refresh" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/queue/update/?roleId=${queue.queueId}')" />
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <div class="col-xs-12 col-sm-8 col-md-10">
            <h3><span class="label label-default">Queue Record</span> ${queue.queueName}</h3>

            <br class="clearfix" />

            <ol class="breadcrumb">
                <li>
                    Snapshot as of the queue as of 
                    <c:set var="now" value="<%=new java.util.Date()%>" />
                <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${now}" />
                </li>
            </ol>

            <div class="create-form-button-container form-override">
                <div class="panel panel-primary">
                    <a name="basic"></a>
                    <div class="panel-heading">
                        <h3 class="panel-title">User information</h3>
                    </div>
                    <div class="panel-body">

                        <input type="hidden" value="${queue.queueId}" name="queueId" />

                        <c:if test="${edit}">
                            <div class="ticketinfo_container">
                                <div class="ticketinfo_heading">
                                    Ticket ID:
                                </div>
                                <div class="ticketinfo_data">
                                    <input type="text" name="ticketId" /> (for request)
                                </div>
                            </div>

                            <br class="clearfix" />
                            <br class="clearfix" />
                        </c:if>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Queue ID:
                            </div>
                            <div class="ticketinfo_data">
                                ${queue.queueId}
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Queue Name:
                            </div>
                            <div class="ticketinfo_data">
                                ${queue.queueName}
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Status:
                            </div>
                            <div class="ticketinfo_data">
                                <c:choose>
                                    <c:when test="${edit}">
                                        Online: <input type="radio" name="activeFlag" value="${activeFlagOnline}" <c:if test="${queue.activeFlag.activeFlag == activeFlagOnline.activeFlag}">checked</c:if> /> 
                                        Offline: <input type="radio" name="activeFlag" value="${activeFlagOffline}" <c:if test="${queue.activeFlag.activeFlag == activeFlagOffline.activeFlag}">checked</c:if> />  
                                    </c:when>
                                    <c:otherwise>
                                        ${queue.activeFlag}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                            
                            <br class="clearfix" />
                            <br class="clearfix" />

                        <div class="ticketinfo_heading">
                            User List
                        </div>

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
                    </div>
                </div>
            </div>
        </div>

        <div class="ticketnav col-xs-4 col-md-2">

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">User Control</h3>
                </div>
                <div class="panel-body">
                    <c:choose>
                        <c:when test="${edit}">
                            <input type="submit" value="Save" class="btn btn-success" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/queue/cancel/?queueId=${queue.queueId}')"  value="Cancel (Exit)" class="btn btn-danger" />
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Edit" class="btn btn-warning" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" value="Refresh" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/queue/update/?queueId=${queue.queueId}')" />
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </form:form>
</div>