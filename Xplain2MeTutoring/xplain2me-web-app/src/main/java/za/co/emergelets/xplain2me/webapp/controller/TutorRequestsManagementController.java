package za.co.emergelets.xplain2me.webapp.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.TutorRequestsManagementForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.TutorRequestsManagementControllerHelper;
import za.co.emergelets.xplain2me.webapp.enumerations.TutorRequestsType;

@Controller
public class TutorRequestsManagementController extends GenericController {
    
    private static final Logger LOG = 
            Logger.getLogger(TutorRequestsManagementController.class.getName(), null);
   
    // the search types
    public static final int SEARCH_BY_REFERENCE_NUMBER = 1;
    public static final int SEARCH_BY_REQUEST_ID = 2;
    public static final int SEARCH_BY_EMAIL_ADDRESS = 3;
    public static final int SEARCH_BY_CONTACT_NUMBER = 4;
    
    // the user context
    private UserContext context;
    // the controller form
    private TutorRequestsManagementForm form;
    // alert block
    private AlertBlock alertBlock;
    // the helper class
    private final TutorRequestsManagementControllerHelper helper;
    
    /**
     * Constructor
     */
    public TutorRequestsManagementController() {
        helper = new TutorRequestsManagementControllerHelper();
    }
    
    /**
     * Displays the main tutor requests page
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW, 
            method = RequestMethod.GET)
    public ModelAndView displayTutorRequestsManagementPage(HttpServletRequest request) {
        
        // get the form from the session
        form = (TutorRequestsManagementForm)getFromSessionScope(request,
                TutorRequestsManagementForm.class);
        
        // if form is null from session
        if (form == null) {
            form = new TutorRequestsManagementForm();
        }
        
        // get the tutor management audit
        // trail for this user
        context = (UserContext)getFromSessionScope(request, UserContext.class);
        helper.populateLatestAuditsByUser(context, form);
        
        // check for the alert parameter
        String alert = getParameterValue(request, "alert");
        if (alert != null && alert.isEmpty() == false) {
            
            // create an alert block
            alertBlock = new AlertBlock();
            
            // if the request id was invalid
            if (alert.equals("invalid-tutor-request")) {
                
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
                alertBlock.getAlertBlockMessages().add("Invalid ID for a tutor request, "
                        + "please try again.");
                
            }
            
            // if the request was marked as read successfully
            if (alert.equals("request-marked-as-read")) {
                
                String referenceNumber = getParameterValue(request, "reference-number");
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
                
                if (referenceNumber == null)
                    alertBlock.getAlertBlockMessages().add("Tutor Request has been "
                            + "marked as read successfully.");
                else
                    alertBlock.getAlertBlockMessages().add("Tutor Request (" + referenceNumber
                            + ") has been marked as read successfully.");
            }
            
            // if the request could not be marked as read
            if (alert.equals("request-not-marked-as-read")) {
                
                String referenceNumber = getParameterValue(request, "reference-number");
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
                
                if (referenceNumber == null)
                     alertBlock.getAlertBlockMessages().add("Tutor request could not be "
                             + "marked as read.");
                else
                    alertBlock.getAlertBlockMessages().add("Tutor request (" + referenceNumber
                            + ") could not be marked as read.");
                
            }
            
            // if the tutor request was deleted successfully
            if (alert.equals("tutor-request-deleted")) {
                
                String referenceNumber = getParameterValue(request, "reference-number");
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
                
                if (referenceNumber == null)
                    alertBlock.getAlertBlockMessages().add("Tutor Request has been deleted "
                            + "successfully.");
                else
                    alertBlock.getAlertBlockMessages().add("Tutor Request (" + referenceNumber
                            + ") has been deleted successfully.");
            }
            
            // if the request could not be deleted
            if (alert.equals("request-not-deleted")) {
                
                String referenceNumber = getParameterValue(request, "reference-number");
                alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
                
                if (referenceNumber == null)
                    alertBlock.getAlertBlockMessages().add("Tutor request could not be deleted.");
                else
                    alertBlock.getAlertBlockMessages().add("Tutor request (" 
                            + referenceNumber
                            + ") could not be deleted.");
                
            }
            
            // save the alert block to the
            // request sope
            saveToRequestScope(request, alertBlock);
            
        }
        
        
        // load all unread tutor requests
        helper.populateTutorRequestsByType(TutorRequestsType.Unread, form); 
        
        // save form to session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.MANAGE_TUTOR_REQUESTS);
    }
    
    /**
     * Searches for the tutor requests using 
     * the specified keyword and the search type
     * @param request
     * @param searchKeyword
     * @param searchType
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH, 
            method = RequestMethod.GET, 
            params = {"searchKeyword", "searchType"})
    public ModelAndView searchTutorRequestByKeyword(HttpServletRequest request, 
            @RequestParam(value = "searchKeyword")String searchKeyword, 
            @RequestParam(value = "searchType")String searchType) {
        
        
        
        return createModelAndView(Views.MANAGE_TUTOR_REQUESTS);
    }
    
    /**
     * Marks the current tutor request as being
     * read / received.
     * 
     * @param request
     * @param requestId
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_MARK_AS_READ, 
            method = RequestMethod.GET, 
            params = {"requestId"})
    public ModelAndView markTutorRequestAsRead(HttpServletRequest request, 
            @RequestParam(value = "requestId")String requestId) {
        
        // check for the form in the session
        form = (TutorRequestsManagementForm)getFromSessionScope(request, 
                TutorRequestsManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.LOGIN);
            
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            LOG.warning(" the request ID is invalid ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=invalid-tutor-request");
            
        }
        
        // get the user context
        context = (UserContext)getFromSessionScope(request, UserContext.class);
        
        // mark this request as being read
        String referenceNumber = form.getUnreadTutorRequests()
                .get(Long.parseLong(requestId)).getReferenceNumber();
                
        if (helper.markTutorRequestAsRead(request, Long.parseLong(requestId), 
                form, context)) {
            
            // log an event
            SystemAuditManager.logAuditAsync(
                    EventTypes.MARK_TUTOR_REQUEST_AS_READ_EVENT, 
                    context.getProfile().getUser(), 
                    Long.parseLong(requestId), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, 
                    true);
            
            LOG.warning(" the request was marked as read ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=request-marked-as-read"
                    + "&reference-number=" + referenceNumber);
        }
        
        else {
            
            LOG.warning(" the request could not be marked as read ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=request-not-marked-as-read"
                    + "&reference-number=" + referenceNumber);
            
        }
    }
    
    /**
     * Removes the specified tutor request
     * 
     * @param request
     * @param requestId
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_REMOVE, 
            method = RequestMethod.GET, 
            params = {"requestId"})
    public ModelAndView removeTutorRequest(HttpServletRequest request, 
            @RequestParam(value = "requestId")String requestId) {
        
        // check for the form in the session
        form = (TutorRequestsManagementForm)
                getFromSessionScope(request, TutorRequestsManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.LOGOUT);
            
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            LOG.warning(" the request ID is invalid ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=invalid-tutor-request");
        }
        
        // get the user context
        context = (UserContext)getFromSessionScope(request, UserContext.class);
        
        String referenceNumber = form.getUnreadTutorRequests()
                .get(Long.parseLong(requestId)).getReferenceNumber();
        
        // delete this request 
        if (helper.deleteTutorRequest(request, Long.parseLong(requestId), form, context)) {
            
            // log an event
            SystemAuditManager.logAuditAsync(
                    EventTypes.DELETE_TUTOR_REQUEST_EVENT, 
                    context.getProfile().getUser(), 
                    Long.parseLong(requestId), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, 
                    true);
            
            LOG.warning(" request was deleted ");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=tutor-request-deleted" 
                    + "&reference-number=" + referenceNumber);
        
        }
        
        else {
            
            LOG.warning(" request could not be deleted ");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=request-not-deleted" 
                    + "&reference-number=" + referenceNumber);
            
        }
    }
    
    /**
     * Shows all the details about this tutor request
     * 
     * @param request
     * @param requestId
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_DETAILS, 
            method = RequestMethod.GET, 
            params = {"requestId"})
    public ModelAndView displayMoreTutorRequestDetails(HttpServletRequest request,
            @RequestParam(value = "requestId")String requestId) {
        
         // check for the form in the session
        form = (TutorRequestsManagementForm)
                getFromSessionScope(request, TutorRequestsManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.LOGOUT);
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            LOG.warning(" the request ID is invalid ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_VIEW 
                    + "?alert=invalid-tutor-request");
        }
        
        // get the tutor request from the data store
        TutorRequest tutorRequest = helper
                .getTutorRequestById(Long.parseLong(requestId));
        
        
        if (tutorRequest == null) {
            
            LOG.log(Level.WARNING, " tutor request with id: {0} not found...", requestId);
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR);
            alertBlock.getAlertBlockMessages().add(
                    "Tutor Request with ID " + requestId + " was not found in "
                            + "the system. Please try again.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
        }
        
        else {
            // save the tutor request to the request scope
            saveToRequestScope(request, tutorRequest);
        }
        
        // return a view
        return createModelAndView(Views.VIEW_TUTOR_REQUEST_DETAILS);
    }
    
}
