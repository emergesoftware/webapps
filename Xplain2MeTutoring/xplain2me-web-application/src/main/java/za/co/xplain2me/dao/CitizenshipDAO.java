package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Citizenship;

public interface CitizenshipDAO {
    
    public List<Citizenship> getAllCitizenships() throws DataAccessException;
    
    public Citizenship getCitizenship(long id) throws DataAccessException;
    
}
