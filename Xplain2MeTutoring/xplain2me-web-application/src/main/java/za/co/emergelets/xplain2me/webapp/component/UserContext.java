package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.Date;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.entity.Person;
import za.co.emergelets.xplain2me.entity.Profile;

@Component
public class UserContext implements Serializable {
    
    // the user profile
    private Profile profile;
    
    // the person details
    private Person person;
    
    // the time the user logged in
    private Date timeUserLoggedIn;
    
    public UserContext() {
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
    
    
    
}
