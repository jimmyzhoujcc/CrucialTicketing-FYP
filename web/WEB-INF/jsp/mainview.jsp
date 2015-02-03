<%@include file="header.jsp" %>

<script type="text/javascript">
    var marker = 0;

    crunchifyAjax();

    function crunchifyAjax() {
        $.post("<%=request.getContextPath()%>/home/alert/", function (data) {
            obj = JSON.parse(data);
            marker = obj.lastUpdated;

            var listCount = getObjectListCount(obj.userAlertLog);

            $('#notificationsBody').empty();

            for (i = 0; i < listCount; i++) {
                var options = {
                    weekday: "long", year: "numeric", month: "short",
                    day: "numeric", hour: "2-digit", minute: "2-digit"
                };

                var t = new Date(obj.userAlertLog[i].stamp * 1000);
                var formatted = t.toLocaleTimeString("en-gb", options);

                $('#notificationsBody')
                        .append("<div id=\"notification_" + (i + 1) + "\">" + obj.userAlertLog[i].message + " <span class=\"notification_stamp\">(" + formatted + " (UTC))</span></div><br />");

                $('.notification_' + (i + 1)).addClass('notification');
            }

            $('#alertcount').html(obj.unread);
        });


    }

    function getObjectListCount(obj) {
        var size = 0, key;
        for (key in obj) {
            if (obj.hasOwnProperty(key))
                size++;
        }
        return size;
    }
</script>

<script type="text/javascript">
    var getNotificationInterval = setInterval(crunchifyAjax, 3000);
</script>

<div id="message" style="position:fixed; float:left;left:20%"><h2>${message}</h2></div>

<script>
    var intervalTime = 3000;
    var timing = intervalTime;
    var timingSub = 1000;

    function clearAlert() {
        var valueFromId = document.getElementById('message');

        if (valueFromId.innerText.length > 0) {
            if (timing === 0) {
                document.getElementById('message').innerText = "";
            } else {
                timing -= timingSub;
            }
        } else {
            clearInterval();
        }
    }

    setInterval(function () {
        clearAlert();
    }, 1000);

</script>

<div class="heading">


    <div class="title"><h3>Crucial Ticketing</h3></div>
    <div class="titletabbox">
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation">  


                <a href="#" id="notificationLink">Notifications <span id="alertcount" class="badge">0</span></a>

                <div id="notificationContainer">
                    <div id="notificationTitle">Notifications</div>
                    <div id="notificationsBody" class="notifications">
                        <div class="notification">
                            notification 1 <span class="notification_stamp">10:11am 12th September 2014</span>

                        </div>
                        <div class="notification">
                            notification 1   
                        </div>
                        <div class="notification">
                            notification 1   
                        </div>
                        <div class="notification">
                            notification 1   
                        </div>
                        <div class="notification">
                            notification 1   
                        </div>
                    </div>

                    <div id="notificationFooter"><a href="#">See All</a></div>
                </div>


            </li>
            <li role="presentation"><a href="#">Help</a></li>
            <li role="presentation"><a href="#">Logout</a></li>
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
    <jsp:include page="<%=pageName%>"/>
</div>

<%@include file="footer.jsp" %>