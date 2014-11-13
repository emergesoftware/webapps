package za.co.emergelets.util;

public final class BooleanToText {
    
    public static final int YES_NO_FORMAT = 1;
    public static final int ON_OFF_FORMAT = 2;
    
    /**
     * Formats the output of the boolean
     * as human readable text
     * 
     * @param value
     * @param format
     * @return 
     */
    public static String format(boolean value, int format) {
        
        switch (format) {
            
            case BooleanToText.YES_NO_FORMAT: 
                if (value == true) return "Yes"; 
                else return "No";
                
            case BooleanToText.ON_OFF_FORMAT:
                if (value == true) return "On"; 
                else return "Off";
            
            default: return null;
        }
        
    }
    
}
