package model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Image;
import model.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:13:10")
@StaticMetamodel(Comment.class)
public class Comment_ { 

    public static volatile SingularAttribute<Comment, Image> img;
    public static volatile SingularAttribute<Comment, Date> commtime;
    public static volatile SingularAttribute<Comment, Integer> coid;
    public static volatile SingularAttribute<Comment, String> text;
    public static volatile SingularAttribute<Comment, User> writer;

}