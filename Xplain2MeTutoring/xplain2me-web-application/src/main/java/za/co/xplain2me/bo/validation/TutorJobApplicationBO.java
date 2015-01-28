package za.co.xplain2me.bo.validation;

import java.util.List;
import za.co.xplain2me.model.TutorJobApproval;

public interface TutorJobApplicationBO {
    
    /**
     * Validates the tutor job approval parameters.
     * @param approval
     * @return 
     */
    public List<String> validateTutorJobApprovalParameters(TutorJobApproval approval);
    
    /**
     * Gets the default email body for the
     * tutor approval.
     * 
     * @param approval
     * @return 
     */
    public String getDefaultEmailBodyForTutorApproval(TutorJobApproval approval);
}
