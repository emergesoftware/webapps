package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import za.co.emergelets.xplain2me.dao.AuditDAO;
import za.co.emergelets.xplain2me.dao.AuditDAOImpl;
import za.co.emergelets.xplain2me.dao.EventTypes;
import za.co.emergelets.xplain2me.dao.SystemAuditManager;
import za.co.emergelets.xplain2me.dao.TutorRequestDAO;
import za.co.emergelets.xplain2me.dao.TutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.Audit;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.webapp.component.TutorRequestsManagementForm;
import za.co.emergelets.xplain2me.webapp.component.UserContext;
import za.co.emergelets.xplain2me.webapp.enumerations.TutorRequestsType;

public class TutorRequestsManagementControllerHelper {
   
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
                userContext.getProfile().getUser().getUsername(),
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
                    context.getPerson().getUser(), id, null, request.getRemoteAddr(), 
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
                    context.getPerson().getUser(), id, null, request.getRemoteAddr(), 
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
}
