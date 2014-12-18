package za.co.emergelets.xplain2me.bo.validation.impl;

import java.util.ArrayList;
import java.util.List;
import za.co.emergelets.xplain2me.bo.validation.PhysicalAddressValidationRule;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;

public class PhysicalAddressValidationRuleImpl implements PhysicalAddressValidationRule {

    @Override
    public List<String> isPhysicalAddressValid(PhysicalAddress address) {
        
        List<String> errors = new ArrayList<>();
        
        // Check for Address Line 1
        if (address.getAddressLine1() == null || 
                address.getAddressLine1().isEmpty()) {
            errors.add("Address Line 1 is required.");
        }
        
        // Check for the Town or Suburb
        if (address.getSuburb() == null || 
                address.getSuburb().isEmpty()) 
            errors.add("Suburb or Town is required.");
        
        // Check for the City
        if (address.getCity() == null || 
                address.getCity().isEmpty())
            errors.add("City is required.");
        
        // Check for the Area Code
        if (address.getAreaCode() == null || 
                address.getAreaCode().isEmpty())
            errors.add("Area Code is required.");
        
        return errors;
        
    }
    
}
