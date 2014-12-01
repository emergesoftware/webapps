package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.Audit;

public interface AuditDAO {
    
    /**
     * Gets the latest audit trail performed by
     * the specified user
     * 
     * @param username
     * @param limit
     * @return 
     */
    public List<Audit> getLatestAuditTrailByUserLimited(String username, 
            int limit) throws DataAccessException;
    
    /**
     * Gets the latest audit trail performed by user
     * per specified event type range
     * 
     * @param username
     * @param eventTypes
     * @param limit
     * @return
     * @throws DataAccessException 
     */
    public List<Audit> getLatestAuditTrailByUser(String username, List<Long> eventTypes, 
            int limit) throws DataAccessException;
    
    /**
     * Creates a new audit entity
     * 
     * @param audit
     * @return
     * @throws DataAccessException 
     */
    public Audit createAudit(Audit audit) throws DataAccessException;
    
}
