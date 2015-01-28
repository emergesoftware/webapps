package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.AcademicLevel;

public interface AcademicLevelDAO {
    
    public AcademicLevel getAcademicLevel(long id) throws DataAccessException;
    public List<AcademicLevel> getAllAcademicLevels() throws DataAccessException;
    
}
