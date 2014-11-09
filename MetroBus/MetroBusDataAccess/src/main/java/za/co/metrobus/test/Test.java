package za.co.metrobus.test;

import java.util.List;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;
import za.co.metrobus.hibernate.entity.Location;

public class Test {
    public static void main(String... args) {
        
        long startTime = System.currentTimeMillis();
        
        BusServiceDAO data = new BusServiceDAOImpl();
        List<Location> locations = data.getDepartureLocationsFromBusRoute("01");
        
        long endTime = System.currentTimeMillis();
        
        System.out.println("---- OPERATION TIME: " + (endTime - startTime) + " ms.\n");
        
        if (locations != null) {
            for (Location l : locations) {
                System.out.println(" + " + l.toString());
            }
        } 
        
        
    }
}
