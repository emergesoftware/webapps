package za.co.metrobus.hibernate.dao;

import java.util.List;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusServiceType;
import za.co.metrobus.hibernate.entity.Location;

public interface BusServiceDAO {
    
    /**
     * Gets a list of all bus routes
     * 
     * @return
     * @throws DataAccessException 
     */
    public List<BusRoute> getAllBusRoutes(BusServiceType type) 
            throws DataAccessException;
    
    /**
     * Gets the bus route with the specified
     * bus route number
     * @param routeNumber
     * @return
     * @throws DataAccessException 
     */
    public BusRoute getBusRouteByRouteNumber(String routeNumber)
            throws DataAccessException;
    
    /**
     * Gets the departure and arrival locations in the bus
     * route provided.
     * 
     * @param routeNumber
     * @return
     * @throws DataAccessException 
     */
    public List<Location> getDepartureLocationsFromBusRoute(String routeNumber)
            throws DataAccessException;
    
    public List<BusDeparture> getBusDeparturesByBusRoute(routeNumber, type, )
}
