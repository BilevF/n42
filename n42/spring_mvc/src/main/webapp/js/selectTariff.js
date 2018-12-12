
function replaceTariff(tariffId, contractId) {
    jQuery.ajax({
        type: "PATCH",
        url: "rest/contract/tariff?contractId=" + contractId +"&tariffId=" + tariffId,
        success: function(data){
            window.location.href = "contract?contractId=" + contractId;
        },
        error: function(xhr, status, error) {
            $("#main").load("contract/tariff/change?contractId=" + contractId + " #main");
        }
    });

}