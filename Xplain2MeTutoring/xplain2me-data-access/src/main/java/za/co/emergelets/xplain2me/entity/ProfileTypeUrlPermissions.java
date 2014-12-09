package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "profile_type_url_permissions")
public class ProfileTypeUrlPermissions implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "profile_type_url_permissions_id")
    private long id;
    
    @Column(name = "profile_type_url_permissions_desc", nullable = false)
    private String description;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "profile_type_url_permissions_date_modified")
    private Date dateModified;
    
    @OneToOne(targetEntity = ProfileType.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_type_id", nullable = false)
    private ProfileType profileType;
    
    @OneToOne(targetEntity = MenuItem.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
    
    public ProfileTypeUrlPermissions() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public ProfileType getProfileType() {
        return profileType;
    }

    public void setProfileType(ProfileType profileType) {
        this.profileType = profileType;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }
    
}
