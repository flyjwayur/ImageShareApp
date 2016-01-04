/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import static java.lang.System.out;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
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
import javax.ws.rs.core.PathSegment;
import model.Rate;
import model.RatePK;

/**
 *
 * @author iosdev
 */
@Stateless
@Path("model.rate")
public class RateFacadeREST extends AbstractFacade<Rate> {
    @PersistenceContext(unitName = "ImgShare3PU")
    private EntityManager em;
    private EntityManagerFactory emf;


    private RatePK getPrimaryKey(PathSegment pathSegment) {
        /*
         * pathSemgent represents a URI path segment and any associated matrix parameters.
         * URI path part is supposed to be in form of 'somePath;rater=raterValue;img=imgValue'.
         * Here 'somePath' is a result of getPath() method invocation and
         * it is ignored in the following code.
         * Matrix parameters are used as field names to build a primary key instance.
         */
        model.RatePK key = new model.RatePK();
        javax.ws.rs.core.MultivaluedMap<String, String> map = pathSegment.getMatrixParameters();
        java.util.List<String> rater = map.get("rater");
        if (rater != null && !rater.isEmpty()) {
            key.setRater(new java.lang.Integer(rater.get(0)));
        }
        java.util.List<String> img = map.get("img");
        if (img != null && !img.isEmpty()) {
            key.setImg(new java.lang.Integer(img.get(0)));
        }
        return key;
    }

    public RateFacadeREST() {
        super(Rate.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Rate entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") PathSegment id, Rate entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") PathSegment id) {
        model.RatePK key = getPrimaryKey(id);
        super.remove(super.find(key));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Rate find(@PathParam("id") PathSegment id) {
        model.RatePK key = getPrimaryKey(id);
        return super.find(key);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Rate> findAll() {
        return super.findAll();
    }
    
    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Rate> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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
