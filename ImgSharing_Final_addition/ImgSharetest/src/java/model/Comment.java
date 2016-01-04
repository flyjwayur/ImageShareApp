/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author iosdev
 */
@Entity
@Table(name = "COMMENT")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Comment.findAll", query = "SELECT c FROM Comment c"),
    @NamedQuery(name = "Comment.findByCoid", query = "SELECT c FROM Comment c WHERE c.coid = :coid"),
    @NamedQuery(name = "Comment.findByText", query = "SELECT c FROM Comment c WHERE c.text = :text"),
    @NamedQuery(name = "Comment.findByCommtime", query = "SELECT c FROM Comment c WHERE c.commtime = :commtime")})
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "COID")
    private Integer coid;
    @Size(max = 30)
    @Column(name = "TEXT")
    private String text;
    @Basic(optional = false)
    @NotNull
    @Column(name = "COMMTIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date commtime;
    @JoinColumn(name = "WRITER", referencedColumnName = "UID")
    @ManyToOne
    private User writer;
    @JoinColumn(name = "IMG", referencedColumnName = "IMGID")
    @ManyToOne
    private Image img;

    public Comment() {
    }

    public Comment(Integer coid) {
        this.coid = coid;
    }

    public Comment(Integer coid, Date commtime) {
        this.coid = coid;
        this.commtime = commtime;
    }

    public Integer getCoid() {
        return coid;
    }

    public void setCoid(Integer coid) {
        this.coid = coid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCommtime() {
        return commtime;
    }

    public void setCommtime(Date commtime) {
        this.commtime = commtime;
    }

    public User getWriter() {
        return writer;
    }

    public void setWriter(User writer) {
        this.writer = writer;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coid != null ? coid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Comment)) {
            return false;
        }
        Comment other = (Comment) object;
        if ((this.coid == null && other.coid != null) || (this.coid != null && !this.coid.equals(other.coid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "model.Comment[ coid=" + coid + " ]";
    }
    
}
