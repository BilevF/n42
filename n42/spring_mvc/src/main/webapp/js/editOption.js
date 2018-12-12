function reloadPage(tariffId) {
    $("#relatedOptionsList").load("tariff/option/new?tariffId=" + tariffId + " #relatedOptionsList");
}


function onSuccess(tariffId) {
    clearErrors();
    window.location.href = "tariff?tariffId=" + tariffId;
}


function editOption(tariffId) {
    let data = {};

    let relatedOptions = [];
    $("label.active").find("input").each(function(index) {
        relatedOptions[index] = {"selectedOptionType": $(this).val(), "id": $(this).attr('name')};
    });

    $(".form-control").each(function() {
        data[$(this).attr("name")] = $(this).val();
    });

    data["relatedOptions"] = relatedOptions;

    jQuery.ajax({
        type: "POST",
        url: "rest/tariff/option",
        data: JSON.stringify(data),
        contentType: "application/json",
        success: function(data){
            onSuccess(tariffId);
        },
        error: function(xhr, status, error) {
            if (error === "Bad Request") {
                reloadPage(tariffId);
            }
            onError(xhr, status, error);
        }
    });
}