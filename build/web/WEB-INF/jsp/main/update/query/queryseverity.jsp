<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Severity</h3>

    <form action="<%=request.getContextPath()%>/home/update/severity/" method="POST">
        <div class="query_container">
            <div class="query_heading">
                Severity Name:
            </div>
            <div class="query_box">
                <input type="text" name="severityId" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Severity Level:
            </div>
            <div class="query_box">
                <input type="text" name="severityLevel" />
            </div>
        </div>

        <div class="query_container">
            <div class="query_heading">
                Severity Name:
            </div>
            <div class="query_box">
                <input type="text" name="severityName" />
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
            <td>Severity Level</td>
            <td>Severity Name</td>
            <td>Status</td>
            <td>Select</td>
        </tr>

        <tr>
            <c:if test="${fn:length(severityList) gt 0}">

                <c:forEach var="severity" items="${severityList}">
                    <td>${severity.severityId}</td>
                    <td>${severity.severityLevel}</td>
                    <td>${severity.severityName}</td>
                    <td>${severity.activeFlag}</td>
                    <td><input type="button" onClick="javascript:updateHiddenIdentifier(${severity.severityId})" value="Select" /></td>
                    </c:forEach>
                </c:if>
                <c:if test="${fn:length(severityList) == 0}">
                <td colspan="5">No records to display</td>
            </c:if>
        </tr>
    </table>

    <script>
        function updateHiddenIdentifier(id) {
            document.getElementById('hiddenIdentifier').value = id;
            $("#queryForSingle").submit();
        }
    </script>

    <form id="queryForSingle" action="<%=request.getContextPath()%>/home/update/severity/update/" method="POST">
        <input id="hiddenIdentifier" type="hidden" name="severityId" />
    </form>
</div>

