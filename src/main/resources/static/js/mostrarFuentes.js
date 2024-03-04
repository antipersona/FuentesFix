L.marker([/*[[${fuente.getLatitud()}]]*/, /*[[${fuente.getLongitud()}]]*/]).addTo(map).on('click', markerOnClick);

function markerOnClick(e) {
    var popLocation= e.latlng;
    var popup = L.popup()
          .setLatLng(popLocation)
          .setContent('<a href="/report">Reporta esta fuente</a><br>' + popLocation.toString())
          .openOn(map);
}

