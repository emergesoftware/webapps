/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.metrobus.test;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;
import za.co.metrobus.hibernate.entity.BusArrival;
import za.co.metrobus.hibernate.entity.BusDeparture;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.Location;

/**
 *
 * @author user
 */
public class InsertBusTimeTableEntry {
    
    
    private static BusServiceDAO data = null;
    private static String routeNumber = null;
    
    public static void main(String... args) {
        
        Scanner input = new Scanner(System.in);
        
        data = new BusServiceDAOImpl();
        
        BusRoute route = null;
        
        print("\n\n\n");
        
        print("----------------------------------------");
        print("   Insert New Bus Time Table Entries    ");
        print("----------------------------------------\n");
        
        // read the route number from the user
        print("Enter the bus route number: ");
        routeNumber = input.nextLine().trim();
        
        // get the bus route from the db
        route = data.getBusRouteByRouteNumber(routeNumber);
        if (route == null) {
            print("The bus route number " + routeNumber + " could not be found.");
            System.exit(1);
        }
        
        BusDeparture departure = null;
        BusArrival arrival = null;
        
        // enter a loop
        while (true) {
            
            print("\n");
            
            departure = new BusDeparture();
            
            // set the bus route
            departure.setRoute(route);
            
            // prompt for the location from which the bus is from
            String locationId;
            print("Enter the location ID from which the bus departs:");
            locationId = input.nextLine().trim();
            
            // try to get the departure location 
            Location from = data.getLocationById(Integer.parseInt(locationId));
            if (from == null) {
                print("\nCould not find the location with ID: " + locationId 
                        + "\nResetting process");
                continue;
            }
            
            else {
                print("\nFound: " + from.getFullName() + "\n");
                departure.setDepartureLocation(from);
            }
            
            // set the time the bus leaves
            print("Enter the time the bus leaves at " + from.getShortName() + " (HH:MM):");
            String timeString = input.nextLine().trim();
            
            if (!timeString.contains(":")) {
                print("Invalid format - use HH:MM - resetting process.");
                continue;
            }
            
            else {
                
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR, Integer.parseInt(timeString.split(":")[0]));
                calendar.set(Calendar.MINUTE, Integer.parseInt(timeString.split(":")[1]));
                calendar.set(Calendar.SECOND, 0);
                calendar.set(Calendar.MILLISECOND, 0);
                
                departure.setDepartureTime(calendar.getTime());
                 
            }
            
            // set if this service is a weekday, sunday or saturday service
            int serviceOption = 0;
            print("\nIs this a: \n\n" + 
                  " 1 - Weekday Service\n" + 
                  " 2 - Saturday Service\n" + 
                  " 3 - Sunday Service\n\n" + 
                  "Enter option:");
            serviceOption = Integer.parseInt(input.nextLine().trim());
            
            switch (serviceOption) {
                case 1:
                    departure.setWeekdayService(true);
                    departure.setSaturdayService(false);
                    departure.setSundayService(false);
                    break;
                    
                case 2:
                    departure.setWeekdayService(false);
                    departure.setSaturdayService(true);
                    departure.setSundayService(false);
                    break;
                    
                case 3:
                    departure.setWeekdayService(false);
                    departure.setSaturdayService(false);
                    departure.setSundayService(true);
                    break;
                    
                default: 
                    departure.setWeekdayService(true);
                    departure.setSaturdayService(false);
                    departure.setSundayService(false);
            }
            
            print("\n ---- working, please wait -----\n\n");
            departure = data.persistNewBusDeparture(departure);
            
            print("[Done]\n");
            
            int sequenceCount = 0;
            
            while (true) {
                
                // prepare the sequence count
                sequenceCount++;
                arrival = new BusArrival();
                
                // link to this bus departure
                arrival.setBusDeparture(departure);
                arrival.setArrivalSequence(sequenceCount);
                
                // get the destination location
                print("Entering destinations / via locations.");
                print("Enter the location ID of destination " + (sequenceCount) + ":");
                locationId = input.nextLine().trim();
                
                Location to = data.getLocationById(Integer.parseInt(locationId));
                if (to == null) {
                    print("\nCould not find the location with ID: " + locationId 
                            + "\nResetting process.");
                    sequenceCount--;
                    continue;
                }

                else {
                    print("\nFound: " + to.getFullName() + "\n");
                    arrival.setArrivalLocation(to);
                }
                
                // set the destination as a turn around or via:
                int destinationOption = 0;
                print("\nIs this destination, " + to.getShortName() + " a:\n" + 
                        " 1 - VIA location \n" + 
                        " 2 - TO/FROM location\n\n" + 
                        "Enter option:");
                destinationOption = Integer.parseInt(input.nextLine().trim());
                
                switch (destinationOption) {
                    
                    case 1: 
                        arrival.setViaLocation(true);
                        arrival.setTurnAroundLocation(false);
                        break;
                        
                    default:
                        arrival.setViaLocation(false);
                        arrival.setTurnAroundLocation(true);
                    
                }
                
                // persist this to the database
                arrival = data.persistNewBusArrival(arrival);
                
                if (arrival != null) {
                    print("Arrival entry persisted.\n\n"
                    + "Add another destination entry to this departure? (y/n):");
                    
                    if (input.nextLine().trim().equalsIgnoreCase("n"))
                        break;
                    
                }
                
            }
            
            print("\n\n--> Insert another departure to this bus route? (y/n)");
             if (input.nextLine().trim().equalsIgnoreCase("n"))
                        break;
            
        }
        
    }
    
    private static void print(Object content) {
        if (content == null)
            System.out.println();
        else
            System.out.println(content.toString());
    }
    
}
