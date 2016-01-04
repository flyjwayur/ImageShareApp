package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Image;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:13:10")
@StaticMetamodel(Category.class)
public class Category_ { 

    public static volatile CollectionAttribute<Category, Image> imageCollection;
    public static volatile SingularAttribute<Category, String> cname;
    public static volatile SingularAttribute<Category, Integer> cid;

}