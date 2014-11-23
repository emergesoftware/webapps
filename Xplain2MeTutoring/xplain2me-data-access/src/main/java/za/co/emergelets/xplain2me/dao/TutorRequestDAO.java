package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.TutorRequest;

public interface TutorRequestDAO {
    
    //public boolean isTutorRequestCompletelyUnique(TutorRequest request) throws DataAccessException;
    
    public TutorRequest getTutorRequestById(long id) throws DataAccessException;
    public TutorRequest getTutorRequestByEmailAddress(String emailAddress) throws DataAccessException;
    public TutorRequest getTutorRequestByContactNumber(String contactNumber) throws DataAccessException;
    public TutorRequest saveTutorRequest(TutorRequest request) throws DataAccessException;
    public List<TutorRequest> getUnreadTutorRequests() throws DataAccessException;
    
    public TutorRequest updateTutorRequest(TutorRequest request) throws DataAccessException;
    public TutorRequest deleteTutorRequest(TutorRequest request) throws DataAccessException;
       
}
