var map = L.map('map').setView([40.416, -3.703], 13);

L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        maxZoom: 19,
        attribution: '&copy; <a href="http://www.openstreetmap.org/copyright">OpenStreetMap</a>'
}).addTo(map); 

L.marker([40.416, -3.703]).addTo(map);

var fountainCoordinates = [
  [40.416, -3.703], 
  [40.415, -3.702], 
  [40.417, -3.704], 
  [40.418, -3.705],
  [40.419, -3.706],
  [40.419, -3.705],
  [40.418, -3.704],
  [40.417, -3.703],
  [40.416, -3.702],
  [40.415, -3.701],
  [40.414, -3.700],
  [40.416, -3.703],
  [40.415, -3.702],
  [40.417, -3.704],
  [40.418, -3.705],
  [40.419, -3.706],
  [40.419, -3.705],
  [40.418, -3.704],
  [40.417, -3.703],
  [40.416, -3.702],
  [40.415, -3.701],
  [40.414, -3.700]
];

for (var i = 0; i < fountainCoordinates.length; i++) {
  L.marker(fountainCoordinates[i]).addTo(map);

}
