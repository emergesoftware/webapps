package za.co.xplain2me.bo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GeneralStatus implements Serializable {
    
    private OutcomeStatus status;
    private String successMessage;
    private List<String> errorsEncountered;
    private Throwable exception;
    
    public GeneralStatus() {
        this.reset();
    }
    
    public final void reset() {
        this.status = OutcomeStatus.UNKNOWN;
        this.errorsEncountered = new ArrayList<>();
        this.exception = null;
        this.successMessage = null;
    }

    public OutcomeStatus getStatus() {
        return status;
    }

    public void setStatus(OutcomeStatus status) {
        this.status = status;
    }

    public List<String> getErrorsEncountered() {
        return errorsEncountered;
    }

    public void setErrorsEncountered(List<String> errorsEncountered) {
        this.errorsEncountered = errorsEncountered;
    }

    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
