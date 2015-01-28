package za.co.xplain2me.bo.validation;

import java.util.List;
import za.co.xplain2me.entity.ContactDetail;

public interface ContactDetailValidationRule {
    
    public List<String> validateContactDetail(ContactDetail contactDetail);
    
}
