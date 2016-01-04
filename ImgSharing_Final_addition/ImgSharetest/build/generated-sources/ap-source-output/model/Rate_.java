package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Image;
import model.RatePK;
import model.User;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:13:10")
@StaticMetamodel(Rate.class)
public class Rate_ { 

    public static volatile SingularAttribute<Rate, Image> image;
    public static volatile SingularAttribute<Rate, Integer> grade;
    public static volatile SingularAttribute<Rate, User> user;
    public static volatile SingularAttribute<Rate, RatePK> ratePK;

}