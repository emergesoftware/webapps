package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.LessonStatus;

public interface LessonDao {
    
    /**
     * Gets a list of all the 
     * lesson statuses (Pending, Completed, Postponed, Canceled) 
     * @return
     * @throws DataAccessException 
     */
    public List<LessonStatus> getAllLessonStatuses() 
            throws DataAccessException;
    
    
}
