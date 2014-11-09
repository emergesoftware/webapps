package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.Citizenship;

public interface CitizenshipDAO {
    
    public List<Citizenship> getAllCitizenships()
            throws DataAccessException;
    
}
