package za.co.xplain2me.webapp.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import za.co.xplain2me.dao.AcademicLevelDAO;
import za.co.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.xplain2me.dao.LessonDao;
import za.co.xplain2me.dao.LessonDaoImpl;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.dao.SubjectDAO;
import za.co.xplain2me.dao.SubjectDAOImpl;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.model.ProfileCollectionWrapper;
import za.co.xplain2me.model.SearchUserProfileType;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.LessonManagementForm;
import za.co.xplain2me.webapp.component.UserContext;

@Controller
public class LessonManagementController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger
            .getLogger(LessonManagementController.class.getName(), null);
    
    public LessonManagementController() {
    }
    
    /**
     * Resolves any session scoped alert block
     * onto the request scope.
     * 
     * @param request 
     */
    private void resolveSessionScopedAlertBlock(HttpServletRequest request) {
        
        LOG.info("... resolve session scoped alert block ...");
        
        AlertBlock alert = (AlertBlock)
                getFromSessionScope(request, AlertBlock.class);
        
        if (alert == null || 
                (alert.getAlertBlockMessages() == null || 
                alert.getAlertBlockMessages().isEmpty())) {
            LOG.info("... no session scoped alert block found ...");
            return;
        }
        
        saveToRequestScope(request, alert);
        removeFromSessionScope(request, AlertBlock.class); 
        
    }
    
    /**
     * Delivers the webpage for adding new lessons.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.ADD_NEW_LESSONS, 
            method = RequestMethod.GET)
    public ModelAndView showAddNewLessonsPage(HttpServletRequest request) {
        
        // check if there is any form
        // in the session scope
        LessonManagementForm form = (LessonManagementForm)
                getFromSessionScope(request, LessonManagementForm.class);
        if (form == null)
            form = new LessonManagementForm();
        else form.resetForm();
        
        // populate the lesson status
        LessonDao lessonDao = new LessonDaoImpl();
        form.setLessonStatus(new TreeMap<Long, LessonStatus>()); 
        for (LessonStatus status : lessonDao.getAllLessonStatuses())
            form.getLessonStatus().put(status.getId(), status);
        
        // populate the academic levels
        AcademicLevelDAO academicLevelDao = new AcademicLevelDAOImpl();
        form.setAcademicLevels(new TreeMap<Long, AcademicLevel>());
        for (AcademicLevel level : academicLevelDao.getAllAcademicLevels())
            form.getAcademicLevels().put(level.getId(), level);
        
        // populate the subjects
        SubjectDAO subjectDao = new SubjectDAOImpl();
        form.setSubjects(new TreeMap<Long, Subject>());
        for (Subject subject : subjectDao.getAllSubjects())
            form.getSubjects().put(subject.getId(), subject);
        
        // resolve any alert blocks in the
        // session into the request scope
        resolveSessionScopedAlertBlock(request); 
        
        // save the form to the session
        // scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.ADD_NEW_LESSONS);
    }
    
    @RequestMapping(value = RequestMappings.FIND_STUDENT_ASYNC, 
            method = RequestMethod.GET, 
            params = { "search-field" })
    @ResponseBody
    public String findStudentsByNameAsync(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "search-field")String searchField) throws IOException {
        
        List<Profile> profiles = new ArrayList<>();
        String outcome = null;
        int code = 0;
        
        // validate the search field
        if (searchField != null && 
                searchField.isEmpty() == false) {
            
            // get the user context
            UserContext context = (UserContext)
                    getFromSessionScope(request, UserContext.class);
            
            // create the data access object
            ProfileDAO profileDao = new ProfileDAOImpl();
            
            // search for the profiles
            profiles = profileDao.searchUserProfile(SearchUserProfileType.MatchFirstNames, 
                    searchField,
                    context.getProfile()); 
            
            if (profiles == null) {
                outcome = "No student could be found.";
                code = ProfileCollectionWrapper.NOT_FOUND;
                profiles = new ArrayList<>();
            }
            else {
                outcome = profiles.size() + " student(s) were found.";
                code = ProfileCollectionWrapper.FOUND;
            }
        }
        
        else {
            outcome = "The search field appears to be invalid.";
            code = ProfileCollectionWrapper.NOT_FOUND;
        }
        
        // set the http headers
        response.setContentType("application/json");
        
        // the jackson object mapper
        ObjectMapper mapper = new ObjectMapper();
        
        // return a JSON string
        ProfileCollectionWrapper wrapper = new ProfileCollectionWrapper();
        wrapper.setProfiles(profiles); 
        wrapper.setOutcome(outcome); 
        wrapper.setCode(code);
        
        return mapper.writeValueAsString(wrapper);
        
    }
    
}
