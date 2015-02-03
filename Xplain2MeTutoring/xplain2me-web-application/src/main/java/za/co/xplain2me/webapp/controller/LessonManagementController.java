package za.co.xplain2me.webapp.controller;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.springframework.beans.factory.annotation.Autowired;
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
import za.co.xplain2me.dao.StudentDao;
import za.co.xplain2me.dao.StudentDaoImpl;
import za.co.xplain2me.dao.SubjectDAO;
import za.co.xplain2me.dao.SubjectDAOImpl;
import za.co.xplain2me.entity.AcademicLevel;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Student;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.model.StudentCollectionWrapper;
import za.co.xplain2me.model.SearchUserProfileType;
import za.co.xplain2me.util.AdvancedDataStructuresFactory;
import za.co.xplain2me.util.DateTimeUtils;
import za.co.xplain2me.util.NumericUtils;
import za.co.xplain2me.util.VerificationCodeGenerator;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.LessonManagementForm;
import za.co.xplain2me.webapp.component.UserContext;
import za.co.xplain2me.webapp.controller.helper.LessonManagementControllerHelper;

@Controller
public class LessonManagementController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger
            .getLogger(LessonManagementController.class.getName(), null);
    
    @Autowired
    private LessonManagementControllerHelper helper;
    
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
    
    /**
     * Finds student profiles from the search pattern provided
     *  - search pattern is done by student's name.
     * 
     * @param request
     * @param response
     * @param searchField
     * @return
     * @throws IOException 
     */
    @RequestMapping(value = RequestMappings.FIND_STUDENT_ASYNC, 
            method = RequestMethod.GET, 
            params = { "search-field" , "profile-type" })
    @ResponseBody
    public String findStudentsByNameAsync(
            HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "search-field")String searchField) throws IOException {
        
        List<Student> students = new ArrayList<>();
        String outcome = null;
        int code = 0;
        
        // validate the search field
        if ((searchField != null && 
                searchField.isEmpty() == false)) {
            
            // get the user context
            UserContext context = (UserContext)
                    getFromSessionScope(request, UserContext.class);
            
            // create the data access object
            StudentDao studentDao = new StudentDaoImpl();
            
            // search for the profiles
            students = studentDao.findStudentsByMatchingFirstNames(searchField,
                    context.getProfile()); 
            
            if (students == null) {
                
                outcome = "No students could be found.";
                code = StudentCollectionWrapper.NOT_FOUND;
            }
            else {
                
                outcome = students.size() + " student(s) were found.";
                code = StudentCollectionWrapper.FOUND;
            }
        }
        
        else {
            outcome = "The search field appears to be invalid.";
            code = StudentCollectionWrapper.NOT_FOUND;
        }
        
        // set the http headers
        response.setContentType("application/json");
        
        // the jackson object mapper
        ObjectMapper mapper = new ObjectMapper();
        
        // return a JSON string
        StudentCollectionWrapper wrapper = new StudentCollectionWrapper();
        wrapper.setStudents(students);
        wrapper.setOutcome(outcome); 
        wrapper.setCode(code);
        
        return mapper.writeValueAsString(wrapper);
        
    }
    
    
    /**
     * Processes the request to add a new lesson.
     * 
     * @param request
     * @return 
     * @throws java.lang.IllegalAccessException 
     */
    @RequestMapping(value = RequestMappings.ADD_NEW_LESSONS, 
            method = RequestMethod.POST)
    public ModelAndView processAddNewLessons(HttpServletRequest request) throws IllegalAccessException {
        
        // variables
        Map<String, String> parameters = null;
        List<String> errorsEncountered = null;
        
        // check if there is a valid session
        LessonManagementForm form = (LessonManagementForm)
                getFromSessionScope(request, LessonManagementForm.class);
        
        if (form == null) {
            // send a redirect
            parameters = AdvancedDataStructuresFactory.createHashMap();
            parameters.put("invalid-request", "1");
            
            return sendRedirect(RequestMappings.ADD_NEW_LESSONS, parameters);
        }
        
        // clear the redirect code
        form.setRedirectCode(null);
        
        // validate the parameters
        errorsEncountered = helper.validateAddNewLessonParameters(request);
        
        // if there were errors
        if (!errorsEncountered.isEmpty()) {
            
            // create the alert block
            // into the session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .setAlertBlockMessages(errorsEncountered)); 
            
            // send a redirect
            parameters = AdvancedDataStructuresFactory.createHashMap();
            parameters.put("errors-encountered", "yes");
            
            return sendRedirect(RequestMappings.ADD_NEW_LESSONS, parameters);
            
        }
        
        // create a list of the lessons
        List<Lesson> lessons = helper.createLessonsFromParameters(request, form);
        
        // if no lessons could be created
        if (lessons == null || lessons.isEmpty()) {
            
            // create the alert block
            // into the session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .addAlertBlockMessage("The lessons could not be created at this moment. "
                            + "Please try again later.")); 
            
            // send a redirect
            parameters = AdvancedDataStructuresFactory.createHashMap();
            parameters.put("internal-failure", "yes");
            
            return sendRedirect(RequestMappings.ADD_NEW_LESSONS, parameters);
            
        }
        
        // save the lessons to the form
        form.setLessons(lessons);
        
        // set the redirect code
        form.setRedirectCode(String.valueOf(VerificationCodeGenerator
                .generateVerificationCode()));
        
        // save the form to the 
        // session scope
        saveToSessionScope(request, form);
        
        // send a redirect
        return sendRedirect(RequestMappings.ADD_NEW_LESSONS + 
                "?find-available-tutors=1" + 
                "&redirect-call=" + form.getRedirectCode()); 
    }
    
    /**
     * Processes the request to locate available tutors
     * to allocate lessons to.
     * 
     * @param request
     * @param redirectCall
     * @return 
     */
    @RequestMapping(value = RequestMappings.ADD_NEW_LESSONS, 
            method = RequestMethod.GET, 
            params = { "find-available-tutors", "redirect-call" })
    public ModelAndView processFindAvailableTutorsRequest(
            HttpServletRequest request,
            @RequestParam(value = "redirect-call")String redirectCall) {
        
        // variables
        Map<String, String> parameters = null;
        List<String> errorsEncountered = null;
        
        // check if there is a valid session
        LessonManagementForm form = (LessonManagementForm)
                getFromSessionScope(request, LessonManagementForm.class);
        
        // also validate the redirect call
        if (form == null || 
            form.getLessons() == null || 
            form.getLessons().isEmpty() || 
            redirectCall == null || redirectCall.isEmpty() ||
            form.getRedirectCode() == null ||
            !redirectCall.equals(form.getRedirectCode())) {
            
            // send a redirect
            parameters = AdvancedDataStructuresFactory.createHashMap();
            parameters.put("invalid-request", "1");
            
            return sendRedirect(RequestMappings.ADD_NEW_LESSONS, parameters);
        }
        
        // 
        
        return null;
    }
    
    
}
