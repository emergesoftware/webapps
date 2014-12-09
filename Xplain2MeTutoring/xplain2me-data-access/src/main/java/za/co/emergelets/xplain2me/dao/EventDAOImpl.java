package za.co.emergelets.xplain2me.dao;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Event;

public class EventDAOImpl implements EventDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(EventDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public EventDAOImpl() {
    }

    @Override
    public Event getEventByType(long type) throws DataAccessException {
        if (type < 1000) {
            LOG.log(Level.WARNING, " ... invalid type for an event: {0}", type); 
            return null;
        }
        
        Event event = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Event.class)
                    .add(Restrictions.eq("type", type));
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                event = (Event)iterator.next();
            
            return event;
            
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, " ... error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }
    
}
