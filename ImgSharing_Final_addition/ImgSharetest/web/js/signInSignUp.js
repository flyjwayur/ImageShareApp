$(document).ready(function () {
    console.log("ready");

    //check logout
    $('#logOutbtn').click(function () {
        //var r = confirm("Are you sure?");
        if (confirm("Do you really want to log out?")) {
            //remove username from local storage when users log out
            window.open("http://127.0.0.1:8080/ImgSharetest/index.html");
            localStorage.removeItem("username");
  
        } else {
        }

    });

    //Sign Up
    $('#signupbtn').click(function () {
        var username = $('#unameInput').val();
        var password = $('#pwordInput').val();
        var email = $('#emailInput').val();

        if (validate(username, password, email)) {
            createUser(username, password, email);
        } else {
            $('#SingupMessage').text("Please fill all the fields");
        }
    });
   
    function createUser(username, password, email) {
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.user/SingUpUsers/" + username + "/" + password + "/" + email,
            dataType: "text",
            success: function (response) {
                if (response === "Yes") {
                    $('#SingupMessage').text("Sign up successfully.");
                }
            }

        });
    };
    
   //check the mandatory user inputs are filled(validate user login form)
    function validate(username, password, email) {
        var valid = true;
        console.log(username, password);
        if (username == "" || username == null) {
            console.log("username empty");
            valid = false;
        } 
        if (password == "" || password == null) {
            console.log("password empty");
            valid = false;
        }
        if (email == "" || email == null) {
            console.log("password empty");
            valid = false;
        }
        return valid;
    }
  
    //check login
    $('#loginbtn').click(function () {
        var username = $('#usernameInput').val();
        var password = $('#passwordInput').val();
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.user/LoginforUsers/" + username + "/" + password,
            dataType: "text",
            success: function (response) {
                
                if (response == username) {
                    console.log("res " + response);
                    console.log("uname " + username);
                    //set username in local storage when users log in
                    localStorage.username = username;
                    window.open("http://127.0.0.1:8080/ImgSharetest/thumbnailGallery.html");
                } else if (response == "No") {
                    $('#loginFail').text("Wrong password or ID, please try again.");
                }
            }
        });
    });
});

    