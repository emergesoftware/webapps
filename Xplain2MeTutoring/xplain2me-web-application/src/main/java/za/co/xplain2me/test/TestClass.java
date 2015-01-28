package za.co.xplain2me.test;

import java.util.List;
import org.codehaus.jackson.map.ObjectMapper;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.model.SearchUserProfileType;


public class TestClass {
    
    public static void main(String... args) throws Exception {
        
        ProfileDAO dao = new ProfileDAOImpl();
        Profile profilePerformingAction = dao.getProfileById(1, 100);
        
        if (profilePerformingAction == null)
            return;
        
        System.out.println("-- Profile performing action: [" + 
                profilePerformingAction.getProfileType().getId() + "] " + 
                profilePerformingAction.getPerson().getFirstNames());
        
        List<Profile> profiles = dao.searchUserProfile(SearchUserProfileType.MatchFirstNames,
                "tshepo", profilePerformingAction);
        
        if (profiles == null || profiles.isEmpty())
            System.out.println("No profiles found.");
        else {
            
            ObjectMapper mapper = new ObjectMapper();
            
            for (Profile profile : profiles)
                System.out.println(mapper.writeValueAsString(profile)); 
        }
            
        
    }
    
}
