package za.co.xplain2me.webapp.controller;

public interface Views {
    
    // APP MANAGER SPECIFIC
    public static final String USER_DASHBOARD = "user-dashboard";
    
    // TUTOR JOB APPLICATION FOR GUESTS
    public static final String BECOME_A_TUTOR_SUBMITTED = "become-a-tutor-submitted";
    public static final String BECOME_A_TUTOR_VERIFICATION = "become-a-tutor-verification";
    public static final String BECOME_A_TUTOR = "become-a-tutor";
    
    // THE MAIN ENTRY VIEW
    public static final String INDEX = "index";
    
    // USER LOGIN
    public static final String LOGIN = "login";
    
    // REQUEST FOR A QUOTE FOR GUESTS
    public static final String QUOTE_REQUEST_SUBMITTED = "quote-request-submitted";
    public static final String QUOTE_REQUEST = "quote-request";
    
    // REQUEST FOR A TUTOR FOR GUESTS
    public static final String REQUEST_A_TUTOR_SUBMITTED = "request-a-tutor-submitted";
    public static final String REQUEST_A_TUTOR_VERIFICATION = "request-a-tutor-verification";
    public static final String REQUEST_A_TUTOR = "request-a-tutor";
    
    // MANAGE TUTOR REQUESTS MADE BY GUESTS
    public static final String MANAGE_TUTOR_REQUESTS = "manage-tutor-requests";
    public static final String VIEW_TUTOR_REQUEST_DETAILS = "view-tutor-request-details";
    public static final String SEARCH_TUTOR_REQUESTS = "search-tutor-requests";
    public static final String SEARCH_TUTOR_REQUESTS_RESULTS = "search-tutor-requests_results";
    
    // PROFILE MANAGEMENT VIEWS
    public static final String VIEW_OWN_PROFILE = "view-own-profile";
    public static final String EDIT_OWN_PROFILE = "edit-own-profile";
    
    // TUTOR JOB APPLICATIONS VIEWS
    public static final String BROWSE_TUTOR_JOBS_APPLICATIONS = "browse-tutor-job-applications";
    public static final String VIEW_TUTOR_JOB_APPLICATION_DETAILS = "view-tutor-job-application-details";
    public static final String APPROVE_TUTOR_JOB_APPLICATION = "approve-tutor-job-application";
    public static final String DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS = "download-tutor-job-application-supporting-documents";
    public static final String SEARCH_TUTOR_JOB_APPLICATIONS_FORM = "search-tutor-job-applications-form";
    public static final String TUTOR_JOB_APPLICATIONS_SEARCH_RESULTS = "tutor-job-applications-search-results";
    
    // ERROR VIEWS
    public static final String GENERIC_ERROR_PAGE = "generic-error-page";
    
    // USER MANAGEMENT VIEWS
    public static final String BROWSE_EXISTING_USERS = "browse-existing-users";
    public static final String ADD_NEW_USER = "add-new-user";
    public static final String VERIFY_NEW_OWN_USER_PROFILE = "verify-new-own-user-profile";
    public static final String EDIT_USER_PROFILE = "edit-user-profile";
    public static final String VIEW_USER_PROFILE = "view-user-profile";
    public static final String CHANGE_USER_ACCOUNT_PROFILE_TYPE = "change-user-account-profile-type";
    public static final String ACTIVATE_OR_BLOCK_USER = "activate-or-block-user";
    public static final String DELETE_USER_PROFILE = "delete-user-profile";
    public static final String SEARCH_USERS = "search-users";
    public static final String SEARCH_USERS_RESULTS = "search-users-results";
    
    // LESSON MANAGEMENT
    public static final String ADD_NEW_LESSONS = "add-new-lessons";
    
    
    // TUTOR MANAGEMENT
    public static final String CREATE_TUTOR_SELECTION = "create-tutor-selection";
    public static final String CREATE_TUTOR_CONFIRMATION = "create-tutor-confirmation";
    public static final String ASSIGN_SUBJECTS_TO_TUTOR = "assign-subjects-to-tutor";
    
}
