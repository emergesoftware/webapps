/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.emergelets.metrobus.mobisite.component;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusStop;
import za.co.metrobus.hibernate.entity.RouteDescription;

@Component
public class RouteForm implements Serializable {
    
    private List<RouteDescription> routeDescriptions;
    private List<BusStop> routeBusStops;
    private BusRoute route;
    
    public RouteForm() {
        this.routeDescriptions = null;
        this.routeBusStops = null;
        this.route = null;
    }

    public List<RouteDescription> getRouteDescriptions() {
        return routeDescriptions;
    }

    public void setRouteDescriptions(List<RouteDescription> routeDescriptions) {
        this.routeDescriptions = routeDescriptions;
    }

    public List<BusStop> getRouteBusStops() {
        return routeBusStops;
    }

    public void setRouteBusStops(List<BusStop> routeBusStops) {
        this.routeBusStops = routeBusStops;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }
    
    public String generateMarkersForBusStops() {
        
        if (routeBusStops == null || 
                routeBusStops.isEmpty())
            return "{}";
        
        //NumberFormat format = new DecimalFormat("#.#####");
        
        StringBuilder json = new StringBuilder();
        json.append("{\n" +
                "    type: 'FeatureCollection',\n" +
                "    features: [");
        
        int counter = 0;
        
        for (BusStop stop : routeBusStops) {
            counter++;
            
            if (counter == routeBusStops.size()) {
                
                json.append("{\n" +
                    "        type: 'Feature',\n" +
                    "        properties: {\n" +
                    "            title: '" + stop.getFullDescription() + "',\n" +
                    "            'marker-color': '#7ec9b1', \n" +
                    "            'marker-size': 'large'\n" +
                    "        },\n" +
                    "        geometry: {\n" +
                    "            type: 'Point',\n" +
                    "            coordinates: [" + String.valueOf(stop.getGpsLatitude()) + ", " + 
                        String.valueOf(stop.getGpsLongitude()) + "]\n" +
                    "        }\n" +
                    "}\n");
            }
            
            else {
                
                json.append("{\n" +
                    "        type: 'Feature',\n" +
                    "        properties: {\n" +
                    "            title: '" + stop.getFullDescription() + "',\n" +
                    "            'marker-color': '#7ec9b1', \n" +
                    "            'marker-size': 'large'\n" +
                    "        },\n" +
                    "        geometry: {\n" +
                    "            type: 'Point',\n" +
                    "            coordinates: [" + String.valueOf(stop.getGpsLatitude()) + ", " + 
                        String.valueOf(stop.getGpsLongitude()) + "]\n" +
                    "        }\n" +
                    "}, \n");
            }
        }
        
        json.append("]\n" +
                "};");
        
        return json.toString();
    }
    
    public String generateMapMarkerWithoutJson() {
        
        if (routeBusStops == null || routeBusStops.isEmpty()) {
            return "";
        }
        
        StringBuilder code = new StringBuilder();
        
        for (BusStop stop : routeBusStops) {
            
            code.append("L.marker([" + stop.getGpsLatitude() + ", " + stop.getGpsLongitude() +"], {\n" +
                        "    icon: L.mapbox.marker.icon({\n" +
                        "         title : '" + stop.getShortDescription() + "',\n" +
                        "        'marker-size': 'large',\n" +
                        "        'marker-symbol': 'bus',\n" +
                        "        'marker-color': '#fa0'\n" +
                        "    })\n" +
                        "}).addTo(map); \n");
            
        }
         return code.toString();
        
        
    }
    
}
