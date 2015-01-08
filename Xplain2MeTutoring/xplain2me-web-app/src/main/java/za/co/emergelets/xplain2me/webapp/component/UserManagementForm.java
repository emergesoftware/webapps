package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.ProfileType;

public class UserManagementForm implements Serializable {
    
    // the map of user profiles
    private TreeMap<Long, Profile> userProfiles;
    // the list of errors encountered
    private List<String> errorsEncountered;
    // the map of profile types
    private TreeMap<Long, ProfileType> profileTypes;
    // the map for citizenship
    private TreeMap<Long, Citizenship> citizenships;
    // the map for Gender
    private TreeMap<String, Gender> gender;
    
    /**
     * Constructor
     */
    public UserManagementForm() {
        this.resetForm();
    }
    
    /**
     * Resets the form properties
     */
    public final void resetForm() {
        this.userProfiles = null;
        this.errorsEncountered = null;
        this.profileTypes = null;
        this.citizenships = null;
        this.gender = null;
    }

    public TreeMap<Long, Profile> getUserProfiles() {
        return userProfiles;
    }

    public void setUserProfiles(TreeMap<Long, Profile> userProfiles) {
        this.userProfiles = userProfiles;
    }

    public List<String> getErrorsEncountered() {
        return errorsEncountered;
    }

    public void setErrorsEncountered(List<String> errorsEncountered) {
        this.errorsEncountered = errorsEncountered;
    }

    public TreeMap<Long, ProfileType> getProfileTypes() {
        return profileTypes;
    }

    public void setProfileTypes(TreeMap<Long, ProfileType> profileTypes) {
        this.profileTypes = profileTypes;
    }

    public TreeMap<Long, Citizenship> getCitizenships() {
        return citizenships;
    }

    public void setCitizenships(TreeMap<Long, Citizenship> citizenships) {
        this.citizenships = citizenships;
    }

    public TreeMap<String, Gender> getGender() {
        return gender;
    }

    public void setGender(TreeMap<String, Gender> gender) {
        this.gender = gender;
    }
    
    
}
