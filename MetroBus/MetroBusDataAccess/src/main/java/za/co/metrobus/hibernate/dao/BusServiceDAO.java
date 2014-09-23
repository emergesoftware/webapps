package za.co.metrobus.hibernate.dao;

import java.util.Date;
import java.util.List;
import za.co.metrobus.hibernate.entity.BusArrival;
import za.co.metrobus.hibernate.entity.BusDeparture;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusServiceType;
import za.co.metrobus.hibernate.entity.BusStop;
import za.co.metrobus.hibernate.entity.BusZone;
import za.co.metrobus.hibernate.entity.Location;
import za.co.metrobus.hibernate.entity.RouteDescription;

/**
 *
 * @author Tsepo Maleka
 */
public interface BusServiceDAO {
    
    /**
     * Gets a list of locations that
     * contain the name specified / pattern /
     * regular expression
     * 
     * @param name
     * @return
     * @throws DataAccessException 
     */
    public List<Location> getLocationsByName(String name)
            throws DataAccessException;
    
    /**
     * Gets the location by the
     * specified ID
     * 
     * @param locationId
     * @return
     * @throws DataAccessException 
     */
    public Location getLocationById(int locationId)
            throws DataAccessException;
    
    /**
     * Gets the bus route by the specified
     * route number
     * 
     * @param routeNumber
     * @return 
     */
    public BusRoute getBusRouteByRouteNumber(String routeNumber)
            throws DataAccessException;
    
    /**
     * Gets the list of bus routes
     * that have the specified keyword
     * in their description
     * 
     * @param keyword
     * @return
     * @throws DataAccessException 
     */
    public List<BusRoute> getBusRoutesByName(String keyword)
            throws DataAccessException;
    
    
    /**
     * Gets a list of bus routes by specifying their
     * zone number they belong in
     * 
     * @param zoneNumber
     * @return
     * @throws DataAccessException 
     */
    public List<BusRoute> getBusRoutesByZoneNumber(int zoneNumber) 
            throws DataAccessException;
    
    /**
     * Gets the bus zone from which the
     * bus belongs to
     * 
     * @param routeNumber
     * @return
     * @throws DataAccessException 
     */
    public BusZone getBusRouteZoneNumber(String routeNumber)
            throws DataAccessException;
    
    /**
     * Gets a list of all available
     * bus routes
     * @param type
     * @return
     * @throws DataAccessException 
     */
    public List<BusRoute> getAllBusRoutes(BusServiceType type) 
            throws DataAccessException;
    
    
    /**
     * Gets all bus routes going to the
     * specified location keyword
     * 
     * @param location
     * @return
     * @throws DataAccessException 
     */
    public List<BusStop> getBusRoutesGoingTo(String location)
            throws DataAccessException;
    
    /**
     * Gets a list of bus departures
     * available from the specified location
     * name
     * 
     * @param locationName
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDepartureTimesByFromLocationName(String locationName)
            throws DataAccessException;
    
    /**
     * Gets a list of bus departures going to a 
     * specified location
     * @param locationName
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDepartureTimesByToLocationName(String locationName)
            throws DataAccessException;
    
    /**
     * Gets a list of bus departures for
     * bus services available between two
     * locations - and optionally 
     * going past / through the specified location
     * 
     * @param fromLocationId
     * @param toLocationId
     * @param goingViaId
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDepartureTimesBetween(int fromLocationId, int toLocationId, int goingViaId)
            throws DataAccessException;
    
    /**
     * Gets a list of bus departures available
     * from the specified bus route
     * 
     * @param routeNumber
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDeparturesByBusRoute(String routeNumber) 
            throws DataAccessException;
    
    /**
     * Gets a list of bus departures
     * for the specified route
     * @param routeNumber
     * @param serviceType
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDeparturesByBusRoute(String routeNumber, 
            BusServiceType serviceType)
            throws DataAccessException;
    
    /**
     * Gets a list of available bus departures
     * from this given moment
     * 
     * @param routeNumber
     * @param moment
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDeparturesAvailableFromThisMoment(String routeNumber, Date moment)
            throws DataAccessException;
    
    /**
     * Persists / inserts a new 
     * bus departure object
     * 
     * @param departure
     * @return
     * @throws DataAccessException 
     */
    public BusDeparture persistNewBusDeparture(BusDeparture departure)
            throws DataAccessException;
    
    /**
     * Persists / inserts a new 
     * entry for the bus arrival info
     * 
     * @param arrival
     * @return
     * @throws DataAccessException 
     */
    public BusArrival persistNewBusArrival(BusArrival arrival)
            throws DataAccessException;

    /**
     * Gets a list of departure locations within
     * a bus route
     *
     * @param routeNumber
     * @return
     * @throws DataAccessException
     */
    public List<Location> getDepartureLocationsFromBusRoute(String routeNumber)
        throws DataAccessException;
    
    /**
     * Gets a list of bus departures leaving
     * from a particular location 
     * within the bus route
     * 
     * @param routeNumber
     * @param locationId
     * @return
     * @throws DataAccessException 
     */
    public List<BusDeparture> getBusDeparturesLeavingFromLocationWithinBusRoute(String routeNumber, 
            BusServiceType type, int locationId) throws DataAccessException;
    
    /**
     * Gets the route descriptions
     * for the specified route number
     * 
     * @param routeNumber
     * @return
     * @throws DataAccessException 
     */
    public List<RouteDescription> getRouteDescriptions(String routeNumber) 
            throws DataAccessException;
    
    /**
     * Gets a list of bus routes
     * within the bus route
     * 
     * @param routeNumber
     * @return
     * @throws DataAccessException 
     */
    public List<BusStop> getBusStopsByRouteNumber(String routeNumber)
            throws DataAccessException;
}
