package za.co.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class LoginForm implements Serializable {
    
    public static final int MAX_LOGIN_ATTEMPTS = 3;
    
    private String username;
    private String password;
    
    private boolean blocked;
    private int loginAttempts;
    
    private List<String> errorsEncountered;
    
    public LoginForm() {
        this.blocked = false;
        this.loginAttempts = 0;
        this.errorsEncountered = new ArrayList<String>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public int getLoginAttempts() {
        return loginAttempts;
    }

    public void setLoginAttempts(int loginAttempts) {
        this.loginAttempts = loginAttempts;
    }

    public List<String> getErrorsEncountered() {
        return errorsEncountered;
    }

    public void setErrorsEncountered(List<String> errorsEncountered) {
        this.errorsEncountered = errorsEncountered;
    }
    
}
