function reloadPage(tariffId) {
    $("#main").load("tariff?tariffId=" + tariffId + " #main");
}


function onBlock(blockName, tariffId) {
    closeAllContent();
    deactivateButtons();
    blockBtn.addClass(activeBtnClassName);
    menuSeparator.addClass(hiddenClassName);
    let path;
    if (blockName === "Activate") {
        path = "rest/tariff/unblock?tariffId=" + tariffId;
    } else {
        path = "rest/tariff/block?tariffId=" + tariffId;
    }

    jQuery.ajax({
        type: "PATCH",
        url: path,
        contentType: "application/json",
        success: function(data){
            reloadPage(tariffId);
        },
        error: function(xhr, status, error) {
            reloadPage(tariffId);
        }
    });
}


function removeOption(optionId) {
    // let data = {"tariffId": tariffId};

    jQuery.ajax({
        type: "DELETE",
        url: "rest/tariff/option?optionId=" + optionId,
        success: function(data){
            reloadPage($("#tariffId").val());
        },
        error: function(xhr, status, error) {
        }
    });
}