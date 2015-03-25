<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Application</h3>

    <form action="<%=request.getContextPath()%>/home/update/application/" method="POST">
        Application ID: <input type="text" name="applicationId" />
        <br class="clearfix" />
        Application Name: <input type="text" name="applicationName" />
        <br class="clearfix" />
        <br class="clearfix" />
        <input type="submit" />
    </form>
        
    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(applicationList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Application Name</td>
            </tr>

            <c:forEach var="application" items="${applicationList}">
                <tr>
                    <td>${application.applicationId}</td>
                    <td>${application.applicationName}</td>
                    <td><input type="button" onClick="javascript:updateApplicationInput(${application.applicationId})" value="Select" /></td>
                </tr>
            </c:forEach>
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
    </c:if>
</div>

