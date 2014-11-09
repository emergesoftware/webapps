package za.co.emergelets.xplain2me.dao;

import java.util.List;
import za.co.emergelets.xplain2me.entity.Gender;

public interface GenderDAO {
    
    public List<Gender> getAllGender()
            throws DataAccessException;
    
}
