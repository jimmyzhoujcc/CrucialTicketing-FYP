<%@include file="header.jsp" %>

<!-- This javascript file is included here as it would trigger false alerts 
if included on the main header which services the login pages -->

<!-- Self: CSS & JS for notification  -->
<script src="<%=request.getContextPath()%>/js/notification.js"></script>

<div class="heading">

    <div class="title"><h3>Crucial Ticketing</h3></div>
    <div class="titletabbox">
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation">  


                <a href="#" id="notificationLink">Notifications <span id="alertcount" class="badge">0</span></a>

                <div id="notificationContainer">
                    <div id="notificationTitle">Notifications</div>
                    <div id="notificationsBody" class="notifications">

                    </div>

                    <div id="notificationFooter"><a href="#">See All</a></div>
                </div>


            </li>
            <li role="presentation"><a href="#">Help</a></li>
            <li role="presentation"><a href="<%=request.getContextPath()%>/home/login/logout/">Logout</a></li>
        </ul>
    </div>

</div>

<br class="clearfix" />
<% String pageName = (String) request.getAttribute("page");
    if (pageName == null) {
        pageName = "menu/home.jsp";
    }
%>

<ul class="nav nav-tabs">
    <li role="presentation" <% if (pageName.equals("menu/main.jsp")) {
            out.print("class=\"active\"");
        }%>>
        <a href="<%=request.getContextPath()%>/home/main/main/">Home</a>
    </li>
    <li role="presentation" <% if (pageName.equals("menu/create.jsp")) {
            out.print("class=\"active\"");
        }%>>
        <a href="<%=request.getContextPath()%>/home/main/create/">Create</a>
    </li>
    <li role="presentation" <% if (pageName.equals("menu/update.jsp")) {
            out.print("class=\"active\"");
        }%>>
        <a href="<%=request.getContextPath()%>/home/main/update/">View</a>
    </li>
    <li role="presentation"><a href="menu/reporting.jsp">Reporting</a></li>
    <li role="presentation"><a href="menu/settings.jsp">Settings</a></li>

</ul>
<div class="main">
    <c:if test="${requestScope.alert != null}">
        <div class="alert alert-danger" role="alert">${requestScope.alert}</div>
    </c:if>
    <c:if test="${requestScope.success != null}">
        <div class="alert alert-success" role="alert">${requestScope.success}</div>
    </c:if>

    <jsp:include page="<%=pageName%>"/>
</div>

<%@include file="footer.jsp" %>