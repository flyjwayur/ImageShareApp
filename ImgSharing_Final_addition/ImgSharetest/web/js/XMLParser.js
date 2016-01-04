// check the rating(like and dislike) and response to a user
$(document).on('click', 'button', function (event) {
    var button = event.target;
    var buttonClass = button.className;
    console.log(buttonClass);
    if (buttonClass != null && (buttonClass == "like btn btn-success btn-sm" || buttonClass == "dislike btn btn-danger btn-sm")) {
        console.log(event.target);
        var buttonId = event.target.id;
        console.log(buttonId);
        //to distingush each image by Image id because modal has similar looking.
        var username = buttonId.split(',')[0];
        var imgId = buttonId.split(',')[1];
        var rate = buttonId.split(',')[2];
        console.log("ID is " + imgId);
        console.log("user is " + username);
        console.log("rate is " + rate);
        console.log(username + imgId + rate + "clicked");
        $.ajax({
            type: "GET",
            //url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.image/rateImg?imgId=" + imgId + "&rate=" + rate + "&username=" + username,
            url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.image/rateImg/" + imgId + "/" + rate + "/" + username,
            dataType: "text",
            success: function (response) {
                if (response == "Yes") {
                    if (rate == "1") {
                        alert("You just liked this picture.");
                        window.open("http://127.0.0.1:8080/ImgSharetest/thumbnailGallery.html");
                    }
                    else if (rate == "-1") {
                        alert("You just disliked this picture.");
                        runXMLparser();
                        window.open("http://127.0.0.1:8080/ImgSharetest/thumbnailGallery.html");
                    }
                } else {
                    alert(response);
                }
            }
        });
    }
});

//Pasre the Image xml and display all images and modals of the images
function runXMLparser() {
    //display user name after getting the username stored in localStorage
    document.getElementById("welcomeID").innerHTML= localStorage.username;
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.image/",
        dataType: "xml",
        success: function (xml) {
            var index = 0;
            $(xml).find('image').each(function () {

                console.log(index);

                var name = $(this).find("imgtitle").text();
                var imgDesc = $(this).find("imgdesc").text();
                var categoryName = $(this).find("cname").text();
                var tagName = $(this).find("tname").text();
                var target = name.split(".");
                var id = $(this).find("imgid").text();
                var quarterWidth = $(document).width() / 4;
                //var uname = $('#sessionuname').val(); //username from session
                var uname = localStorage.username; 
                var likes;
                var dislikes;
                countLikes(id).done(function (value1) {
                    likes = value1;
                    countDislikes(id).done(function (value2) {
                        dislikes = value2;
                        console.log(likes + " likes and " + dislikes + "dislikes");
                        console.log(uname + " username received");
                        console.log("ID is " + id);
                        if ((index % 4) === 0) {
                            $(".thumbnails").append('<div class= "row">');
                        }
                        ;
                        // put photos to thumbnails
                        $(".thumbnails").append(
                                '<div class="col-lg-3 col-md-3 col-xs-6 thumb">' +
                                '<a class="thumbnail" href="#' + name + '" data-image-id="" data-toggle="modal" data-title="' + name + '"'
                                + 'data-caption="something" '
                                + 'data-image="http://127.0.0.1:8888/images/' + name + '" '
                                + 'data-target="#' + target[0] + '">'
                                + '<img class="img-responsive" src="http://127.0.0.1:8888/images/' + name + '" alt="' + imgDesc + '">'
                                + '<input type="hidden" name="hiddenImgID" value=' + id + '>'
                                + '</a></div>');
                        // generate modals
                        $(".modals").append(
                                '<div id="' + target[0] + '" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
                                + '<div class="img modal-dialog" style="margin-left:-' + quarterWidth + 'px;">'
                                + '<div class="modal-content">'
                                + '<div class="modal-header">'
                                + '<h4 class="modal-title">' + target[0] + '</h4>'
                                + '<button type="button"  class="close" data-dismiss="modal" aria-label=""><span>&times;</span></button>'
                                + '</div>'
                                + '<div class="modal-body">'
                                + '<img id="image-gallery-image" class="img-responsive" src="http://127.0.0.1:8888/images/' + name + '">'
                                + '<input type="hidden" name="hiddenImgID" value=' + id + '>'
                                + '<p> Share URL : http://127.0.0.1:8888/images/' + name + '</p>'
                                + '<p> Category: ' + categoryName + '</p>'
                                + '<p> Image Description: ' + imgDesc + '</p>'
                                + '<div class="caption box" style="height:50px;">'
                                + '<button class="like btn btn-success btn-sm" id="' + uname + ',' + id + ',1" type="button"><span class="glyphicon glyphicon-thumbs-up"></span> Like ' + likes + '</button>'
                                + '<button class="dislike btn btn-danger btn-sm" id="' + uname + ',' + id + ',-1" type="button"><span class="glyphicon glyphicon-thumbs-down"></span> Dislike ' + dislikes + '</button>'
                                + '<button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-star"></span> Favorite</button>'
                                + '<form>'
                                + '<input type="text" name="tname" id="tnameInput' + id + '">'
                                + '<input type="hidden" name="hiddenImgID' + id + '" value=' + id + ' id="hiddenImgIDInput">'
                                + '<input type="text" id="comments" style="font-family:Cambria;font-size:1.2em;">'
                                + '<input type="submit" value="Comment">'
                                + '</form>'
                                + '<button type="submit" class="ADDTag" name="ADDTag" value= "ADDTag" id="addTag,' + id + '">ADD Tag<br>'
                                + '</div>'
                                + '</div>'
                                + '</div>'
                                + '</div>'
                                + '</div>'
                                );
                        if ((index % 4) === 3) {
                            $(".thumbnails").append('</div>');
                        }
                        ;
                        index++;
                    });
                });
            });

        }
    });
}

//count likes
function countLikes(imgId) {
    console.log("countLikes with " + imgId);

    return $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.image/countLikes/" + imgId,
        dataType: "text"
    });
}

//count dislikes
function countDislikes(imgId) {
    console.log("countDislikes with " + imgId);

    return $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.image/countDislikes/" + imgId,
        dataType: "text"
    });
}