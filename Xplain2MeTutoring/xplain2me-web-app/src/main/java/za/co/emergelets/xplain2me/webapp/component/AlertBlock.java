package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class AlertBlock implements Serializable {
    
    public static final int ALERT_BLOCK_INFORMATIVE = 1;
    public static final int ALERT_BLOCK_WARNING = 2;
    public static final int ALERT_BLOCK_ERROR = 3;
    
    private int alertBlockType;
    private List<String> alertBlockMessages;
    
    public AlertBlock(){
        this.alertBlockType = ALERT_BLOCK_INFORMATIVE;
        this.alertBlockMessages = new ArrayList<>();
    }
    
    public AlertBlock(int alertBlockType, String alertBlockMessage) {
        this();
        this.alertBlockType = alertBlockType;
        this.alertBlockMessages.add(alertBlockMessage);
    }

    public int getAlertBlockType() {
        return alertBlockType;
    }

    public AlertBlock setAlertBlockType(int alertBlockType) {
        this.alertBlockType = alertBlockType;
        return this;
    }

    public List<String> getAlertBlockMessages() {
        return alertBlockMessages;
    }

    public AlertBlock setAlertBlockMessages(List<String> alertBlockMessages) {
        this.alertBlockMessages = alertBlockMessages;
        return this;
    }
    
    public AlertBlock addAlertBlockMessage(String message) {
        
        if (this.alertBlockMessages == null)
            this.alertBlockMessages = new ArrayList<>();
        
        if (!this.alertBlockMessages.contains(message))
            this.alertBlockMessages.add(message);
        
        return this;
    }
    
}
