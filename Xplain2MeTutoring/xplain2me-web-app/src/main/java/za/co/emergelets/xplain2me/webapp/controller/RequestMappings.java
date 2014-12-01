package za.co.emergelets.xplain2me.webapp.controller;

public interface RequestMappings {
    
    // BECOME A TUTOR MAPPINGS
    public static final String BECOME_A_TUTOR = "/become-a-tutor";
    public static final String PROCESS_TUTOR_JOB_APPLICATION = "/process-tutor-job-application";
    public static final String VERIFY_BECOME_A_TUTOR_REQUEST = "/verify-become-a-tutor-request";
    
    // USER LOGIN MAPINGS
    public static final String LOGIN = "/login";
    public static final String PROCESS_LOGIN_REQUEST = "/process-login-request";
    public static final String LOGOUT = "/logout";
    
    // MANAGER DASHBOARD MAPPINGS
    public static final String MANAGER_DASHBOARD = "/portal/manager/dashboard/*";
    public static final String MANAGER_DASHBOARD_BROWSE = "browse";
    
    // QUOTE REQUEST MAPPINGS
    public static final String QUOTE_REQUEST = "/quote-request";
    
    // TUTOR REQUEST MAPPINGS
    public static final String REQUEST_A_TUTOR = "/request-a-tutor";
    public static final String PROCESS_REQUEST_FOR_TUTOR = "/process-request-for-tutor";
    public static final String VERIFY_TUTOR_REQUEST = "/verify-tutor-request";
    
    // TUTOR REQUESTS MANAGEMENT MAPPINGS
    public static final String TUTOR_REQUESTS_MANAGEMENT_VIEW = 
            "/portal/manager/tutor-requests/view";
    public static final String TUTOR_REQUESTS_MANAGEMENT_SEARCH = 
            "/portal/manager/tutor-requests/search";
    public static final String TUTOR_REQUESTS_MANAGEMENT_MARK_AS_READ = 
            "/portal/manager/tutor-requests/mark-as-read";
    public static final String TUTOR_REQUESTS_MANAGEMENT_REMOVE = 
            "/portal/manager/tutor-requests/remove";
    public static final String TUTOR_REQUESTS_MANAGEMENT_DETAILS = 
            "/portal/manager/tutor-requests/details";
    
}
