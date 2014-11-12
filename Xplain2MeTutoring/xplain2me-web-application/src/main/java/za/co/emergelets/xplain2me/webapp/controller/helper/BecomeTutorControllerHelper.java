package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import net.tanesha.recaptcha.ReCaptchaImpl;
import net.tanesha.recaptcha.ReCaptchaResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import za.co.emergelets.util.CellphoneNumberValidator;
import za.co.emergelets.util.CitizenshipType;
import za.co.emergelets.util.EmailAddressValidator;
import za.co.emergelets.util.ReCaptchaUtil;
import za.co.emergelets.util.SHA256Encryptor;
import za.co.emergelets.util.SouthAfricanIdentityTool;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.GenderDAO;
import za.co.emergelets.xplain2me.entity.AcademicLevel;
import za.co.emergelets.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;
import za.co.emergelets.xplain2me.entity.Citizenship;
import za.co.emergelets.xplain2me.entity.Gender;
import za.co.emergelets.xplain2me.webapp.component.BecomeTutorForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

public class BecomeTutorControllerHelper extends GenericController {
    
    private static final Logger LOG = 
            Logger.getLogger(BecomeTutorControllerHelper.class.getName(), null);
    
    private static final long MAX_FILE_UPLOAD_SIZE = 1000000 * 5;
    private static final int FILE_SIZE_THRESHOLD = 4 * 1024;
    private static final String WIN_FILE_REPOSITORY = "C:\\\\Users\\{0}\\Temp";
    private static final String LINUX_FILE_REPOSITORY = "/tmp";
    
    public BecomeTutorControllerHelper() {
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
            form.getErrorsEncountered().add("The file uploaded exceeds the "
                    + "maximum upload size of 5MB.");
            return false;
        }
        
        return true;
    }
    
    /**
     * Populates the academic levels
     * @param form
     * @param dao 
     */
    public void populateAcademicLevels(BecomeTutorForm form, AcademicLevelDAO dao) {
        if (form == null || dao == null) return;
        
        List<AcademicLevel> list = dao.getAllAcademicLevels();
        form.setAcademicLevels(new TreeMap<Long, AcademicLevel>()); 
        for (AcademicLevel level : list) {
            form.getAcademicLevels().put(level.getId(), level);
        }
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
        
        // if the request is not a multi-part then
        // return null
        /*if (ServletFileUpload.isMultipartContent(request)) {
            LOG.warning("HTTP POST request is not multipart..."); 
            return null;
        }*/
        
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
        if (item.getAcademicLevelsTutoredBefore() == null)
            item.setAcademicLevelsTutoredBefore(
                    new ArrayList<AcademicLevelsTutoredBefore>()); 
        
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
        
        LOG.info("validationg the personal information for the "
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
    
    private boolean validateTutorPriorTutoringExperienceInformation(BecomeTutorForm form) {
        
        int count = 0;
        
        // get the tutor job request
        BecomeTutorRequest request = form.getBecomeTutorRequest();
        
        // check if the user has tutored before
        boolean tutoredBefore = request.isTutoredBefore();
        
        // if the user has tutored before, check that, at least
        // one academic level tutored before has been selected
        if (tutoredBefore) {
            if (form.getAcademicLevelsTutoredBefore() == null || 
                    form.getAcademicLevelsTutoredBefore().isEmpty()) {
                count++;
                form.getErrorsEncountered().add("You must select at least one academic "
                        + "level you have tutored before.");
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
        
        /* String fieldName = item.getFieldName();
        String contentType = item.getContentType();
        boolean isInMemory = item.isInMemory();
        long sizeInBytes = item.getSize(); */
        
        String fileName = item.getName();
        
        // create supporting document instance
        // or get existing if the list is not empty
        BecomeTutorSupportingDocument document;
        if (form.getSupportingDocuments().isEmpty()) {
            document = new BecomeTutorSupportingDocument();
            form.getSupportingDocuments().add(document);
        }
        
        else {
            document = form.getSupportingDocuments().get(0);
        }
        
        // recreate a filename for this upload
        if (fileName.indexOf(".pdf") > 0) 
                fileName = fileName.replace(".pdf", "");
            
        fileName = SHA256Encryptor.computeSHA256(fileName, 
                String.valueOf(System.currentTimeMillis()))
                .toUpperCase();
        
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
        document.setRequest(form.getBecomeTutorRequest());
        document.setDocument(byteFile);
        
        // close the input stream
        stream.close();
        
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
        if (name.startsWith("academicLevel_")) {
            
            Long id = Long.parseLong(value);
            
            AcademicLevelsTutoredBefore tutoredBefore = new AcademicLevelsTutoredBefore();
            tutoredBefore.setAcademicLevel(form.getAcademicLevels().get(id));
            tutoredBefore.setRequest(request);
            
            form.getAcademicLevelsTutoredBefore().add(tutoredBefore);
            
        }
        
        // Supporting Document Label
        if (name.equalsIgnoreCase("supportingDocumentLabel")) {
            
            BecomeTutorSupportingDocument document;
            
            if (form.getSupportingDocuments().isEmpty()) {
                document = new BecomeTutorSupportingDocument();
                document.setLabel(value.trim());
                form.getSupportingDocuments().add(document);
            }
            else {
                document = form.getSupportingDocuments()
                        .get(0);
                document.setLabel(value.trim());
            }
                
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
    private boolean isWindowsOperationSystem() {
        
        String operatingSystemName = 
                System.getProperty("os.name").toLowerCase();
        
        return operatingSystemName.contains("windows");
        
    }
}
