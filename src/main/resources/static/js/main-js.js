function show(id) {
    document.getElementById(id).style.display = "block";
}

function ifAttributeThenShowElement(str, id) {
    let strGET = window.location.search;
    console.log(strGET);
    if (strGET === "?" + str) {
        show(id);
    }
}

function showElement(id) {
    window.document.getElementById(id).style.display = "block";
}

function hideAllForms() {
    let mainModalWindow = document.getElementById("exampleModal");
    let cells = mainModalWindow.getElementsByTagName("form");
    for (let i = 0; i < cells.length; i++) {
        cells[i].style.display = "none";
    }
}

function showThisHideOther(id) {
    hideAllForms();
    showElement(id)
}