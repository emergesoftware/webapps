package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.Subject;

/**
 * Data Access Object for Subject
 * @author tsepo maleka
 */
public interface SubjectDAO {
    
    public Subject getSubject(long id) throws DataAccessException;
    public List<Subject> getAllSubjects() throws DataAccessException;
    public List<Subject> searchSubjects(String keyword) throws DataAccessException;
    
}
