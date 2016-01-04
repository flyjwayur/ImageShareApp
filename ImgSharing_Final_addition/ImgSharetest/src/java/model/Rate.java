/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author iosdev
 */
@Entity
@Table(name = "RATE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rate.findAll", query = "SELECT r FROM Rate r"),
    @NamedQuery(name = "Rate.findByRater", query = "SELECT r FROM Rate r WHERE r.ratePK.rater = :rater"),
    @NamedQuery(name = "Rate.findByImg", query = "SELECT r FROM Rate r WHERE r.ratePK.img = :img"),
    @NamedQuery(name = "Rate.findByGrade", query = "SELECT r FROM Rate r WHERE r.grade = :grade")})
public class Rate implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RatePK ratePK;
    @Column(name = "GRADE")
    private Integer grade;
    @JoinColumn(name = "RATER", referencedColumnName = "UID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;
    @JoinColumn(name = "IMG", referencedColumnName = "IMGID", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Image image;

    public Rate() {
    }

    public Rate(RatePK ratePK) {
        this.ratePK = ratePK;
    }

    public Rate(int rater, int img) {
        this.ratePK = new RatePK(rater, img);
    }

    public RatePK getRatePK() {
        return ratePK;
    }

    public void setRatePK(RatePK ratePK) {
        this.ratePK = ratePK;
    }

    public Integer getGrade() {
        return grade;
    }
    
    public Integer getGrade(Integer rate) {
        return rate;
    }
    

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ratePK != null ? ratePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Rate)) {
            return false;
        }
        Rate other = (Rate) object;
        if ((this.ratePK == null && other.ratePK != null) || (this.ratePK != null && !this.ratePK.equals(other.ratePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Rate[ ratePK=" + ratePK + " ]";
    }
    
}
