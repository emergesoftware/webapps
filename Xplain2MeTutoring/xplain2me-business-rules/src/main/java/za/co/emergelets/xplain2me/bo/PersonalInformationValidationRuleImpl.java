package za.co.emergelets.xplain2me.bo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import za.co.emergelets.xplain2me.entity.Person;

public class PersonalInformationValidationRuleImpl implements PersonalInformationValidationRule {
    

    public PersonalInformationValidationRuleImpl() {
    }
    
    @Override
    public List<String> validatePersonalInformation(Person person) {
        
        if (person == null) {
            return null;
        }
        
        List<String> errors = new ArrayList<>();
        
        // 1. First Names and Last Names each should have
        //    at least 3 characters or not more than 64 each.
        if (person.getLastName() == null || 
                person.getLastName().isEmpty() || 
                person.getLastName().length() < MIN_CHARS_FOR_PERSON_NAME ||
                person.getLastName().length() > MAX_CHARS_FOR_PERSON_NAME) {
            
            errors.add("LAST NAME: Only allowed between 3 - 64 number of characters.");
        }
        
        if (person.getFirstNames() == null || 
                person.getFirstNames().isEmpty() || 
                person.getFirstNames().length() < MIN_CHARS_FOR_PERSON_NAME ||
                person.getFirstNames().length() > MAX_CHARS_FOR_PERSON_NAME) {
            
            errors.add("FIRST NAMES: Only allowed between 3 - 64 number of characters.");
        }
        
        // 2. Date Of Birth must not be after the current date and time.
        //    Should also be at least 6 years old.
        Date today = new Date();
        
        if (person.getDateOfBirth() == null) {
            errors.add("DATE OF BIRTH: Required.");
        }
        
        else {
            
            DateTime now = new DateTime(today);
            DateTime then = new DateTime(person.getDateOfBirth());
            
            if (person.getDateOfBirth().after(today) || 
                    Days.daysBetween(then, now).getDays() < (365 * MINIMUM_ALLOWED_AGE)) {
                errors.add("DATE OF BIRTH: Date of birth does not appear authentic.");
            }
        }
        
        return errors;
        
    }
    
}
