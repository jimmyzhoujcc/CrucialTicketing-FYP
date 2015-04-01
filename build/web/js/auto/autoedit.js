// Ticket auto direct 

var id = null;

function checkItemIsOpen() {
    if (id === null) {
        clearInterval(checkTicketIsOpenInterval);
    } else {
        $.post(homeURI + "/home/alert/check"+item + "/", {id: id}, function (data) {
            if(data === "1") {
                $("#editRequestForm").submit();
            }
        });
    }

}