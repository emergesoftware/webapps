package za.co.emergelets.xplain2me.webapp.component;

import java.io.Serializable;
import org.springframework.stereotype.Component;

@Component
public class ErrorForm implements Serializable {
    
    private String errorTitle;
    private String errorDescription;
    private String returnUrl;
    
    public ErrorForm() {
    }
    
    public void resetForm() {
        this.errorDescription = null;
        this.errorTitle = null;
        this.returnUrl = null;
    }

    public String getErrorTitle() {
        return errorTitle;
    }

    public void setErrorTitle(String errorTitle) {
        this.errorTitle = errorTitle;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    
    
}
