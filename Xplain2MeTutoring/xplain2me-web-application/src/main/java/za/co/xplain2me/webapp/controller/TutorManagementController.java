package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.xplain2me.dao.EventTypes;
import za.co.xplain2me.dao.ProfileDAO;
import za.co.xplain2me.dao.ProfileDAOImpl;
import za.co.xplain2me.dao.SubjectDAO;
import za.co.xplain2me.dao.SubjectDAOImpl;
import za.co.xplain2me.dao.SystemAuditManager;
import za.co.xplain2me.dao.TutorDao;
import za.co.xplain2me.dao.TutorDaoImpl;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.ProfileType;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.entity.Tutor;
import za.co.xplain2me.entity.TutorSubject;
import za.co.xplain2me.model.SearchUserProfileType;
import za.co.xplain2me.util.NumericUtils;
import za.co.xplain2me.util.mail.EmailSender;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.TutorManagementForm;
import za.co.xplain2me.webapp.component.UserContext;

@Controller
public class TutorManagementController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger
            .getLogger(TutorManagementController.class.getName(), null);
    
    public TutorManagementController() {
    }
    
    /**
     * Browse the existing tutors
     * 
     * @param request
     * @param startFrom
     * @param resultsPerPage
     * @return 
     */
    @RequestMapping(value = RequestMappings.BROWSE_TUTORS, 
            method = RequestMethod.GET)
    public ModelAndView showBrowseTutorsPage(
            HttpServletRequest request,
            @RequestParam(value = "start_from", required = false)Integer startFrom,
            @RequestParam(value = "results_per_page", required = false)Integer resultsPerPage) {
        
        // create a new form
        TutorManagementForm form = new TutorManagementForm();
        
        // get the starting point and limit per page
        if (startFrom == null || 
                startFrom < 0 || startFrom > Integer.MAX_VALUE)
            startFrom = 0;
        
        form.setBrowseStartingFrom(startFrom);
        
        if (resultsPerPage == null || 
                resultsPerPage == 0 || resultsPerPage > 50)
            resultsPerPage = TutorManagementForm.LIMIT_RESULTS_PER_PAGE_TO;
        
        form.setBrowseLimitPerPage(resultsPerPage);
        
        // get the user context
        UserContext context = getUserContext(request);
        Profile profilePerformingAction = context.getProfile();
        
        // get a list of all tutors
        TutorDao tutorDao = new TutorDaoImpl();
        List<Tutor> tutors = tutorDao.browseTutors(startFrom, resultsPerPage, 
                profilePerformingAction);
        
        // if there were no results in the
        // the browse search
        if (tutors == null || tutors.isEmpty()) {
            
            // create an alert into the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                    .addAlertBlockMessage("You have reached the end of the list " + 
                            "of available tutors."));
            
        }
        
        else {
            
            // update the start from variable
            if (tutors.size() == resultsPerPage)
                form.setBrowseStartingFrom(startFrom + resultsPerPage);
            
            // set the tutors into a tree map
            form.setTutors(new TreeMap<Long, Tutor>());
            for (Tutor tutor : tutors)
                form.getTutors().put(tutor.getId(), tutor);
            
        }
        
        // save the form to the 
        // session scope
        saveToSessionScope(request, form);
         
        // return a view
        return createModelAndView(Views.BROWSE_TUTORS);
    }
    
    /**
     * Displays a page of all tutor-eligible user
     * profiles for selection to be added as a tutor.
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = RequestMappings.CREATE_TUTOR, 
            method = RequestMethod.GET)
    public ModelAndView showCreateTutorSelectionPage(HttpServletRequest request) {
        
        
        // get the user context from the
        // session scope
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        Profile profilePerformingAction = context.getProfile();
        
        // retrive the profile account
        ProfileDAO profileDao = new ProfileDAOImpl();
        List<Profile> tutorProfiles = new ArrayList<>();
        
        // get all manager profiles
        List<Profile> results = profileDao.searchUserProfile(SearchUserProfileType.ProfileType, 
                (long)ProfileType.APP_MANAGER_PROFILE,
                profilePerformingAction);
        
        if (results != null)
            tutorProfiles.addAll(results);
        
        // get all tutor profiles
        results = profileDao.searchUserProfile(SearchUserProfileType.ProfileType, 
                (long)ProfileType.TUTOR_PROFILE,
                profilePerformingAction);
        
        if (results != null)
            tutorProfiles.addAll(results);
        
        // no tutor elifgible user profiles could be found
        if (tutorProfiles.isEmpty()) {
            
            saveToRequestScope(request, new AlertBlock()
                .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                .addAlertBlockMessage("No user profile accounts eligible to be "
                        + "added as tutors could be found."));
            
        }
        
        // migrate alert block from session scope
        // to request scope
        migrateAlertBlockToRequestScope(request);
        
        // save the list of tutor profiles
        // to the request scope
        saveToRequestScope(request, tutorProfiles, "tutorProfiles");
        
        // return a view
        return createModelAndView(Views.CREATE_TUTOR_SELECTION);
        
    }
    
    /**
     * Creates a tutor from an existing user profile account.
     * 
     * @param request
     * @param profileId
     * @return 
     */
    @RequestMapping(value = RequestMappings.CREATE_TUTOR, method = RequestMethod.GET, 
            params = { "profile_id" } )
    public ModelAndView createTutorFromProfileAccount(
            HttpServletRequest request,
            @RequestParam(value = "profile_id")String profileId) {
        
        // validate the parameter
        if (profileId == null || profileId.isEmpty() || 
                NumericUtils.isNaN(profileId) ||
                (Long.parseLong(profileId) < 1 || Long.parseLong(profileId) > Long.MAX_VALUE)) {
            
            // create an alert block into the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .addAlertBlockMessage("The sequence ID provided for the profile account "
                            + "appears invalid for a numeric value."));
            
            // send a redirect 
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS + 
                    "?error-occured=true");
        }
        
        // get the user context from the
        // session scope
        UserContext context = (UserContext)getFromSessionScope(request,
                UserContext.class);
        Profile profilePerformingAction = context.getProfile();
        
        // retrive the profile account
        ProfileDAO profileDao = new ProfileDAOImpl();
        Profile tutorProfile = profileDao.getProfileById(Long.parseLong(profileId), 
                profilePerformingAction.getProfileType().getId());
        
        // if the tutor profile was not found 
        // or the tutor profile is below the Tutor Profile
        // privilege level
        if (tutorProfile == null || 
                tutorProfile.getProfileType().getId() < ProfileType.TUTOR_PROFILE) {
            
            // create an alert block into the
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .addAlertBlockMessage("The user profile cannot be assigned as a tutor. "
                            + "You may only assign an app manager and tutor profile accounts as tutors. "
                            + "You may also only assign tutors to users you are allowed to manage."));
            
            // send a redirect 
            return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS + 
                    "?error-occured=true");
            
        }
        
        // if a valid tutor or app manager profile was found
        else {
            
            TutorDao tutorDao = new TutorDaoImpl();
            
            // check if the tutor is not already
            // assigned as tutor
            boolean isTutorAlready = tutorDao.isTutorAlready(tutorProfile,
                    profilePerformingAction);
            
            if (isTutorAlready) {
                
                // create an alert block into the
                // request scope
                saveToRequestScope(request, new AlertBlock()
                        .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                        .addAlertBlockMessage("The user profile account is already"
                                + " assigned as tutor."));
                
                return createModelAndView(Views.CREATE_TUTOR_CONFIRMATION);
                
            }
            
            // create an instance of a tutor
            Tutor tutor = new Tutor();
            tutor.setProfile(tutorProfile);
            tutor.setRequest(null);
            
            // persist the tutor to the data store
            tutor = tutorDao.createTutor(tutor, profilePerformingAction);
            
            if (tutor == null) {
                
                // create an alert block into the
                // request scope
                saveToRequestScope(request, new AlertBlock()
                        .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                        .addAlertBlockMessage("You do not have the authority "
                                + "to perform this action."));
                // send a redirect 
                return sendRedirect(RequestMappings.BROWSE_EXISTING_USERS + 
                        "?error-occured=true");
            
            }
            
            else {
                
                // log an audit
                SystemAuditManager.logAuditAsync(EventTypes.CREATE_NEW_TUTOR, 
                        context.getProfile().getPerson().getUser(), 
                        tutor.getId(), null, 
                        request.getRemoteAddr(), 
                        request.getHeader("User-Agent"), 
                        0, true);
                
                // send the user an email notification
                final String emailAddress = tutor.getProfile().getPerson()
                        .getContactDetail().getEmailAddress().toLowerCase();
                
                final String firstName = tutor.getProfile().getPerson()
                        .getFirstNames().split(" ")[0];
                
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        
                        StringBuilder body = new StringBuilder();
                        body.append("Howdy, ").append(firstName).append("\r\n")
                            .append("You have been added as a tutor at ")
                            .append("Xplain2Me Tutoring. We are happy to have you ")
                            .append("on board and hope you will enjoy working ")
                            .append("with us.").append("\r\n\r\n")
                            .append("Regards,\r\n")
                            .append("Xplain2Me Tutoring \r\n")
                            .append("Visit our website: ")
                            .append("www.xplain2me.co.za\r\n")
                            .append("Email us at: ")
                            .append("info@xplain2me.co.za\r\n");
                            
                        EmailSender email = new EmailSender();
                        email.setToAddress(emailAddress);
                        email.setSubject("Welcome to our team of tutors | Xplain2Me Tutoring");
                        email.setHtmlBody(false);
                        email.sendEmail(body.toString());
                        
                    }
                }).start();
                
                // create an alert block into the
                // request scope
                saveToRequestScope(request, new AlertBlock()
                        .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                        .addAlertBlockMessage("A new tutor has been assigned successfully."));
                
                
                // save the tutor to the
                // request scope
                saveToRequestScope(request, tutor);
            }
            
        }
        
        return createModelAndView(Views.CREATE_TUTOR_CONFIRMATION);
    }
    
    /**
     * Displays a page to assign a tutor subjects.
     * 
     * @param request
     * @param tutorId
     * @return 
     */
    @RequestMapping(value = RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR, 
            method = RequestMethod.GET)
    public ModelAndView showAssignSubjectsToTutorPage(
            HttpServletRequest request,
            @RequestParam(value = "tutor_id", required = true)Long tutorId) {
        
        // create a new form
        TutorManagementForm form = new TutorManagementForm();
        form.setTutors(new TreeMap<Long, Tutor>());
        
        // validate the tutor ID
        if (tutorId == null || 
                tutorId < 0 || tutorId > Long.MAX_VALUE) {
            
            // save to the request scope
            // an alert block
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .addAlertBlockMessage("The tutor sequence ID does not appear authentic.")); 
            
            // return a view
            return createModelAndView(Views.ASSIGN_SUBJECTS_TO_TUTOR);
        }
        
        // get the user context
        UserContext context = (UserContext)
                getFromSessionScope(request, UserContext.class);
        Profile profilePerformingAction = context.getProfile();
        
        // get the tutor by ID
        TutorDao tutorDao = new TutorDaoImpl();
        List<Tutor> tutors = tutorDao.searchTutor(TutorDao.SEARCH_BY_TUTOR_ID,
                tutorId, profilePerformingAction);
        
        // if no tutor could be found
        if (tutors == null || tutors.size() != 1) {
            
            // save an alert into the 
            // request scope
            saveToRequestScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_ERROR)
                    .addAlertBlockMessage("No tutor could be found or you may not have "
                            + "any permission to manage this tutor with your"
                            + " given current privilege."));
            
            // return a view
            return createModelAndView(Views.ASSIGN_SUBJECTS_TO_TUTOR);
            
        } 
        
        // get a single instance of the tutor
        Tutor tutor = tutors.get(0);
        form.getTutors().put(tutor.getId(), tutor);
        
        // get a list of subjects the tutor is already assigned
        List<Subject> subjectsAlreadyAssigned = tutorDao.getTutorSubjects(tutor, 
                profilePerformingAction);
        
        // save the subjects to a form
        form.setSubjectsAlreadyAssigned(new TreeMap<Long, Subject>());
        for (Subject item : subjectsAlreadyAssigned)
            form.getSubjectsAlreadyAssigned().put(item.getId(), item);
        
        // get a list of all other subjects
        SubjectDAO subjectDao = new SubjectDAOImpl();
        List<Subject> subjectsToChooseFrom = subjectDao.getAllSubjects();
        
        // exclude from the list subjects that 
        // are already assigned to the tutor
        for (Subject subject : subjectsAlreadyAssigned) {
            
            if (subjectsToChooseFrom.contains(subject))
                subjectsToChooseFrom.remove(subject);
        }
        
        // save the subjects to choose from to the
        // form
        form.setSubjectsToChooseFrom(new TreeMap<Long, Subject>()); 
        for (Subject item : subjectsToChooseFrom) 
            form.getSubjectsToChooseFrom().put(item.getId(), item);
        
        // migrate the alert block
        migrateAlertBlockToRequestScope(request);
        
        // save to the session scope
        saveToSessionScope(request, form);
        
        // return a view
        return createModelAndView(Views.ASSIGN_SUBJECTS_TO_TUTOR);
    }
    
    /**
     * Processes the request to add, remove or append 
     * subjects to a tutor.
     * 
     * @param request
     * @param addSubjects
     * @param removeSubjects
     * @return 
     */
    @RequestMapping(value = RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR, 
            method = RequestMethod.POST)
    public ModelAndView processAssignSubjectsToTutorPage(
            HttpServletRequest request, 
            @RequestParam(value = "add_subject", required = false)Long[] addSubjects, 
            @RequestParam(value = "remove_subject", required = false)Long[] removeSubjects) {
        
        // variables
        Map<String, String> parameters = null;
        TutorDao tutorDao = null;
        
        // get the form from the 
        // session scope
        TutorManagementForm form = (TutorManagementForm)
                getFromSessionScope(request, TutorManagementForm.class);
        
        if (form == null) {
            
            // create an error alert into the
            // session scope
            saveToSessionScope(request, 
                    new AlertBlock(AlertBlock.ALERT_BLOCK_ERROR, 
                    "An invalid request was detected."));
            
            // send a redirect
            parameters = new HashMap<>();
            parameters.put("invalid-request", "1");

            return sendRedirect(RequestMappings.BROWSE_TUTORS,
                    parameters);
            
        }
        
        // get the tutor
        Tutor tutor = form.getTutors().firstEntry().getValue();
        
        // get the user context
        UserContext context = getUserContext(request);
        Profile profilePerformingAction = context.getProfile();
        
        // instantiate the tutor DAO
        tutorDao = new TutorDaoImpl();
        
        boolean completed = false;
        boolean changeDone = false;
        
        // if there are subjects to be 
        // removed 
        if (removeSubjects != null && removeSubjects.length > 0) {
            
            // build a list of new tutor subject entities
            List<Subject> entries = new ArrayList<>();
            
            for (Long subjectId : removeSubjects) {
                
                Subject entry = form
                        .getSubjectsAlreadyAssigned().get(subjectId);
                entries.add(entry);
            }
            
            completed = tutorDao.removeSubjectsFromTutor(tutor, entries,
                    profilePerformingAction);
            changeDone = true;
        }
        
        // if there are subjects to be added
        if (addSubjects != null && addSubjects.length > 0) {
            
            // build a list of new tutor subject entities
            List<TutorSubject> entries = new ArrayList<>();
            
            for (Long subjectId : addSubjects) {
                
                TutorSubject entry = new TutorSubject();
                entry.setSubject(form.getSubjectsToChooseFrom().get(subjectId));
                entry.setTutor(tutor); 
                
                entries.add(entry);
            }
            
            completed = tutorDao.assignSubjectsToTutor(entries, 
                    profilePerformingAction);
            changeDone = true;
        }
        
        // if the update was successful and there
        // was some changes done
        if (completed && changeDone) {
            
            // create an alert into the
            // session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                    .addAlertBlockMessage("Changes to the assignment of subjects to " + 
                            "were successfully done.")
            );
            
        }
        
        // if the update was not successful and 
        // there were changes attempted
        else if (!completed && changeDone) {
            
            // create an alert into the
            // session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_WARNING)
                    .addAlertBlockMessage("Changes to the assignment of subjects to " + 
                            "were not successfully done - an internal error occured. " + 
                            "Contact your system administrator if this persists.")
            );
            
        }
        
        // if the update was not completed 
        // and no changes could be picked up
        else if (!completed && !changeDone) {
            
            // create an alert into the
            // session scope
            saveToSessionScope(request, new AlertBlock()
                    .setAlertBlockType(AlertBlock.ALERT_BLOCK_INFORMATIVE)
                    .addAlertBlockMessage("No changes were made to the " + 
                            "assignment of subjects to the tutor.")
            );
            
        }
        
        // remove form from session
        // scope
        removeFromSessionScope(request, TutorManagementForm.class);
        
        // send a redirect
        parameters = new HashMap<>();
        parameters.put("tutor_id", String.valueOf(tutor.getId()));
        parameters.put("action", "completed");
        
        return sendRedirect(RequestMappings.ASSIGN_SUBJECTS_TO_TUTOR, parameters);
    }
    
    
    // =========================================================================
    //  CUSTOM METHODS
    // =========================================================================
    
}
