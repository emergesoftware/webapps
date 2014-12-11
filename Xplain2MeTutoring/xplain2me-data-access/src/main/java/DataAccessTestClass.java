
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.HibernateConnectionProvider;

public class DataAccessTestClass {
    
    public static void main(String... args) throws Exception {
       
        new AcademicLevelDAOImpl().getAllAcademicLevels(); 
        HibernateConnectionProvider.destroyConnectionPools();
    }
    
   
    
}
