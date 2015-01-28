package za.co.xplain2me.util.mail;

import za.co.xplain2me.bo.validation.EmailAddressValidator;
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

public class EmailSender {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(EmailSender.class.getName(), null);
    
    // the application manager email address
    public static final String APP_MANAGER_EMAIL_ADDRESS = //"tsepomaleka@gmail.com";
           "wchigwaza@yahoo.com";
            
    // the documents email address
    public static final String DOCUMENTS_EMAIL_ADDRESS = 
            "documents@xplain2me.co.za";
    
    // the Gmail Email Server properties
    private static final Properties properties = new Properties();
    static {
        
        LOG.info("... setting up Email properties ... ");
        
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.port", "587");
    }
    
    // email session
    private static Session session = null;
    
    // other variables
    private String toAddress;
    private String ccAddress;
    private String bccAddress;
    private String subject;
    private boolean htmlBody;
    
    public EmailSender() {
        this.toAddress = null;
        this.subject = null;
        this.ccAddress = null;
        this.bccAddress = null;
        this.htmlBody = false;
    }

    /**
     * Gets the CC mail address
     * @return 
     */
    public String getCcAddress() {
        return ccAddress;
    }

    /**
     * Sets the CC main address
     * @param ccAddress 
     */
    public void setCcAddress(String ccAddress) {
        this.ccAddress = ccAddress;
    }

    /**
     * Gets the BCC main address
     * @return 
     */
    public String getBccAddress() {
        return bccAddress;
    }

    /**
     * Sets the BCC main address
     * @param bccAddress 
     */
    public void setBccAddress(String bccAddress) {
        this.bccAddress = bccAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isHtmlBody() {
        return htmlBody;
    }

    public void setHtmlBody(boolean htmlBody) {
        this.htmlBody = htmlBody;
    }

    
    /**
     * Sends an email
     * 
     * @param body 
     * @return  
     */
    public boolean sendEmail(String body) {
        
        try {
            
            if (toAddress == null ||
                    !EmailAddressValidator.isEmailAddressValid(toAddress) ||
                    subject == null || body == null) {
                LOG.warning("... invalid params found... not sending mail.");
                return false;
            }
            
            beginEmailServerAuthentication();
            
            LOG.info(" ... sending email started ...");
            
            Message message = new MimeMessage(session);
            
            // set the sender
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS, 
                    "Xplain2Me Tutoring Services"));
            
            // set the main receiver
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress));
            
            // add CC recepient
            if (ccAddress != null && 
                    EmailAddressValidator.isEmailAddressValid(ccAddress)) {
                message.addRecipient(Message.RecipientType.CC, 
                        new InternetAddress(ccAddress));
            }
            
            // add BCC recepient
            if (bccAddress != null && 
                    EmailAddressValidator.isEmailAddressValid(bccAddress)) {
                message.addRecipient(Message.RecipientType.BCC, 
                        new InternetAddress(bccAddress));
            }
            
            // set the subject
            message.setSubject(subject);
            
            // set the body
            if (htmlBody)
                message.setContent(body, "text/html");
            else
                message.setText(body);

            Transport.send(message);
            
            LOG.info(" ... email sent ....");
            LOG.log(Level.INFO,"\nTO: {0}" + "\n" + "SUBJECT: {1}" + 
                    "\n" + "BODY:\n{2}" + "\n" + 
                    "-------------------------\n", 
                    new Object[]{toAddress, subject, body});
            
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
     * @param body
     * @param attachments
     * @return 
     */
    public boolean sendEmailWithAttachments(String body, List<File> attachments) {
        
        try {
            
            // validate params
            if (toAddress == null ||
                    !EmailAddressValidator.isEmailAddressValid(toAddress) ||
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
            
            // set the sender
            message.setFrom(new InternetAddress(FROM_EMAIL_ADDRESS, 
                    "Xplain2Me Tutoring Services"));
            
            // set the recipients
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(toAddress));
            
              // add CC recepient
            if (ccAddress != null && 
                    EmailAddressValidator.isEmailAddressValid(ccAddress)) {
                message.addRecipient(Message.RecipientType.CC, 
                        new InternetAddress(ccAddress));
            }
            
            // add BCC recepient
            if (bccAddress != null && 
                    EmailAddressValidator.isEmailAddressValid(bccAddress)) {
                message.addRecipient(Message.RecipientType.BCC, 
                        new InternetAddress(bccAddress));
            }
            
            // set the subject
            message.setSubject(subject);
            
            // create multipart email
            Multipart multipart = new MimeMultipart();
            
            // set the message HTML body part
            BodyPart bodyPart = new MimeBodyPart();
            if (htmlBody)
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
    private static final String USERNAME = "support@emergesoftware.co.za";
    //the Gmail email address
    private static final String FROM_EMAIL_ADDRESS = USERNAME;
    // the Gmail account password
    private static final String PASSWORD = "ben10hasGrown";
    
}