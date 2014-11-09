package za.co.emergelets.xplain2me.dao;

import za.co.emergelets.xplain2me.entity.TutorRequest;

public interface TutorRequestDAO {
    
    public boolean isTutorRequestCompletelyUnique(TutorRequest request) throws DataAccessException;
    public TutorRequest getTutorRequestById(long id) throws DataAccessException;
    public TutorRequest getTutorRequestByEmailAddress(String emailAddress) throws DataAccessException;
    public TutorRequest getTutorRequestByContactNumber(String contactNumber) throws DataAccessException;
    public TutorRequest saveTutorRequest(TutorRequest request) throws DataAccessException;
       
}