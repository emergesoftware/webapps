/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.emergelets.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public final class Utilities {
    
    public static final String HTML_BREAK = "<br/>";
    
    /**
     * Parses the String type date in the format
     * MM/DD/YYYY to a Date object
     * 
     * @param value
     * @return 
     */
    public static Date parseDate(String value) {
        
        Date date = null;
        SimpleDateFormat f = new SimpleDateFormat("MM/dd/yyyy");
        try {
            date = f.parse(value);
        } catch (ParseException ex) {
            Logger.getLogger(Utilities.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return date;
    }
    
    /**
     * Determines if the String is a 
     * valid numerical value
     * 
     * @param value
     * @return 
     */
    public static boolean isNaN(String value) {
        try {
            Double.parseDouble(value);
        }
        catch (NumberFormatException e) {
            return true;
        }
        return false;
    }
    
}
