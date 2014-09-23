package za.co.metrobus.hibernate.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.sql.JoinType;
import za.co.metrobus.hibernate.entity.*;

/**
 *
 * @author Tsepo Maleka
 */
public class BusServiceDAOImpl extends DefaultDataAccessObject 
                               implements BusServiceDAO {
    
    // Logger
    private static Logger LOG = Logger
            .getLogger(BusServiceDAOImpl.class.getName(), null);
    
    /**
     * Constructor
     */
    public BusServiceDAOImpl() {
        super();
        //startConfiguration();
    }

    @Override
    public List<Location> getLocationsByName(String name) throws DataAccessException {
        
        List<Location> locations = null;
        if (name == null || name.isEmpty())
            return locations;
        
        LOG.log(Level.INFO, "INSIDE: getLocationByName(" + name + ")...");
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Location.class);
            criteria.add(Restrictions.or(Restrictions.ilike("fullName", name, MatchMode.ANYWHERE), 
                    Restrictions.ilike("shortName", name, MatchMode.ANYWHERE)));
            
            criteria.addOrder(Order.asc("shortName"));
            
            locations = criteria.list();
            
            return locations;
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error:" + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
         
    }

    @Override
    public Location getLocationById(int locationId) throws DataAccessException {
        
        Location location = null;
        if (locationId < 100)
            return null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Location.class);
            criteria.add(Restrictions.eq("locationId", locationId));
            
            Iterator iterator = criteria.list().iterator();
            while (iterator.hasNext()) {
                location = (Location)iterator.next();
            }
            
            return location;
            
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public BusRoute getBusRouteByRouteNumber(String routeNumber) throws DataAccessException {
        
        BusRoute route = null;
        if (routeNumber == null || routeNumber.isEmpty())
            return null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusRoute.class);
            criteria.add(Restrictions.eq("routeNumber", routeNumber));
            
            Iterator iterator = criteria.list().iterator();
            while (iterator.hasNext()) {
                route = (BusRoute)iterator.next();
            }
            
            return route;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public List<BusRoute> getBusRoutesByName(String keyword) throws DataAccessException {
        List<BusRoute> routes = null;
        
        if (keyword == null || keyword.isEmpty())
            return null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusRoute.class);
            criteria.add(Restrictions.ilike("routeDescription", keyword, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("routeNumber"));
            
            routes = criteria.list();
            
            return routes;
        
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<BusRoute> getBusRoutesByZoneNumber(int zoneNumber) throws DataAccessException {
        List<BusRoute> routes = null;
        if (zoneNumber < 1){
            return routes;
        }
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusRoute.class)
                    .createAlias("zone", "zones", JoinType.FULL_JOIN)
                    .add(Restrictions.eq("zone.zoneNumber", zoneNumber))
                    .addOrder(Order.asc("routeNumber"));
            
            routes = criteria.list();
            
            return routes;
        
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public BusZone getBusRouteZoneNumber(String routeNumber) throws DataAccessException {
        
        BusZone zone = null;
        if (routeNumber == null || 
                routeNumber.isEmpty())
            return zone;
        
        try {
            
           // openSession();
            
            /* criteria = session.createCriteria(BusRoute.class, "route");
            criteria.createAlias("zone", "zones", JoinType.FULL_JOIN);
            criteria.add(Restrictions.eq("route.routeNumber", routeNumber));
            
            Iterator iterator = criteria.list().iterator();
            while(iterator.hasNext())
                zone = ((BusRoute)iterator.next()).getZone(); */
            
            return zone;
        
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public List<BusDeparture> getBusDepartureTimesByFromLocationName(String locationName) throws DataAccessException {
        
        List<BusDeparture> list = null;
        if (locationName == null || locationName.isEmpty())
            return list;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "times");
            criteria.createAlias("departureLocation", "from", JoinType.FULL_JOIN).add(
                    Restrictions.or(
                            Restrictions.ilike("from.shortName", locationName), 
                            Restrictions.ilike("from.fullName", locationName)));
            criteria.addOrder(Order.asc("times.departureTime"));
            list = criteria.list();
            
            return list;
        
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
        
    }

   
    @Override
    public List<BusDeparture> getBusDepartureTimesByToLocationName(String locationName) throws DataAccessException {
        
        List<BusDeparture> list = null;
        if (locationName == null)
            return list;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class);
            criteria.createAlias("to", "arrivalLocation", JoinType.FULL_JOIN).add(
                    Restrictions.or(
                            Restrictions.ilike("to.shortName", locationName), 
                            Restrictions.ilike("to.fullName", locationName)));
            criteria.addOrder(Order.asc("departureTime"));
            list = criteria.list();
            
            return list;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<BusDeparture> getBusDepartureTimesBetween(int fromLocationId, int toLocationId, int goingViaId) throws DataAccessException {
        List<BusDeparture> list = null;
        if (fromLocationId < 100 || toLocationId < 100)
            return list;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            
             criteria.createAlias("departureLocation", "from", JoinType.FULL_JOIN)
                     .add(Restrictions.eq("from.locationId", fromLocationId));
            
            criteria.createAlias("arrivalLocation", "to", JoinType.FULL_JOIN)
                    .add(Restrictions.eq("to.locationId", toLocationId));
            
            if (goingViaId >= 100) {
                
                criteria.createAlias("viaLocation", "via", JoinType.FULL_JOIN)
                        .add(Restrictions.eq("via.locationId", goingViaId));
            }
            
            criteria.addOrder(Order.asc("departure.departureTime"));
            list = criteria.list();
            
            return list;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<BusDeparture> getBusDeparturesByBusRoute(String routeNumber) throws DataAccessException {
        
        List<BusDeparture> list = null;
        if (routeNumber == null || routeNumber.isEmpty())
            return list;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            criteria.createAlias("route", "routes", JoinType.FULL_JOIN).add(
                            Restrictions.eq("routes.routeNumber", routeNumber));
            
            criteria.createAlias("departureLocation", "from", JoinType.FULL_JOIN);
            
            criteria.addOrder(Order.asc("from.shortName"))
                    .addOrder(Order.asc("departure.departureTime"));
            
            list = criteria.list();
            
            return list;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<BusDeparture> getBusDeparturesByBusRoute(String routeNumber, BusServiceType serviceType) throws DataAccessException {
        
        List<BusDeparture> list = null;
        if (routeNumber == null || routeNumber.isEmpty())
            return list;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            
            criteria.createAlias("route", "routes", JoinType.FULL_JOIN).add(
                            Restrictions.eq("routes.routeNumber", routeNumber));
            
            switch (serviceType) {
                
                case WeekdayService:
                    criteria.add(Restrictions.eq("departure.weekdayService", true));
                    break;
                
                case SaturdayService:
                    criteria.add(Restrictions.eq("departure.saturdayService", true));
                    break;    
                    
                case SundayService: 
                case HolidayService:
                    criteria.add(Restrictions.eq("departure.sundayService", true));
                    break;  
                
                default:
                    criteria.add(Restrictions.eq("departure.weekdayService", true));
                    break;
            }
            
            criteria.addOrder(Order.asc("departure.departureTime"));
            
            list = criteria.list();
            
            return list;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<BusDeparture> getBusDeparturesAvailableFromThisMoment(String routeNumber, Date moment) throws DataAccessException {
        
        List<BusDeparture> list = null;
        if (routeNumber == null || routeNumber.isEmpty())
            return list;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            
            criteria.createAlias("route", "routes", JoinType.FULL_JOIN).add(
                            Restrictions.eq("routes.routeNumber", routeNumber));
            criteria.createAlias("departureLocation", "from", JoinType.FULL_JOIN);
            
            criteria.add(Restrictions.ge("departure.departureTime", new Date()));
            
            criteria.addOrder(Order.asc("from.shortName"))
                    .addOrder(Order.asc("departure.departureTime"));
             
            list = criteria.list();
            
            return list;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public BusDeparture persistNewBusDeparture(BusDeparture departure) throws DataAccessException {
        
        if (departure == null)
            return null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            tx = session.beginTransaction();
            
            session.save(departure);
            session.persist(departure);
            
            tx.commit();
            return departure;
        
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public BusArrival persistNewBusArrival(BusArrival arrival) throws DataAccessException {
        
        if (arrival == null)
           return null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            tx = session.beginTransaction();
            
            session.save(arrival);
            session.persist(arrival);
            
            tx.commit();
            
            return arrival;
            
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<Location> getDepartureLocationsFromBusRoute(String routeNumber) 
            throws DataAccessException {

        if (routeNumber == null || routeNumber.isEmpty())
            return null;

        List<Location> locations = null;

        try {

            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            criteria.createAlias("route", "busRoute", JoinType.FULL_JOIN);
            criteria.setProjection(Projections.distinct(
                    Projections.property("departure.departureLocation")));
            
            criteria.add(Restrictions.eq("busRoute.routeNumber", routeNumber));

            locations = criteria.list();
            
            return locations;
        }

        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }

    }

    @Override
    public List<BusDeparture> getBusDeparturesLeavingFromLocationWithinBusRoute(String routeNumber, 
            BusServiceType type, int locationId) throws DataAccessException {
        
        if (routeNumber == null || routeNumber.isEmpty() || locationId < 100) {
            return null;
        }
        
        List<BusDeparture> departures = null;
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            criteria.createAlias("route", "routes", JoinType.FULL_JOIN).add(
                            Restrictions.eq("routes.routeNumber", routeNumber));
            criteria.createAlias("departureLocation", "from", JoinType.FULL_JOIN);
            
            expressions.add(Restrictions.eq("from.locationId", locationId)); 
            
            if (type != null) {
                
                switch (type) {
                    case WeekdayService:
                        expressions.add(Restrictions.eq("departure.weekdayService", true));
                        break;
                        
                    case SaturdayService:
                        expressions.add(Restrictions.eq("departure.saturdayService", true));
                        break;
                        
                    case SundayService:
                    case HolidayService:
                        expressions.add(Restrictions.eq("departure.sundayService", true));
                        break;
                }
            }
            
            criteria.add(Restrictions.and(expressions.get(0), expressions.get(1)));
            
            criteria.addOrder(Order.asc("from.shortName"))
                    .addOrder(Order.asc("departure.departureTime"));
             
            departures = criteria.list();
            
            
            return departures;
            
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<BusRoute> getAllBusRoutes(BusServiceType type) throws DataAccessException {
        
        List<BusRoute> routes = null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusRoute.class);
            
            if (type != null) {
                switch (type) {
                    
                    case WeekdayService:
                        criteria.add(Restrictions.eq("weekdayService", true));
                        break;
                    
                    case SaturdayService:
                        criteria.add(Restrictions.eq("saturdayService", true));
                        break;
                    
                    default:
                        criteria.add(Restrictions.eq("sundayService", true));
                        break;
                }
            }
            
            criteria.addOrder(Order.asc("routeNumber"));
            routes = criteria.list();
            
            return routes;
        }
        
        catch (HibernateException e) {
        LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public List<BusStop> getBusRoutesGoingTo(String location) 
            throws DataAccessException {
        
        if (location == null || location.isEmpty())
            return null;
        
        List<BusStop> routes = null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusStop.class);
            criteria.add(Restrictions.ilike("fullDescription", location, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("busStopId"));
            routes = criteria.list();
            return routes;
            
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<RouteDescription> getRouteDescriptions(String routeNumber) 
            throws DataAccessException {
        
        List<RouteDescription> descriptions = null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(RouteDescription.class, "description");
            criteria.createAlias("route", "route", JoinType.FULL_JOIN);
            criteria.add(Restrictions.eq("route.routeNumber", routeNumber));
            descriptions = criteria.list();
            
            return descriptions;
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public List<BusStop> getBusStopsByRouteNumber(String routeNumber) throws DataAccessException {
        
        if (routeNumber == null || routeNumber.isEmpty())
            return null;
        
        List<BusStop> busStops = null;
        
        try {
        
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(BusStop.class, "busStop");
            criteria.createAlias("route", "route", JoinType.FULL_JOIN);
            criteria.add(Restrictions.eq("route.routeNumber", routeNumber));
            criteria.addOrder(Order.asc("busStop.busStopId"));
            busStops = criteria.list();
            
            return busStops;
            
        }
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }
    


}
