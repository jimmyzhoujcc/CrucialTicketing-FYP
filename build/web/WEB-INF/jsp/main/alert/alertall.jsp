<%-- 
    Document   : alertall
    Created on : 17-Feb-2015, 02:27:49
    Author     : DanFoley
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<jsp:useBean id="dateValue" class="java.util.Date"/>

<c:forEach var="userAlert" items="${userAlertLog.userAlertLog}">
    <blockquote>
        <p>${userAlert.message}.</p>
        <footer>System 
            <jsp:setProperty name="dateValue" property="time" value="${userAlert.stamp*1000}"/>
            <fmt:formatDate type="both" dateStyle="long" timeStyle="long" value="${dateValue}" />
            <cite title="Source Title">(${dateValue}</cite>)
        </footer>
    </blockquote>
    <hr />
</c:forEach>