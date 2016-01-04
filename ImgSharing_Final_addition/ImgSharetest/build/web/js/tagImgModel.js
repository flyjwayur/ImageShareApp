$(document).ready(function () {
    console.log("ready");
     
     //Search image by tags and pasre the Image xml
     
    $('#tagbtn1').click(function () {
        console.log("clicked");
        var tag = $('#searchbyTaginput').val();
        console.log(tag);
        $.ajax({
            type: "GET",
            url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.tag/SearchByTag/" + tag,
            dataType: "xml",
            success: function (xml) {
                var index = 0;
                //$("#imgsbytagtitle").text(");
                $("#imgsbytagtitle").html("Images for "+tag+"<br><hr>");
                $("#imgsbytag").empty();
                $(xml).find('image').each(function () {
                    console.log(index);
                    var name = $(this).find("imgtitle").text();
                    var target = name.split(".");
                    var id = $(this).find("imgid").text();
                    var quarterWidth = $(document).width()/4
                    console.log(name);

                    //For counting 4 images in a row
                    if ((index % 4) === 0) {
                        $("#imgsbytag").append('<div class= "row">');
                    };
                    
                    $("#imgsbytag").append(
                            '<div class="col-lg-3 col-md-3 col-xs-6 thumb">' +
                            '<a class="thumbnail" href="#' + name + '" data-image-id="" data-toggle="modal" data-title="' + name + '"'
                            + 'data-caption="something" '
                            + 'data-image="http://127.0.0.1:8888/images/' + name + '" '
                            + 'data-target="#' + target[0] + '">'
                            + '<img class="img-responsive" src="http://127.0.0.1:8888/images/' + name + '">'
                            + '<input type="hidden" name="hiddenImgID" value=' + id+'>'
                            + '</a></div>');
                    
 
                    $("imgModals").append(
                            '<div id="' + target[0] + '" class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">'
                            + '<div class="img modal-dialog" style="margin-left:-'+quarterWidth+'px;">'
                            + '<div class="modal-content">'
                            + '<div class="modal-header">'
                            + '<h4 class="modal-title">' + target[0] + '</h4>'
                            + '<button type="button"  class="close" data-dismiss="modal" aria-label=""><span>&times;</span></button>'
                            + '</div>'
                            + '<div class="modal-body">'
                            + '<img id="image-gallery-image" class="img-responsive" src="http://127.0.0.1:8888/images/' + name + '">'
                            + '<input type="hidden" name="hiddenImgID" value=' + id + '>'
                            + '<p> Share URL : http://127.0.0.1:8888/images/' + name + '</p>'
                            + '</div>'
                            + '<div class="caption box">'
                            + '<button type="button" class="btn btn-success btn-sm"><span class="glyphicon glyphicon-thumbs-up"></span> Like</button>'
                            + '<button type="button" class="btn btn-danger btn-sm"><span class="glyphicon glyphicon-thumbs-down"></span> Dislike</button>'
                            + '<button type="button" class="btn btn-default btn-sm"><span class="glyphicon glyphicon-star"></span> Favorite</button>'
                            + '<form class="pull-right" action="webresources/model.tag/addTag" method="post">'
                            + '<input type="text" name="tname" id="tnameID">'
                            + '<input type="hidden" name="hiddenImgID" value=' + id + '>'
                            + '<input type="submit" value="ADD Tag" id="addTag"><br>'
                            + '<textarea name="comments" id="comments" style="font-family:Cambria;font-size:1.2em;">'
                            + '</textarea>'
                            + '<input type="submit" value="Comment">'
                            + '</form>'
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
            }
        });
    });
});
                