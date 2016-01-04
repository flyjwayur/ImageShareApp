//All the button are created dynamically, 
//so we need to use 'on' to bind button function and need to use event

$(document).on('click', 'button', function (event) {
    var button = event.target; //the button that fired event when users press
    var buttonClass = button.className; //we have tag event and rate event, so we need to check the event class name
    if (buttonClass != null && buttonClass == "ADDTag") {
        console.log(event.target);
        var buttonId = event.target.id.split(',')[0];
        var hiddenImgID = event.target.id.split(',')[1];
        console.log("button id is " + buttonId);
        console.log("hiddenImgID is " + hiddenImgID);
        if (buttonId == "addTag") {
            var identifier = '#tnameInput' + hiddenImgID
            console.log(identifier);
            var tname = $(identifier).val();
            console.log("tname is " + tname);
            console.log("hiddenImgID is " + hiddenImgID);
        }

        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.tag/addTag/" + tname + "/" + hiddenImgID,
            dataType: "text",
            success: function (response) {
                if (response == "TAGADDED") {
                    alert("The tag " + tname + " was added!!!");
                } else if (response == "TAGEXIST") {
                    //$('#SingupMessage').text("Sign up successfully.");
                    alert("The tag " + tname + " already exists!!!.");
                }
            }

        });
    }
});
