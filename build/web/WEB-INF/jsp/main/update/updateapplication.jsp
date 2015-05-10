<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<script>
    function goToUrl(inputUrl) {
        window.location.replace(inputUrl)
    }
</script>

<!-- Auto edit functionality -->
<script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
<script>
    var id = ${application.applicationId};
    var item = "application";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
</script>

<c:choose>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/application/save/" />
        <c:set var="formName" value="saveRequestForm" />
        <script>
            clearInterval(checkIdIsOpenInterval);
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="formLink" value="/home/update/application/edit/" />
        <c:set var="formName" value="editRequestForm" />
    </c:otherwise>
</c:choose>

<div class="create-form-button-container">

    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="application">

        <c:choose>
            <c:when test="${edit}">
                <input type="submit" value="Save" />
                <input type="hidden" value="${application.applicationId}" name="applicationId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <input type="submit" value="Edit" />
                <input type="hidden" value="${application.applicationId}" name="applicationId" />
            </form>
        </c:otherwise>
    </c:choose>

    <br class="clearfix" />
    <br class="clearfix" />

    Application ID: ${application.applicationId}

    <br class="clearfix" />

    Application Name: ${application.applicationName}

    <br class="clearfix" />
    <br class="clearfix" />
    Status:
    <c:choose>
        <c:when test="${edit}">
            Online: <input type="radio" value="${activeFlagOnline}" name="activeFlag" <c:if test="${application.activeFlag.activeFlag == activeFlagOnline}">checked</c:if> /> 
            Offline: <input type="radio" value="${activeFlagOffline}" name="activeFlag" <c:if test="${application.activeFlag.activeFlag == activeFlagOffline}">checked</c:if> />  
        </c:when>
        <c:otherwise>
            ${application.activeFlag}
        </c:otherwise>
    </c:choose>

</form:form>
</div>