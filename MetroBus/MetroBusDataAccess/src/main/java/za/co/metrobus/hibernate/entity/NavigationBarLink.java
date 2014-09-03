/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.metrobus.hibernate.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = NavigationBarLink.TABLE_NAME)
public class NavigationBarLink implements Serializable {
    
    // the table name
    public static final String TABLE_NAME = "navigation_bar_link";
    // the columns
    public static final String NAVIGATION_LINK_ID = "navigation_bar_link_id";
    public static final String NAVIGATION_LINK_TEXT = "navigation_bar_link_text";
    public static final String NAVIGATION_LINK_DESC = "navigation_bar_link_desc";
    public static final String NAVIGATION_LINK_HREF = "navigation_bar_link_href";
    public static final String NAVIGATION_LINK_GLYPHICON_CSS = "navigation_bar_link_glyphicon_css";
    public static final String NAVIGATION_LINK_ACTIVE = "navigation_bar_link_active"; 
    
    @Id
    @Column(name = NAVIGATION_LINK_ID, unique = true)
    private int navigationLinkId;
    
    @Column(name = NAVIGATION_LINK_TEXT, unique = true, nullable = false)
    private String text;
    
    @Column(name = NAVIGATION_LINK_DESC)
    private String description;
    
    @Column(name = NAVIGATION_LINK_HREF, nullable = false)
    private String href;
    
    @Column(name = NAVIGATION_LINK_GLYPHICON_CSS)
    private String glyphiconCssName;
    
    @Column(name = NAVIGATION_LINK_ACTIVE, nullable = false)
    private boolean active;
    
    public NavigationBarLink() {
        this.active = true;
    }

    public int getNavigationLinkId() {
        return navigationLinkId;
    }

    public void setNavigationLinkId(int navigationLinkId) {
        this.navigationLinkId = navigationLinkId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getGlyphiconCssName() {
        return glyphiconCssName;
    }

    public void setGlyphiconCssName(String glyphiconCssName) {
        this.glyphiconCssName = glyphiconCssName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "NavigationBarLink{" + "navigationLinkId=" + navigationLinkId + ", text=" + text + ", description=" + description + ", href=" + href + ", glyphiconCssName=" + glyphiconCssName + ", active=" + active + '}';
    }
    
    
}
