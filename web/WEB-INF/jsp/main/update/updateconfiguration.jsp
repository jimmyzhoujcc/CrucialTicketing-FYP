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
    var id = ${applicationControl.applicationControlId};
    var item = "configuration";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
</script>

<c:choose>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/configuration/save/" />
        <c:set var="formName" value="saveRequestForm" />
        <script>
            clearInterval(checkIdIsOpenInterval);
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="formLink" value="/home/update/configuration/edit/" />
        <c:set var="formName" value="editRequestForm" />
    </c:otherwise>
</c:choose>

<div class="create-form-button-container">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="applicationControl">

        <c:choose>
            <c:when test="${edit}">
                <input type="submit" value="Save" />
                <input type="hidden" value="${applicationControl.applicationControlId}" name="applicationControlId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <input type="submit" value="Edit" />
                <input type="hidden" value="${applicationControl.applicationControlId}" name="applicationControlId" />
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        <br class="clearfix" />

        Configuration ID: ${applicationControl.applicationControlId}

        <br class="clearfix" />

        Ticket Type: ${applicationControl.ticketType.ticketTypeName}

        <br class="clearfix" />

        Application: ${applicationControl.application.applicationName}

        <br class="clearfix" />

        Severity: ${applicationControl.severity.severityLevel}:${applicationControl.severity.severityName}

        <br class="clearfix" />

        Workflow: ${applicationControl.workflow.workflowName}

        <br class="clearfix" />

        Role: ${applicationControl.role.roleName}

        <br class="clearfix" />

        SLA Clock ${applicationControl.slaClock}

        <br class="clearfix" />
        <br class="clearfix" />
        Status:
        <c:choose>
            <c:when test="${edit}">
                Online: <input type="radio" value="${activeFlagOnline}" name="activeFlag" <c:if test="${applicationControl.activeFlag.activeFlag == activeFlagOnline}">checked</c:if> /> 
                Offline: <input type="radio" value="${activeFlagOffline}" name="activeFlag" <c:if test="${applicationControl.activeFlag.activeFlag == activeFlagOffline}">checked</c:if> />  
            </c:when>
            <c:otherwise>
                ${applicationControl.activeFlag}
            </c:otherwise>
        </c:choose>

    </form:form>
</div>