

function onSuccess(data) {
    clearErrors();
    alert(data);
    window.location.href = "user?userId=" + data;
}


function editUser() {
    let data = {};

    $(".form-control").each(function() {
        data[$(this).attr("name")] = $(this).val();
    });

    jQuery.ajax({
        type: "POST",
        url: "rest/user",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(data){
            onSuccess(data);
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
        }
    });
}