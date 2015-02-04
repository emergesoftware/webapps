package za.co.xplain2me.webapp.controller.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.springframework.stereotype.Component;
import za.co.xplain2me.util.BooleanToText;
import za.co.xplain2me.bo.validation.CitizenshipType;
import za.co.xplain2me.bo.validation.EmailAddressValidator;
import za.co.xplain2me.util.ReCaptchaUtil;
import za.co.xplain2me.bo.validation.SouthAfricanIdentityTool;
import za.co.xplain2me.util.VerificationCodeGenerator;
import za.co.xplain2me.util.mail.EmailSender;
import za.co.xplain2me.util.mail.EmailTemplateFactory;
import za.co.xplain2me.util.mail.EmailTemplateType;
import za.co.xplain2me.bo.validation.CellphoneNumberValidator;
import za.co.xplain2me.dao.AcademicLevelDAO;
import za.co.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.xplain2me.dao.CitizenshipDAO;
import za.co.xplain2me.dao.GenderDAO;
import za.co.xplain2me.dao.SubjectDAO;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.BecomeTutorRequest;
import za.co.xplain2me.entity.BecomeTutorSupportingDocument;
import za.co.xplain2me.entity.Citizenship;
import za.co.xplain2me.entity.Gender;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.entity.SubjectsTutoredBefore;
import za.co.xplain2me.webapp.component.BecomeTutorForm;
import za.co.xplain2me.webapp.controller.GenericController;

@Component
public class BecomeTutorControllerHelper extends GenericController implements Serializable {
    
    private static final Logger LOG = 
            Logger.getLogger(BecomeTutorControllerHelper.class.getName(), null);
    
    private static final long MAX_FILE_UPLOAD_SIZE = 1000000 * 5;
    private static final int FILE_SIZE_THRESHOLD = 4 * 1024;
    public static final String WIN_FILE_REPOSITORY = "C:\\Users\\{0}\\Temp";
    public static final String LINUX_FILE_REPOSITORY = "/tmp";
    
    public BecomeTutorControllerHelper() {
    }
    
    /**
     * Verifies the window period for the 
     * @param request
     * @param form
     * @return 
     */
    public boolean checkVerificationCode(HttpServletRequest request, BecomeTutorForm form) {
        
        int count = 0;
        
        // the current date + time
        Date now = new Date();
        
        // get the verification code
        String verificationCode = getParameterValue(request, "verificationCode");
        
        // check if the code has not yet expired
        if (form.getDateGeneratedVerificationCode() == null || 
                form.getVerificationCode() == 0 ||
                Minutes.minutesBetween(new DateTime(form.getDateGeneratedVerificationCode()), 
                        new DateTime(now)).getMinutes() > 5) {
            count++;
            form.getErrorsEncountered().add("The verification time window has expired.");
            
            form.setVerificationCode(0);
            form.setDateGeneratedVerificationCode(null);
            
        }
        
        else {
            // check if the user entered a correct code
            if (Integer.parseInt(verificationCode) != form.getVerificationCode()) {
                count++;
                form.getErrorsEncountered().add("The verification code is incorrect.");
            }
        }
        
        return (count == 0);
        
    }
    
    /**
     * Generates the verification code
     * @param form 
     */
    public void generateVerificationCode(BecomeTutorForm form) {
        
        form.setVerificationCode(VerificationCodeGenerator.generateVerificationCode());
        form.setDateGeneratedVerificationCode(new Date());
        
    }
    
    /**
     * Sends the user a verification code
     * @param form 
     */
    public void sendVerificationCodeToUserAsync(final BecomeTutorForm form) {
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                
                String subject = null, 
                        body = "";

                if (form != null) {

                    // resolve the first provided
                    // first name
                    String firstName = form.getBecomeTutorRequest()
                            .getFirstNames().split(" ")[0];

                    // set the subject
                    subject = "Do not reply | Tutor Employment Request";

                    // injection values
                    Map<String, Object> values = new HashMap<>();
                    values.put("name", firstName);
                    values.put("verification_code", form.getVerificationCode());
                    values.put("request_type", "- Tutor Employment Application");
                    
                    try {
                        // set the body of the email
                        body = EmailTemplateFactory.injectValuesIntoEmailTemplate(
                                EmailTemplateFactory
                                        .getTemplateByType(EmailTemplateType.SendUserVerificatioCode),
                                values);
                    } catch (IOException ex) {
                        LOG.severe(" ... html template not found ... "); 
                    }
                    
                    EmailSender emailSender = new EmailSender();
                    emailSender.setToAddress(form.getBecomeTutorRequest().getEmailAddress());
                    emailSender.setSubject(subject);
                    emailSender.setHtmlBody(true);
                    
                    emailSender.sendEmail(body);

                }

            }
        }).start();
        
    }
    
    /**
     * Deletes all the uploaded files from the file system
     * 
     * @param form 
     */
    public void deleteFilesUploadedToServer(final BecomeTutorForm form) {
        
        if (form.getEmailAttachments() != null &&
                        form.getEmailAttachments().isEmpty() == false) {

            for (File file : form.getEmailAttachments()) {
                if (file.exists())
                    file.delete();
            }

        }
    
    }
    
    /**
     * Asynchronous thread to send an email to the 
     * applicant on receipt of the 
     * @param form
     */
    public void sendUserReceiptOfApplicationEmailAsync(final BecomeTutorForm form) {
        
        new Thread(new Runnable() {

            @Override
            public void run() {
                
                String subject = null, 
                        body = null;

                if (form != null) {

                    // resolve the first provided
                    // first name
                    String firstName = form.getBecomeTutorRequest()
                            .getFirstNames().split(" ")[0];

                    // set the subject
                    subject = "Do not reply | Tutor Employment Request";

                    // set the body of the email
                    body = "\n" +
                           "Howdy, " + firstName + "\n\n" +
                           "Thank you for your interest in joining our team of passionate tutors. \n" +
                           "We have received your request for a tutor employment application and we will be \n" +
                           "in contact with you very soon. \n\n" + 
                           "Do not hesitate to contact us - find our details from our\n" + 
                           "website: http:" + "//" + "xplain2me.co.za/\n\n" + 
                           "Yours truly, \n" +
                           "Xplain2Me Tutoring Services\n\n";
                    
                    EmailSender emailSender = new EmailSender();
                    emailSender.setToAddress(form.getBecomeTutorRequest().getEmailAddress());
                    emailSender.setSubject(subject);
                    emailSender.setHtmlBody(false);
                    
                    emailSender.sendEmail(body);

                }

            }
        }).start();
    }
    
    /**
     * Sends an email to the app manager
     * when a new tutor job application has
     * been completed.
     * 
     * @param form 
     */
    public void sendTutorApplicationNotificationEmailAsync(final BecomeTutorForm form) {
        
        if (form == null) return;
        
        new Thread(
                
                new Runnable() {

            @Override
            public void run() {
                
                // date formatter
                DateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy HH:mm");
                
                // the subject
                String subject = "New Tutor Employment Application";
                
                // the job application object
                BecomeTutorRequest request = form.getBecomeTutorRequest();
                
                // resolve subjects tutored before
                List<String> levelsTutoredBefore = new ArrayList<>();
                for (SubjectsTutoredBefore item : form.getSubjectsTutoredBefore()) 
                    levelsTutoredBefore.add(item.getSubject().getName());
                
                // set up values for injection
                Map<String, Object> values = new HashMap<>();
                values.put("date_completed", formatter.format(new Date()));
                values.put("job_application_id", request.getId());
                values.put("last_name", request.getLastName());
                values.put("first_names", request.getFirstNames());
                values.put("email_address", request.getEmailAddress());
                values.put("contact_number", request.getContactNumber());
                values.put("street_address", request.getStreetAddress());
                values.put("suburb", request.getSuburb());
                values.put("city", request.getCity());
                values.put("area_code", request.getAreaCode());
                values.put("id_number", request.getIdentityNumber());
                values.put("date_of_birth", formatter.format(request.getDateOfBirth()));
                values.put("citizenship", request.getCitizenship().getDescription());
                values.put("tutored_before", BooleanToText.format(request.isTutoredBefore(),
                        BooleanToText.YES_NO_FORMAT));
                values.put("subjects_tutored_before", EmailTemplateFactory
                        .constructHtmlListItemsFromList(levelsTutoredBefore, false));
                values.put("motivation", request.getMotivationalText());
                values.put("supporting_documents_attached", BooleanToText.format(
                        !form.getSupportingDocuments().isEmpty(), 
                        BooleanToText.YES_NO_FORMAT));
                
                // the body
                String body = null;
                try {
                    
                    body = EmailTemplateFactory.injectValuesIntoEmailTemplate(
                            EmailTemplateFactory.getTemplateByType(
                                    EmailTemplateType.NotifyNewTutorJobApplication), 
                            values);
                    
                } catch (IOException ex) {
                    
                    LOG.log(Level.SEVERE, 
                            "error while reading email template: {0}", ex.getMessage());
                }
                
                // send email
                EmailSender emailSender = new EmailSender();
                emailSender.setToAddress(EmailSender.APP_MANAGER_EMAIL_ADDRESS);
                emailSender.setSubject(subject);
                emailSender.setHtmlBody(true);
                
                if (form.getEmailAttachments() == null || 
                        form.getEmailAttachments().isEmpty()) {
                    
                    emailSender.sendEmail(body);
                }
                else {
                    
                    emailSender.setBccAddress(EmailSender.DOCUMENTS_EMAIL_ADDRESS);
                    emailSender.sendEmailWithAttachments(body, form.getEmailAttachments());
                }
                
                // remove all files after 
                // sending email
                deleteFilesUploadedToServer(form);
            }
        }
                
        ).start();
        
    }
    
    /**
     * Validates the HTTP Post 
     * content length
     * 
     * @param request
     * @param form
     * @return 
     */
    public boolean validateHttpPostContentLength(HttpServletRequest request, 
            BecomeTutorForm form) {
        
        // the content length header
        final String CONTENT_LENGTH = "Content-Length";
        // check if the request has this content length header
        if (request.getHeader(CONTENT_LENGTH) == null) {
            LOG.warning("No Content-Length header was found...");
            return false;
        }
        
        // get the size of the request
        Long contentLength = Long.parseLong(request.getHeader(CONTENT_LENGTH));
        // check if the length is within limit
        if (contentLength > MAX_FILE_UPLOAD_SIZE) {
            form.getErrorsEncountered().add("The files uploaded exceed the "
                    + "maximum upload size of 5MB in total size.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Populates the different
     * citizenship entries from the data source
     * 
     * @param form
     * @param dao 
     */
    public void populateCitizenshipEntries(BecomeTutorForm form, CitizenshipDAO dao) {
        if (form == null || dao == null) return;
        form.setCitizenships(dao.getAllCitizenships());
    }
    
    /**
     * Populates the entries 
     * for gender from repository
     * 
     * @param form
     * @param dao 
     */
    public void populateGenderEntries(BecomeTutorForm form, GenderDAO dao) {
        if (form == null || dao == null) return;
        form.setGender(dao.getAllGender());
    }
    
    /**
     * Reads off the http request parameter values
     * grabs the values and creates an instance of the
     * TutorRequest
     * 
     * @param request
     * @param form
     * @return 
     * @throws org.apache.commons.fileupload.FileUploadException 
     * @throws java.io.FileNotFoundException 
     * @throws java.security.NoSuchAlgorithmException 
     */
    public BecomeTutorRequest createBecomeTutorRequestInstance(HttpServletRequest request, 
            BecomeTutorForm form) 
            
            throws Exception { 
        
        // create new instance of the 
        BecomeTutorRequest item = new BecomeTutorRequest();
        form.setBecomeTutorRequest(item);
       
        // set the ggreement to terms of service
        item.setAgreedToTermsOfService(true);
        // set the date submitted
        item.setDateSubmitted(new Date());
        
        // initialise the suporting documents list
        if (item.getSupportingDocuments() == null)
            item.setSupportingDocuments(
                    new ArrayList<BecomeTutorSupportingDocument>());
        
        // initialse the academic levels tutored before list
        if (item.getSubjectsTutoredBefore() == null)
            item.setSubjectsTutoredBefore(new ArrayList<SubjectsTutoredBefore>()); 
        
        // initialise the list of files
        form.setEmailAttachments(new ArrayList<File>());
        
        // create a factory for disk-based file items
        DiskFileItemFactory factory = new DiskFileItemFactory();
        // set factory constraints
        factory.setSizeThreshold(FILE_SIZE_THRESHOLD);
        
        if (isWindowsOperationSystem()) {
            String user = System.getProperty("user.name");
            factory.setRepository(new File(WIN_FILE_REPOSITORY.replace("{0}", user)));
        }
        
        else 
            factory.setRepository(new File(LINUX_FILE_REPOSITORY));
        
        // create a new file upload handler
        ServletFileUpload upload = new ServletFileUpload(factory);
        
        // set overall request size constraint
        upload.setSizeMax(MAX_FILE_UPLOAD_SIZE);
        // parse the request
        List<FileItem> fileItems = upload.parseRequest(request);
        // process all the files and form fields
        Iterator iterator = fileItems.iterator();
        
        while (iterator.hasNext()) {
            
            FileItem fileItem = (FileItem)iterator.next();
            
            if (fileItem.isFormField())
                processFormField(fileItem, form);
            else
                processUploadedFile(fileItem, form);
            
        }
        
        return item;
    }
    
    /**
     * Validates the personal information of the
     * tutor applicant
     * 
     * @param form
     * @return 
     * @throws java.text.ParseException 
     */
    public boolean validatePersonalInformation(BecomeTutorForm form) throws ParseException {
        
        LOG.info("validating the personal information for the "
                + "tutor application form...");
        
        if (form == null) {
            LOG.warning("The form is NULL"); 
            return false;
        }
        
        BecomeTutorRequest request = 
                form.getBecomeTutorRequest();
        int count = 0;
        
         if (request.getLastName() == null || 
                request.getLastName().length() < 2) {
            count++;
            form.getErrorsEncountered().add("Last Name is too short.");
        }
        
        if (request.getFirstNames() == null || 
                request.getFirstNames().length() < 2) {
            count++;
            form.getErrorsEncountered().add("First Names is too short.");
        }
        
        if (request.getIdentityNumber() == null || request.getIdentityNumber().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("Identity /Passport Number is too short.");
        }
        
        if (request.getCitizenship().getId() == Citizenship.SOUTH_AFRICAN && 
                SouthAfricanIdentityTool.isValid(request.getIdentityNumber()) == false) {
            count++;
            form.getErrorsEncountered().add("Identity Number does not appear authentic "
                    + "for South African citizen.");
        }
        
        if (request.getCitizenship().getId() == Citizenship.SOUTH_AFRICAN && 
            SouthAfricanIdentityTool.getCitizenship(request.getIdentityNumber()) == CitizenshipType.Other) {
            
            count++;
            form.getErrorsEncountered().add("Your ID Number reflects that you are not "
                    + "a South African citizen by birth - please rectify.");
        }
        
        if (request.getCitizenship().getId() == Citizenship.SOUTH_AFRICAN && 
                request.getDateOfBirth() != null) {
            
            Date date = SouthAfricanIdentityTool.getDateOfBirth(request.getIdentityNumber());
            
            if (date != null &&
                    date.compareTo(request.getDateOfBirth()) != 0) {
                count++;
                form.getErrorsEncountered().add("Your ID Number does not match the Date of Birth "
                        + "you have provided.");
            }
            
        }
        
        Date now = new Date();
        
        if (request.getDateOfBirth() == null || 
                request.getDateOfBirth().after(now)) {
            count++;
            form.getErrorsEncountered().add("Date of birth has invalid format. "
                    + "Use: YYYY-MM-DD");
        }
        
        
        return (count == 0);
        
    }
    
    /**
     * Validates the contact information of the
     * tutor applicant
     * @param form
     * @return 
     */
    public boolean validateContactInformation(BecomeTutorForm form) {
        if (form == null)
            return false;
        
        BecomeTutorRequest request = 
                form.getBecomeTutorRequest();
        int count = 0;
        
        if (request.getContactNumber() == null || 
                !CellphoneNumberValidator.isCellphoneNumberValid(
                        "27" + request.getContactNumber().substring(1))) {
            count++;
            form.getErrorsEncountered().add("Contact Number does not appear authentic.");
        }
        
        if (request.getEmailAddress() == null || 
                !EmailAddressValidator.isEmailAddressValid(
                        request.getEmailAddress())) {
            count++;
            form.getErrorsEncountered().add("Email Address does not appear authentic.");
        }
        
        return (count == 0);
    }
    
    /**
     * Validates the address details
     * 
     * @param form
     * @return 
     */
    public boolean validateAddressInformation(BecomeTutorForm form) {
        
        if (form == null) return false;
        
        BecomeTutorRequest request = form.getBecomeTutorRequest();
        int count = 0;
        
        if (request.getStreetAddress() == null || 
                request.getStreetAddress().length() < 5) {
            count++;
            form.getErrorsEncountered().add("Street Address is too short.");
        }
        
        if (request.getSuburb() == null || 
                request.getSuburb().length() < 3) {
            count++;
            form.getErrorsEncountered().add("Suburb is too short.");
        }
        
        if (request.getCity() == null || 
                request.getCity().length() < 3) {
            count++;
            form.getErrorsEncountered().add("City is too short.");
        }
        
        if (request.getAreaCode() == null || 
                request.getAreaCode().length() < 4) {
            count++;
            form.getErrorsEncountered().add("Area Code is too short.");
        }
        
        return (count == 0);
    }
    
    /**
     * Validates the tutors prior experience
     * 
     * @param form
     * @return 
     */
    public boolean validateTutorPriorTutoringExperienceInformation(BecomeTutorForm form) {
        
        int count = 0;
        
        // get the tutor job request
        BecomeTutorRequest request = form.getBecomeTutorRequest();
        
        // check if the user has tutored before
        boolean tutoredBefore = request.isTutoredBefore();
        
        // if the user has tutored before, check that, at least
        // one academic level tutored before has been selected
        if (tutoredBefore) {
            if (form.getSubjectsTutoredBefore() == null || 
                    form.getSubjectsTutoredBefore().isEmpty()) {
                count++;
                form.getErrorsEncountered().add("You must select at least one subject "
                        + "you have tutored before.");
            }
        }
        
        // validate if the motivation text has been entered
        if (request.getMotivationalText() == null || 
                request.getMotivationalText().isEmpty()) {
            count++;
            form.getErrorsEncountered().add("You must enter some motivational text as to why you "
                    + "think Xplain2Me Tutoring should consider you.");
        }
        
        return (count == 0);
    }
    
    /**
     * Determines if tutor application is
     * completely unique
     * 
     * @param becomeTutorRequestDAO
     * @param form
     * @return 
     */
    public boolean isTutorApplicationCompletelyUnique(BecomeTutorRequestDAO becomeTutorRequestDAO, 
            BecomeTutorForm form) {
        
        if (form == null || becomeTutorRequestDAO == null)
            return false;
        
        boolean isCompletelyUnique = becomeTutorRequestDAO
                .isTutorApplicationUnique(form.getBecomeTutorRequest());
        
        if (!isCompletelyUnique)
            form.getErrorsEncountered().add(
                    "It looks like you have submitted a "
                            + "request for tutor job application.");
        
        return isCompletelyUnique;
    }
    
    /**
     * Verifies the reCAPCTHA
     * challenge
     * 
     * @param request
     * @param form
     * @return 
     */
    public boolean verifyReCaptchaCode(HttpServletRequest request, BecomeTutorForm form) {
        
        String challenge = form.getReCaptchaChallenge();
        String uresponse = form.getReCaptchaResponse();
        
        return ReCaptchaUtil.verifyReCaptchaCode(request, challenge, 
                uresponse, form.getErrorsEncountered());
       
    }

    /**
     * Process Uploaded File
     * 
     * @param item
     * @param form
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NoSuchAlgorithmException 
     */
    private void processUploadedFile(FileItem item, BecomeTutorForm form) 
            throws Exception {
        
        // check if file size is greater than 0kB
        long contentSize = item.getSize();
        if (contentSize == 0) {
            LOG.warning("... file not attached ....");
            return;
        }
        
        String fieldName = item.getFieldName();
        String fileName = item.getName();
        
        // applicant ID Number
        String identityNumber = form.getBecomeTutorRequest().getIdentityNumber();
        String label = "";
        
        // resolve the filename and label
        if (fieldName.equalsIgnoreCase("curriculumVitaeFile")) {
            fileName = "Curriculum_Vitae_" + identityNumber + ".pdf";
            label = "Curriculum Vitae";
        }
        
        if (fieldName.equalsIgnoreCase("matricCertificateFile")) {
            fileName = "Matric_Certificate_" + identityNumber + ".pdf";
            label = "Matric Certificate";
        }
        
        if (fieldName.equalsIgnoreCase("copyOfIDorPassportFile")) {
            fileName = "ID_Passport_Copy_" + identityNumber + ".pdf";
            label = "Identity / Passport Document";
        }
        
        if (fieldName.equalsIgnoreCase("highestObtainedQualificationFile")) {
            fileName = "Highest_Obtained_Qualification_" + identityNumber + ".pdf";
            label = "Highest Obtained Qualification";
        }
        
        if (fieldName.equalsIgnoreCase("academicTranscriptsFile")) {
            fileName = "Academic_Transcripts_" + identityNumber + ".pdf";
            label = "Academic Transcripts";
        }
        
        File file = null;
        if (isWindowsOperationSystem()) {
            String user = System.getProperty("user.name");
            file = (new File(WIN_FILE_REPOSITORY.replace("{0}", user) 
                    + "\\" + fileName));
        }
        
        else {
            file = new File(LINUX_FILE_REPOSITORY + "/" + fileName);
        }
        
        // create the file
        item.write(file);
        
        // read off the bytes of the file into an array
        byte[] byteFile = new byte[(int)file.length()];
        FileInputStream stream = new FileInputStream(file);
        stream.read(byteFile);
        
        // save the file data into an object 
        BecomeTutorSupportingDocument document = new BecomeTutorSupportingDocument();
        document.setRequest(form.getBecomeTutorRequest());
        document.setDocument(byteFile);
        document.setLabel(label);
        form.getSupportingDocuments().add(document);
        
        // close the input stream
        stream.close();
        
        // add file to the list of attachments
        form.getEmailAttachments().add(file);
        
    }

    /**
     * Process the Form Field
     * @param item
     * @param form 
     */
    private void processFormField(FileItem item, BecomeTutorForm form) {
        
        String name = item.getFieldName();
        String value = item.getString();
        
        BecomeTutorRequest request = form.getBecomeTutorRequest();
        
        if (value == null) value = "";
        
        // reCAPTCHA challenge field
        if (name.equalsIgnoreCase("recaptcha_challenge_field")) 
            form.setReCaptchaChallenge(value);
        
        // reCAPTCHA response field
        if (name.equalsIgnoreCase("recaptcha_response_field"))
            form.setReCaptchaResponse(value);
        
        // Last Name
        if (name.equalsIgnoreCase("lastName")) 
            request.setLastName(value.trim());
        
        // First Names
        if (name.equalsIgnoreCase("firstNames")) 
            request.setFirstNames(value.trim());
        
        // Gender
        if (name.equalsIgnoreCase("gender")) {
            for (Gender gender : form.getGender()) {
                if (gender.getId().equalsIgnoreCase(value)) {
                    request.setGender(gender);
                    break;
                }
            }
        }
        
        // Citizenship
        if (name.equalsIgnoreCase("citizenship")) {
            
            for (Citizenship citizenship : form.getCitizenships()) {
                if (citizenship.getId() == Integer.parseInt(value)) {
                    request.setCitizenship(citizenship);
                    break;
                }
            }
        }
        
        // Date Of Birth
        if (name.equalsIgnoreCase("dateOfBirth")) {
            
            try {
                Date dateOfBirth = new SimpleDateFormat("yyyy-MM-dd")
                        .parse(value);

                request.setDateOfBirth(dateOfBirth);
            }
            catch (ParseException e) {
                LOG.log(Level.SEVERE, " an error occured while parsing date of birth: {0}",
                        e.getMessage());
                request.setDateOfBirth(new Date());
            }
        }
        
        // ID / Passport Number
        if (name.equalsIgnoreCase("idNumber")) 
            request.setIdentityNumber(value.trim());
        
        // Email Address
        if (name.equalsIgnoreCase("emailAddress")) 
            request.setEmailAddress(value.trim());
        
        // Contact Number
        if (name.equalsIgnoreCase("contactNumber")) 
            request.setContactNumber(value.trim());
        
        // Street Address
        if (name.equalsIgnoreCase("streetAddress")) 
            request.setStreetAddress(value.trim());
        
        // Suburb
        if (name.equalsIgnoreCase("suburb")) 
            request.setSuburb(value.trim());
        
        // City
        if (name.equalsIgnoreCase("city")) 
            request.setCity(value.trim());
        
        // Area Code
        if (name.equalsIgnoreCase("areaCode")) 
            request.setAreaCode(value.trim());
        
        // Tutored Before
        if (name.equalsIgnoreCase("tutoredBefore")) {
            if (value.isEmpty() || value.equalsIgnoreCase("No"))
                request.setTutoredBefore(false);
            else request.setTutoredBefore(true);
        }
        
        // Academic Levels tutored before
        if (name.startsWith("subject_")) {
            
            Long id = Long.parseLong(value);
            
            SubjectsTutoredBefore tutoredBefore = new SubjectsTutoredBefore();
            tutoredBefore.setSubject(form.getSubjects().get(id));
            tutoredBefore.setRequest(request);
            
            form.getSubjectsTutoredBefore().add(tutoredBefore);
            
        }
        
        // Academic Levels tutored before
        if (name.equalsIgnoreCase("motivation")) {
            request.setMotivationalText(value);
        }
      
    }
    
    /**
     * Determines if the OS 
     * is Windows
     * 
     * @return 
     */
    public static boolean isWindowsOperationSystem() {
        
        String operatingSystemName = 
                System.getProperty("os.name").toLowerCase();
        
        return operatingSystemName.contains("windows");
        
    }

    /**
     * Populates the form with a list of all
     * subjects.
     * 
     * @param form
     * @param dao 
     */
    public void populateSubjects(BecomeTutorForm form, SubjectDAO dao) {
        
        if (form == null || dao == null) return;
        
        Map<Long, Subject> map = new TreeMap<>();
        List<Subject> subjects = dao.getAllSubjects();
        
        for (Subject item : subjects)
            map.put(item.getId(), item);
        
        form.setSubjects(map);
         
    }
}
