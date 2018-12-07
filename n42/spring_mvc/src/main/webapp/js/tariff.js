function reloadPage(tariffId) {
    $("#main").load("tariff?tariffId=" + tariffId + " #main");
}

let tariffId = $("#tariffId").val();


function blockTariff(tariffId) {
    jQuery.ajax({
        type: "PATCH",
        url: "rest/tariff/block?tariffId=" + tariffId,
        success: function(data){
            reloadPage(tariffId);
        },
        error: function(xhr, status, error) {
        }
    });
}

function unblockTariff(tariffId) {
    // let data = {"tariffId": tariffId};

    jQuery.ajax({
        type: "PATCH",
        url: "rest/tariff/unblock?tariffId=" + tariffId,
        success: function(data){
            reloadPage(tariffId);
        },
        error: function(xhr, status, error) {
        }
    });
}

function removeOption(optionId) {
    // let data = {"tariffId": tariffId};

    jQuery.ajax({
        type: "DELETE",
        url: "rest/tariff/option?optionId=" + optionId,
        success: function(data){
            reloadPage(tariffId);
        },
        error: function(xhr, status, error) {
        }
    });
}