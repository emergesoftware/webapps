package za.co.emergelets.util;

public final class StringUtils {
    
    /**
     * IF THE STRING VALUE IS NULL, IT SETS ITS
     * VALUE TO AN EMPTY STRING, ELSE
     * JUST RETURNS THE ACTUAL STRING VALUE.
     * 
     * @param value
     * @return 
     */
    public static String nullToEmpty(String value) {
        if (value == null) value = "";
        return value;
    }
    
}
