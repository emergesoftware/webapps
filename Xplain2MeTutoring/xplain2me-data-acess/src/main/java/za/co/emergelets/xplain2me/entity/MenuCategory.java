package za.co.emergelets.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu_category")
public class MenuCategory implements Serializable {
    /*
    
    Table: menu_category
    
    Columns:
        menu_category_id int not null unique,
        menu_category_desc text not null unique,
        menu_category_active boolean not null default true,
        menu_category_icon_url text
    
    */
    
    @Id
    @Column(name = "menu_category_id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "menu_category_desc", nullable = false, unique = true)
    private String description;
    
    @Column(name = "menu_category_active", nullable = false)
    private boolean active;
    
    @Column(name = "menu_category_icon_url")
    private String iconUrl;
    
    public MenuCategory() {
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

    public String getIconUrl() {
        return iconUrl;
    }

    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    
    
}
