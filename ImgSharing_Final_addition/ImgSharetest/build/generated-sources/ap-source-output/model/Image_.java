package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Category;
import model.Comment;
import model.Rate;
import model.Tag;
import model.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:13:10")
@StaticMetamodel(Image.class)
public class Image_ { 

    public static volatile SingularAttribute<Image, User> owner;
    public static volatile SingularAttribute<Image, String> path;
    public static volatile CollectionAttribute<Image, Tag> tagCollection;
    public static volatile SingularAttribute<Image, Integer> imgid;
    public static volatile CollectionAttribute<Image, Rate> rateCollection;
    public static volatile SingularAttribute<Image, Date> uploaddate;
    public static volatile SingularAttribute<Image, Category> cateid;
    public static volatile CollectionAttribute<Image, Comment> commentCollection;
    public static volatile SingularAttribute<Image, String> imgtitle;
    public static volatile SingularAttribute<Image, String> imgdesc;

}