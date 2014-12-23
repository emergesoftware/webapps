package za.co.emergelets.xplain2me.webapp.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.zip.ZipOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.util.mail.EmailSender;
import za.co.emergelets.xplain2me.bo.validation.EmailAddressValidator;
import za.co.emergelets.xplain2me.bo.validation.TutorJobApplicationBO;
import za.co.emergelets.xplain2me.bo.validation.impl.TutorJobApplicationBOImpl;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;
import za.co.emergelets.xplain2me.model.TutorJobApproval;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.TutorJobApplicationForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.TutorJobApplicationControllerHelper;

@Controller
public class TutorJobApplicationController extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(TutorJobApplicationController.class.getName(), null);
    
    public static final int TUTOR_APPROVAL_FAILED = 300;
    public static final int TUTOR_APPROVAL_SENT = 301;
    
    public static final String SEARCH_MODE_DISPLAY_FORM = "display-form";
    public static final String SEARCH_MODE_SHOW_RESULTS = "show-results";
    
    public static final int SEARCH_TUTOR_JOB_APPLICATIONS_FAILED = 302;
    public static final int SEARCH_TUTOR_JOB_APPLICATIONS_NOT_FOUND = 303;
    
    public static final int SEARCH_TYPE_SEQUENCE_ID = 1;
    public static final int SEARCH_TYPE_IDENTITY_NUMBER = 2;
    public static final int SEARCH_TYPE_EMAIL_ADDRESS = 3;
    public static final int SEARCH_TYPE_CONTACT_NUMBER = 4;
    
    @Autowired
    private TutorJobApplicationControllerHelper helper;
    
    public TutorJobApplicationController() {
    }
    
    /**
     * Shows the browser page for all the
     * tutor job applications made.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.BROWSE_TUTOR_JOB_APPLICATIONS, 
                    method = RequestMethod.GET)
    public ModelAndView showTutorJobApplicationsBrowserPage(HttpServletRequest request) {
        
        LOG.info("... show tutor job applications browser page ...");
        
        // check for the form
        // from the session scope
        TutorJobApplicationForm form = (TutorJobApplicationForm)
                getFromSessionScope(request, TutorJobApplicationForm.class);
        if (form == null) {
            form = new TutorJobApplicationForm();
        }
        
        // check for the page number
        String pageNumber = getParameterValue(request, "page");
        
        if (pageNumber == null || pageNumber.isEmpty() || 
                NumericUtils.isNaN(pageNumber)) {
            form.setCurrentPageNumber(1);
        }
        
        else {
            form.setCurrentPageNumber(Integer.parseInt(pageNumber));
        }
        
        // load the become tutor requests
        helper.loadTutorJobApplications(form);
        
        // save the form to the
        // session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.BROWSE_TUTOR_JOBS_APPLICATIONS);
    }
    
    /**
     * Shows the page with all the details of the
     * tutor employment application.
     * 
     * @param request
     * @param id
     * @return 
     */
    @RequestMapping(value = RequestMappings.VIEW_TUTOR_JOB_APPLICATION_DETAILS, 
            method = RequestMethod.GET, 
            params = "id")
    public ModelAndView viewAllTutorJobApplicationDetails(HttpServletRequest request, 
            @RequestParam(value = "id")String id) {
        
        LOG.info("... show tutor job application details ...");
        
        // variables
        AlertBlock alertBlock = null;
        BecomeTutorRequest becomeTutorRequest = null;
        
        // check if the parameter is valid
        if (id == null || id.isEmpty() || 
            NumericUtils.isNaN(id) || Long.parseLong(id) < 1) {
            
            LOG.warning(" ... the id param is not valid ...");
            
            // create an alert block and save to the
            // request scope
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "The tutor job application ID does not appear to be valid.");
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.VIEW_TUTOR_JOB_APPLICATION_DETAILS);
        }
        
        // pull the tutor job application from the 
        // data store
        BecomeTutorRequestDAO dao = new BecomeTutorRequestDAOImpl();
        becomeTutorRequest = dao.getBecomeTutorRequest(Long.parseLong(id));
        
        // if no entity was found
        if (becomeTutorRequest == null) {
            
            LOG.warning(" ... tutor job application with the ID could not be found ...");
            
            // create an alert block and save to the
            // request scope
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "The tutor job application with the specified "
                            + "sequence ID could not be found.");
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.VIEW_TUTOR_JOB_APPLICATION_DETAILS);
        }
        
        else {
            
            // save the entity to the request
            // scope
            saveToRequestScope(request, becomeTutorRequest);
            
            // return a view
            return createModelAndView(Views.VIEW_TUTOR_JOB_APPLICATION_DETAILS);
        }
        
    }
    
    /**
     * Displays the page for completing an approval of
     * a tutor job application.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.APPROVE_TUTOR_JOB_APPLICATION, 
            method = RequestMethod.GET)
    public ModelAndView approveTutorJobApplication(HttpServletRequest request) {
        
        // variables
        AlertBlock alertBlock = null;
        
        // check the form from the
        // session scope
        TutorJobApplicationForm form = (TutorJobApplicationForm)
                getFromSessionScope(request, TutorJobApplicationForm.class);
        if (form == null) 
            form = new TutorJobApplicationForm();
        else form.resetForm();
        
        String id = getParameterValue(request, "id");
        
        // check the parameter
        // check if the parameter is valid
        if (id == null || id.isEmpty() || 
            NumericUtils.isNaN(id) || Long.parseLong(id) < 1) {
            
            LOG.warning(" ... the id param is not valid ...");
            
            // create an alert block and save to the
            // request scope
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "The tutor job application ID does not appear to be valid.");
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.APPROVE_TUTOR_JOB_APPLICATION);
        }
        
        // pull the tutor job application from the 
        // data store
        BecomeTutorRequestDAO dao = new BecomeTutorRequestDAOImpl();
        BecomeTutorRequest becomeTutorRequest = dao.getBecomeTutorRequest(
                Long.parseLong(id));
        
        // if no entity was found
        if (becomeTutorRequest == null) {
            
            LOG.warning(" ... tutor job application with the ID could not be found ...");
            
            // create an alert block and save to the
            // request scope
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR,
                    "The tutor job application with the specified "
                            + "sequence ID could not be found.");
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.APPROVE_TUTOR_JOB_APPLICATION);
        }
        
        else {
            // resolve any alerts
            String result = getParameterValue(request, "result");
            if (result != null && result.isEmpty() == false) {
                
                switch (Integer.parseInt(result)) {
                    
                    case TUTOR_APPROVAL_FAILED:
                        alertBlock = (AlertBlock)getFromSessionScope(request, AlertBlock.class);
                        removeFromSessionScope(request, AlertBlock.class);
                        break;
                        
                    case TUTOR_APPROVAL_SENT:
                        alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_INFORMATIVE,
                                "The interview invitation or job approval was successfully processed "
                                        + "for sending.");
                        break;
                }
                
                if (alertBlock != null && !alertBlock.getAlertBlockMessages().isEmpty())
                    saveToRequestScope(request, alertBlock);
            }
            
            // save the form to the session
            // scope
            form.setTutorJobApplication(becomeTutorRequest);
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView(Views.APPROVE_TUTOR_JOB_APPLICATION);
        }
        
    }
    
    
    /**
     * Processes the request to send a job approval (interview
     * invite) to the applicant.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.APPROVE_TUTOR_JOB_APPLICATION, 
            method = RequestMethod.POST, 
            params = {"dateOfInterview", "hourOfInterview", 
                      "minuteOfInterview", "location", 
                      "additionalNotes"})
    public ModelAndView sendTutorJobApplicationApproval(
            HttpServletRequest request) {
        
        // variables
        AlertBlock alertBlock = null;
        Map<String, String> parameters = null;
        
        // check the form from the
        // session scope
        TutorJobApplicationForm form = (TutorJobApplicationForm)
                getFromSessionScope(request, TutorJobApplicationForm.class);
        
        if (form == null || 
                form.getTutorJobApplication() == null) {
            
            LOG.info("... no form found in the session ...");
            parameters = new TreeMap<>();
            parameters.put("invalid-request", "1");
            
            return sendRedirect(RequestMappings.DASHBOARD_OVERVIEW, parameters);
        }
        
        // create an instance of the tutor job application
        final TutorJobApproval approval = helper.createTutorJobApproval(request, form);
        
        // validate the tutor job approval
        final TutorJobApplicationBO tutorJobApplicationBO = new TutorJobApplicationBOImpl();
        List<String> errors = tutorJobApplicationBO.validateTutorJobApprovalParameters(approval);
        
        // if there were errors
        if (errors.isEmpty() == false) {
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            
            for (String message : errors)
                alertBlock.addAlertBlockMessage(message);
            
            // save the alert block in the
            // session scope
            saveToSessionScope(request, alertBlock);
            
            // send a redirect
            parameters = new TreeMap<>();
            parameters.put("id", String.valueOf(approval.getAttachedJobApplication().getId()));
            parameters.put("result", String.valueOf(TUTOR_APPROVAL_FAILED));
            
            return sendRedirect(RequestMappings.APPROVE_TUTOR_JOB_APPLICATION,
                    parameters);
        }
        
        else {
        
            // prepare an email to be sent
            new Thread(new Runnable(){

                @Override
                public void run() {
                    
                    EmailSender email = new EmailSender();
                    email.setSubject("No reply | Interview Invitation | Xplain2Me Tutoring");
                    email.setToAddress(approval.getAttachedJobApplication()
                            .getEmailAddress().toLowerCase());
                    email.setHtmlBody(true); 

                    email.sendEmail(tutorJobApplicationBO
                            .getDefaultEmailBodyForTutorApproval(approval));
                    
                }
            }).start();
            
            // get the user context
            UserContext context = (UserContext)getFromSessionScope(request,
                    UserContext.class);
            
            // log an event
            SystemAuditManager.logAuditAsync(EventTypes.SEND_TUTOR_APPLICANT_INTERVIEW_INVITATION,
                    context.getProfile().getPerson().getUser(), 
                    approval.getAttachedJobApplication().getId(), 
                    null,
                    request.getRemoteAddr(),
                    request.getHeader("User-Agent"),
                    0, true);
            
            // send a redirect
            parameters = new TreeMap<>();
            parameters.put("id", String.valueOf(approval.getAttachedJobApplication().getId()));
            parameters.put("result", String.valueOf(TUTOR_APPROVAL_SENT));
            
            return sendRedirect(RequestMappings.APPROVE_TUTOR_JOB_APPLICATION,
                    parameters);
            
        }
    }
    
    /**
     * Checks if the tutor job application had any supporting documents.
     * 
     * @param request
     * @param id
     * @return 
     */
    @RequestMapping(value = RequestMappings.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS, 
            method = RequestMethod.GET,
            params = {"id"})
    public ModelAndView checkIfTutorJobApplicationHasSupportingDocuments(
            HttpServletRequest request, 
            @RequestParam(value = "id", required = true)String id) {
        
        // variables
        AlertBlock alertBlock = null;
        Map<String, String> parameters = null;
        
        // check if there is a session
        TutorJobApplicationForm form = (TutorJobApplicationForm)getFromSessionScope(
                request, TutorJobApplicationForm.class);
        if (form == null)
            form = new TutorJobApplicationForm();
        else form.resetForm();
       
        
        // check if the parameter is valid
        if (id == null || id.isEmpty() || 
            NumericUtils.isNaN(id) || Long.parseLong(id) < 1) {
            
            LOG.warning(" ... the id param is not valid ...");
            
            // create an alert block
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR, 
                    "The sequence ID for the tutor job application is not valid."
            );
            
            // save the alert block to the request
            // scope
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS);
            
        }
        
        // get the supporting documents
        BecomeTutorRequestDAO dao = new BecomeTutorRequestDAOImpl();
        List<BecomeTutorSupportingDocument> documents = 
                dao.getBecomeTutorSupportingDocuments(Long.parseLong(id));
        form.setSupportingDocuments(documents); 
        
        if (documents == null || documents.isEmpty()) {
            
            // create an alert block
            alertBlock = new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR, 
                    "The tutor job application does not "
                            + "have any supporting documents available."
            );
            
            // save the alert block to the request
            // scope
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView(Views.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS);
            
        }
        
        else {
            
            // save the form to the
            // session scope
            saveToSessionScope(request, form);
            
            // send a redirect to
            // download these supporting documents
            parameters = new TreeMap<>();
            parameters.put("found", "yes");
            
            return sendRedirect(RequestMappings.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS, 
                    parameters);
            
        }
    }
    
    /**
     * Downloads the supporting documents
     * 
     * @param request
     * @param response
     * @throws java.io.IOException
     */
    @RequestMapping(value = RequestMappings.DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS, 
            method = RequestMethod.GET, 
            params = {"found", "rand"})
    public void downloadSupportingDocuments(HttpServletRequest request, HttpServletResponse response) 
            throws IOException {
        
        // check the form from the
        // session scope
        TutorJobApplicationForm form = (TutorJobApplicationForm)
                getFromSessionScope(request, TutorJobApplicationForm.class);
        
        if (form == null || (form.getSupportingDocuments() == null || 
                form.getSupportingDocuments().isEmpty())) {
            LOG.warning(" ... no form was found in the session ...");
            // abort the download
            return;
        }
            
        // get the list of supporting documents
        List<BecomeTutorSupportingDocument> documents = form.getSupportingDocuments();
        
        // determine the base working directory
        final String directory = helper.determineCompressionWorkingFolder();
        
        // map for keeping all the files
        Map<String, File> files = helper.writeSupportingDocumentsToFile(documents, directory);
        
        // build the compressed filename
        String compressedFilename = directory + "supporting_documents_" + 
                System.currentTimeMillis() + "_" 
                + documents.get(0).getRequest().getId() + ".zip";
        
        // create new zip file
        File compressedFile = new File(compressedFilename);
        
        // create the ZIP output stream
        FileOutputStream fileOutputStream = new FileOutputStream(compressedFile);
        ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
        
        // for each filename in the documents
        for (String filename : files.keySet()) {
            helper.addFileEntryToZipArchive(filename, zipOutputStream);
        }
        
        // close zip entries
        zipOutputStream.close();
        fileOutputStream.close();
        
        // set the response headers
        helper.appendHttpHeadersForZipArchiveFile(response, compressedFile);
        
        // get output stream of the response
        OutputStream outputStream = response.getOutputStream();
        // set a new file input stream for the zip file
        FileInputStream inputStream = new FileInputStream(compressedFile);
        // the buffer
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
        
        // write bytes read from the input stream into the output stream
        while ((bytesRead = inputStream.read(buffer)) >= 0) 
            outputStream.write(buffer, 0, bytesRead);
 
        inputStream.close();
        outputStream.close();
    }
    
    /**
     * Shows the search form or the search results - 
     * depending on the search mode parameter.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, 
            method = RequestMethod.GET)
    public ModelAndView showSearchTutorJobApplicationsPage(HttpServletRequest request) {
        
        // check for the search mode parameter
        String searchMode = getParameterValue(request, "search-mode");
        
        if (searchMode == null || searchMode.equalsIgnoreCase(SEARCH_MODE_DISPLAY_FORM)) {
            
            // check if there is 
            // a form in the session scope
            TutorJobApplicationForm form = (TutorJobApplicationForm)getFromSessionScope(request,
                    TutorJobApplicationForm.class);
            if (form == null)
                form = new TutorJobApplicationForm();
            else
                form.resetForm();
            
            // resolve parameter messages
            helper.resolveAnySearchParameterMessages(request);
            
            // save the form in the
            // session scope
            saveToSessionScope(request, form);
            
            // return a view
            return createModelAndView(Views.SEARCH_TUTOR_JOB_APPLICATIONS_FORM);
        }
        
        else if (searchMode.equalsIgnoreCase(SEARCH_MODE_SHOW_RESULTS)) {
            
            // check if there is 
            // a form in the session scope
            TutorJobApplicationForm form = (TutorJobApplicationForm)getFromSessionScope(request,
                    TutorJobApplicationForm.class);
            if (form == null) {
                
                LOG.warning(" ... no form in the session - invalid request ... ");
                
                Map<String, String> parameters = new HashMap<>();
                parameters.put("search-mode", SEARCH_MODE_DISPLAY_FORM);
                parameters.put("invalid-request", "yes");
                
                return sendRedirect(RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, parameters);
            }
            
            // return a view
            return createModelAndView(Views.TUTOR_JOB_APPLICATIONS_SEARCH_RESULTS);
            
        }
        
        return sendRedirect(RequestMappings.DASHBOARD_OVERVIEW + 
                "?invalid-request=1");
    }
    
    /**
     * Processes the request to search for 
     * tutor job applications.
     * 
     * @param request
     * @param searchType
     * @param searchKeyword
     * @return 
     */
    @RequestMapping(value = RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, 
            method = RequestMethod.POST, 
            params = {"searchType", "searchKeyword"})
    public ModelAndView searchTutorJobApplicationsRequest(
            HttpServletRequest request, 
            @RequestParam(value = "searchType")String searchType, 
            @RequestParam(value = "searchKeyword")String searchKeyword) {
        
        Map<String, String> parameters = null;
        
        // check if there is a form
        // in the session scope
        TutorJobApplicationForm form = (TutorJobApplicationForm)getFromSessionScope(request,
                TutorJobApplicationForm.class);
        if (form == null) {

            LOG.warning(" ... no form in the session - invalid request ... ");

            parameters = new HashMap<>();
            parameters.put("search-mode", SEARCH_MODE_DISPLAY_FORM);
            parameters.put("invalid-request", "yes");

            return sendRedirect(RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, parameters);
        }
        
        // validate the parameters
        if (searchType == null || searchType.isEmpty() || 
                NumericUtils.isNaN(searchType) || 
                Integer.parseInt(searchType) < SEARCH_TYPE_SEQUENCE_ID || 
                Integer.parseInt(searchType) > SEARCH_TYPE_CONTACT_NUMBER) {
            
            // create an error alert block in the
            // session scope
            saveToSessionScope(request, new AlertBlock(
                    AlertBlock.ALERT_BLOCK_ERROR, 
                    "The search type does not appear to be within the range of "
                            + "allowed search types."));
            
            // send a redirect
            parameters = new HashMap<>();
            parameters.put("message", String.valueOf(SEARCH_TUTOR_JOB_APPLICATIONS_FAILED));
            parameters.put("search-mode", String.valueOf(SEARCH_MODE_DISPLAY_FORM));
            
            return sendRedirect(RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, parameters);
        }
        
        if (searchKeyword == null || searchKeyword.isEmpty()) {
            
            // create an error alert block in the
            // session scope
            saveToSessionScope(request, new AlertBlock(
                    AlertBlock.ALERT_BLOCK_ERROR, 
                    "The search keyword is required."));
            
            // send a redirect
            parameters = new HashMap<>();
            parameters.put("message", String.valueOf(SEARCH_TUTOR_JOB_APPLICATIONS_FAILED));
            parameters.put("search-mode", String.valueOf(SEARCH_MODE_DISPLAY_FORM));
            
            return sendRedirect(RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, parameters);
        }
        
        // begin the search
        BecomeTutorRequest result = null;
        BecomeTutorRequestDAO dao = new BecomeTutorRequestDAOImpl();
        
        switch (Integer.parseInt(searchType)) {
            
            case SEARCH_TYPE_SEQUENCE_ID:
                if (!NumericUtils.isNaN(searchKeyword)) 
                    result = dao.getBecomeTutorRequest(Long.parseLong(searchKeyword));
                
                break;
            
            case SEARCH_TYPE_IDENTITY_NUMBER:
                result = dao.searchBecomeTutorRequestByIdNumber(searchKeyword);
                break;
                
            case SEARCH_TYPE_EMAIL_ADDRESS:
                if (EmailAddressValidator.isEmailAddressValid(searchKeyword)) 
                    result = dao.searchBecomeTutorRequestByEmailAddress(searchKeyword);
       
                break;
                
            case SEARCH_TYPE_CONTACT_NUMBER:
                result = dao.searchBecomeTutorRequestByContactNumber(searchKeyword);
                break;
        }
        
        // if nothing was found
        if (result == null) {
            
            // send a redirect
            parameters = new HashMap<>();
            parameters.put("message", String.valueOf(SEARCH_TUTOR_JOB_APPLICATIONS_NOT_FOUND));
            parameters.put("search-mode", String.valueOf(SEARCH_MODE_DISPLAY_FORM));
            
            return sendRedirect(RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, parameters);
        }
        
        else {
            
            // save the result into a map
            TreeMap<Long, BecomeTutorRequest> results = new TreeMap<>();
            results.put(result.getId(), result);
            
            // save to the form
            form.setTutorJobApplications(results);
            
            // save the form to the 
            // session scope
            saveToSessionScope(request, form);
            
            // send a redirect
            parameters = new HashMap<>();
            parameters.put("search-mode", SEARCH_MODE_SHOW_RESULTS);
            
            return sendRedirect(RequestMappings.SEARCH_TUTOR_JOB_APPLICATIONS, parameters);
        }
        
    }
    
}
