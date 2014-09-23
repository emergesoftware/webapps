/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emergelets.metrobus.mobisite.component;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.metrobus.hibernate.entity.BusStop;

@Component
public class SearchForm implements Serializable {
    
    // stores the errors encountered
    private String errors;
    
    // the results list
    private List<BusStop> busStops;
    
    // the search query
    private String searchQuery;
    
    public SearchForm() {
        errors = null;
    }
    
    public String getErrors() {
        return errors;
    }
    
    public void setErrors(String errors) {
        this.errors = errors;
    }

    public List<BusStop> getBusStops() {
        return busStops;
    }

    public void setBusStops(List<BusStop> busStops) {
        this.busStops = busStops;
    }

    public String getSearchQuery() {
        return searchQuery;
    }

    public void setSearchQuery(String searchQuery) {
        this.searchQuery = searchQuery;
    }
    
    
    
}
