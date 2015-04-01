<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="create-form-button-container">

    <h3>Query Severity</h3>

    <form action="<%=request.getContextPath()%>/home/update/severity/" method="POST">
        Severity ID: <input type="text" name="severityId" />
        <br class="clearfix" />
        Severity Level: <input type="text" name="severityLevel" />
        <br class="clearfix" />
        Severity Name: <input type="text" name="severityName" />

        <br class="clearfix" />
        <br class="clearfix" />
        <input type="submit" />
    </form>
    <br class="clearfix" />
    <hr />
    <br class="clearfix" />

    <c:if test="${fn:length(severityList) gt 0}">

        <table class="table table-hover">
            <tr>
                <td>ID</td>
                <td>Severity Level</td>
                <td>Severity Name</td>
                <td>Status</td>
                <td>Select</td>
            </tr>

            <c:forEach var="severity" items="${severityList}">
                <tr>
                    <td>${severity.severityId}</td>
                    <td>${severity.severityLevel}</td>
                    <td>${severity.severityName}</td>
                    <td>${severity.activeFlag}</td>
                    <td><input type="button" onClick="javascript:updateHiddenIdentifier(${severity.severityId})" value="Select" /></td>
                </tr>
            </c:forEach>
        </table>

        <script>
            function updateHiddenIdentifier(id) {
                document.getElementById('hiddenIdentifier').value = id;
                $("#queryForSingle").submit();
            }
        </script>

        <form id="queryForSingle" action="<%=request.getContextPath()%>/home/update/severity/update/" method="POST">
            <input id="hiddenIdentifier" type="hidden" name="id" />
        </form>
    </c:if>
</div>

