import java.util.List;
import za.co.emergelets.xplain2me.dao.HibernateConnectionProvider;
import za.co.emergelets.xplain2me.dao.ProfileDAO;
import za.co.emergelets.xplain2me.dao.ProfileDAOImpl;
import za.co.emergelets.xplain2me.entity.Profile;

public class DataAccessTestClass {
    
    public static void main(String... args) throws Exception {
       
        ProfileDAO profileDao = new ProfileDAOImpl();
        List<Profile> profiles = profileDao.getUserAuthorisedProfiles(101);
        
        if (profiles == null || profiles.isEmpty())
            System.out.println("None");
        else {
            for (Profile profile : profiles) {
                System.out.println("user: " + profile.getPerson().getUser().getUsername());
            }
        }
    }
    
   
    
}
