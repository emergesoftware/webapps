package za.co.emergelets.util;

import java.util.Properties;
import java.util.logging.Logger;
 
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {
    
    // the logger
    private static Logger LOG = 
            Logger.getLogger(EmailSender.class.getName(), null);
    
    // the Gmail Email Server properties
    private static Properties properties = null;
    static {
        
        LOG.info("... setting up Gmail properties ... ");
        
        properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
    }
    
    // email session
    private static Session session = null;
    
    public EmailSender() {
    }
    
    /**
     * Sends an email
     * @param to
     * @param subject
     * @param body
     * @throws AddressException
     * @throws MessagingException 
     */
    public boolean sendEmail(String to, String subject, String body) {
        
        try {
            
            beginEmailServerAuthentication();
            
            LOG.info(" ... sending email started ...");
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            Transport.send(message);
            
            LOG.info(" ... email sent ....");
            LOG.info("\nTO: " + to + "\n" + 
                    "SUBJECT: " + subject + "\n" + 
                    "BODY:\n" + body + "\n" + 
                    "-------------------------\n");
            
            return true;
        }
        
        catch (Exception e) {
            
            LOG.severe("... an error occured while trying to send "
                    + "the email, details: " + e.getMessage());
            return false;
        }
        
    }
    
    
    /**
     * Initiates the Gmail server authentication
     * 
     */
    private void beginEmailServerAuthentication() {
        
        LOG.info("... initialising Gmail server authentication... ");
        
        session = Session.getInstance(properties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });
        
    }
    
    // the Gmail account username
    private static final String USERNAME = "info@emergesoftware.co.za";
    //the Gmail email address
    private static final String FROM_EMAIL_ADDRESS = USERNAME;
    // the Gmail account password
    private static final String PASSWORD = "ben10hasGrown";
    
}