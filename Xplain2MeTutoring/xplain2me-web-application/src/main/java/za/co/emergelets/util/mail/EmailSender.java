package za.co.emergelets.util.mail;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import za.co.emergelets.util.EmailAddressValidator;

public class EmailSender {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(EmailSender.class.getName(), null);
    
    // the application manager email address
    public static final String APP_MANAGER_EMAIL_ADDRESS = 
           "washington@xplain2me.co.za";
    
    // the Gmail Email Server properties
    private static final Properties properties = new Properties();
    static {
        
        LOG.info("... setting up Email properties ... ");
        
        properties.put("mail.smtp.host", "smtp.xplain2me.co.za");
        
        //properties.put("mail.smtp.auth", "true");
        //properties.put("mail.smtp.starttls.enable", "true");
        //properties.put("mail.smtp.host", "smtp.gmail.com");
        //properties.put("mail.smtp.port", "587");
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
     * @param isHtml 
     * @return  
     */
    public boolean sendEmail(String to, String subject, String body, 
            boolean isHtml) {
        
        try {
            
            if (!EmailAddressValidator.isEmailAddressValid(to) || 
                    subject == null || body == null) {
                LOG.warning("... invalid params found... not sending mail.");
                return false;
            }
            
            beginEmailServerAuthentication();
            
            LOG.info(" ... sending email started ...");
            
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS, 
                    "Xplain2Me Tutoring Services"));
            
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            
            message.setSubject(subject);
            
            if (isHtml)
                message.setContent(body, "text/html");
            else
                message.setText(body);

            Transport.send(message);
            
            LOG.info(" ... email sent ....");
            LOG.log(Level.INFO,"\nTO: {0}" + "\n" + "SUBJECT: {1}" + 
                    "\n" + "BODY:\n{2}" + "\n" + 
                    "-------------------------\n", 
                    new Object[]{to, subject, body});
            
            return true;
        }
        
        catch (UnsupportedEncodingException | MessagingException e) {
            
            LOG.log(Level.SEVERE,"... an error occured while trying to send " + 
                    "the email, details: {0}", e.getMessage());
            return false;
        }
        
    }
    
    /**
     * Sends a multi part email with attachments
     * 
     * @param to
     * @param subject
     * @param body
     * @param attachments
     * @param isHtml
     * @return 
     */
    public boolean sendEmailWithAttachments(String to, String subject, String body, 
            List<File> attachments, boolean isHtml) {
        
        try {
            
            // validate params
            if (!EmailAddressValidator.isEmailAddressValid(to)&& 
                    subject == null || body == null || 
                    (attachments == null || attachments.isEmpty())) {
                LOG.warning("... invalid params found... not sending mail.");
                return false;
            }
            
            // configure mail server authentication
            beginEmailServerAuthentication();
            
            LOG.info(" ... sending email started ...");
            
            // configure email headers
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS, 
                    "Xplain2Me Tutoring Services"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            
            // create multipart email
            Multipart multipart = new MimeMultipart();
            
            // set the message HTML body part
            BodyPart bodyPart = new MimeBodyPart();
            if (isHtml)
                bodyPart.setContent(body, "text/html");
            else
                bodyPart.setText(body); 
            
            // add the message body part to multipart
            multipart.addBodyPart(bodyPart);
            
            // create the file attachment parts
            BodyPart attachmentPart = null;
            FileDataSource fileDataSource = null;
            
            // attach each file to the multipart
            for (File file : attachments) {
                
                if (file != null && file.exists() && 
                        file.isDirectory() == false) {
                    
                    // create attachment part
                    attachmentPart = new MimeBodyPart();
                    fileDataSource = new FileDataSource(file);
                    attachmentPart.setDataHandler(new DataHandler(fileDataSource));
                    attachmentPart.setFileName(file.getName());
                    
                    // add the attachment part to multipart
                    multipart.addBodyPart(attachmentPart);
                }
            }
            
            // set the message content as multipart
            message.setContent(multipart);
            // send message
            Transport.send(message);
            
            LOG.info(" ... multipart email sent .... [no preview available]");
        
            return true;
        }
        
        catch (UnsupportedEncodingException | MessagingException e) {
            LOG.log(Level.SEVERE,"... an error occured while trying to send " + 
                    "the email, details: {0}", e.getMessage());
            return false;
        }
        
    }
    
    /**
     * Initiates the Gmail server authentication
     * 
     */
    private void beginEmailServerAuthentication() {
        
        LOG.info("... initialising Email server authentication... ");
        
        session = Session.getInstance(properties,
            new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(USERNAME, PASSWORD);
                }
            });
        
    }
    
    // the Gmail account username
    private static final String USERNAME = "noreply@xplain2me.co.za";
    //the Gmail email address
    private static final String FROM_EMAIL_ADDRESS = USERNAME;
    // the Gmail account password
    private static final String PASSWORD = "PzRMHM18Wm0W";
    
}