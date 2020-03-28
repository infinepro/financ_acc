/**
  * возможно есть варианты формировать html коды из файлов, разработчик не стал
  *
 */

//устанавливает значение скрытого поля input для последующей отправки его на сервер
function setDataForHiddenInput(id) {
    $(".hiddenInput").val(id);
}


//добавление нового типа счёта, значение берётся из поля
function addNewTypeAccount() {
    const type = String($('#type-input').val());
    $.post('/app/user-account/add-new-type-account', { "accountName" : type}, function(){getTypesAccountsList();});

}

//post запрос на добавление нового счёта, значения беруться из полей
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

//запрос списка типов счетов, но вывод зависит от аргумента, вывод в модальное окно либо на главную
function getTypesAccountsList(arg) {
    if (arg === "from-form") {
        $.getJSON('/app/user-accounts/get-types-accounts', {}, parseTypesAccountsIntoSelectForModal);
    } else if (arg === "from-main") {
        $.getJSON('/app/user-accounts/get-types-accounts', {}, parseTypesAccountsIntoSelect);
    }
}

//запрос списка категорий транзакций и из обработка
function getCategoryTransactionsList() {
    $.getJSON("/app/user-accounts/get-category-transactions", {}, parseCategoryTransactionsIntoSelect)
}

//вывод категорий транзакций в параметры тега <select>
function parseCategoryTransactionsIntoSelect(data) {
    $('.type-category-tr').detach();
    $.each(data, function (index, json) {
        $('.category-tr').append('<option class="type-category-tr" value="' + json.id + '">' + json.categoryName + '</option>');
    });
}

//вывод полученных типов счетов в параметры тега <select> на главной странице в таблицу
function parseTypesAccountsIntoSelect(data) {
    $('.type').detach();
    $.each(data, function (index, json) {
        $('#account-type-name').append('<option class="type" value="' + json.id + '">' + json.accountName + '</option>');
    });
}

//вывод полученных типов счетов в список <select> в модальное окно
function parseTypesAccountsIntoSelectForModal(data) {
    $('.type-form').detach();
    $.each(data, function (index, json) {
        $('#account-type-name-form').append('<option class="type-form" value="' + json.id + '">' + json.accountName + '</option>');
    });
}

//отчистка сгенерированной таблицы (для нового запроса)
function clearAjaxTable() {
    $("tr.ajax-rows").detach();
}

//post запрос на добавление новой транзакции, обновление списка транзакций обьекта <select>
function addNewCategoryTransaction() {
    const category = String($('#type-input').val());
    $.post('/app/user-account/add-new-category-transaction',
            { "categoryName" : category},
            parseCategoryTransactionsIntoSelect);
}

//ajax запрос на получение всех транзакций по ID счёта и вызов функции обработки и вывода в таблицу
function ajaxQueryForTransactionsAccountList() {
    let url = window.location.pathname;
    let accountId = url.split("/")[3];

    $.getJSON('/app/transactions/get', { "id": accountId}, parseAccountTransactionsToTableRows);
}

//вставка полученных значенией JSON (транзакций по счёту) таблицу
function parseAccountTransactionsToTableRows(data) {
    let count = data.length;
    clearAjaxTable();
    $.each(data, function (index, json) {
        $('#ajax-account-transactions')
            .prepend('<tr class="ajax-rows">' +
                '<td scope = "row">' + count-- + '</td>' +
                '<td>' + json.categoryName + '</td>' +
                '<td>' + json.sum + ' руб.' + '</td>' +
                '<td>' + parseDate(json.date) + '</td>' +
                '<td>' + addButtondeleteTransaction(json.id) + '</td>' +
                '</tr>')
    });
}

//создание кнопки по удалению каждой транзакции в таблице
function addButtondeleteTransaction(transactionId) {
    return '<button onclick="deleteTransaction('+ transactionId +')">delete</button>';
}
//добавление ajax функции с короткой записью для метода delete
$.delete = function(url, data, callback, type){
    if ($.isFunction(data) ){
        type = type || callback,
            callback = data,
            data = {}
    }
    return $.ajax({
        url: url,
        type: 'DELETE',
        success: callback,
        data: data,
        contentType: type
    });
}

//delete запрос на удаление транзакции по id
function deleteTransaction(transactionId) {
    let url = "/app/transaction/" + transactionId + '/delete';

    /*$.ajax({ url: url, method: "DELETE" })
        .then(function () {

        })
        .catch(function (err) {
            console.log("ошибка сервера, запись не найдена или уже удалена")
        });*/

   $.delete(url, { "id": transactionId}, ajaxQueryForTransactionsAccountList);

}




//вывод даты в нужном формате, перевод из миллисекунд с 1970 г в dd.mm.yyyy
function parseDate(date) {
    let locDate = new Date(Number(date));
    let formatDate = "" +
        locDate.getDate() + "." +
        ((locDate.getMonth()+1)<10 ? "0"+(locDate.getMonth()+1) : locDate.getMonth()+1 ) + "." +
        locDate.getFullYear();
    return formatDate;
}

//ajax запрос на получение и обработку счетов пользователя
function ajaxQueryForUserAccountsList() {
    $.getJSON('/app/user-accounts/get', {}, parseUserAccountsToTableRows);
}

//вывод JSON данных (счета пользователя) в таблицу, добавление кнопки редактирования записей
function parseUserAccountsToTableRows(data) {
    let count = data.length;
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

//функция добавления кнопки редактирования записей данной строки в таблице
//каждой кнопке передаём свой id чтобы редактирование было нужной нам записи
function setIdAccountForLinkButton(id) {
    return "" +
    "<div class='btn-group dropright'>" +
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
            "<a class='dropdown-item' href='/app/transactions/" + id.toString() +"'>" +
                "Show all transactions on this account</a>" +
        "</div>" +
     "</div>";
}

//вызов всплывающего окна изменения счёта по accountID, сокрытие остальных форм
function clickOnChangeAccount(id) {
    setDataForHiddenInput(id);
    $("#delete-account").css( "display", "none" );
    $("#change-account").css( "display", "block" );
    $("#add-transaction").css("display", "none")
}

//вызов всплывающего окна удаления счёта по accountID, сокрытие остальных форм
function clickOnDeleteAccount(id) {
    setDataForHiddenInput(id);
    $("#change-account").css( "display", "none" );
    $("#delete-account").css( "display", "block" );
    $("#add-transaction").css("display", "none")
}

//вызов всплывающего окна добавления новой транзакции, сокрытие остальных форм
function clickAddNewTransaction(id) {
    setDataForHiddenInput(id);
    $("#change-account").css( "display", "none" );
    $("#delete-account").css( "display", "none" );
    $("#add-transaction").css("display", "block")
}