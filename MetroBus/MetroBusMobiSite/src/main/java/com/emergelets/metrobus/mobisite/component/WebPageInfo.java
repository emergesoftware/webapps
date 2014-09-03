package com.emergelets.metrobus.mobisite.component;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class WebPageInfo implements Serializable {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getTabIndex() {
        return tabIndex;
    }

    public void setTabIndex(int tabIndex) {
        this.tabIndex = tabIndex;
    }
    
    private String title;
    private String description;
    private int pageIndex;
    private int tabIndex;
    
    public WebPageInfo() {
        this.title = "MetroBus App";
        this.description = null;
        this.pageIndex = 0;
        this.tabIndex = 0;
    }
    
    
}
