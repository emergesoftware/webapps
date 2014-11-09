package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole implements Serializable {
    
    /*
    Table: user_role
    
    Columns:
        user_role_id int not null unique,
        user_role_desc varchar(64) not null unique,
        user_role_active boolean not null default true,
        user_role_privilege_level int not null default 0,
        user_role_allowed_login boolean not null default true
    
    */
    
    @Id
    @Column(name = "user_role_id", nullable = false, unique = true)
    private long id;
    
    @Column(name = "user_role_desc", nullable = false, unique = true)
    private String description;
    
    @Column(name = "user_role_active", nullable = false)
    private boolean active;
    
    @Column(name = "user_role_privilege_level", nullable = false)
    private int privilegeLevel;
    
    @Column(name = "user_role_allowed_login", nullable = false)
    private boolean allowedLogin;
    
    public UserRole() {
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

    public int getPrivilegeLevel() {
        return privilegeLevel;
    }

    public void setPrivilegeLevel(int privilegeLevel) {
        this.privilegeLevel = privilegeLevel;
    }

    public boolean isAllowedLogin() {
        return allowedLogin;
    }

    public void setAllowedLogin(boolean allowedLogin) {
        this.allowedLogin = allowedLogin;
    }
}
