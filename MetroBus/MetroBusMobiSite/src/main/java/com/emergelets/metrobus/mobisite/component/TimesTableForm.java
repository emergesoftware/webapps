package com.emergelets.metrobus.mobisite.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import org.springframework.stereotype.Component;
import za.co.metrobus.hibernate.entity.BusDeparture;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusServiceType;
import za.co.metrobus.hibernate.entity.Location;

@Component
public class TimesTableForm implements Serializable {
    
    private BusRoute route;
    private List<BusDeparture> busDepartures;
    private List<Location> locations;
    private String nextDepartureAlert;
    private int secondsBeforeNextDeparture;
    private int searchLocationId;
    private boolean isTodaysServiceSearched;
    private BusServiceType serviceType;
    
    private List<BusRoute> routes;

    public TimesTableForm() {

        this.busDepartures = new ArrayList<BusDeparture>();
        this.locations = new ArrayList<Location>();
        this.nextDepartureAlert = "No more buses running today.";
        this.secondsBeforeNextDeparture = 0;
        this.isTodaysServiceSearched = true;
        this.searchLocationId = 0;
        this.routes = new ArrayList<BusRoute>();
    }
    
    /**
     * Eliminates the duplicated entries
     * 
     * @param busDepartures
     * @return 
     */
    private List<BusDeparture> eliminateDuplicateTimeTableEntries(List<BusDeparture> busDepartures) {
        
        if (busDepartures == null)
            busDepartures = new ArrayList<BusDeparture>();
        
        if (busDepartures.isEmpty()) {
            return busDepartures;
        }
        
        else {
        
            SortedMap<Integer, BusDeparture> map = new TreeMap<Integer, BusDeparture>();
            List<BusDeparture> sortedList = new ArrayList<BusDeparture>();

            for (BusDeparture departure : busDepartures) {

                Integer key = departure.getBusDepartureId();

                if (!map.containsKey(key)) {
                    map.put(key, departure);
                }
            }

            TreeSet<Integer> set = new TreeSet<Integer>(map.keySet());

            for (Integer key : set) {
                sortedList.add(map.get(key));
            }

            return sortedList;
        }
    }

    public BusServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(BusServiceType serviceType) {
        this.serviceType = serviceType;
    }
    
    public List<BusDeparture> getBusDepartures() {

        return busDepartures;
    }

    public void setBusDepartures(List<BusDeparture> busDepartures) {
        
        this.busDepartures = 
                eliminateDuplicateTimeTableEntries(busDepartures);
         
    }

    public List<Location> getLocations() {
        return locations;
    }

    public void setLocations(List<Location> locations) {
        this.locations = locations;
    }

    public String getNextDepartureAlert() {
        return nextDepartureAlert;
    }

    public void setNextDepartureAlert(String nextDepartureAlert) {
        this.nextDepartureAlert = nextDepartureAlert;
    }

    public int getSecondsBeforeNextDeparture() {
        return secondsBeforeNextDeparture;
    }

    public void setSecondsBeforeNextDeparture(int minutesBeforeNextDeparture) {
        this.secondsBeforeNextDeparture = minutesBeforeNextDeparture;
    }

    public int getSearchLocationId() {
        return searchLocationId;
    }

    public void setSearchLocationId(int searchLocationId) {
        this.searchLocationId = searchLocationId;
    }

    public boolean isTodaysServiceSearched() {
        return isTodaysServiceSearched;
    }

    public void setIsTodaysServiceSearched(boolean isTodaysServiceSearched) {
        this.isTodaysServiceSearched = isTodaysServiceSearched;
    }

    public List<BusRoute> getRoutes() {
        return routes;
    }

    public void setRoutes(List<BusRoute> routes) {
        this.routes = routes;
    }

    public BusRoute getRoute() {
        return route;
    }

    public void setRoute(BusRoute route) {
        this.route = route;
    }
    
    
}
