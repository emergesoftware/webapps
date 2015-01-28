package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.xplain2me.entity.BecomeTutorRequest;
import za.co.xplain2me.entity.BecomeTutorSupportingDocument;

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
    
    /**
     * Gets a list of supporting documents
     * 
     * @param requestId
     * @return
     * @throws DataAccessException 
     */
    public List<BecomeTutorSupportingDocument> getBecomeTutorSupportingDocuments(long requestId)
            throws DataAccessException;
    
    /**
     * Searches for a BecomeTutorRequest entity
     * using the identity number.
     * 
     * @param identityNumber
     * @return
     * @throws DataAccessException 
     */
    public BecomeTutorRequest searchBecomeTutorRequestByIdNumber(String identityNumber)
            throws DataAccessException;
    
    /**
     * Searches for a BecomeTutorRequest entity 
     * using the email address.
     * 
     * @param emailAddress
     * @return
     * @throws DataAccessException 
     */
    public BecomeTutorRequest searchBecomeTutorRequestByEmailAddress(String emailAddress)
            throws DataAccessException;
    
    /**
     * Searches for a BecomeTutorRequest entity
     * using the contact number.
     * 
     * @param contactNumber
     * @return
     * @throws DataAccessException 
     */
    public BecomeTutorRequest searchBecomeTutorRequestByContactNumber(String contactNumber)
            throws DataAccessException;
}
