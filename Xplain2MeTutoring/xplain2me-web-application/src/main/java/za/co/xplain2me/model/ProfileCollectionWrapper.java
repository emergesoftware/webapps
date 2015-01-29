package za.co.xplain2me.model;

import java.io.Serializable;
import java.util.List;
import za.co.xplain2me.entity.Profile;

public class ProfileCollectionWrapper implements Serializable {
    
    public static final int FOUND = 1;
    public static final int NOT_FOUND = 0;
    
    private String outcome;
    private int code;
    private List<Profile> profiles;
    
    public ProfileCollectionWrapper() {
        this.profiles = null;
        this.outcome = null;
        this.code = NOT_FOUND;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
    
}
