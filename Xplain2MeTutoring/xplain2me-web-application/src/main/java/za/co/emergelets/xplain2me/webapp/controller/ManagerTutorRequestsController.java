package za.co.emergelets.xplain2me.webapp.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.webapp.component.ManagerTutorRequestsForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.controller.helper.ManagerTutorRequestsControllerHelper;
import za.co.emergelets.xplain2me.webapp.enumerations.TutorRequestsType;

@Controller
@RequestMapping(value = "/portal/manager/tutor-requests/*")
public class ManagerTutorRequestsController extends GenericController {
    
    // the search types
    public static final int SEARCH_BY_REFERENCE_NUMBER = 1;
    public static final int SEARCH_BY_REQUEST_ID = 2;
    public static final int SEARCH_BY_EMAIL_ADDRESS = 3;
    public static final int SEARCH_BY_CONTACT_NUMBER = 4;
    
    // the user context
    private UserContext context;
    // the controller form
    private ManagerTutorRequestsForm form;
    // the helper class
    private final ManagerTutorRequestsControllerHelper helper;
    
    public ManagerTutorRequestsController() {
        helper = new ManagerTutorRequestsControllerHelper();
    }
    
    /**
     * Displays the main tutor requests page
     * @param request
     * @return 
     */
    @RequestMapping(value = "view", method = RequestMethod.GET)
    public ModelAndView displayTutorRequestsManagementPage(HttpServletRequest request) {
        
        // get the form from the session
        form = (ManagerTutorRequestsForm)getFromSessionScope(request,
                ManagerTutorRequestsForm.class);
        
        // if form is null from session
        if (form == null) {
            form = new ManagerTutorRequestsForm();
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
