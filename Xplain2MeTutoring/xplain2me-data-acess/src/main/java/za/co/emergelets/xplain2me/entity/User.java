package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "users")
public class User implements Serializable {
    
    /*
    
    Table: users
    
    Columns:
        user_name varchar(24) not null unique,
        user_password text not null,
        user_active boolean not null default false,
        user_added timstamp without time zone not null default now(),
        user_deactivated timestamp without time zone,
        user_role_id int not null
    */
    
    @Id
    @Column(name = "user_name", nullable = false, unique = true)
    private String username;
    
    @Column(name = "user_password", nullable = false)
    private String password;
    
    @Column(name = "user_active", nullable = false)
    private boolean active;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_added", nullable = false)
    private Date added;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "user_deactivated")
    private Date deactivated;
    
    @OneToOne(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole role;
    
    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getAdded() {
        return added;
    }

    public void setAdded(Date added) {
        this.added = added;
    }

    public Date getDeactivated() {
        return deactivated;
    }

    public void setDeactivated(Date deactivated) {
        this.deactivated = deactivated;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
    
    
}
