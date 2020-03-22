function setDataForHiddenInput(id) {
    $("#hiddenInput").val(id);
}

function setIdAccountForLinkButton(id) {
    return "<div class='btn-group dropright'>" +
                "<button type='button' class='edit-btn btn btn-secondary'>" +
                    "Operations" +
                "</button>" +
                "<button type='button' class='btn btn-secondary dropdown-toggle dropdown-toggle-split edit-btn'" +
                    "data-toggle='dropdown' aria-haspopup='true' aria-expanded='false'>" +
                    "<span class='sr-only'>Toggle Dropright</span>" +
                "</button>" +
                "<div class='dropdown-menu'>" +
                    "<a class='dropdown-item' href='#' data-toggle='modal' data-target='#exampleModal' " +
                        "onclick='setDataForHiddenInput(" + id.toString() + ")'>Delete this account</a>" +
                    "<a class='dropdown-item' href='#' >Change data</a>" +
                "</div>" +
            "</div>";
}

function ajaxQueryForUserAccountsList() {
    $.getJSON('/app/user-accounts/get', {}, parseUserAccountsToTableRows);
};

function parseUserAccountsToTableRows(data) {
    var count = 1;
    $.each(data, function (index, json) {
        $('#ajax-user-accounts')
            .append('<tr>')
            .append('<th scope = "row">' + count++ + '</th>')
            .append('<td>' + json.nameAccount + '</td>')
            .append('<td>' + json.balance + ' руб.' + '</td>')
            .append('<td>' + setIdAccountForLinkButton(json.id) + '</td>')
            .append('</tr>')
    });
};



