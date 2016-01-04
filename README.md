### What is this repository for? ###

* For image sharing project 

* Sharing the project with team members

* Keep the history of project 

### The environment for the image sharing project ###
 
[Server: in Virtual box]

* Web server : httpd 

* Application server : Glassfish

[DB: in Virtual box]

* DB : MariaDB (Managing the DB : phpmyadmin)

[development tool]

* NetBeans (Java EE Bundle) + java JDK >= 7
* Front end : HTML, CSS, JS, JQEURY, AJAX
* Back end : Servlet, Restful, (JSP)  

### The features in the image sharing project ###

We made features by MVC model. 

* Model : Java entities from Database

* View : front end

* Controller : restful, servlet (Back end)

1. [Mandatory features]

* create user : We implemented User registration in the index.html page which is a main page and a user can log in from the main page too. The user login system can check whether a user logged in or not and if the user exist in the DB, they can log in.

* upload file together with metadata : 
We implemented the upload image file. First when user try to choose the file, it checked the file format to see whether it is image file or not from front end side and after that it goes to database by servlet 'upload' in back end. 
The image file can have a metadata such as image description, image name, image category and later on, user can also add the tag name to each image, when the image pops up.

* get a link (url) to selected image :
A user can see the image link, when they click the separate image in image modal.

* browse images
A user can see the image in the thumbnailGallery.html after they log in. 
All the images are parsed by XMLparser which is in the XMLParser.js and tagImgModel. 
First, we tried to use JSON which is created by Glassfish but it was not created stable,
so we decided to use XML and parse it to display the all the images which is in the database.

* search image files (utilising metadata, like tags)
A user can search the images by the existing tags. And the tag displays all the images which has the tags on the thumbnailGallery.html 




2.[Extra features] 

### The features both in frontend and backend ###

* Login in system

* Add tag to the database and check whether same tag exist in the same picture 

* Add the category to the image, get the category list from database to frontend

* Count Like /Dislike per a picture and store who/which picture/whether they like or dislike 

### The features which are only in Backend (Servlet, Restful, GlassfishServer) + DB ###

* Delete user by user ID

### The features which are only in Front end(using html, css, Jquery, JS, AJAX) ###

* Comments 

* Log out(asking confirmation of log out by JS)

### The features which we want to implement in the future ###

* modify user data

* utilizing rating find most popular images

* update image

* update metadata

* delete image

* swipe image using Jquery like tinder 

### Who do I talk to? ###

*The member of #Error404 group

   - Tehetena behailu

   - Yasamin Salami

   - HyeSoo Park