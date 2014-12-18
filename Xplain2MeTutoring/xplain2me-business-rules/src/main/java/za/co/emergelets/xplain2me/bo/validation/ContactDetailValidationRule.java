package za.co.emergelets.xplain2me.bo.validation;

import java.util.List;

public interface ContactDetailValidationRule {
    
    public List<String> isEmailAddressValid(String emailAddress);
    public List<String> isContactNumberValid(String contactNumber);
    
}
