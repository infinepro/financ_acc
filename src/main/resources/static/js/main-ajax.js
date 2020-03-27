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
                        "onclick='clickOnDeleteAccount(" + id.toString() + ")'>Delete this account</a>" +
                    "<a class='dropdown-item' href='#' data-toggle='modal' data-target='#exampleModal'" +
                        "onclick='clickOnChangeAccount(" + id.toString() + ")'>Change data</a>" +
                    "<a class='dropdown-item' href='#' data-toggle='modal' data-target='#exampleModal'" +
                        "onclick='clickAddNewTransaction(" + id.toString() + ")'>Add new transaction</a>" +
                "</div>" +
            "</div>";
}

function clickOnChangeAccount(id) {
    setDataForHiddenInput(id);
    $("#delete-account").css( "display", "none" );
    $("#change-account").css( "display", "block" );
    $("#add-transaction").css("display", "none")
}

function clickOnDeleteAccount(id) {
    setDataForHiddenInput(id);
    $("#change-account").css( "display", "none" );
    $("#delete-account").css( "display", "block" );
    $("#add-transaction").css("display", "none")
}

function clickAddNewTransaction(id) {
    setDataForHiddenInput(id);
    $("#change-account").css( "display", "none" );
    $("#delete-account").css( "display", "none" );
    $("#add-transaction").css("display", "block")
}

function setDataForHiddenInput(id) {
    $(".hiddenInput").val(id);
}

function addNewTypeAccount() {
    const type = String($('#type-input').val());
    $.post('/app/user-account/add-new-type-account', { "accountName" : type}, function(){getTypesAccountsList();});

}

function addNewAccount() {
    let typeId = $("#account-type-name").val();
    let balance = $("#balance").val();

    console.log(typeId);
    $.post('/app/user-accounts/add-new-account',
        {
            "typeId": typeId,
            "balance": balance
        } ,
        function() {
            clearAjaxTable();
            ajaxQueryForUserAccountsList();
        }
    );
}

function getTypesAccountsList(arg) {
    if (arg === "from-form") {
        $.getJSON('/app/user-accounts/get-types-accounts', {}, parseTypesAccountsIntoSelectForModal);
    } else if (arg === "from-main") {
        $.getJSON('/app/user-accounts/get-types-accounts', {}, parseTypesAccountsIntoSelect);
    }
}

function getCategoryTransactionsList() {
    $.getJSON("/app/user-accounts/get-category-transactions", {}, parseCategoryTransactionsIntoSelect)
}

function parseCategoryTransactionsIntoSelect(data) {
    $('.type-category-tr').detach();
    $.each(data, function (index, json) {
        $('.category-tr').append('<option class="type-category-tr" value="' + json.id + '">' + json.categoryName + '</option>');
    });
}

function parseTypesAccountsIntoSelect(data) {
    $('.type').detach();
    $.each(data, function (index, json) {
        $('#account-type-name').append('<option class="type" value="' + json.id + '">' + json.accountName + '</option>');
    });
}

function parseTypesAccountsIntoSelectForModal(data) {
    $('.type-form').detach();
    $.each(data, function (index, json) {
        $('#account-type-name-form').append('<option class="type-form" value="' + json.id + '">' + json.accountName + '</option>');
    });
}

function clearAjaxTable() {
    $("tr.ajax-rows").detach();
}


function addNewCategoryTransaction() {
    const category = String($('#type-input').val());
    $.post('/app/user-account/add-new-category-transaction',
            { "categoryName" : category},
            function(){/*getCategoryTransactionsList();*/});
}