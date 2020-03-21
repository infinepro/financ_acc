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
            .append('</tr>')
    });
};