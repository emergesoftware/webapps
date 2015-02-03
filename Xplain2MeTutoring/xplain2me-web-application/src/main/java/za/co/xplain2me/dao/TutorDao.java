package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.ProfileType;

public interface TutorDao {
    
    public static final long TUTOR_PROFILE_TYPE = ProfileType.TUTOR_PROFILE;
    
    /**
     * Finds a list of the next available tutors
     * who:
     * 1. Could offer this lessons at the 
     * @param lessons
     * @param profileTypePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public List<Profile> findAvailableNextTutors(List<Lesson> lessons,
            long profileTypePerformingAction) throws DataAccessException;
    
}
