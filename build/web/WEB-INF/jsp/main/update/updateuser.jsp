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
            <form action="<%=request.getContextPath()%>/home/update/user/save/" method="POST">
                <input type="submit" value="Save" />
                <input type="hidden" value="${user.userId}" name="userId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <form action="<%=request.getContextPath()%>/home/update/user/edit/" method="POST">
                    <input type="submit" value="Edit" />
                    <input type="hidden" value="1" name="editRequest" />
                    <input type="hidden" value="${user.userId}" name="userId" />
                </form>
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        <br class="clearfix" />

        User ID: ${user.userId}

        <br class="clearfix" />

        Username: ${user.username}

        <br class="clearfix" />
        Password:  
        <c:choose>
            <c:when test="${edit}">
                <input type="password" name="password" />
            </c:when>
            <c:otherwise>
                HIDDEN
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        First Name: 
        <c:choose>
            <c:when test="${edit}">
                <input text="text" name="firstName" value="${user.firstName}" />
            </c:when>
            <c:otherwise>
                ${user.firstName}
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        Last Name: 
        <c:choose>
            <c:when test="${edit}">
                <input text="text" name="lastName" value="${user.lastName}" />
            </c:when>
            <c:otherwise>
                ${user.lastName}
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        Email Address: 
        <c:choose>
            <c:when test="${edit}">
                <input text="text" name="email" value="${user.emailAddress}" />
            </c:when>
            <c:otherwise>
                ${user.emailAddress}
            </c:otherwise>
        </c:choose>

        <br class="clearfix" />
        Contact Number: 
        <c:choose>
            <c:when test="${edit}">
                <input text="text" name="contact" value="${user.contact}" />
            </c:when>
            <c:otherwise>
                ${user.contact}
            </c:otherwise>
        </c:choose>

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

        <br class="clearfix" />
        <br class="clearfix" />

        Role List
        <br class="clearfix" />
        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Role Name</td>
                <td>Valid From</td>
                <td>Valid To</td>
                <td>Status Flag</td>
            </tr>

            <c:forEach var="role" items="${user.userRoleConList}">
                <tr>
                    <td>${role.role.roleId}</td>
                    <td>${role.role.roleName}</td>
                    <td>${role.validFrom}</td>
                    <td>${role.validTo}</td>
                    <td>${role.activeFlag}</td>
                </tr>
            </c:forEach>

        </table>
</div>