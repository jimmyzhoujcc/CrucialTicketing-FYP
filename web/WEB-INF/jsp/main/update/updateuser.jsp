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

<script>
    function goToUrl(inputUrl) {
        window.location.replace(inputUrl)
    }
</script>

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

<div class="row">
    <form:form action="${pageContext.request.contextPath}${formLink}" id="${formName}" method="POST" commandName="user">

        <div id="subticketnav" class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">User Control</h3>
            </div>
            <div class="panel-body">
                <c:choose>
                    <c:when test="${edit}">
                        <input type="submit" value="Save User" class="btn btn-success" />
                        <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/user/cancel/?userId=${user.userId}')"  value="Cancel (Exit)" class="btn btn-danger" />
                    </c:when>
                    <c:otherwise>
                        <input type="submit" value="Edit" class="btn btn-warning" />
                        <input type="button" value="Refresh Ticket" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/user/update/?userId=${user.userId}')" />
                    </c:otherwise>
                </c:choose>

            </div>
        </div>

        <div class="col-xs-12 col-sm-8 col-md-10">
            <h3><span class="label label-default">User Record</span> ${user.username}</h3>

            <br class="clearfix" />

            <ol class="breadcrumb">
                <li>
                    Snapshot as of the user as of 
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

                        <input type="hidden" value="${user.userId}" name="userId" />

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
                                User ID:
                            </div>
                            <div class="ticketinfo_data">
                                ${user.userId}
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Username:
                            </div>
                            <div class="ticketinfo_data">
                                ${user.username}
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Password: 
                            </div>
                            <div class="ticketinfo_data">
                                <c:choose>
                                    <c:when test="${edit}">
                                        <input type="password" name="password" id="password" />
                                    </c:when>
                                    <c:otherwise>
                                        HIDDEN
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                First Name:
                            </div>
                            <div class="ticketinfo_data">
                                <c:choose>
                                    <c:when test="${edit}">
                                        <input text="text" name="firstName" value="${user.firstName}" />
                                    </c:when>
                                    <c:otherwise>
                                        ${user.firstName}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Last Name:
                            </div>
                            <div class="ticketinfo_data">
                                <c:choose>
                                    <c:when test="${edit}">
                                        <input text="text" name="lastName" value="${user.lastName}" />
                                    </c:when>
                                    <c:otherwise>
                                        ${user.lastName}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Email Address:
                            </div>
                            <div class="ticketinfo_data">
                                <c:choose>
                                    <c:when test="${edit}">
                                        <input text="text" name="emailAddress" value="${user.emailAddress}" />
                                    </c:when>
                                    <c:otherwise>
                                        ${user.emailAddress}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Contact:
                            </div>
                            <div class="ticketinfo_data">
                                <c:choose>
                                    <c:when test="${edit}">
                                        <input text="text" name="contact" value="${user.contact}" />
                                    </c:when>
                                    <c:otherwise>
                                        ${user.contact}
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>

                        <div class="ticketinfo_container">
                            <div class="ticketinfo_heading">
                                Status:
                            </div>
                            <div class="ticketinfo_data">
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
                            </div>
                        </div>

                        <br class="clearfix" />
                        <br class="clearfix" />

                        <div class="ticketinfo_heading">
                                Role List
                            </div>
                        
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
                            <input type="submit" value="Save User" class="btn btn-success" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/user/cancel/?userId=${user.userId}')"  value="Cancel (Exit)" class="btn btn-danger" />
                        </c:when>
                        <c:otherwise>
                            <input type="submit" value="Edit" class="btn btn-warning" />
                            <br class="clearfix" />
                            <br class="clearfix" />
                            <input type="button" value="Refresh Ticket" class="btn btn-success"  onClick="javascript:goToUrl('${pageContext.request.contextPath}/home/update/user/update/?userId=${user.userId}')" />
                        </c:otherwise>
                    </c:choose>

                </div>
            </div>
        </div>
    </form:form>
</div>