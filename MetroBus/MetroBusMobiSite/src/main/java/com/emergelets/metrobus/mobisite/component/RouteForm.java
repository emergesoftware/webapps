/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emergelets.metrobus.mobisite.component;

import java.io.Serializable;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.metrobus.hibernate.entity.RouteDescription;

@Component
public class RouteForm implements Serializable {
    
    private List<RouteDescription> routeDescriptions;
    
    public RouteForm() {
        this.routeDescriptions = null;
    }

    public List<RouteDescription> getRouteDescriptions() {
        return routeDescriptions;
    }

    public void setRouteDescriptions(List<RouteDescription> routeDescriptions) {
        this.routeDescriptions = routeDescriptions;
    }
    
    
    
}
