package za.co.xplain2me.webapp;

/**
 * COMMON CSS AND JAVASCRIPT FILES
 * REFERENCE COMPONENT
 * 
 */
public final class CommonStylesheetsAndScripts {
    
    private static final String APP_CONTEXT = "";
    
    // NB: replace {0} with the bootswatch theme name
    public static final String BOOTSWATCH_CSS = 
            "http://maxcdn.bootstrapcdn.com/bootswatch/3.3.2/{0}/bootstrap.min.css";
            //"http://maxcdn.bootstrapcdn.com/bootswatch/3.3.0/{0}/bootstrap.min.css";
    
    public static final String DEFAULT_CSS = 
            APP_CONTEXT + "/assets/stylesheets/default-style.css";
    
    public static final String JQUERY_JS = 
            "http://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js";
    
    public static final String BOOTSTRAP_GLYPHICONS_CSS = 
            "http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css";
    
    public static final String BOOTSTRAP_JS = 
            "http://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js";
            //"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js";
    
    public static final String JQUERY_FORM_VALIDATION_JS = 
            "http://cdnjs.cloudflare.com/ajax/libs/jquery-form-validator/2.1.47/jquery.form-validator.min.js";
    
    public static final String BOOTSTRAP_DATEPICKER_CSS = 
            APP_CONTEXT + "/assets/datepicker/css/datepicker.css";
    
    public static final String BOOTSTRAP_DATEPICKER_JS = 
            APP_CONTEXT + "/assets/datepicker/js/bootstrap-datepicker.js";
    
    public static final String NORMALISE_CSS =
            APP_CONTEXT + "/assets/normalize/normalize.css";
}
