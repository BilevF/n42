
function clearErrors() {
    $( "#phoneOrEmail" ).removeClass( "is-invalid" );
}

function onError(xhr, status, error) {
    clearErrors();
    let err = JSON.parse(xhr.responseText);
    if (error === "Expectation Failed") {
        $( "#phoneOrEmail_error" ).text(err);
        $( "#phoneOrEmail" ).addClass( "is-invalid" );
    }
}

function onSuccess(data) {
    clearErrors();
    window.location.href = "user/" + data;
}


function findUser() {
    let data = {"phoneOrEmail": $("#phoneOrEmail").val()};

    jQuery.ajax({
        type: "GET",
        url: "rest/user/find",
        data: data,
        success: function(data){
            onSuccess(data);
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
        }
    });
}