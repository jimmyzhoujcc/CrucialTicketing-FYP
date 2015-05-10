<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Application</h3>

    <form action="<%=request.getContextPath()%>/home/update/application/" method="POST">

        <div class="query_container">
            <div class="query_heading">
                Application ID:
            </div>
            <div class="query_box">
                <input type="text" name="applicationId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Application Name:
            </div>
            <div class="query_box">
               <input type="text" name="applicationName" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                <input type="submit" />
            </div>
        </div>
    </form>

    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <table class="table table-hover">
        <tr>
            <td>ID</td>
            <td>Application Name</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(applicationList) gt 0}">
                <c:forEach var="application" items="${applicationList}">
                    <td>${application.applicationId}</td>
                    <td>${application.applicationName}</td>
                    <td><input type="button" onClick="javascript:updateApplicationInput(${application.applicationId})" value="Select" /></td>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(applicationList) == 0}">
                <td colspan="3">No records to display</td>
            </c:if>
        </tr>

    </table>

    <script>
        function updateApplicationInput(applicationId) {
            document.getElementById('applicationHiddenInput').value = applicationId;
            $("#queryApplication").submit();
        }
    </script>
    <form id="queryApplication" action="<%=request.getContextPath()%>/home/update/application/update/" method="POST">
        <input id="applicationHiddenInput" type="hidden" name="applicationId" />
    </form>

</div>

