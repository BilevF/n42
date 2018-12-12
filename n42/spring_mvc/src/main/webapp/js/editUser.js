

function onSuccess(data) {
    clearErrors();
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


function updateUser() {
    let data = {};

    $(".form-control").each(function() {
        data[$(this).attr("name")] = $(this).val();
    });

    if (data['password'] !== "") {
        data['password'] = sha1(data['password']);
    }

    jQuery.ajax({
        type: "PATCH",
        url: "rest/user",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(data){
            window.location.href = "account";
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
        }
    });
}
