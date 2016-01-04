/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author iosdev
 */
@Embeddable
public class RatePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "RATER")
    private int rater;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IMG")
    private int img;

    public RatePK() {
    }

    public RatePK(int rater, int img) {
        this.rater = rater;
        this.img = img;
    }

    public int getRater() {
        return rater;
    }

    public void setRater(int rater) {
        this.rater = rater;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) rater;
        hash += (int) img;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RatePK)) {
            return false;
        }
        RatePK other = (RatePK) object;
        if (this.rater != other.rater) {
            return false;
        }
        if (this.img != other.img) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.RatePK[ rater=" + rater + ", img=" + img + " ]";
    }
    
}
