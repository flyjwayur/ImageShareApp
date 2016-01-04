/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.System.out;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import model.User;

/**
 *
 * @author iosdev
 */
@Stateless
@Path("model.user")
public class UserFacadeREST extends AbstractFacade<User> {

    @PersistenceContext(unitName = "ImgShare3PU")
    private EntityManager em;
    private EntityManagerFactory emf;
    private Integer userSearchTerm = 0;
    private List<User> userList;
    private boolean login;

    public UserFacadeREST() {
        super(User.class);
    }

    @GET
    @Path("SingUpUsers/{username}/{password}/{email}")
    public String create(@PathParam("username") String username, @PathParam("password") String password, @PathParam("email") String email) throws URISyntaxException {
        if ((username == null || password == null) || (username.equals("")||password.equals(""))){
            return "No";             
        } else {
            User user = new User();
            user.setUsername(username);
            user.setPw(password);
            user.setEmail(email);
            super.create(user);
            return "Yes";         
        }

        //URI loc = new URI("../");
        //URI loc = new URI("../?msg=Signup%20Successful!");
        //return Response.temporaryRedirect(loc).build();
    }

    /*
     @POST
     //@Override
     //@Consumes({"application/xml", "application/json"})
     @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
     public Response create(@FormParam("username") String username, @FormParam("password") String password, @FormParam("email") String email) throws URISyntaxException {
     User user = new User();
     user.setUsername(username);
     user.setPw(password);
     user.setEmail(email);
     super.create(user);
     URI loc = new URI("../");
     //URI loc = new URI("../?msg=Signup%20Successful!");
     return Response.temporaryRedirect(loc).build();
     }
     */
    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, User entity) {
        super.edit(entity);
    }

    @GET
    @Path("LoginforUsers/{username}/{password}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String LoginforUsers(@PathParam("username") String username, @PathParam("password") String password) {
        try {
            List<User> listUser = super.findAll();
            for (User u : listUser) {
                out.println(u.getUsername());
                System.out.println(u.getUsername());
                if (u.getUsername().equals(username) && u.getPw().equals(password)) {
                    return username;
                }
            }

        } catch (Exception e) {

            return "Error";
        }
        System.out.println("somehow im here");
        return "No";
    }

    @GET
    @Path("LoginforUsersTest")
    public String logIn() {
        return "OK";
    }

    @POST
    @Path("findUserByPK")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String findUserByPK(@FormParam("text") String text) {

        this.userSearchTerm = Integer.parseInt(text);
        for (User p : (List<User>) em.createQuery("SELECT c FROM User c WHERE c.uid LIKE :UID").setParameter("UID", this.userSearchTerm).getResultList()) {
            return "User found matching term " + this.userSearchTerm + " name " + p.getUsername() + " email " + p.getEmail();
        }
        em.getTransaction().commit();
        emf.close();
        return "Nothing was found!";
    }

    ;
    
    /*@DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }*/
    
    //@DELETE
    @POST
    @Path("delete")
    public void remove(@FormParam("id") String id) {
        super.remove(super.find(Integer.parseInt(id)));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public User find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<User> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<User> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public void createTrans() {

        try {
            emf = Persistence.createEntityManagerFactory("ImgShare3PU");
            //Transaction begin
            UserTransaction transaction = (UserTransaction) new InitialContext().lookup("java:comp/UserTransaction");
            transaction.begin();
            em = emf.createEntityManager();
            out.println("Start");
        } catch (Exception e) {
            out.println("Database Exception: " + e.getMessage());
        }
    }

    public void closeTrans() {
        em.getTransaction().commit();
        emf.close();
        out.close();
    }
}
