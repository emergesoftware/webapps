package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "school")
public class School implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "school_id", nullable = false, unique = true)
    private long id;
    
    @Column(name = "school_name", nullable = false, unique = true)
    private String name;
    
    @OneToOne(targetEntity = ContactDetail.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_detail_id", nullable = false)
    private ContactDetail contactDetail;
    
    @Column(name = "school_area_name", nullable = false)
    private String areaName;
    
    @Column(name = "school_is_public_school", nullable = false)
    private boolean publicSchool;
    
    public School() {
        this.publicSchool = true;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ContactDetail getContactDetail() {
        return contactDetail;
    }

    public void setContactDetail(ContactDetail contactDetail) {
        this.contactDetail = contactDetail;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public boolean isPublicSchool() {
        return publicSchool;
    }

    public void setPublicSchool(boolean publicSchool) {
        this.publicSchool = publicSchool;
    }
    
}
