package za.co.emergelets.xplain2me.bo.validation.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Days;
import za.co.emergelets.xplain2me.bo.validation.PersonalInformationValidationRule;
import za.co.emergelets.xplain2me.bo.validation.SouthAfricanIdentityTool;
import za.co.emergelets.xplain2me.dao.PersonDAO;
import za.co.emergelets.xplain2me.dao.PersonDAOImpl;
import za.co.emergelets.xplain2me.entity.Citizenship;
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
        
        // First Names and Last Names each should have
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
        
        // Date Of Birth must not be after the current date and time.
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
        
        // Check if Gender is provided
        if (person.getGender() == null) {
            errors.add("GENDER: Gender is required.");
        }
        
        // check for Citizenship
        if (person.getCitizenship() == null) {
            errors.add("CITIZENSHIP: Citizenship is required.");
        }
        
        // Check if the user has a unique ID number not being
        // used by another person already.
        PersonDAO personDao = new PersonDAOImpl();
        if (!personDao.isPersonCompletelyUnique(person, false)) {
            errors.add("ID NUMBER: Identity or Passport Number provided is already "
                    + "in use by another person.");
        }
        
        else {
            // validate the SA ID Number against the Home Affairs algorithm
            if (person.getCitizenship() != null && 
                    person.getCitizenship().getId() == Citizenship.SOUTH_AFRICAN &&
                    !SouthAfricanIdentityTool.isValid(person.getIdentityNumber())) {
                errors.add("ID NUMBER: Identity Number does not appear authentic for " + 
                           "South African Citizen.");
            }
            
            // validate passport number for non-south africans
            else if (person.getCitizenship() != null &&
                    person.getCitizenship().getId() == Citizenship.NON_SOUTH_AFRICAN &&
                    (person.getIdentityNumber() == null || 
                    (person.getIdentityNumber().length() < 6 || person.getIdentityNumber().length() > 24)))
                errors.add("PASSPORT NUMBER: The passport number appears invalid.");
        }
        
        return errors;
        
    }
    
}
