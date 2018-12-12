
function reloadPage(contractId) {
    $("#main").load("contract?contractId=" + contractId + " #main");
}

function removeOption(contractId, optionId) {
    jQuery.ajax({
        type: "DELETE",
        url: "rest/contract/option?contractId=" + contractId + "&optionId=" + optionId,
        success: function(data){
            reloadPage(contractId);
        },
        error: function(xhr, status, error) {
            reloadPage(contractId);
        }
    });
}

function onBlock(blockName, contractId) {
    closeAllContent();
    deactivateButtons();
    blockBtn.addClass(activeBtnClassName);
    menuSeparator.addClass(hiddenClassName);
    let path;
    if (blockName === "Activate") {
        path = "rest/contract/unblock?contractId="+contractId;
    } else {
        path = "rest/contract/block?contractId="+contractId;
    }

    jQuery.ajax({
        type: "PATCH",
        url: path,
        contentType: "application/json",
        success: function(data){
            $("#main").load("contract?contractId=" + contractId + " #main");
        },
        error: function(xhr, status, error) {
            $("#main").load("contract?contractId=" + contractId + " #main");
        }
    });
}