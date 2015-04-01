<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Auto edit functionality -->
<script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
<script>
    var id = ${severity.severityId};
    var item = "severity";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
</script>

<c:choose>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/severity/save/" />
        <c:set var="formName" value="saveRequestForm" />
        <script>
            clearInterval(checkIdIsOpenInterval);
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="formLink" value="/home/update/severity/edit/" />
        <c:set var="formName" value="editRequestForm" />
    </c:otherwise>
</c:choose>
<div class="create-form-button-container">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="severity">
        <c:choose>
            <c:when test="${edit}">
                <input type="submit" value="Save" />
                <input type="hidden" value="${severity.severityId}" name="severityId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <form action="<%=request.getContextPath()%>/home/update/user/edit/" method="POST">
                    <input type="submit" value="Edit" />
                    <input type="hidden" value="${severity.severityId}" name="severityId" />
                </form>
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        <br class="clearfix" />

        Severity ID: ${severity.severityId}

        <br class="clearfix" />

        Severity Level: ${severity.severityLevel}

        <br class="clearfix" />
        
        Severity Name: ${severity.severityName}

        <br class="clearfix" />
        <br class="clearfix" />
        Status:
        <c:choose>
            <c:when test="${edit}">
                Online: <input type="radio" value="${activeFlagOnline}" name="activeFlag"
                               <c:if test="${severity.activeFlag == activeFlagOnline}">checked</c:if> /> 
                Offline: <input type="radio" value="${activeFlagOffline}" name="activeFlag"
                                <c:if test="${severity.activeFlag == activeFlagOffline}">checked</c:if> />  
            </c:when>
            <c:otherwise>
                ${severity.activeFlag}
            </c:otherwise>
        </c:choose>

    </form:form>
</div>