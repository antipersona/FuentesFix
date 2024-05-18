var mapfunc = L.map('mapfunc').setView([40.416, -3.703], 13);
//load the fuentes variable from the model
var fuentes = JSON.parse(document.getElementById('fuentes'));
var reportes = /*[[${reportes}]]*/ [];
  
L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(mapfunc); 
