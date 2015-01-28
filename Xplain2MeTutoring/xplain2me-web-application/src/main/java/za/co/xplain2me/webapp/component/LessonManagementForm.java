package za.co.xplain2me.webapp.component;

import java.util.TreeMap;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Subject;

public class LessonManagementForm {
    
    private TreeMap<Long, LessonStatus> lessonStatus;
    private TreeMap<Long, AcademicLevel> academicLevels;
    private TreeMap<Long, Subject> subjects;
    
    public LessonManagementForm() {
        this.resetForm();
    }
    
    public final void resetForm() {
        this.lessonStatus = null;
        this.academicLevels = null;
        this.subjects = null;
    }

    public TreeMap<Long, LessonStatus> getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(TreeMap<Long, LessonStatus> lessonStatus) {
        this.lessonStatus = lessonStatus;
    }

    public TreeMap<Long, AcademicLevel> getAcademicLevels() {
        return academicLevels;
    }

    public void setAcademicLevels(TreeMap<Long, AcademicLevel> academicLevels) {
        this.academicLevels = academicLevels;
    }

    public TreeMap<Long, Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(TreeMap<Long, Subject> subjects) {
        this.subjects = subjects;
    }
    
}
