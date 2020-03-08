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

/*
function showMessageFromAttribute(id) {
    let str = window.location.search.slice(9);
    if (str !== "") {
        document.getElementById(id).textContent = str;
        show(id);
    }
}*/
