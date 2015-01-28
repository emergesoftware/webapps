package za.co.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.Map;
import org.springframework.stereotype.Component;
import za.co.xplain2me.entity.Citizenship;
import za.co.xplain2me.entity.Gender;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.webapp.controller.ProfileManagementController;

@Component
public class ProfileManagementForm implements Serializable {
    
    // the user profile
    private Profile profile;
    
    // the profile mode (edit or view)
    private int profileMode;
    
    // a list of gender types
    private Map<String, Gender> gender;
    // a list of citizenships
    private Map<Long, Citizenship> citizenship;
    
    // the Password field
    private PasswordSet passwordSet;
    
    public ProfileManagementForm() {
        resetForm();
    }
    
    public final void resetForm() {
        this.profile = null;
        this.profileMode = ProfileManagementController.VIEW_PROFILE_MODE;
        
        this.gender = null;
        this.citizenship = null;
        
        this.passwordSet = null;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public int getProfileMode() {
        return profileMode;
    }

    public void setProfileMode(int profileMode) {
        this.profileMode = profileMode;
    }

    public Map<String, Gender> getGender() {
        return gender;
    }

    public void setGender(Map<String, Gender> gender) {
        this.gender = gender;
    }

    public Map<Long, Citizenship> getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(Map<Long, Citizenship> citizenship) {
        this.citizenship = citizenship;
    }

    public PasswordSet getPasswordSet() {
        return passwordSet;
    }

    public void setPasswordSet(PasswordSet passwordSet) {
        this.passwordSet = passwordSet;
    }
    
    
}
