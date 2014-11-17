package za.co.emergelets.util.mail;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public final class EmailTemplateFactory {
    
    private static String TEMPLATES_DIRECTORY = null;
    static {
        if (isWindowsOperatingSystem()) {
            TEMPLATES_DIRECTORY = "C:\\Users\\" + 
                    System.getProperty("user.name") + 
                    "\\Temp\\email_templates\\";
        }
        
        else
            TEMPLATES_DIRECTORY = 
                    "/home/tsepo/xplain2me/deployment/email-templates/";
    }
    
    /**
     * Reads a template from the html 
     * file on the file system
     * 
     * @param file
     * @return
     * @throws IOException 
     */
    private static String readTemplateFromHtmlFile(String filename) throws IOException {
        
        StringBuilder template = new StringBuilder();
        BufferedReader reader;
        
        File file = new File(TEMPLATES_DIRECTORY + filename);
        reader = createBufferedReader(file);

        while (reader.ready()) {
            template.append(reader.readLine())
                    .append("\n");
        }

        reader.close();
        
        return template.toString();
        
    }
    
    /**
     * Determines if the current OS is
     * Windows or not
     * 
     * @return 
     */
    private static boolean isWindowsOperatingSystem() {
        return System.getProperty("os.name")
                .toLowerCase().contains("windows");
    }
    
    /**
     * Creates an instance of the buffered
     * reader for a file
     * 
     * @param file
     * @return
     * @throws FileNotFoundException 
     */
    private static BufferedReader createBufferedReader(File file) throws FileNotFoundException {
        
        if (file == null)
            return null;
        
        return new BufferedReader(new FileReader(file));
        
    }
    
    /**
     * Injects or replaces the keys on the
     * HTML template with values
     * 
     * @param template
     * @param values
     * @return 
     */
    public static String injectValuesIntoEmailTemplate(String template, 
            Map<String, Object> values) {
        
        if (template == null || values == null || 
                values.isEmpty()) 
            return template;
        
        for (String key : values.keySet()) {
            if (template.contains("{" + key + "}")) {
                
                template = template.replace("{" + key + "}", 
                        values.get(key).toString());
            }
        }
        
        return template;
    }
    
    /**
     * Gets the HTML Email template
     * per type given
     * 
     * @param type
     * @return
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static String getTemplateByType(EmailTemplateType type) 
            throws FileNotFoundException, IOException {
        
        String template = null;
        
        switch (type) {
            
            case NotifyNewTutorRequest:
                template = readTemplateFromHtmlFile("notify_new_tutor_request.html");
                break;
            
            case NotifyNewQuoteRequest:
                template = readTemplateFromHtmlFile("notify_new_quote_request.html");
                break;
            
            case NotifyNewTutorJobApplication:
                template = readTemplateFromHtmlFile("notify_new_tutor_job_application.html");
                break;
                
                
            case SendUserVerificatioCode:
                template = readTemplateFromHtmlFile("send_user_verification_code.html");
                break;
        
        }
        
        return template;
        
    }
    
    /**
     * Constructs an HTML ordered or unordered list element
     * as a string
     * 
     * @param items
     * @param isOrderedList
     * @return 
     */
    public static String constructHtmlListItemsFromList(
            List items, boolean isOrderedList) {
        
        if (items == null || items.isEmpty()) 
            return "";
        
        StringBuilder builder = new StringBuilder();
        
        if (!isOrderedList)
            builder.append("<ul>\n");
        else
            builder.append("<ol>\n");
        
        for (Object item : items) {
            
            if (item == null) continue;
            
            builder.append("<li>")
                    .append(item.toString())
                    .append("</li>\n");
        }
        
        if (!isOrderedList)
            builder.append("</ul>\n");
        else
            builder.append("</ol>\n");
        
        return builder.toString();
        
    }
    
}
