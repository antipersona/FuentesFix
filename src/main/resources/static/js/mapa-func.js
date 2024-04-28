var map = L.map('map-func').setView([40.416, -3.703], 13);
//load the fuentes variable from the model
var fuentes = JSON.parse(document.getElementById('fuentes'));

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map); 

function markerOnClick(e) {
  var popLocation= e.latlng;
  var popup = L.popup()
        .setLatLng(popLocation)
        .setContent('<a href="/fuente/' + fuente.id + '">Reporta esta fuente</a><br>' + popLocation.toString())
        .openOn(map);
}

L.marker([40.416, -3.703]).addTo(map).on('click', markerOnClick);

fuentes.forEach(anadeFuente);

function anadeFuente(fuente) {
  L.marker([fuente.latitud, fuente.longitud]).addTo(map).on('click', markerOnClick);
}
