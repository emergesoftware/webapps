package za.co.xplain2me.webapp.controller.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import za.co.xplain2me.util.NumericUtils;
import za.co.xplain2me.bo.validation.EmailAddressValidator;
import za.co.xplain2me.bo.validation.CellphoneNumberValidator;
import za.co.xplain2me.dao.AuditDAO;
import za.co.xplain2me.dao.AuditDAOImpl;
import za.co.xplain2me.dao.EventTypes;
import za.co.xplain2me.dao.SystemAuditManager;
import za.co.xplain2me.dao.TutorRequestDAO;
import za.co.xplain2me.dao.TutorRequestDAOImpl;
import za.co.xplain2me.entity.Audit;
import za.co.xplain2me.entity.TutorRequest;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.TutorRequestsManagementForm;
import za.co.xplain2me.webapp.component.UserContext;
import za.co.xplain2me.webapp.controller.GenericController;
import za.co.xplain2me.webapp.enumerations.TutorRequestsType;

@Component
public class TutorRequestsManagementControllerHelper extends GenericController {
   
    // the logger 
    private static final Logger LOG = 
            Logger.getLogger(TutorRequestsManagementControllerHelper.class.getName(), null);
    
    // the data access objects
    private TutorRequestDAO tutorRequestDAO;
    private AuditDAO auditDAO;
    
    public TutorRequestsManagementControllerHelper() {
        initialiseDataAccessObjects();
    }
    
    /**
     * Resolves any ALERT parameters
     * @param request 
     */
    public void resolveAnyAlertParameters(HttpServletRequest request) {
        
        AlertBlock alertBlock = null;
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
    }
    
    /**
     * Gets a tutor request by ID
     * 
     * @param id
     * @return 
     */
    public TutorRequest getTutorRequestById(Long id) {
        if (id < 1) {
            LOG.warning(" ... invalid ID for a tutor request ...");
            return null;
        }
        
        return tutorRequestDAO.getTutorRequestById(id);
    }
    
    /**
     * initialization for Data Access Objects
     */
    private void initialiseDataAccessObjects() {
        this.tutorRequestDAO = new TutorRequestDAOImpl();
        this.auditDAO = new AuditDAOImpl();
    }
    
    /**
     * Populates the audit trail
     * for tutor request amendments
     * @param userContext
     * @param form 
     */
    public void populateLatestAuditsByUser(UserContext userContext, 
            TutorRequestsManagementForm form) {
        
        if (form == null || 
                userContext == null) {
            LOG.warning(" ... either the form or the userContext is null ..."); 
            return;
        }
        
        // create list of event types to look for
        List<Long> eventTypes = new ArrayList<>();
        eventTypes.add(EventTypes.MARK_TUTOR_REQUEST_AS_READ_EVENT);
        eventTypes.add(EventTypes.DELETE_TUTOR_REQUEST_EVENT);
        
        // get the audit trail
        List<Audit> trail = auditDAO.getLatestAuditTrailByUser(
                userContext.getProfile().getPerson().getUser().getUsername(),
                eventTypes, 10);
        
        if (trail != null)
            form.setAuditTrail(trail);
        
    }
    
    /**
     * Deletes the specified
     * tutor request from the data store
     * 
     * @param request
     * @param id
     * @param form
     * @param context
     * @return 
     */
    public boolean deleteTutorRequest(HttpServletRequest request, long id, 
            TutorRequestsManagementForm form, UserContext context) {
        
        if (id < 1 || form == null || context == null) 
            return false;
        
         // get the unread request
        TutorRequest item = tutorRequestDAO.getTutorRequestById(id);
        
        if (item != null) {
            
            // remove the request 
            tutorRequestDAO.deleteTutorRequest(item);
            
            // create an audit
            SystemAuditManager.logAuditAsync(EventTypes.DELETE_TUTOR_REQUEST_EVENT, 
                    context.getProfile().getPerson().getUser(), id, null, request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 0, true);
            
            return true;
            
        }
        else {
            return false;
        }
    }
    
    /**
     * Marks the tutor request as being read
     * 
     * @param request
     * @param id
     * @param form
     * @param context
     * @return 
     */
    public boolean markTutorRequestAsRead(HttpServletRequest request, long id, 
            TutorRequestsManagementForm form, UserContext context) {
        
        if (id < 1 || form == null || context == null) 
            return false;
        
        // get the unread request
        TutorRequest item = tutorRequestDAO.getTutorRequestById(id);
        
        if (item != null) {
            
            // update as being read
            item.setReceived(true);
            tutorRequestDAO.updateTutorRequest(item);
            
            // create an audit
            SystemAuditManager.logAuditAsync(EventTypes.MARK_TUTOR_REQUEST_AS_READ_EVENT, 
                    context.getProfile().getPerson().getUser(), id, null, request.getRemoteAddr(), 
                    request.getHeader("User-Agent"), 0, true);
            
            return true;
            
        }
        else {
            return false;
        }
    }
    
    /**
     * Populates the tutor requests by
     * type (read or unread)
     * 
     * @param type
     * @param form 
     */
    public void populateTutorRequestsByType(TutorRequestsType type, 
            TutorRequestsManagementForm form) {
        
        if (form == null || type == null) {
            LOG.warning("Either form or tutor request type is NULL - " + 
                    "did not populate tutor requests.");
            return;
        }
        
        // if the type is unread...
        if (type == TutorRequestsType.Unread) {
            
            // clear all unread tutor requests
            if (form.getUnreadTutorRequests() != null)
                form.getUnreadTutorRequests().clear();
            else
                form.setUnreadTutorRequests(
                        new TreeMap<Long, TutorRequest>());
            
            // yield the unread tutor requests
            for (TutorRequest request : tutorRequestDAO.getUnreadTutorRequests()) {
                form.getUnreadTutorRequests().put(request.getId(), request);
            }
            
        }
        
        // if the type is read...
        
    }

    /**
     * PROCESS ANY RESULT PARAMETERS IN THE
     * REQUEST
     * 
     * @param request 
     */
    public void resolveAnySearchResultParameters(HttpServletRequest request) {
        
        String result = getParameterValue(request, "result");
        
        if (result != null && result.isEmpty() == false) {
            
            // if the parameters were found to be invalid
            if (result.equalsIgnoreCase("invalid-params")) {
                
                saveToRequestScope(request, 
                        new AlertBlock(
                        AlertBlock.ALERT_BLOCK_WARNING, 
                        "Invalid search parameters were received."));
            }
            
            // if the search returned nothing
            if (result.equalsIgnoreCase("empty")) {
                
                saveToRequestScope(request, 
                        new AlertBlock(
                        AlertBlock.ALERT_BLOCK_INFORMATIVE, 
                        "No tutor requests could be found, please try again."));
            }
            
        }
    }

    /**
     * SEARCHES FOR THE 
     * TUTOR REQUESTS
     * 
     * @param searchKeyword
     * @param searchType
     * @param form 
     */
    public void searchTutorRequest(String searchKeyword, int searchType, TutorRequestsManagementForm form) {
        
        if (form == null) {
            LOG.warning("... could not initiate tutor request search, "
                    + "form is null ...");
            return;
        }
        
        Map<Long, TutorRequest> results = new TreeMap<>();
        
        // search by request ID
        if (searchType == TutorRequest.SEARCH_BY_REQUEST_ID && 
                !NumericUtils.isNaN(searchKeyword)) {
            
            TutorRequest result = tutorRequestDAO.searchTutorRequestById(
                    Long.parseLong(searchKeyword));
            
            if (result != null)
                results.put(result.getId(), result);
        }
        
        // search by reference number
        if (searchType == TutorRequest.SEARCH_BY_REFERENCE_NUMBER && 
                searchKeyword.startsWith("TR")) {
            
            TutorRequest result = tutorRequestDAO
                    .searchTutorRequestByReferenceNumber(searchKeyword);
            
            if (result != null)
                results.put(result.getId(), result);
            
        }
        
        // search by email address
        if (searchType == TutorRequest.SEARCH_BY_EMAIL_ADDRESS &&
                EmailAddressValidator.isEmailAddressValid(searchKeyword)) {
            
            List<TutorRequest> list = tutorRequestDAO
                    .searchTutorRequestByEmailAddress(searchKeyword);
            
            if (list != null) {
                for (TutorRequest result : list)
                    results.put(result.getId(), result);
            }
        }
        
        // search by contact number
        if (searchType == TutorRequest.SEARCH_BY_CONTACT_NUMBER &&
                CellphoneNumberValidator.isCellphoneNumberValid(
                        "27" + searchKeyword.substring(1))) { 
            
            List<TutorRequest> list = tutorRequestDAO
                    .searchTutorRequestByContactNumber(searchKeyword);
            
            if (list != null) {
                for (TutorRequest result : list)
                    results.put(result.getId(), result);
            }
        }
        
        form.setSearchResults(results);
        
    }
}
