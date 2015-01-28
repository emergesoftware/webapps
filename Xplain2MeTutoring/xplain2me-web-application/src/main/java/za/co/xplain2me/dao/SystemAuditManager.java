package za.co.xplain2me.dao;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import za.co.xplain2me.entity.Audit;
import za.co.xplain2me.entity.Event;
import za.co.xplain2me.entity.User;


public final class SystemAuditManager {
    
    private static final Logger LOG = 
            Logger.getLogger(SystemAuditManager.class.getName(), null);
    
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
    public synchronized static void logAuditAsync(final long eventType, final User user, final long referenceNumber, 
            final String auditXml, final String sourceIpAddress, final String userAgent,
            final long authorityCode, final boolean authorised) {
        
        new Thread(
            new Runnable() {

                @Override
                public void run() {
                    try {

                        // attempt to log an event
                        logAudit(eventType, user, referenceNumber, auditXml, 
                                sourceIpAddress, userAgent, authorityCode, authorised);
                    }
                    catch (DataAccessException e) {
                        // silently catch the error
                        LOG.log(Level.SEVERE, "An error occured while attempting "
                                + "to log an audit: {0}", 
                               e.getMessage());
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
        
        try {
            
            event = new EventDAOImpl().getEventByType(eventType);
            
            if (event == null) {
                LOG.warning("no event was found - system audit failed.");
                return;
            }
            
            audit = new Audit();
            audit.setUser(user);
            audit.setEvent(event);
            audit.setTimestamp(new Date()); 
            audit.setSourceIpAddress(sourceIpAddress);
            audit.setAuditXml(auditXml);
            audit.setAuthorityCode(authorityCode);
            audit.setReference(referenceNumber);
            audit.setAuthorised(authorised);
            audit.setUserAgent(userAgent);
            
            new AuditDAOImpl().createAudit(audit);
            
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
    }
    
}
