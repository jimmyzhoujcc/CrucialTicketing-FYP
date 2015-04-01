<%@include file="header.jsp" %>


<!-- This javascript file is included here as it would trigger false alerts 
if included on the main header which services the login pages -->

<!-- Self: CSS & JS for notification  -->
<script src="<%=request.getContextPath()%>/js/autofunctions.js"></script>

<div class="heading">

    <div class="title"><h3>Crucial Ticketing</h3></div>
    <div class="titletabbox">
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation"><a href="<%=request.getContextPath()%>/home/main/profile/">My Profile</a></li>
            <li role="presentation">  


                <a href="#" id="notificationLink">Notifications <span id="alertcount" class="badge">0</span></a>

                <div id="notificationContainer">
                    <div id="notificationTitle">Notifications</div>
                    
                    <div id="notificationsBody" class="notifications">

                    </div>

                    <div id="notificationFooter"><a href="<%=request.getContextPath()%>/home/alert/all/">See All</a></div>
                </div>


            </li>
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
    <li role="presentation" <% if (pageName.equals("menu/hub.jsp")) {
            out.print("class=\"active\"");
        }%>>
        <a href="<%=request.getContextPath()%>/home/main/hub/">Hub</a>
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
    <li role="presentation"><a href="<%=request.getContextPath()%>/home/main/reporting/">Reporting</a></li>

</ul>
<div class="main">
    <c:if test="${fn:length(requestScope.alert) > 0}">
        <div id="alertbox" class="alert alert-danger" role="alert">${requestScope.alert}</div>
    </c:if>
    <c:if test="${fn:length(requestScope.success) > 0}">
        <div id="successbox" class="alert alert-success" role="alert">${requestScope.success}</div>
    </c:if>

    <jsp:include page="<%=pageName%>"/>
</div>

<%@include file="footer.jsp" %>