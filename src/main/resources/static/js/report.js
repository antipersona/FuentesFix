// document.addEventListener('DOMContentLoaded', function() {
//     const cogerEnCarga = document.getElementById('coger_en_carga');

    
// });
    
    // cogerEnCarga.addEventListener('click', function() {
        
//});

function takerep(repId){
    var reporteId = repId; // Get the Reporte ID from the data attribute
    var funcionarioId = user_id; //should take it from fuente.html but doesn't work
    var currentURL = window.location.pathname;
    console.log("url = ",currentURL);
    
    // Send an AJAX request to update the Reporte object
    var data = {
        reporteId: reporteId,
        funcionarioId: funcionarioId
    };

    go(currentURL, 'PUT', data)
        .then(response => {
            // Handle success response
            const commentCell = document.getElementById(repId);
            if (commentCell) {
                commentCell.textContent = "Un funcionario ya esta occupendo se";
            }
            console.log('Reporte updated successfully', response);
        })
        .catch(error => {
            // Handle error
            console.error('Failed to update Reporte', error);
        });
}