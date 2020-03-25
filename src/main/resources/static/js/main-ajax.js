function ajaxQueryForUserAccountsList() {
    $.getJSON('/app/user-accounts/get', {}, parseUserAccountsToTableRows);
}

function parseUserAccountsToTableRows(data) {
    var count = data.length;
    $.each(data, function (index, json) {
        $('#ajax-user-accounts')
            .prepend('<tr class="ajax-rows">' +
            '<td scope = "row">' + count-- + '</td>' +
            '<td>' + json.nameAccount + '</td>' +
            '<td>' + json.balance + ' руб.' + '</td>' +
            '<td>' + setIdAccountForLinkButton(json.id) + '</td>' +
            '</tr>')
    });
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

function setDataForHiddenInput(id) {
    $("#hiddenInput").val(id);
}

function addNewTypeAccount() {
    const type = String($('#type-input').val());
    console.log(type);

    $.post('/app/user-account/add-new-type-account', { "accountName" : type}, function(){getTypesAccountsList();});

}

function addNewAccount() {
    let typeId = $("#account-type-name").val();
    let balance = $("#balance").val();
    $.post('/app/user-accounts/add-new-account',
        {
            "id": typeId,
            "balance": balance
        } ,
        function() {
            clearAjaxTable();
            ajaxQueryForUserAccountsList();
        }
    );
}

function getTypesAccountsList() {
    $.getJSON('/app/user-accounts/get-types-accounts', {}, parseTypesAccountsIntoSelect);
}

function parseTypesAccountsIntoSelect(data) {
    $('.type').detach();
    $.each(data, function (index, json) {
        $('#account-type-name').append('<option class="type" value="' + json.id + '">' + json.accountName + '</option>');
    });
}

function clearAjaxTable() {
    $("tr.ajax-rows").detach();
}
