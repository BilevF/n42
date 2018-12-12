function reloadPage(userId) {
    $("#main").load("contract/new?userId=" + userId + " #main");
}


function onSuccess(userId) {
    clearErrors();
    window.location.href = "user?userId=" + userId;
}


function editContract(userId) {
    let data = {};

    let tariff = {'id': $("#tariffId").val()}

    $(".form-control").each(function() {
        data[$(this).attr("name")] = $(this).val();
    });

    data['tariff'] = tariff;

    jQuery.ajax({
        type: "POST",
        url: "rest/contract",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(data){
            onSuccess(userId);
        },
        error: function(xhr, status, error) {
            if (error === "Bad Request") {
                reloadPage(userId);
            }
            onError(xhr, status, error);
        }
    });
}

function updateContract(contractId) {
    let data = {};

    $(".form-control").each(function() {
        data[$(this).attr("name")] = $(this).val();
    });

    jQuery.ajax({
        type: "PATCH",
        url: "rest/contract",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(data){
            window.location.href = "contract?contractId=" + contractId;
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
        }
    });
}