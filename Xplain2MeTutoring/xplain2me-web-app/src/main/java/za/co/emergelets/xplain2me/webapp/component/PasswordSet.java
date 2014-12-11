package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class PasswordSet implements Serializable {
    
    private String currentPassword;
    private String enteredPassword;
    private String reEnteredPassword;
    private String newPassword;
    
    public PasswordSet() {
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getEnteredPassword() {
        return enteredPassword;
    }

    public void setEnteredPassword(String enteredPassword) {
        this.enteredPassword = enteredPassword;
    }

    public String getReEnteredPassword() {
        return reEnteredPassword;
    }

    public void setReEnteredPassword(String reEnteredPassword) {
        this.reEnteredPassword = reEnteredPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
    
    
}
