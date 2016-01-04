/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author iosdev
 */
@Entity
@Table(name = "IMAGE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Image.findAll", query = "SELECT i FROM Image i"),
    @NamedQuery(name = "Image.findByImgid", query = "SELECT i FROM Image i WHERE i.imgid = :imgid"),
    @NamedQuery(name = "Image.findByImgtitle", query = "SELECT i FROM Image i WHERE i.imgtitle = :imgtitle"),
    @NamedQuery(name = "Image.findByImgdesc", query = "SELECT i FROM Image i WHERE i.imgdesc = :imgdesc"),
    @NamedQuery(name = "Image.findByPath", query = "SELECT i FROM Image i WHERE i.path = :path"),
    @NamedQuery(name = "Image.findByUploaddate", query = "SELECT i FROM Image i WHERE i.uploaddate = :uploaddate")})
public class Image implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IMGID")
    private Integer imgid;
    @Size(max = 30)
    @Column(name = "IMGTITLE")
    private String imgtitle;
    @Size(max = 50)
    @Column(name = "IMGDESC")
    private String imgdesc;
    @Size(max = 200)
    @Column(name = "PATH")
    private String path;
    @Column(name = "UPLOADDATE")
    @Temporal(TemporalType.DATE)
    private Date uploaddate;
    @JoinTable(name = "TAGS", joinColumns = {
        @JoinColumn(name = "IMG", referencedColumnName = "IMGID")}, inverseJoinColumns = {
        @JoinColumn(name = "TAG", referencedColumnName = "TID")})
    @ManyToMany
    private Collection<Tag> tagCollection;
    @JoinColumn(name = "OWNER", referencedColumnName = "UID")
    @ManyToOne
    private User owner;
    @JoinColumn(name = "CATEID", referencedColumnName = "CID")
    @ManyToOne
    private Category cateid;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "image")
    private Collection<Rate> rateCollection;
    @OneToMany(mappedBy = "img")
    private Collection<Comment> commentCollection;

    public Image() {
    }

    public Image(Integer imgid) {
        this.imgid = imgid;
    }
   
    public Image(String imgtitle, Date uploaddate, String imgdesc, Category cateid) {
        this.imgtitle = imgtitle;
        this.uploaddate = uploaddate;
        this.imgdesc = imgdesc;
        this.cateid = cateid;
    }

    public Integer getImgid() {
        return imgid;
    }

    public void setImgid(Integer imgid) {
        this.imgid = imgid;
    }

    public String getImgtitle() {
        return imgtitle;
    }

    public void setImgtitle(String imgtitle) {
        this.imgtitle = imgtitle;
    }

    public String getImgdesc() {
        return imgdesc;
    }

    public void setImgdesc(String imgdesc) {
        this.imgdesc = imgdesc;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Date getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(Date uploaddate) {
        this.uploaddate = uploaddate;
    }

    @XmlTransient
    public Collection<Tag> getTagCollection() {
        return tagCollection;
    }

    public void setTagCollection(Collection<Tag> tagCollection) {
        this.tagCollection = tagCollection;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Category getCateid() {
        return cateid;
    }

    public void setCateid(Category cateid) {
        this.cateid = cateid;
    }

    @XmlTransient
    public Collection<Rate> getRateCollection() {
        return rateCollection;
    }

    public void setRateCollection(Collection<Rate> rateCollection) {
        this.rateCollection = rateCollection;
    }

    @XmlTransient
    public Collection<Comment> getCommentCollection() {
        return commentCollection;
    }

    public void setCommentCollection(Collection<Comment> commentCollection) {
        this.commentCollection = commentCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (imgid != null ? imgid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Image)) {
            return false;
        }
        Image other = (Image) object;
        if ((this.imgid == null && other.imgid != null) || (this.imgid != null && !this.imgid.equals(other.imgid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Image[ imgid=" + imgid + " ]";
    }
    
}
