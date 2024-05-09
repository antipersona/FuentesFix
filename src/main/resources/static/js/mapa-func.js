var mapfunc = L.map('mapfunc').setView([40.416, -3.703], 13);
//load the fuentes variable from the model
var fuentes = JSON.parse(document.getElementById('fuentes'));
var reportes = /*[[${reportes}]]*/ [];

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(mapfunc); 

//$(document).ready(function() {

fuentes.forEach(anadeFuente);

function anadeFuente(fuente) {
  if (isReportAttached(reportes, fuente.id)) {
    var color = {orange: '#ffa500'};
    //L.Marker([fuente.latitud, fuente.longitud],color).addTo(map).on('click', markerOnClick);
    L.marker([fuente.latitud, fuente.longitud], color).addTo(mapfunc).bindPopup('<a href="/fuente/' + fuente.id + '">' + fuente.direccion_aux +'</a><br>');
    //L.marker([fuente.latitud, fuente.longitud]).addTo(map).on('click', markerOnClick);
  }else{
    L.marker([fuente.latitud, fuente.longitud]).addTo(mapfunc).bindPopup('<a href="/fuente/' + fuente.id + '">' + fuente.direccion_aux +'</a><br>');
  }
}

// Fonction pour vérifier si un rapport est attaché à une fontaine
function isReportAttached(reportes, fuenteId) {
  for (var i = 0; i < reportes.length; i++) {
      if (reportes[i].getFuente_id() === fuenteId) {
          return true;
      }
  }
  return false;
}
//});