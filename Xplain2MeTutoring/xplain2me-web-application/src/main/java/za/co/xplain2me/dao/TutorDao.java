package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.entity.Tutor;
import za.co.xplain2me.entity.TutorSubject;

public interface TutorDao {
    
    public final static int SEARCH_BY_SUBJECT_TUTORING = 100;
    public final static int SEARCH_BY_TUTOR_ID = 101;
    
    /**
     * Gets a list of tutors for browsing
     * from starting point and limited to
     * per browsing instance.
     * 
     * @param startFrom
     * @param limitTo
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public List<Tutor> browseTutors(int startFrom, int limitTo, Profile profilePerformingAction) throws DataAccessException;
    
    /**
     * Creates a new tutor entity from the provided
     * profile.
     * 
     * @param tutor
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public Tutor createTutor(Tutor tutor, 
            Profile profilePerformingAction) throws DataAccessException; 
    
    /**
     * Assigns the tutor to subjects.
     * 
     * @param tutorSubjects
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public boolean assignSubjectsToTutor(List<TutorSubject> tutorSubjects,
            Profile profilePerformingAction) throws DataAccessException;
    
    /**
     * Removes subjects from tutor
     * 
     * @param tutor
     * @param subjects
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public boolean removeSubjectsFromTutor(Tutor tutor, List<Subject> subjects, 
            Profile profilePerformingAction)
            throws DataAccessException;
    
    /**
     * Searches for tutor(s) 
     * 
     * @param tutorSearchCriteria
     * @param value
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public List<Tutor> searchTutor(int tutorSearchCriteria, Object value,
            Profile profilePerformingAction) throws DataAccessException;
    
    /**
     * Updates a tutor 
     * 
     * @param tutor
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public Tutor updateTutor(Tutor tutor, Profile profilePerformingAction)
            throws DataAccessException;
    
    /**
     * Assigns a tutor to lessons.
     * 
     * @param lessons
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public boolean assignLessonsToTutor(List<Lesson> lessons, 
            Profile profilePerformingAction) throws
            DataAccessException;
    
    /**
     * Detaches a user as a tutor.
     * 
     * @param tutor
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public boolean detachUserAsTutor(Tutor tutor, Profile profilePerformingAction)
            throws DataAccessException;
    
    /**
     * Gets a list of all lessons
     * assigned to tutor according to
     * lesson status.
     * 
     * @param tutor
     * @param status
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public List<Lesson> getTutorLessons(Tutor tutor, LessonStatus status, 
            Profile profilePerformingAction)
            throws DataAccessException;
    
    /**
     * Gets all the subjects a tutor is
     * currently tutoring.
     * 
     * @param tutor
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public List<Subject> getTutorSubjects(Tutor tutor, Profile profilePerformingAction)
            throws DataAccessException;
    
    /**
     * Checks if the user profile is already 
     * assigned as tutor.
     * 
     * @param profile
     * @param profilePerformingAction
     * @return
     * @throws DataAccessException 
     */
    public boolean isTutorAlready(Profile profile, Profile profilePerformingAction)
            throws DataAccessException;
}
