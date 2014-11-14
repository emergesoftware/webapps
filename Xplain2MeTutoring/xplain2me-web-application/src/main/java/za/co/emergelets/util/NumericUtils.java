package za.co.emergelets.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class NumericUtils {
    
    private static final Logger LOG = Logger.getLogger(NumericUtils.class.getName(), null);
    
    /**
     * Determines if the String value is
     * a number or not
     * 
     * @param value
     * @return 
     */
    public static boolean isNaN(String value) {
        
        if (value == null || value.isEmpty())
            return true;
        
        try {
            Double.parseDouble(value.trim());
            return false;
        }
        catch (NumberFormatException e) {
            LOG.log(Level.SEVERE, " error: {0}", e.getMessage());
            return true;
        }
        
    }
    
}
