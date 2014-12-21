package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;

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
     * @param academicLevelsTutoredBefore
     * @param documents
     * @return
     * @throws DataAccessException 
     */
    public BecomeTutorRequest saveBecomeTutorRequest(BecomeTutorRequest request, 
            List<AcademicLevelsTutoredBefore> academicLevelsTutoredBefore, 
            List<BecomeTutorSupportingDocument> documents) throws DataAccessException;
    
    /**
     * Gets a list of become a tutor requests
     * 
     * @param pageNumber
     * @return
     * @throws DataAccessException 
     */
    public List<BecomeTutorRequest> getBecomeTutorRequests(int pageNumber)
            throws DataAccessException;
    
    /**
     * Gets the become a tutor request
     * by ID.
     * 
     * @param id
     * @return
     * @throws DataAccessException 
     */
    public BecomeTutorRequest getBecomeTutorRequest(long id) 
            throws DataAccessException;
    
}
