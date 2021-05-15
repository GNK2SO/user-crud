$(document).ready(function () {
    
    $("#logout-button").on('click', (event) => {
        $.ajax({
            type: 'DELETE',
            url: "/manager/auth",
        }).done(() => {
            window.location.assign("/manager/");
        });
    });

});