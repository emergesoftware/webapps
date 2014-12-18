package za.co.emergelets.xplain2me.bo.validation;

import java.util.List;
import za.co.emergelets.xplain2me.entity.Person;

/**
 * VALIDATION RULES INTERFACE FOR:
 * 
 * PERSONAL INFORMATION
 * 
 * @author TSEPO MALEKA
 */
public interface PersonalInformationValidationRule {
    
    public static final int MIN_CHARS_FOR_PERSON_NAME = 3;
    public static final int MAX_CHARS_FOR_PERSON_NAME = 64;
    public static final int MINIMUM_ALLOWED_AGE = 6;
    
    /**
     * VALIDATES THE PERSONAL INFORMATION
     * 
     * @param person
     * @return 
     */
    public List<String> validatePersonalInformation(Person person);
    
}
