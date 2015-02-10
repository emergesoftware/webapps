package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
import za.co.xplain2me.model.SearchUserProfileType;
import za.co.xplain2me.util.NumericUtils;
import za.co.xplain2me.util.mail.EmailSender;
import za.co.xplain2me.webapp.component.AlertBlock;
import za.co.xplain2me.webapp.component.UserContext;

@Controller
public class TutorManagementController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger
            .getLogger(TutorManagementController.class.getName(), null);
    
    public TutorManagementController() {
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
            method = RequestMethod.GET, 
            params = "tutor_id")
    public ModelAndView showAssignSubjectsToTutorPage(
            HttpServletRequest request,
            @RequestParam(value = "tutor_id")Long tutorId) {
        
        if (tutorId < 0 || tutorId > Long.MAX_VALUE) {
            
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
        
        // get a list of subjects the tutor is already assigned
        List<Subject> subjectsAlreadyAssigned = tutorDao.getTutorSubjects(tutor, 
                profilePerformingAction);
        
        // get a list of all other subjects
        SubjectDAO subjectDao = new SubjectDAOImpl();
        List<Subject> subjectsToChooseFrom = subjectDao.getAllSubjects();
        
        // exclude from the list subjects that 
        // are already assigned to the tutor
        for (Subject subject : subjectsAlreadyAssigned) {
            
            if (subjectsToChooseFrom.contains(subject))
                subjectsToChooseFrom.remove(subject);
        }
        
        // save to the request scope
        saveToRequestScope(request, subjectsAlreadyAssigned, "subjectsAlreadyAssigned"); 
        saveToRequestScope(request, subjectsToChooseFrom, "subjectsToChooseFrom");
        saveToRequestScope(request, tutor, "tutor");
        
        // return a view
        return createModelAndView(Views.ASSIGN_SUBJECTS_TO_TUTOR);
    }
}
