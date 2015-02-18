<%-- 
    Document   : ticket
    Created on : 07-Jan-2015, 02:21:08
    Author     : Owner
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<div class="row">
    <form:form method="POST" action="${pageContext.servletContext.contextPath}/home/create/createticket/">  

        <div class="col-xs-12 col-sm-8 col-md-10">


            <h3>Ticket Creation</h3>
            <br />

            <div class="panel panel-primary">
                <a name="basic"></a>
                <div class="panel-heading">
                    <h3 class="panel-title">Basic information</h3>
                </div>
                <div class="panel-body">
                    Ticket Type: 
                    <select name="ticketTypeId">
                        <c:forEach var="ticketType" items="${ticketTypeList}">
                            <option value="${ticketType.ticketTypeId}">${ticketType.ticketTypeName}</option>
                        </c:forEach>
                    </select>
                    <br /><br />

                    Severity: 
                    <select name="severityId">
                        <c:forEach var="severity" items="${severityList}">
                            <option value="${severity.severityId}">${severity.severityLevel}: ${severity.severityName}</option>
                        </c:forEach>
                    </select>
                    <br /><br />

                    Application: 
                    <select name="applicationId">
                        <c:forEach var="application" items="${applicationList}">
                            <option value="${application.applicationId}">${application.applicationName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

        </div>

        <div class="ticketnav col-xs-4 col-md-2" style="position:fixed; right:0">

            <div class="panel panel-primary">
                <div class="panel-heading">
                    <h3 class="panel-title">Navigation</h3>
                </div>
                <div class="panel-body">


                    <div class="btn-group">

                        <input type="submit" value="Next" class="btn btn-success" />
                        <br /><br />
                        <input type="button" id="cancelEdit" value="Cancel (Exit)" class="btn btn-danger" />
                        <script>
                            $('#cancelEdit').on('click', function () {
                                window.history.back();
                            });
                        </script>

                    </div>
                </div>
            </div>

        </form:form>  
    </div>


