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
import javax.persistence.Table;

@Entity
@Table(name = "menu_item")
public class MenuItem implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "menu_item_id", nullable = false, unique = true)
    private long id;
    
    @Column(name = "menu_item_desc", nullable = false)
    private String description;
    
    @Column(name = "menu_item_title", nullable = false)
    private String title;
    
    @Column(name = "menu_item_relative_url", nullable = false, unique = true)
    private String relativeUrl;
    
    @Column(name = "menu_item_opens_on_new_window", nullable = false)
    private boolean opensOnNewWindow;
    
    @Column(name = "menu_item_load_onto_panel", nullable = false)
    private boolean loadOntoPanel;
    
    @OneToOne(targetEntity = MenuCategory.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_category_id", nullable = false)
    private MenuCategory category;
    
    public MenuItem() {
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRelativeUrl() {
        return relativeUrl;
    }

    public void setRelativeUrl(String relativeUrl) {
        this.relativeUrl = relativeUrl;
    }

    public boolean isOpensOnNewWindow() {
        return opensOnNewWindow;
    }

    public void setOpensOnNewWindow(boolean opensOnNewWindow) {
        this.opensOnNewWindow = opensOnNewWindow;
    }

    public boolean isLoadOntoPanel() {
        return loadOntoPanel;
    }

    public void setLoadOntoPanel(boolean loadOntoPanel) {
        this.loadOntoPanel = loadOntoPanel;
    }

    public MenuCategory getCategory() {
        return category;
    }

    public void setCategory(MenuCategory category) {
        this.category = category;
    }
    
    
    
}
