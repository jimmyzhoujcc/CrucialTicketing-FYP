var marker = 0;
var timeForNotificationInterval = 3000;
var pageDefaultTitle = document.title;

$(document).ready(function ()
{
    $("#notificationLink").click(function ()
    {
        clearInterval(getNotificationInterval);

        $.post(homeURI + "/home/alert/clear/", {marker: marker}, function () {
        });

        $("#alertcount").text("0");
        $("#notificationContainer").fadeToggle(300);

        getNotificationInterval = setInterval(checkForNotification, timeForNotificationInterval);
        return false;
    });

    //Document Click
    $(document).click(function ()
    {
        $("#notificationContainer").hide();
    });
    //Popup Click
    $("#notificationContainer").click(function ()
    {
        return false
    });

});

checkForNotification();

function checkForNotification() {
    $.post(homeURI + "/home/alert/", function (data) {
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

        if (obj.unread === 0) {
            document.title = pageDefaultTitle;
        } else {
            document.title = "(" + obj.unread + ") " + pageDefaultTitle;
            $('#alertcount').html(obj.unread);
        }
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

var getNotificationInterval = setInterval(checkForNotification, timeForNotificationInterval);