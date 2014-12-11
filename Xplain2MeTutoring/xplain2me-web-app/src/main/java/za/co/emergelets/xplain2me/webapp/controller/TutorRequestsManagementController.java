package za.co.emergelets.xplain2me.webapp.controller;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    // the helper class
    @Autowired
    private TutorRequestsManagementControllerHelper helper;
    
    /**
     * Constructor
     */
    public TutorRequestsManagementController() {
    }
    
    /**
     * DISPLAYS THE TUTOR REQUESTS THAT
     * HAVE NOT BEEN READ OR ATTENDED YET
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD, 
            method = RequestMethod.GET)
    public ModelAndView showUnreadTutorRequests(HttpServletRequest request) {
        
        // get the form from the session
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request,
                TutorRequestsManagementForm.class);
        
        // if form is null from session
        if (form == null) {
            form = new TutorRequestsManagementForm();
        }
        
        // check for the alert parameter
        helper.resolveAnyAlertParameters(request);
        
        // load all unread tutor requests
        helper.populateTutorRequestsByType(TutorRequestsType.Unread, form); 
        
        // populate the latest tutor management audits
        UserContext context = (UserContext)getFromSessionScope(request, UserContext.class);
        helper.populateLatestAuditsByUser(context, form);
        
        // save form to session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.MANAGE_TUTOR_REQUESTS);
    }
    
    /**
     * SHOWS THE PAGE FOR SEARCHING FOR AN
     * EXISTING TUTOR REQUEST
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH, 
            method = RequestMethod.GET)
    public ModelAndView showSearchTutorRequestsPage(HttpServletRequest request) {
        
        // get the form from the session
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request,
                TutorRequestsManagementForm.class);
        
        // if form is null from session
        if (form == null) {
            form = new TutorRequestsManagementForm();
        }
        else form.resetForm();
        
        // process the result params
        helper.resolveAnySearchResultParameters(request);
        
        // save the form to the session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.SEARCH_TUTOR_REQUESTS);
    }
    
    /**
     * PROCESSES THE SEARCH FOR A TUTOR
     * REQUEST
     * 
     * @param request
     * @param searchType
     * @param searchKeyword
     * @return 
     */
    @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH, 
            method = RequestMethod.POST, 
            params = {"searchType", "searchKeyword"})
    public ModelAndView processSearchTutorRequest(
            HttpServletRequest request,
            @RequestParam(value = "searchType")String searchType,
            @RequestParam(value = "searchKeyword")String searchKeyword) {
        
        // check for the form from the
        // session
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request, TutorRequestsManagementForm.class);
        if (form == null) {
            LOG.warning("... invalid request ..."); 
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH + 
                                "?rand=" + System.currentTimeMillis());
        }
        
        // get the parameters
        if ((searchKeyword == null || searchKeyword.isEmpty()) || 
                (searchType == null || searchType.isEmpty())) {
            
            LOG.warning(" ... invalid params for the search type or keyword ...");
            
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH + 
                    "?result=invalid-params" + 
                    "&rand=" + System.currentTimeMillis());
        }
        
        //save the search params
        form.setSearchKeyword(searchKeyword);
        form.setSearchType(Integer.parseInt(searchType));
        
        // initiate the search
        helper.searchTutorRequest(searchKeyword, Integer.parseInt(searchType), form);
        
        // check if the any requests could be
        // found
        if (form.getSearchResults() == null || 
                form.getSearchResults().isEmpty()) {
            
            LOG.warning(" ... no tutor request could be found ...");
            
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH + 
                    "?result=empty" + 
                    "&rand=" + System.currentTimeMillis());
        }
        
        // generate a random key
        form.setShowResultsAnswer(1000 + (int)(Math.random() * 4000));
        
        // save the form to the session
        // scope
        saveToSessionScope(request, form);
        
        // send a redirect
        return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH + 
                "?searchKey=" + form.getShowResultsAnswer());
    }
    
    
     @RequestMapping(value = RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH, 
            method = RequestMethod.GET,
            params = {"searchKey"})
    public ModelAndView showTutorRequestSearchResultsPage(HttpServletRequest request,
            @RequestParam(value = "searchKey")String searchKey) {
        
        // check for the form from the
        // session
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request, TutorRequestsManagementForm.class);
        if (form == null) {
            LOG.warning("... invalid request ..."); 
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH + 
                                "?rand=" + System.currentTimeMillis());
        }
        
        // check the search key
        if (NumericUtils.isNaN(searchKey) || 
                Integer.parseInt(searchKey) != form.getShowResultsAnswer()) {
            LOG.warning("... invalid search key ..."); 
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_SEARCH + 
                                "?rand=" + System.currentTimeMillis());
        }
        
        // return a view
        return createModelAndView(Views.SEARCH_TUTOR_REQUESTS_RESULTS);
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
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request, 
                TutorRequestsManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.LOGIN);
            
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            LOG.warning(" the request ID is invalid ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
                    + "?alert=invalid-tutor-request");
            
        }
        
        // get the user context
        UserContext context = (UserContext)getFromSessionScope(
                request, UserContext.class);
        
        // mark this request as being read
        String referenceNumber = form.getUnreadTutorRequests()
                .get(Long.parseLong(requestId)).getReferenceNumber();
                
        if (helper.markTutorRequestAsRead(request, Long.parseLong(requestId), 
                form, context)) {
            
            // log an event
            SystemAuditManager.logAuditAsync(
                    EventTypes.MARK_TUTOR_REQUEST_AS_READ_EVENT, 
                    context.getProfile().getPerson().getUser(), 
                    Long.parseLong(requestId), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, 
                    true);
            
            LOG.warning(" the request was marked as read ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
                    + "?alert=request-marked-as-read"
                    + "&reference-number=" + referenceNumber);
        }
        
        else {
            
            LOG.warning(" the request could not be marked as read ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
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
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request, TutorRequestsManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.LOGOUT);
            
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            LOG.warning(" the request ID is invalid ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
                    + "?alert=invalid-tutor-request");
        }
        
        // get the user context
        UserContext context = (UserContext)getFromSessionScope(request, UserContext.class);
        
        String referenceNumber = form.getUnreadTutorRequests()
                .get(Long.parseLong(requestId)).getReferenceNumber();
        
        // delete this request 
        if (helper.deleteTutorRequest(request, Long.parseLong(requestId), form, context)) {
            
            // log an event
            SystemAuditManager.logAuditAsync(
                    EventTypes.DELETE_TUTOR_REQUEST_EVENT, 
                    context.getProfile().getPerson().getUser(), 
                    Long.parseLong(requestId), 
                    null, 
                    request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 
                    0, 
                    true);
            
            LOG.warning(" request was deleted ");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
                    + "?alert=tutor-request-deleted" 
                    + "&reference-number=" + referenceNumber);
        
        }
        
        else {
            
            LOG.warning(" request could not be deleted ");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
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
        TutorRequestsManagementForm form = (TutorRequestsManagementForm)
                getFromSessionScope(request, TutorRequestsManagementForm.class);
        
        if (form == null) {
            
            LOG.warning("... invalid request access...");
            invalidateCurrentSession(request);
            return sendRedirect(RequestMappings.LOGOUT);
        }
        
        // check the request ID
        if (NumericUtils.isNaN(requestId) || Integer.parseInt(requestId) < 1) {
            
            LOG.warning(" the request ID is invalid ...");
            return sendRedirect(RequestMappings.TUTOR_REQUESTS_MANAGEMENT_UNREAD 
                    + "?alert=invalid-tutor-request");
        }
        
        // get the tutor request from the data store
        TutorRequest tutorRequest = helper
                .getTutorRequestById(Long.parseLong(requestId));
        
        AlertBlock alertBlock = null;
        
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
