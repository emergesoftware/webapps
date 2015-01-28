package za.co.xplain2me.dao;

import java.util.List;
import za.co.xplain2me.entity.Province;

public interface ProvinceDAO {
    
    public List<Province> getAllProvinces()
            throws DataAccessException;
    
}
