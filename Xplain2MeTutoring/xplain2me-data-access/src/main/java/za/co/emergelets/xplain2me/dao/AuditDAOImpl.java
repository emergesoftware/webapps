package za.co.emergelets.xplain2me.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Audit;

public class AuditDAOImpl implements AuditDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(AuditDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public AuditDAOImpl() {
    }

    @Override
    public List<Audit> getLatestAuditTrailByUserLimited(String username, 
            int limit) throws DataAccessException {
        
       if (username == null || username.isEmpty() || 
               limit <= 0) {
           LOG.warning("... invalid params found, aborting... ");
           return null;
       }
       
       try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(Audit.class, "audit")
                       .createAlias("audit.user", "user")
                       .add(Restrictions.eq("user.username", username))
                       .addOrder(Order.desc("audit.id"))
                       .setMaxResults(limit);
            
            return criteria.list();
       }
       
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, " ... error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
           HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public Audit createAudit(Audit audit) throws DataAccessException {
        
        if (audit == null || 
                audit.getUser() == null ||
                audit.getEvent() == null) {
            LOG.severe(" ... invalid params found, aborting... ");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.save(audit);
            session.persist(audit);
            transaction.commit();
            
            LOG.log(Level.INFO, ">> logged new audit: {0} of event type: {1}", 
                    new Object[]{audit.getId(), audit.getEvent().getLongDescription()});
            
            return audit;
        
        }
        
        catch (HibernateException e) {
            
            HibernateConnectionProvider.rollback(transaction);
            
            LOG.log(Level.SEVERE, " ... error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public List<Audit> getLatestAuditTrailByUser(String username, List<Long> eventTypes, int limit) 
            throws DataAccessException {
        
        if (username == null || username.isEmpty() || 
               limit <= 0 || eventTypes == null || 
                eventTypes.isEmpty()) {
           LOG.warning("... invalid params found, aborting... ");
           return null;
       }
       
       try {
           
            LOG.log(Level.INFO," ... getting last {0} activities on the " + 
                    "audit trail containing {1} by user {2} ... ", 
                    new Object[]{limit, eventTypes.toString(), username});
            
            System.out.println(" ... getting last " + limit + " activities on the " + 
                    "audit trail containing " + eventTypes.toString() + 
                    " by user " + username + " ... ");
            
            session = HibernateConnectionProvider.getSessionFactory()
                    .openSession();
            
            List<Criterion> eventTypeCriterion = new ArrayList<>();
            for (Long eventType : eventTypes) {
                eventTypeCriterion.add(Restrictions.eq("event.type", eventType));
            }
            
            criteria = session.createCriteria(Audit.class, "audit")
                       .createAlias("audit.user", "user")
                       .createAlias("audit.event", "event")
                       .add(Restrictions.eq("user.username", username))
                       .add(Restrictions.or(
                               eventTypeCriterion.toArray(
                                       new Criterion[eventTypeCriterion.size()]
                               )
                        ))
                       .addOrder(Order.desc("audit.id"))
                       .setMaxResults(limit);
            
            return criteria.list();
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

