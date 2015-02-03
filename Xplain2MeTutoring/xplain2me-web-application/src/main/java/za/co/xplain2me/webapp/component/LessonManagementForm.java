package za.co.xplain2me.webapp.component;

import java.util.List;
import java.util.TreeMap;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Subject;

public class LessonManagementForm {
    
    private TreeMap<Long, LessonStatus> lessonStatus;
    private TreeMap<Long, Subject> subjects;
    
    private List<Lesson> lessons;
    private String redirectCode;
    
    public LessonManagementForm() {
        this.resetForm();
    }
    
    public final void resetForm() {
        this.lessonStatus = null;
        this.subjects = null;
        this.lessons = null;
        this.redirectCode = null;
    }

    public TreeMap<Long, LessonStatus> getLessonStatus() {
        return lessonStatus;
    }

    public void setLessonStatus(TreeMap<Long, LessonStatus> lessonStatus) {
        this.lessonStatus = lessonStatus;
    }

    public TreeMap<Long, Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(TreeMap<Long, Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public void setLessons(List<Lesson> lessons) {
        this.lessons = lessons;
    }

    public String getRedirectCode() {
        return redirectCode;
    }

    public void setRedirectCode(String redirectCode) {
        this.redirectCode = redirectCode;
    }
    
}
