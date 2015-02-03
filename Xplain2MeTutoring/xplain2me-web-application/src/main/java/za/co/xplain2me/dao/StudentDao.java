package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Student;

public interface StudentDao {
    
    public List<Student> findStudentsByMatchingFirstNames(String searchValue,
            Profile profilePerformingAction) throws DataAccessException;
    
    public Student getStudentById(long id, Profile profilePerformingAction)
            throws DataAccessException;
    
}
