package za.co.metrobus.hibernate.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import za.co.metrobus.hibernate.entity.BusDeparture;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusServiceType;
import za.co.metrobus.hibernate.entity.Location;

public class BusServiceDAOImpl extends DefaultDataAccessObject 
                               implements BusServiceDAO {
    
    private static Logger LOG = Logger
            .getLogger(BusServiceDAOImpl.class.getName(), null);
    
    private String xmlConfigFile;
    
    public BusServiceDAOImpl() {
        super();
        this.xmlConfigFile = null;
    }
    
    public BusServiceDAOImpl(String xmlConfigFile) {
        this();
        this.xmlConfigFile = xmlConfigFile;
    }
    
    /**
     * Opens a new connection session
     */
    protected void openSession() {
        factory = DataRepositoryUtility.configure(xmlConfigFile);
        session = factory.openSession();
    }
    
    /**
     * Closes the current open session
     */
    protected void closeSession() {
        DataRepositoryUtility.close();
    }
    

    @Override
    public List<BusRoute> getAllBusRoutes(BusServiceType type) 
            throws DataAccessException {
        
        try {
            
            openSession();
            
            criteria = session.createCriteria(BusRoute.class, "route");
            
            if (type != null && type == BusServiceType.WeekdayService) 
                criteria.add(Restrictions.eq("route.weekdayService", true));
            
            else if (type != null && (type == BusServiceType.SaturdayService || 
                    type == BusServiceType.HolidayService))
                criteria.add(Restrictions.eq("route.saturdayService", true));
            
            else if (type != null && type == BusServiceType.SundayService)
                criteria.add(Restrictions.eq("route.sundayService", true));
            
            criteria.addOrder(Order.asc("route.routeNumber"));
            return criteria.list();
            
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeSession();
        }
    }

    @Override
    public BusRoute getBusRouteByRouteNumber(String routeNumber) 
            throws DataAccessException {
        
        if (routeNumber == null || routeNumber.isEmpty())
            return null;
        
        BusRoute route = null;
        
        try {
            
            openSession();
            
            criteria = session.createCriteria(BusRoute.class, "route");
            criteria.add(Restrictions.eq("route.routeNumber", routeNumber));
            Iterator iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                route = (BusRoute)iterator.next();
            
            return route;
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeSession();
        }
    }

    @Override
    public List<Location> getDepartureLocationsFromBusRoute(String routeNumber) 
            throws DataAccessException {
        
        if (routeNumber == null || routeNumber.isEmpty())
            return null;
        
        List<Location> results = null;
        
        try {
            openSession();
            
            criteria = session.createCriteria(BusDeparture.class, "departure");
            criteria.createAlias("route", "route", JoinType.FULL_JOIN);
            criteria.createAlias("departureLocation", "departureLocation", JoinType.FULL_JOIN);
            criteria.setProjection(Projections.distinct(
                    Projections.property("departure.departureLocation")));
            criteria.add(Restrictions.eq("route.routeNumber", routeNumber));
            
            results = new ArrayList<Location>();
            Iterator iterator = criteria.list().iterator();
            
            while (iterator.hasNext()) {
                Location location = (Location)iterator.next();
                if (location != null)
                    results.add(location);
            }
            
            return results;
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeSession();
        }
        
    }
    
    
    
}
