package za.co.xplain2me.util;

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
    
    /**
     * Determines if the value lies in between
     * the specified minimum and maximum ranges.
     * 
     * @param value
     * @param minimum
     * @param maximum
     * @return 
     */
    public static boolean isBetween(int value, int minimum, int maximum) {
        return (value >= minimum && value <= maximum);
    }
    
    public static boolean isBetween(double value, double minimum, double maximum) {
        return (value >= minimum && value <= maximum);
    }
    
}
