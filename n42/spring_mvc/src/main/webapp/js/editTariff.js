

function onSuccess(data) {
    clearErrors();
    alert(data);
    window.location.href = "tariff?tariffId=" + data;
}


function editTariff() {
    let data = {};

    $(".form-control").each(function() {
        data[$(this).attr("name")] = $(this).val();
    });
    data['valid'] = $("label.active input[name='valid']").val();

    jQuery.ajax({
        type: "POST",
        url: "rest/tariff",
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