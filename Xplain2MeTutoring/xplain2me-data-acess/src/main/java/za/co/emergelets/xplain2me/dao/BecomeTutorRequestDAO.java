package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;

public interface BecomeTutorRequestDAO {
    
    /**
     * Determines if the tutor application
     * is completely unique
     * 
     * @param request
     * @return
     * @throws DataAccessException 
     */
    public boolean isTutorApplicationUnique(BecomeTutorRequest request)
            throws DataAccessException;
    
    /**
     * Saves the tutor application
     * to the data repo
     * 
     * @param request
     * @return
     * @throws DataAccessException 
     */
    public BecomeTutorRequest saveBecomeTutorRequest(BecomeTutorRequest request)
            throws DataAccessException;
    
}
