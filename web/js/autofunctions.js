var timeForNotificationInterval = 3000; // GLOBAL

// For notification
var marker = 0;
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

            var messageString = obj.userAlertLog[i].message;
            
            if (messageString.length > 50) {
                var sliceString = messageString.slice(0, 50) + "...";
            } else {
                var sliceString = messageString;
            }

            $('#notificationsBody')
                    .append("<div class=\"notification-text\" id=\"notification_" + (i + 1) + "\"><a href=\"" + homeURI + "/home/alert/single?alert="+ obj.userAlertLog[i].userAlertId +"\">" + sliceString + "</a> <span class=\"notification_stamp\">" + formatted + " UTC</span></div><br />");

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
