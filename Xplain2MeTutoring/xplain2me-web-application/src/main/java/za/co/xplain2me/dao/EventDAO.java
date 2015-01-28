package za.co.xplain2me.dao;

import za.co.xplain2me.entity.Event;

public interface EventDAO {
    
    /**
     * Gets an event by type
     * provided
     * 
     * @param type
     * @return
     * @throws DataAccessException 
     */
    public Event getEventByType(long type) throws DataAccessException;
    
}
