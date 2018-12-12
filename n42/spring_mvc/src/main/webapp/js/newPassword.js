

function setPassword() {
    let password = sha1($("#newPassword").val());
    let userId = $("#userId").val();
    let token = $("#token").val();

    if ($("#newPassword").val() == "") {
        $( "#exception" ).html("<div class='alert alert-danger' role='alert'>Password should not be empty</div>");
        return;
    }

    jQuery.ajax({
        type: "PATCH",
        url: "rest/user/password?userId="+userId+"&token="+token+"&password="+password,
        success: function(data){
            window.location.href = "account";
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
        }
    });
}

function sendPasswordLink() {
    let email = $("#email").val();;

    jQuery.ajax({
        type: "POST",
        url: "rest/user/password/link?email="+email,
        success: function(data){
            window.location.href = "";
        },
        error: function(xhr, status, error) {
            onError(xhr, status, error);
        }
    });
}

