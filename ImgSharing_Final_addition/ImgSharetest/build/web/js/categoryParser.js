$(document).ready(function () {
    console.log("ready");
    
    //Parsing the category names and display htem in the part of file upload in front end.
    $.ajax({
        type: "GET",
        url: "http://127.0.0.1:8080/ImgSharetest/webresources/model.category/allCategory",
        dataType: "xml",
        success: function (xml) {
            var index = 1;
            $("#categories").append(
                    '<option value="" >Select a category for the image:</option>'
            );
            $(xml).find('category').each(function () {

                console.log(index);

                var categoryName = $(this).find('cname').text();
                var categoryId = $(this).find('cid').text();
                
                $("#categories").append(
                        '<option value="'+categoryId+'">'+categoryId+". "+categoryName+'</option>');
                index++;   
            });
        }
    });
});    