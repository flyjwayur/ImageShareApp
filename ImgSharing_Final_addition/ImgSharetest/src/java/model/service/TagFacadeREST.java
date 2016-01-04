/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.service;

import static java.lang.Integer.parseInt;
import static java.lang.System.out;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.ws.rs.core.Response;
import model.Image;
import model.Tag;
import model.User;

/**
 *
 * @author iosdev
 */
@Stateless
@Path("model.tag")
public class TagFacadeREST extends AbstractFacade<Tag> {

    @PersistenceContext(unitName = "ImgShare3PU")
    private EntityManager em;

    public TagFacadeREST() {
        super(Tag.class);
    }

    @POST
    @Override
    @Consumes({"application/xml", "application/json"})
    public void create(Tag entity) {
        super.create(entity);
    }

    @PUT
    @Path("{id}")
    @Consumes({"application/xml", "application/json"})
    public void edit(@PathParam("id") Integer id, Tag entity) {
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
    public Tag find(@PathParam("id") Integer id) {
        return super.find(id);
    }

    @GET
    @Override
    @Produces({"application/xml", "application/json"})
    public List<Tag> findAll() {
        return super.findAll();
    }

    @GET
    @Path("{from}/{to}")
    @Produces({"application/xml", "application/json"})
    public List<Tag> findRange(@PathParam("from") Integer from, @PathParam("to") Integer to) {
        return super.findRange(new int[]{from, to});
    }

    @GET
    @Path("SearchByTag/{imgbyTag}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces({"application/xml", "application/json"})
    public List<Image> SearchByTag(@PathParam("imgbyTag") String imgbyTag) {
        ArrayList<Image> resultList = new ArrayList<Image>();
        List<Tag> tags = super.findTagByName(imgbyTag);
        List<Image> allImages = em.createNamedQuery("Image.findAll").getResultList();
        for (Tag t : tags) {
            for (Image img : allImages) {
                if (img.getTagCollection().contains(t)) {
                    resultList.add(img);
                }
            }
        }
        return resultList;
    }

    ;

    @GET
    @Path("count")
    @Produces("text/plain")
    public String countREST() {
        return String.valueOf(super.count());
    }
    /*
     @POST
     @Path("images")
     @Produces({"application/xml", "application/json"})
     public List<Image> getImagesFromTag(@FormParam("imgs") String tagID){
     Tag t = super.findTagByName(tagID);
     return new ArrayList<>(super.find(t.getTid()).getImageCollection());
     }
     */
    
    @GET
    @Path("addTag/{tname}/{hiddenImgID}")
    //@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Produces({"application/xml", "application/json"})
    public String attachTagToimg(@PathParam("tname") String tname, @PathParam("hiddenImgID") String imgID) {
        //URI loc;
        List<Tag> listTags = super.findAll();
        int imageID = Integer.parseInt(imgID);
        try {
            for (Tag t : listTags) {
                if (t.getTname().equals(tname)) {
                    for (Image i : t.getImageCollection()) {
                        if (imageID == i.getImgid()) {
                            //loc = new URI("../thumbnailGallery.jsp?reply=The+tag+already+exists(" + tname + ")");
                            return "TAGEXIST";
                        }
                    }
                }
            }
            
            ArrayList<Image> listImage = new ArrayList<Image>();
            listImage.add(em.find(Image.class, parseInt(imgID)));
            ArrayList<Tag> listTag = new ArrayList<Tag>();
            // Adding to image tags that was already there
            for (Image i : listImage) {
                for (Tag t : i.getTagCollection()) {
                    listTag.add(t);
                }
            }
            // adding to image the newly created tag
            Tag tag = new Tag();
            tag.setTname(tname);
            listTag.add(tag);
            tag.setImageCollection(listImage);
            for (Image img : listImage) {
                img.setTagCollection(listTag);
            }
            super.create(tag);
            //loc = new URI("../thumbnailGallery.jsp?reply=The+tag+was+added(" + tname + ")");
            return "TAGADDED";

        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("somehow im here");
        return "HERE";
    }
    /*
    @POST
    @Path("addTag")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    //@Produces({"application/xml", "application/json"})
    public Response attachTagToimg(@FormParam("tname") String tname, @FormParam("hiddenImgID") String imgID) {
        URI loc;
        List<Tag> listTags = super.findAll();
        int imageID = Integer.parseInt(imgID);
        try {
            for (Tag t : listTags) {
                if (t.getTname().equals(tname)) {
                    for (Image i : t.getImageCollection()) {
                        if (imageID == i.getImgid()) {
                            loc = new URI("../thumbnailGallery.jsp?reply=The+tag+already+exists(" + tname + ")");
                            return Response.temporaryRedirect(loc).build();
                        }
                    }
                }
            }
            
            ArrayList<Image> listImage = new ArrayList<Image>();
            listImage.add(em.find(Image.class, parseInt(imgID)));
            ArrayList<Tag> listTag = new ArrayList<Tag>();
            // Adding to image tags that was already there
            for (Image i : listImage) {
                for (Tag t : i.getTagCollection()) {
                    listTag.add(t);
                }
            }
            // adding to image the newly created tag
            Tag tag = new Tag();
            tag.setTname(tname);
            listTag.add(tag);
            tag.setImageCollection(listImage);
            for (Image img : listImage) {
                img.setTagCollection(listTag);
            }
            super.create(tag);
            loc = new URI("../thumbnailGallery.jsp?reply=The+tag+was+added(" + tname + ")");
            return Response.temporaryRedirect(loc).build();

        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("somehow im here");
        return Response.noContent().build();
    }
    */

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }
}
