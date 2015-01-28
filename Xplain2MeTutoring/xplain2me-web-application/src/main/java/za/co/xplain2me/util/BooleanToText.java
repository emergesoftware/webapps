package za.co.xplain2me.util;

public final class BooleanToText {
    
    public static final int YES_NO_FORMAT = 1;
    public static final int ON_OFF_FORMAT = 2;
    public static final int GENDER_FORMAT = 3;
    public static final int ACTIVE_INACTIVE_FORMAT = 4;
    
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
                
            case BooleanToText.GENDER_FORMAT:
                if (value == true) return "Male";
                else return "Female";
                
            case BooleanToText.ACTIVE_INACTIVE_FORMAT:
                if (value == true) return "Active";
                else return "Inactive";
            
            default: return null;
        }
        
    }
    
}
