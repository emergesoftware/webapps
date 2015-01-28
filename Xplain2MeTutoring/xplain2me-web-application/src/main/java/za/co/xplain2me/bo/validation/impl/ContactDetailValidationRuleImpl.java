package za.co.xplain2me.bo.validation.impl;

import java.util.ArrayList;
import java.util.List;
import za.co.xplain2me.bo.validation.CellphoneNumberValidator;
import za.co.xplain2me.bo.validation.ContactDetailValidationRule;
import za.co.xplain2me.bo.validation.EmailAddressValidator;
import za.co.xplain2me.dao.ContactDetailDAO;
import za.co.xplain2me.dao.ContactDetailDAOImpl;
import za.co.xplain2me.entity.ContactDetail;

public class ContactDetailValidationRuleImpl implements ContactDetailValidationRule {
    
    private List<String> errors;
    
    public ContactDetailValidationRuleImpl() {
        this.errors = null;
    }

    @Override
    public List<String> validateContactDetail(ContactDetail contactDetail) {
        errors = new ArrayList<>();
        
        if (contactDetail == null) {
            errors.add("Contact Details are not provided.");
        }
        
        else {
            
            // validate the email address
            if (contactDetail.getEmailAddress() == null || 
                     contactDetail.getEmailAddress().isEmpty() || 
                    !EmailAddressValidator.isEmailAddressValid(contactDetail.getEmailAddress())) {
                errors.add("Email Address does not appear authentic.");
            }
            
            // if the number starts with 0 - then replace with 
            // the SA telephone prefix 27
            if (contactDetail.getCellphoneNumber() != null) {
                
                if (contactDetail.getCellphoneNumber().startsWith("0"))
                    contactDetail.setCellphoneNumber("27" + contactDetail
                            .getCellphoneNumber().substring(1));
                
                if (!contactDetail.getCellphoneNumber().startsWith("27"))
                    contactDetail.setCellphoneNumber("27" + contactDetail
                            .getCellphoneNumber());
            }

            // validate the contact number
            if (contactDetail.getCellphoneNumber() == null || 
                    contactDetail.getCellphoneNumber().isEmpty() || 
                    !CellphoneNumberValidator.isCellphoneNumberValid(
                            contactDetail.getCellphoneNumber())) {
                errors.add("Contact Number does not appear authentic.");
            }
            
            // validate to see if anyone else is
            // currently using this contact detail combination
            ContactDetailDAO contactDetailDao = new ContactDetailDAOImpl();
            if (!contactDetailDao.isContactDetailCompletelyUnique(contactDetail, false)) {
                errors.add("The contact details combination [Email Address and Contact Number] is " + 
                        "already being used by another person.");
            }
        }
        
        return errors;
    }
    
}
