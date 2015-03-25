// Ticket auto direct 

var ticketId = null;

function checkTicketIsOpen() {
    if (ticketId === null) {
        clearInterval(checkTicketIsOpenInterval);
    } else {
        $.post(homeURI + "/home/alert/checkticket/", {ticketId: ticketId}, function (data) {
            if(data === "1") {
                $("#ticketeditrequest").submit();
            }
        });
    }

}