document.getElementById('f_avatar').addEventListener('change', function() {
    var file = this.files[0];
    if (file) {
        var reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('avatar').src = e.target.result;
        };
        reader.readAsDataURL(file);

        var form = document.getElementById('avatarForm');
        form.submit();
    }
});


function openModal(comment, reportId) {
    document.getElementById("commentInput").value = comment;
    document.getElementById("myModal").style.display = "block";
    console.log(reportId);
    document.getElementById("currentReportId").value = reportId;
}

function saveComment() {
    const newComment = document.getElementById("commentInput").value;
    const reportId = parseInt(document.getElementById("currentReportId").value, 10);
    var currentURL = window.location.pathname;
    var data = {
        reporteId: reportId,
        newComment: newComment
    };

    go(currentURL, 'PUT', data)
            .then(d =>  {
                console.log("happy", d);
                // Mettre à jour le commentaire dans le tableau
                const commentCell = document.getElementById(reportId);
                if (commentCell) {
                    commentCell.textContent = newComment;
                }else{
                    console.log("cell not found");
                }
                closeModal();
            })
            .catch(e => console.log("sad", e));
}



function closeModal() {
    document.getElementById("myModal").style.display = "none";
}

//to close the pop up if there is a click out of it
window.onclick = function(event) {
    const modal = document.getElementById("myModal");
    if (event.target == modal) {
        closeModal();
    }
}