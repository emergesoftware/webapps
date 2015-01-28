package za.co.xplain2me.model;

import java.io.Serializable;
import java.util.List;
import za.co.xplain2me.entity.Profile;

public class ProfileCollectionWrapper implements Serializable {
    
    private String outcome;
    private List<Profile> profiles;
    
    public ProfileCollectionWrapper() {
        this.profiles = null;
        this.outcome = null;
    }
    
    public ProfileCollectionWrapper(List<Profile> profiles) {
        this();
        this.profiles = profiles;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }
    
}
