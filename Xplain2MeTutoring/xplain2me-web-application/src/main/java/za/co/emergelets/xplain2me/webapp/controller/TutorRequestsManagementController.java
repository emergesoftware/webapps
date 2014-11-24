package za.co.emergelets.xplain2me.webapp.controller;

import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.util.NumericUtils;
import za.co.emergelets.xplain2me.webapp.component.AlertBlock;
import za.co.emergelets.xplain2me.webapp.component.TutorRequestsManagementForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.TutorRequestsManagementControllerHelper;
import za.co.emergelets.xplain2me.webapp.enumerations.TutorRequestsType;

@Controller
@RequestMapping(value = "/portal/manager/tutor-requests/*")
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
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView displayTutorRequestsManagementPage(HttpServletRequest request) {
        
        // get the form from the session
        form = (TutorRequestsManagementForm)getFromSessionScope(request,
                TutorRequestsManagementForm.class);
        
        // if form is null from session
        if (form == null) {
            form = new TutorRequestsManagementForm();
        }
        
        // load all unread tutor requests
        helper.populateTutorRequestsByType(TutorRequestsType.Unread, form); 
        
        // save form to session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView("app-manager/view-tutor-requests");
    }
    
    /**
     * Searches for the tutor requests using 
     * the specified keyword and the search type
     * @param request
     * @param searchKeyword
     * @param searchType
     * @return 
     */
    @RequestMapping(value = "search", method = RequestMethod.GET, 
            params = {"searchKeyword", "searchType"})
    public ModelAndView searchTutorRequestByKeyword(HttpServletRequest request, 
            @RequestParam(value = "searchKeyword")String searchKeyword, 
            @RequestParam(value = "searchType")String searchType) {
        
        
        
        return createModelAndView("app-manager/view-tutor-requests");
    }
    
    /**
     * Marks the current tutor request as being
     * read / received.
     * 
     * @param request
     * @param requestId
     * @return 
     */
    @RequestMapping(value = "mark-as-read", method = RequestMethod.GET, 
            params = {"requestId"})
    public ModelAndView markTutorRequestAsRead(HttpServletRequest request, 
            @RequestParam(value = "requestId")String requestId) {
        
        // check for the form in the session
        if ((form = (TutorRequestsManagementForm)
                getFromRequestScope(request, TutorRequestsManagementForm.class)) == null) {
            
            LOG.warning("invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect("/logout");
            
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
            alertBlock.getAlertBlockMessages().add("Invalid ID for a tutor request, please try again.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView("app-manager/view-tutor-requests");
            
        }
        
        // get the user context
        context = (UserContext)getFromSessionScope(request, UserContext.class);
        
        // mark this request as being read
        if (helper.markTutorRequestAsRead(request, Long.parseLong(requestId), form, context)) {
            
            // get a fresh list of unread tutor request
            helper.populateTutorRequestsByType(TutorRequestsType.Unread, form);
  
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
            alertBlock.getAlertBlockMessages().add("Tutor Request (" + 
                    form.getUnreadTutorRequests().get(Long.parseLong(requestId)).getReferenceNumber()
                    + ") has been marked as read successfully.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
            // save the form to the session
            saveToSessionScope(request, form);
            
        }
        
        else {
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
            alertBlock.getAlertBlockMessages().add("Tutor request (" + 
                    form.getUnreadTutorRequests().get(Long.parseLong(requestId)).getReferenceNumber()
                    + ") could not be marked as read.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
        }
        
        
        // return a view
        return createModelAndView("app-manager/view-tutor-requests");
    }
    
    /**
     * Removes the specified tutor request
     * 
     * @param request
     * @param requestId
     * @return 
     */
    @RequestMapping(value = "remove", method = RequestMethod.GET, 
            params = {"requestId"})
    public ModelAndView removeTutorRequest(HttpServletRequest request, 
            @RequestParam(value = "requestId")String requestId) {
        
        // check for the form in the session
        if ((form = (TutorRequestsManagementForm)
                getFromRequestScope(request, TutorRequestsManagementForm.class)) == null) {
            
            LOG.warning("invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect("/logout");
            
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
            alertBlock.getAlertBlockMessages().add("Invalid ID for a tutor request, please try again.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
            // return a view
            return createModelAndView("app-manager/view-tutor-requests");
            
        }
        
        // get the user context
        context = (UserContext)getFromSessionScope(request, UserContext.class);
        
        // delete this request 
        if (helper.deleteTutorRequest(request, Long.parseLong(requestId), form, context)) {
            
            // get a fresh list of unread tutor request
            helper.populateTutorRequestsByType(TutorRequestsType.Unread, form);
  
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE);
            alertBlock.getAlertBlockMessages().add("Tutor Request (" + 
                    form.getUnreadTutorRequests().get(Long.parseLong(requestId)).getReferenceNumber()
                    + ") has been deleted successfully.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
            // save the form to the session
            saveToSessionScope(request, form);
            
        }
        
        else {
            
            // create an alert block
            alertBlock = new AlertBlock();
            alertBlock.setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING);
            alertBlock.getAlertBlockMessages().add("Tutor request (" + 
                    form.getUnreadTutorRequests().get(Long.parseLong(requestId)).getReferenceNumber()
                    + ") could not be deleted.");
            
            // save to the request scope
            saveToRequestScope(request, alertBlock);
            
        }
        
        
        // return a view
        return createModelAndView("app-manager/view-tutor-requests");
    }
    
    /**
     * Shows all the details about this tutor request
     * 
     * @param request
     * @param requestId
     * @return 
     */
    @RequestMapping(value = "details", method = RequestMethod.GET, 
            params = {"requestId"})
    public ModelAndView displayMoreTutorRequestDetails(HttpServletRequest request,
            @RequestParam(value = "requestId")String requestId) {
        
         // return a view
        return createModelAndView("app-manager/view-tutor-requests");
    }
    
}
