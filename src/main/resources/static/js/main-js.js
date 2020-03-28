/**
  *     данный файл представляет собой java script костыли,
  *     ибо разработчик не шибко шарит во frontend разработке
  *     как говориться главное чтоб работало =) а рефакторинг провести мы всегда успеем))
 */

//показать элемент по его id (меняем css на display:block)
function show(id) {
    window.document.getElementById(id).style.display = "block";
}

//скрывает сначала все формы а потом показывает нужную по ID
function showThisHideOther(id) {
    hideAllForms();
    show(id)
}

//функция проверяет URL на наличие доб. параметров и показывает какой-либо из элементов
//в данном случае показывает сообщение об ошибке авторизации или успешном разлогинивании
function ifAttributeThenShowElement(str, id) {
    let strGET = window.location.search;
    console.log(strGET);
    if (strGET === "?" + str) {
        show(id);
    }
}

//скрывает все формы с определённым ID
function hideAllForms() {
    let mainModalWindow = document.getElementById("exampleModal");
    let cells = mainModalWindow.getElementsByTagName("form");
    for (let i = 0; i < cells.length; i++) {
        cells[i].style.display = "none";
    }
}

