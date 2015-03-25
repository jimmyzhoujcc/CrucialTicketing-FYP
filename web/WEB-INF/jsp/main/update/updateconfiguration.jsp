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
            <form action="<%=request.getContextPath()%>/home/update/configuration/save/" method="POST">
                <input type="submit" value="Save" />
                <input type="hidden" value="${applicationControl.applicationControlId}" name="applicationControlId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <form action="<%=request.getContextPath()%>/home/update/configuration/edit/" method="POST">
                    <input type="submit" value="Edit" />
                    <input type="hidden" value="${applicationControl.applicationControlId}" name="applicationControlId" />
                </form>
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
</div>