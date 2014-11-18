package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "user_role_permissions")
public class UserRolePermission implements Serializable {
    
    /*
    
    Table: user_role_permissions
    
    Columns:
        user_role_permissions_id int not null unique default nextval('user_role_permissions_id_sequence'),
        user_role_permissions_desc text not null,
        user_role_id int not null,
        event_type int not null
    */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_permissions_id")
    private long id;
    
    @Column(name = "user_role_permissions_desc", nullable = false)
    private String description;
    
    @OneToOne(targetEntity = UserRole.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_role_id", nullable = false)
    private UserRole role;
    
    @OneToOne(targetEntity = Event.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_type", nullable = false)
    private Event event;
    
    public UserRolePermission() {
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
    
    
}

