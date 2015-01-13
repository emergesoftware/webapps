package za.co.emergelets.xplain2me.bo.validation;

import java.util.List;
import za.co.emergelets.xplain2me.entity.ContactDetail;

public interface ContactDetailValidationRule {
    
    public List<String> validateContactDetail(ContactDetail contactDetail);
    
}
