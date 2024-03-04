
for (var i = 0; i < fuentes.length; i++) {
    var fuente = fuentes[i];
    L.marker([fuente.getLatitud(), fuente.getLongitud()]).addTo(map).on('click', markerOnClick);
}

function markerOnClick(e) {
    var popLocation = e.latlng;
    var popup = L.popup()
        .setLatLng(popLocation)
        .setContent('<a href="/fuente/">Reporta esta fuente</a><br>' + popLocation.toString())
        .openOn(map);
}

