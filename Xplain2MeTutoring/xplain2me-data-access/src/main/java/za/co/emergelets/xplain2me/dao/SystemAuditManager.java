package za.co.emergelets.xplain2me.dao;

import java.util.Date;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Audit;
import za.co.emergelets.xplain2me.entity.Event;
import za.co.emergelets.xplain2me.entity.User;


public final class SystemAuditManager {
    
    private static final Logger LOG = 
            Logger.getLogger(SystemAuditManager.class.getName(), null);
    
    private static SessionFactory factory = null;
    
    /**
     * Starts an async system audit log
     * 
     * @param eventType
     * @param user
     * @param referenceNumber
     * @param auditXml
     * @param sourceIpAddress
     * @param userAgent
     * @param authorityCode
     * @param authorised 
     */
    public static void logAuditAsync(final long eventType, final User user, final long referenceNumber, 
            final String auditXml, final String sourceIpAddress, final String userAgent,
            final long authorityCode, final boolean authorised) {
        
        new Thread(
                new Runnable() {

            @Override
            public void run() {
                try {
                    
                    // attempt to log an event
                    SystemAuditManager.logAudit(eventType, user, referenceNumber, auditXml, 
                            sourceIpAddress, userAgent, authorityCode, authorised);
                }
                catch (DataAccessException e) {
                    // silently catch the error
                    LOG.log(Level.SEVERE, "An error occured while attempting to log an audit: {0}", 
                           e.getMessage());
                }
                finally {
                    // flush the session factory
                    factory = null;
                }
            }
        }
        ).start();
    }
    
    /**
     * Logs an audit into the system
     * 
     * @param eventType
     * @param user
     * @param referenceNumber
     * @param auditXml
     * @param sourceIpAddress
     * @param authorityCode
     * @param authorised 
     */
    private static void logAudit(long eventType, User user, long referenceNumber, 
            String auditXml, String sourceIpAddress, String userAgent,
            long authorityCode, boolean authorised) {
        
        if (user == null || 
            (sourceIpAddress == null || sourceIpAddress.isEmpty()) || 
            (userAgent == null || userAgent.isEmpty())) {
            LOG.warning("The user / source IP address / user agent are not provided.");
            return;
        }
        
        Audit audit = null;
        Event event = null;
        
        Session session = null;
        Transaction tx = null;
        
        try {
            
            event = SystemAuditManager.getEventById(eventType);
            
            if (event == null) {
                LOG.warning("no event was found - system audit failed.");
                return;
            }
            
            audit = new Audit();
            audit.setUser(user);
            audit.setEvent(event);
            audit.setTimestamp(SystemAuditManager.getCurrentTimestamp()); 
            audit.setSourceIpAddress(sourceIpAddress);
            audit.setAuditXml(auditXml);
            audit.setAuthorityCode(authorityCode);
            audit.setReference(referenceNumber);
            audit.setAuthorised(authorised);
            audit.setUserAgent(userAgent);
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            tx = session.beginTransaction();
            
            session.save(audit);
            session.persist(audit);
            
            tx.commit();
            
            LOG.log(Level.INFO, ">> logged new audit: {0} of event type: {1}", 
                    new Object[]{audit.getId(), event.getLongDescription()});
            
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    
    }
    
    /**
     * Gets the current date and time
     * (timestamp)
     * 
     * @return 
     */
    private static Date getCurrentTimestamp() {
        return new Date();
    }
    
    /**
     * Gets the event by type / id
     * 
     * @param type
     * @return
     * @throws DataAccessException 
     */
    private static Event getEventById(long type) throws DataAccessException {
        
        if (type < 1000) {
            LOG.log(Level.WARNING, "event type: {0} invalid.", type);
            return null;
        }
        
        Event event = null;
        Session session = null;
        Criteria criteria = null;
        Iterator iterator = null;
        
        try {
        
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Event.class);
            criteria.add(Restrictions.eq("type", type));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                event = (Event)iterator.next();
            
            return event;
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }
    
    
    
}
