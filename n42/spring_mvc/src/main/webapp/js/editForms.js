
function clearErrors() {
    $(".form-control").each(function() {
        $(this).removeClass( "is-invalid" );
    });
    $( "#exception" ).html("");

}

function addError(field, message) {
    $( "#" + field + "_error" ).text(message);
    $( "#" + field).addClass( "is-invalid" );
}

function onError(xhr, status, error) {
    clearErrors();
    let err = JSON.parse(xhr.responseText);
    if (error === "Expectation Failed") {
        for (let property in err.fieldErrors) {
            if (err.fieldErrors.hasOwnProperty(property)) {
                addError(property, err.fieldErrors[property]);
            }
        }
    } else if (error === "Bad Request") {
        $( "#exception" ).html("<div class='alert alert-danger' role='alert'>" + err + "</div>");
    }
}