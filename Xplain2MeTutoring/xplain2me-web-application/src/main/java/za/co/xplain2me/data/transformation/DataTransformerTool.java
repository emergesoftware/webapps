package za.co.xplain2me.data.transformation;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;
import javassist.Modifier;

public final class DataTransformerTool {
    
    private static final Logger LOG = 
            Logger.getLogger(DataTransformerTool.class.getName(), null);
    
    /**
     * Sets all the string fields to an uppercase
     * 
     * @param object 
     */
    public static void transformStringValuesToUpperCase(Object object) {
        
        try {
            
            Field[] fields = object.getClass().getDeclaredFields();
            Method setterMethod = null,
                   getterMethod = null;
            
            for (Field field : fields) {
                
                if (field.getType() == String.class && 
                    Modifier.isFinal(field.getModifiers()) == false &&
                    Modifier.isPublic(field.getModifiers()) == false &&
                    Modifier.isStatic(field.getModifiers()) == false) {
                    
                    LOG.log(Level.INFO, "\n\n> found field of String type: {0}", 
                            field.getName());
                    
                    // build a setter method for this field
                    String setter = "set" + field.getName().substring(0, 1).toUpperCase() + 
                            field.getName().substring(1);
                    
                    // build a getter method for this field
                    String getter = "get" + field.getName().substring(0,1).toUpperCase() + 
                            field.getName().substring(1);
                    
                    // get the setter method
                    setterMethod = object.getClass()
                            .getMethod(setter, new Class[] {String.class});
                    
                    // get the getter method
                    getterMethod = object.getClass()
                            .getMethod(getter, new Class[] {});
                    
                    if (setterMethod == null || getterMethod == null) {
                        LOG.info(" > could not find both getter + setter methods, skipping.");
                        continue;
                    }
                    
                    // get the value from the getter method
                    String value = getterMethod.invoke(object, 
                            new Object[0]).toString().toUpperCase();
                    
                    
                    if (value != null && value.isEmpty() == false) {
                        
                        setterMethod.invoke(object, new String[] {value});
                        
                        LOG.log(Level.INFO, "> built setter method: {0}", setterMethod.getName()); 
                        LOG.log(Level.INFO, "> built getter method: {0}", getterMethod.getName());
                        LOG.log(Level.INFO, "> value from this field: {0}", value); 
                        
                        LOG.log(Level.INFO, "> changing value to uppercase for "
                            + "field: {0}", field.getName());
                    }
                    
                    
                }
            }
        }
        
        catch (IllegalArgumentException | IllegalAccessException | NoSuchMethodException 
                | InvocationTargetException e) {
            LOG.log(Level.SEVERE, " ... error: {0}", e.getMessage());
        } 
    }
    
}
