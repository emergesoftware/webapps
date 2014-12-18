
import java.util.List;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.emergelets.xplain2me.dao.HibernateConnectionProvider;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;

public class DataAccessTestClass {
    
    public static void main(String... args) throws Exception {
       
        BecomeTutorRequestDAO dao = new BecomeTutorRequestDAOImpl();
        List<BecomeTutorRequest> requests = dao.getBecomeTutorRequests(2);
        
        HibernateConnectionProvider.destroyConnectionPools();
    }
    
   
    
}
