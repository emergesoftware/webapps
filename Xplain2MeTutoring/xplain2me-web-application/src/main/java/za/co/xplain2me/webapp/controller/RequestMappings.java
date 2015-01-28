package za.co.xplain2me.webapp.controller;

public interface RequestMappings {
    
    // ERROR MAPPINGS
    public static final String UNAUTHORIZED_ACCESS = "/error/unauthorized";
    
    // BECOME A TUTOR MAPPINGS
    public static final String BECOME_A_TUTOR = "/become-a-tutor";
    public static final String PROCESS_TUTOR_JOB_APPLICATION = "/process-tutor-job-application";
    public static final String VERIFY_BECOME_A_TUTOR_REQUEST = "/verify-become-a-tutor-request";
    
    // USER LOGIN MAPINGS
    public static final String LOGIN = "/account/login";
    public static final String PROCESS_LOGIN_REQUEST = "/account/process-login-request";
    public static final String LOGOUT = "/account/logout";
    public static final String VERIFY_OWN_USER_PROFILE = "/account/verify-own-profile";
    
    // USER DASHBOARD MAPPINGS
    public static final String DASHBOARD_OVERVIEW = "/portal/dashboard/overview";
    
    //PROFILE + ACCOUNT MAPPINGS
    public static final String MY_PROFILE_EDIT = "/portal/my-profile/edit";
    public static final String EDIT_MY_CREDENTIALS = "/portal/account/edit-credentials";
    
    // QUOTE REQUEST MAPPINGS
    public static final String QUOTE_REQUEST = "/quote-request";
    
    // TUTOR REQUEST MAPPINGS
    public static final String REQUEST_A_TUTOR = "/request-a-tutor";
    public static final String PROCESS_REQUEST_FOR_TUTOR = "/process-request-for-tutor";
    public static final String VERIFY_TUTOR_REQUEST = "/verify-tutor-request";
    
    // TUTOR REQUESTS MANAGEMENT MAPPINGS
    public static final String TUTOR_REQUESTS_MANAGEMENT_UNREAD = 
            "/portal/tutor-requests/unread";
    public static final String TUTOR_REQUESTS_MANAGEMENT_SEARCH = 
            "/portal/tutor-requests/search";
    public static final String TUTOR_REQUESTS_MANAGEMENT_MARK_AS_READ = 
            "/portal/tutor-requests/mark-as-read";
    public static final String TUTOR_REQUESTS_MANAGEMENT_REMOVE = 
            "/portal/tutor-requests/remove";
    public static final String TUTOR_REQUESTS_MANAGEMENT_DETAILS = 
            "/portal/tutor-requests/details";
    
    // BECOME A TUTOR MANAGEMENT MAPPINGS
    public static final String BROWSE_TUTOR_JOB_APPLICATIONS = 
            "/portal/tutor-job-applications/browse";
    public static final String VIEW_TUTOR_JOB_APPLICATION_DETAILS = 
            "/portal/tutor-job-applications/details";
    public static final String APPROVE_TUTOR_JOB_APPLICATION = 
            "/portal/tutor-job-applications/approve";
    public static final String DOWNLOAD_TUTOR_JOB_APPLICATION_SUPPORTING_DOCUMENTS = 
            "/portal/tutor-job-applications/documents/download";
    public static final String SEARCH_TUTOR_JOB_APPLICATIONS = 
            "/portal/tutor-job-applications/search";
    
    // USER MANAGEMENT MAPPINGS
    public static final String BROWSE_EXISTING_USERS = "/portal/users/browse";
    public static final String ADD_NEW_USER = "/portal/users/add";
    public static final String EDIT_USER_PROFILE = "/portal/users/edit-profile";
    public static final String VIEW_USER_PROFILE = "/portal/users/view-profile";
    public static final String CHANGE_USER_PROFILE_TYPE = "/portal/users/change-profile-type";
    public static final String ACTIVATE_OR_BLOCK_USER = "/portal/users/activate-or-block-user";
    public static final String DELETE_USER_PROFILE = "/portal/users/delete-user-profile";
    public static final String SEARCH_USER = "/portal/users/search";
    
    // LESSON MANAGEMENT
    public static final String ADD_NEW_LESSONS = "/portal/lessons/create";
    public static final String FIND_STUDENT_ASYNC = "/portal/lessons/find-student-async";
    
}
