package za.co.xplain2me.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "menu_category")
public class MenuCategory implements Serializable {
    
    @Id
    @Column(name = "menu_category_id", unique = true, nullable = false)
    private long id;
    
    @Column(name = "menu_category_desc", nullable = false, unique = true)
    private String description;
    
    @Column(name = "menu_category_glyphicon_css", nullable = false)
    private String glyphiconCssClass;
    
    @Column(name = "menu_category_load_onto_panel", nullable = false)
    private boolean loadOntoPanel;
  
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

    public String getGlyphiconCssClass() {
        return glyphiconCssClass;
    }

    public void setGlyphiconCssClass(String glyphiconCssClass) {
        this.glyphiconCssClass = glyphiconCssClass;
    }

    public boolean isLoadOntoPanel() {
        return loadOntoPanel;
    }

    public void setLoadOntoPanel(boolean loadOntoPanel) {
        this.loadOntoPanel = loadOntoPanel;
    }
    
}
