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
    
}