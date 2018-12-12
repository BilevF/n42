function reloadPage(contractId) {
    $("#main").load("contract/basket?contractId=" + contractId + " #main");
}

function onError(xhr, status, error) {
    let err = JSON.parse(xhr.responseText);
    if (error === "Bad Request") {
        $( "#exception" ).html("<div class='alert alert-danger' role='alert'>" + err + "</div>");
    }
}

function clearError() {
    $( "#exception" ).html("");
}


function clearBasket(contractId) {
    jQuery.ajax({
        type: "DELETE",
        url: "rest/contract/basket?contractId=" + contractId,
        success: function(data){
            clearError();
            reloadPage(contractId);
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
            reloadPage(contractId);
        }
    });
}

function submitBasket(contractId) {
    jQuery.ajax({
        type: "POST",
        url: "rest/contract/basket?contractId=" + contractId,
        success: function(data){
            clearError();
            window.location.href = "contract?contractId=" + contractId;
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
            reloadPage(contractId);
        }
    });

}

function removeFromBasket(contractId, optionId) {
    jQuery.ajax({
        type: "DELETE",
        url: "rest/contract/basket/option?contractId=" + contractId + "&optionId=" + optionId,
        success: function(data){
            clearError();
            reloadPage(contractId);
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
            reloadPage(contractId);
        }
    });

}

function addToBasket(contractId, optionId) {
    jQuery.ajax({
        type: "PUT",
        url: "rest/contract/basket/option?contractId=" + contractId + "&optionId=" + optionId,
        success: function(data){
            clearError();
            reloadPage(contractId);
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
            reloadPage(contractId);
        }
    });
}