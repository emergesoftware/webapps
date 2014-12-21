package za.co.emergelets.xplain2me.bo.validation;

import java.util.List;
import za.co.emergelets.xplain2me.entity.PhysicalAddress;

public interface PhysicalAddressValidationRule {
    
    /**
     * Validates the physical address.
     * 
     * @param address
     * @return 
     */
    public List<String> isPhysicalAddressValid(PhysicalAddress address);
    
}
