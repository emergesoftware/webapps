package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "profile_type")
public class ProfileType implements Serializable {
    
    public static final int DEFAULT_PROFILE = 100;
    public static final int APP_MANAGER_PROFILE = 101;
    public static final int TUTOR_PROFILE = 102;
    public static final int STUDENT_PROFILE = 103;
    
    @Id
    @Column(name = "profile_type_id", nullable = false, unique = true)
    private long id;
    
    @Column(name = "profile_type_desc", nullable = false, unique = true)
    private String description;
    
    @Column(name = "profile_type_active", nullable = false)
    private boolean active;
    
    public ProfileType() {
    
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProfileType other = (ProfileType) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
}

