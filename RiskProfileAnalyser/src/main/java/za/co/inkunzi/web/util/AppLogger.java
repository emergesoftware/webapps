/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.web.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author user
 */
public class AppLogger {
    
    private static Logger LOG = null;
    private static final AppLogger INSTANCE = new AppLogger();
    
    private AppLogger(){
    }
    
    public static AppLogger getInstance(Class c) {
        LOG = Logger.getLogger(c.getCanonicalName());
        return INSTANCE;
    }
    
    public void info(String message) {
        LOG.log(Level.INFO, message);
    }
    
    public void severe(String message) {
        LOG.log(Level.SEVERE, message);
    }
    
    public void debug(String message) {
        LOG.log(Level.INFO, message);
    }
    
}
