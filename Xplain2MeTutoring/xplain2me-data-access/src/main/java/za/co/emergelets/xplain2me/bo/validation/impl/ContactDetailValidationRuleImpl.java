package za.co.emergelets.xplain2me.bo.validation.impl;

import java.util.ArrayList;
import java.util.List;
import za.co.emergelets.xplain2me.bo.validation.CellphoneNumberValidator;
import za.co.emergelets.xplain2me.bo.validation.ContactDetailValidationRule;
import za.co.emergelets.xplain2me.bo.validation.EmailAddressValidator;

public class ContactDetailValidationRuleImpl implements ContactDetailValidationRule {
    
    private List<String> errors;
    
    public ContactDetailValidationRuleImpl() {
        this.errors = null;
    }
    
    @Override
    public List<String> isEmailAddressValid(String emailAddress) {
        
        errors = new ArrayList<>();
        
        if (emailAddress == null || emailAddress.isEmpty() || 
                EmailAddressValidator.isEmailAddressValid(emailAddress) == false) {
            errors.add("Email Address does not appear authentic.");
        }
        
        return errors;
    }

    @Override
    public List<String> isContactNumberValid(String contactNumber) {
        errors = new ArrayList<>();
        
        if (contactNumber != null) {
            if (contactNumber.startsWith("0"))
                contactNumber = "27" + contactNumber.substring(1);
        }
        
        if (contactNumber == null || contactNumber.isEmpty() || 
                CellphoneNumberValidator
                        .isCellphoneNumberValid(contactNumber) == false) {
            errors.add("Contact Number does not appear authentic.");
        }
        
        return errors;
    }
    
}
