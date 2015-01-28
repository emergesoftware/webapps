package za.co.xplain2me.bo;

import java.util.List;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.Profile;

public interface LessonBO {
    
    /**
     * Creates a new lessons entry
     * 
     * @param lessons - the list of lessons to be created.
     * @param profilePerformingAction - the user profile performing this action.
     * 
     * @return 
     */
    public GeneralStatus createNewLessons(List<Lesson> lessons, 
            Profile profilePerformingAction);
    
    /**
     * Logs time against the lesson and 
     * other details of the lesson after
     * a tutor has completed the lesson.
     * 
     * @param lesson
     * @param profilePerformingAction
     * @return 
     */
    public GeneralStatus logTimeAgainstLessonEntry(Lesson lesson, 
            Profile profilePerformingAction);
    
    /**
     * Postpones a lesson in the future or one that
     * has gone past but was never completed.
     * 
     * @param lesson
     * @param profilePerformingAction
     * @return 
     */
    public GeneralStatus postponeLessonEntry(Lesson lesson, 
            Profile profilePerformingAction);
    
    /**
     * Cancels a lesson entry that is in the future
     * or that has gone past but did not take place.
     * 
     * @param lesson
     * @param profilePerformingAction
     * @return 
     */
    public GeneralStatus cancelLessonEntry(Lesson lesson, 
            Profile profilePerformingAction);
    
    /**
     * Updates the lesson overview such as the
     * content covered, learner performance and 
     * other actions taken.
     * 
     * @param lesson
     * @param profilePerformingAction
     * @return 
     */
    public GeneralStatus updateLessonOverview(Lesson lesson, 
            Profile profilePerformingAction);
    
    /**
     * Updates the lesson entity entry.
     * 
     * @param lesson
     * @param profilePerformingAction
     * @return 
     */
    public GeneralStatus updateLessonEntry(Lesson lesson, 
            Profile profilePerformingAction);
}
