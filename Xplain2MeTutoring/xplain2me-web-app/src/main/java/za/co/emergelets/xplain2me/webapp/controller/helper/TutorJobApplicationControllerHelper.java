package za.co.emergelets.xplain2me.webapp.controller.helper;

import java.io.Serializable;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.springframework.stereotype.Component;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAO;
import za.co.emergelets.xplain2me.dao.BecomeTutorRequestDAOImpl;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.webapp.component.TutorJobApplicationForm;
import za.co.emergelets.xplain2me.webapp.controller.GenericController;

@Component
public class TutorJobApplicationControllerHelper extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(TutorJobApplicationControllerHelper.class.getName(), null);
    
    private final BecomeTutorRequestDAO becomeTutorRequestDAO;
    
    public TutorJobApplicationControllerHelper() {
        this.becomeTutorRequestDAO = new BecomeTutorRequestDAOImpl();
    }

    /**
     * Loads the tutor job requests onto the
     * form.
     * 
     * @param form 
     */
    public void loadTutorJobApplications(TutorJobApplicationForm form) {
        
        if (form == null) {
            LOG.warning(" the form is null - aborted...");
            return;
        }
        
        List<BecomeTutorRequest> list = becomeTutorRequestDAO
                .getBecomeTutorRequests(form.getCurrentPageNumber());
        TreeMap<Long, BecomeTutorRequest> map = new TreeMap<>();
        
        if (list != null && !list.isEmpty()) {
            for (BecomeTutorRequest request : list) 
                map.put(request.getId(), request);
            
            form.setTutorJobApplications(map);
            form.setCannotGoForward(false);
        }
        
        else {
            
            form.setCurrentPageNumber(form.getCurrentPageNumber() - 1);
            form.setCannotGoForward(true);
        }
    }
    
}
