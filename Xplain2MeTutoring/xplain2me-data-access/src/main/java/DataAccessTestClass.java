
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.EventDAOImpl;
import za.co.emergelets.xplain2me.dao.HibernateConnectionProvider;

public class DataAccessTestClass {
    
    public static void main(String... args) throws Exception {
        
        new EventDAOImpl().getEventByType(1000);
        //Thread.sleep(15000);
        
        new AcademicLevelDAOImpl().getAllAcademicLevels();
        
        HibernateConnectionProvider.destroyConnectionPools();
    }
    
   
    
}
