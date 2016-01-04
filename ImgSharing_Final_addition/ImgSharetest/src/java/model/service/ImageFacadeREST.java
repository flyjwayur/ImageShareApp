/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
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
import model.Image;
import model.Rate;
import model.Tag;
import model.User;

/**
 *
 * @author iosdev
 */
@Stateless
@Path("model.image")
public class ImageFacadeREST extends AbstractFacade<Image> {

    @PersistenceContext(unitName = "ImgShare3PU")
    EntityManager em;
    EntityManagerFactory emf;
    private Integer imgSearchTerm = 0;
    private String username;
    private Integer uid;
    private Integer imgID;
    private Integer rate;
    private String uRating;

    public ImageFacadeREST() {
        super(Image.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Image entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Image entity) {
        super.edit(entity);
    }

    @DELETE
    @Path("{id}")
    public void remove(@PathParam("id") Integer id) {
        super.remove(super.find(id));
    }

    @GET
    @Path("{id}")
    @Produces({"application/xml", "application/json"})
    public Image find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Image> findAll() {
        return super.findAll();
    }

    //rate image
    @GET
    @Path("rateImg/{imgId}/{rate}/{username}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String rateImage(@PathParam("imgId") String imgId0, @PathParam("rate") String rate0, @PathParam("username") String username0) {
        // changing the search queries to integers
        imgID = Integer.parseInt(imgId0);
        rate = Integer.parseInt(rate0);

        // get the list of all the user
        Class<User> entityClass = User.class;
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<User> users = getEntityManager().createQuery(cq).getResultList();

        //find the user with that name
        User user = new User();
        for (User u : users) {
            if (u.getUsername().equals(username0)) {
                user = u;
            }
        }

        // getting all ratings
        Rate newRate = new Rate(user.getUid(), imgID);
        newRate.setGrade(rate);
        Image img = super.findImgByID(imgID);

        List<Rate> rateList = findRateByImg(img);
        // check if the user liked the image already
        for (Rate r : rateList) {
            if (newRate.getRatePK().getRater() == r.getRatePK().getRater()
                    && newRate.getRatePK().getImg() == r.getRatePK().getImg()) {
                if (r.getGrade() == 1) {
                    return "You liked this picture already.";
                } else if (r.getGrade() == -1) {
                    return "you disliked this picture already.";
                }
            }
        }
        rateList.add(newRate);
        img.setRateCollection(rateList);

        return "Yes";
    }

    //rate image by like
    @GET
    @Path("countLikes/{imgId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String countLikes(@PathParam("imgId") String imgId0) {
        imgID = Integer.parseInt(imgId0);
        // get the list of all the Rate
        Class<Rate> entityClass = Rate.class;
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<Rate> rates = getEntityManager().createQuery(cq).getResultList();
        //count likes
        int likes = 0;
        for (Rate r : rates) {
            if (r.getRatePK().getImg() == imgID) {
                if (r.getGrade() == 1) {
                    likes += 1;
                }
            }
        }
        return Integer.toString(likes);
    }

    //rate image by dislike
    @GET
    @Path("countDislikes/{imgId}")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String countDislikes(@PathParam("imgId") String imgId0) {
        imgID = Integer.parseInt(imgId0);
        // get the list of all the Rate
        Class<Rate> entityClass = Rate.class;
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        List<Rate> rates = getEntityManager().createQuery(cq).getResultList();
        //count dislikes
        int dislikes = 0;
        for (Rate r : rates) {
            if (r.getRatePK().getImg() == imgID) {
                if (r.getGrade() == -1) {
                    dislikes += 1;
                }
            }
        }
        return Integer.toString(dislikes);
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Image> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
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

}
