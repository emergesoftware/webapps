package za.co.xplain2me.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DateTimeUtils {
    
    private static final Logger LOG = 
            Logger.getLogger(DateTimeUtils.class.getName(), null);
    
    // the default date time formats - in use
    public static final String DEFAULT_DATE_TIME_FORMAT = 
            "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = 
            "yyyy-MM-dd";
    
    /**
     * Parses the string into a date using the
     * default date format: YYYY-MM-DD HH:MM:SS
     * 
     * @param value
     * @return 
     */
    public static Date parseDate(String value) {
        
        if (value == null || value.isEmpty() || 
                value.indexOf("-") <= 0) {
            LOG.warning("... invalid format for a date string for parsing ...");
            return null;
        }
        
        Date date = null;
        SimpleDateFormat formatter = 
                new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        try {
            date = formatter.parse(value);
        }
        catch (ParseException e) {
            LOG.log(Level.WARNING," ... an exception was thrown while "
                    + "parsing the date ... ({0})", e.getMessage()); 
        }
        
        return date;
    }
    
    /**
     * Formats the date object into
     * a string using the default 
     * date format - YYYY-MM-DD
     * 
     * @param date
     * @return 
     */
    public static String formatDate(Date date) {
        if (date == null) {
            LOG.warning(" ... date object is null - "
                    + "could not format ...");
            return "";
        }
        
        SimpleDateFormat formatter = 
                new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return formatter.format(date);
    }
    
     /**
     * Formats the date object into
     * a string using the default 
     * date format - YYYY-MM-DD HH:mm:SS
     * 
     * @param date
     * @return 
     */
    public static String formatDateTime(Date date) {
         if (date == null) {
            LOG.warning(" ... date object is null - "
                    + "could not format ...");
            return "";
        }
        
        SimpleDateFormat formatter = 
                new SimpleDateFormat(DEFAULT_DATE_TIME_FORMAT);
        return formatter.format(date);
    }
}
