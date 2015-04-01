<%-- 
    Document   : createuser
    Created on : 15-Feb-2015, 20:51:43
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<!-- Auto edit functionality -->
<script src="<%=request.getContextPath()%>/js/auto/autoedit.js"></script>
<script>
    var id = ${user.userId};
    var item = "user";
    var checkIdIsOpenInterval = setInterval(checkItemIsOpen, timeForNotificationInterval);
</script>

<c:choose>
    <c:when test="${edit}">
        <c:set var="formLink" value="/home/update/user/save/" />
        <c:set var="formName" value="saveRequestForm" />
        <script>
            clearInterval(checkIdIsOpenInterval);
        </script>
    </c:when>
    <c:otherwise>
        <c:set var="formLink" value="/home/update/user/edit/" />
        <c:set var="formName" value="editRequestForm" />
    </c:otherwise>
</c:choose>
        
<div class="create-form-button-container">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="user">
        <c:choose>
            <c:when test="${edit}">
                <input type="submit" value="Save" />
                <input type="hidden" value="${user.userId}" name="userId" />
                <br class="clearfix" />
                Ticket ID: <input type="text" name="ticketId" /> (for request)
            </c:when>
            <c:otherwise>
                <form action="<%=request.getContextPath()%>/home/update/user/edit/" method="POST">
                    <input type="submit" value="Edit" />
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
                <input text="text" name="emailAddress" value="${user.emailAddress}" />
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
                Online: <input type="radio" value="${activeFlagOnline}" name="activeFlag"
                               <c:if test="${user.activeFlag == activeFlagOnline}">checked</c:if> /> 
                Offline: <input type="radio" value="${activeFlagOffline}" name="activeFlag"
                                <c:if test="${user.activeFlag == activeFlagOffline}">checked</c:if> />  
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

            <c:set var="counter" value="0" />
            <c:forEach var="role" items="${user.userRoleConList}">
                <tr>
                    <td>${role.role.roleId}</td>
                    <td>${role.role.roleName}</td>
                    <c:choose>
                        <c:when test="${edit}">
                        <input type="hidden" name="userRoleConList[${counter}].role.roleId" value="${role.role.roleId}" />

                        <script>
                            $(function () {
                                $("#datepicker_to_${counter}").datepicker({dateFormat: 'dd-mm-yy'});
                            });
                        </script>    

                        <jsp:setProperty name="dateValue" property="time" value="${user.userRoleConList[counter].validFrom*1000}"/>

                        <td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" pattern="dd-MM-yyyy" value="${dateValue}" /></td>

                        <jsp:setProperty name="dateValue" property="time" value="${user.userRoleConList[counter].validTo*1000}"/>

                        <td><fmt:formatDate pattern="dd-MM-yyyy" var="dateFrom" value="${dateValue}" />
                            <input type="text" name="userRoleConList[${counter}].validToStr" value="${dateFrom}" id="datepicker_to_${counter}">
                        </td>

                        <td>
                            Online: <input type="radio" value="${activeFlagOnline}" name="userRoleConList[${counter}].activeFlag" 
                                           <c:if test="${user.userRoleConList[counter].activeFlag == activeFlagOnline}">checked</c:if> /> 
                                           <br class="clearfix" />
                                           Offline: <input type="radio" value="${activeFlagOffline}" name="userRoleConList[${counter}].activeFlag" 
                                            <c:if test="${user.userRoleConList[counter].activeFlag == activeFlagOffline}">checked</c:if> /> 
                            </td>
                    </c:when>
                    <c:otherwise>
                        <jsp:setProperty name="dateValue" property="time" value="${user.userRoleConList[counter].validFrom*1000}"/>
                        <td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" pattern="dd-MM-yyyy" value="${dateValue}" /></td>

                        <jsp:setProperty name="dateValue" property="time" value="${user.userRoleConList[counter].validTo*1000}"/>

                        <td><fmt:formatDate type="both" dateStyle="long" timeStyle="long" pattern="dd-MM-yyyy" value="${dateValue}" /></td>

                        <td>${role.activeFlag}</td>
                    </c:otherwise>
                </c:choose>

                </tr>

                <c:set var="counter" value="${counter+1}" />

            </c:forEach>

        </table>
    </form:form>
</div>