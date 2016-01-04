package model;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import model.Image;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2015-12-13T12:13:10")
@StaticMetamodel(Tag.class)
public class Tag_ { 

    public static volatile CollectionAttribute<Tag, Image> imageCollection;
    public static volatile SingularAttribute<Tag, String> tname;
    public static volatile SingularAttribute<Tag, Integer> tid;

}