package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.ProfileTypeUrlPermissions;

@Component
public class UserContext implements Serializable {
    
    private List<String> permittedUrls;
    
    // the user profile
    private Profile profile;
    
    // the list of permitted urls
    private List<ProfileTypeUrlPermissions> profileTypeUrlPermissions;
    
    // the time the user logged in
    private Date timeUserLoggedIn;
    
    public UserContext() {
    }

    public List<String> getPermittedUrls() {
        if (permittedUrls == null)
            permittedUrls = new ArrayList<>();
        
        return permittedUrls;
    }

    public void setPermittedUrls(List<String> permittedUrls) {
        this.permittedUrls = permittedUrls;
    }

    public List<ProfileTypeUrlPermissions> getProfileTypeUrlPermissions() {
        return profileTypeUrlPermissions;
    }

    public void setProfileTypeUrlPermissions(List<ProfileTypeUrlPermissions> profileTypeUrlPermissions) {
        this.profileTypeUrlPermissions = profileTypeUrlPermissions;
    }
    
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Date getTimeUserLoggedIn() {
        return timeUserLoggedIn;
    }

    public void setTimeUserLoggedIn(Date timeUserLoggedIn) {
        this.timeUserLoggedIn = timeUserLoggedIn;
    }
    
}
