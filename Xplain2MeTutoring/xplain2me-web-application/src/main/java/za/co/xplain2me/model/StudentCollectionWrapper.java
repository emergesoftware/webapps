package za.co.xplain2me.model;

import java.io.Serializable;
import java.util.List;
import za.co.xplain2me.entity.Student;

public class StudentCollectionWrapper implements Serializable {
    
    public static final int FOUND = 1;
    public static final int NOT_FOUND = 0;
    
    private String outcome;
    private int code;
    private List<Student> students;
    
    public StudentCollectionWrapper() {
        this.students = null;
        this.outcome = null;
        this.code = NOT_FOUND;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}
