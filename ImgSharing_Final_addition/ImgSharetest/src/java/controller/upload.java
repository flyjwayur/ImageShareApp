/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.transaction.UserTransaction;
import model.Category;
import model.Image;
import model.Tag;


/**
 *
 * @author iosdev
 */
//Image File upload to DB
@WebServlet(name = "upload", urlPatterns = {"/upload"})
@MultipartConfig(location = "/var/www/html/images")
public class upload extends HttpServlet {

    String link = "http://127.0.0.1:8888/images/";
    EntityManager em;
    EntityManagerFactory emf;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String filename = getFilename(request.getPart("file"));
        String imgdesc = request.getParameter("imgdesc");
        //String tname = request.getParameter("tname");
        Category imgCategory = new Category(Integer.parseInt(request.getParameter("imgCategory")));
        
        try {
            request.getPart("file").write(filename);
            out.println("File uploaded successfully!");
        } catch (Exception e) {
            out.println("File upload Exception: " + e.getMessage());
        }
        emf = Persistence.createEntityManagerFactory("ImgShare3PU");
        try {
            
            //Transaction begin
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em = emf.createEntityManager();
            out.println("Start");
            Image img = new Image(filename, new Date(), imgdesc, imgCategory);
            img.setPath(link + filename);
            out.println(img.getImgtitle() + " <- Filename");
            em.persist(img);
            transaction.commit();
            //Transaction end
            
            out.println("File in DB successfully!");
            response.sendRedirect("http://127.0.0.1:8080/ImgSharetest/thumbnailGallery.html");
        } catch (Exception e) {
            out.println("Database Exception: " + e.getMessage());
            response.sendRedirect("http://127.0.0.1:8080/ImgSharetest/thumbnailGallery.html");
        } finally {
            em.close();
            emf.close();
            out.close();
        }
    }

    //from http://stackoverflow.com/questions/2422468/how-to-upload-files-to-server-using-jsp-servlet/2424824#2424824
    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }
}
