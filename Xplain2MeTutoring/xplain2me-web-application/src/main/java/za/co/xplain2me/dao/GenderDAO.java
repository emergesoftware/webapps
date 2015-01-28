package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Gender;

public interface GenderDAO {
    
    public List<Gender> getAllGender()
            throws DataAccessException;
    
    public Gender getGender(String id)
            throws DataAccessException;
    
}
