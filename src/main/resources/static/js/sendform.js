var object = {};
var formData = new FormData(document.forms.registration);

formData.forEach(function(value, key){
    object[key] = value;
});
var json = JSON.stringify(object);

var xhr = new XMLHttpRequest();
xhr.open("POST", '/submit', true)
xhr.setRequestHeader('Content-type', 'application/json; charset=utf-8');

// Отсылаем объект в формате JSON и с Content-Type application/json
xhr.send(json);
