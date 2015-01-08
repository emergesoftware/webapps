package za.co.emergelets.xplain2me.dao;

public interface EventTypes {
    
    /** Login Event - 1000 */
    public static final long LOGIN_EVENT = 1000;
    /** Mark Tutor Request As Read Event - 1020 */
    public static final long MARK_TUTOR_REQUEST_AS_READ_EVENT = 1020;
    /** Delete Tutor Request Event 1021 */
    public static final long DELETE_TUTOR_REQUEST_EVENT = 1021;
    /** Update Own User Password */
    public static final long UPDATE_OWN_USER_PASSWORD = 1022;
    /** Update Own User Login Name */
    public static final long UPDATE_OWN_USERNAME = 1023;
    /** Update Own Personal Information */
    public static final long UPDATE_OWN_PERSONAL_INFORMATION = 1024;
    /** Update Own Contact Details */
    public static final long UPDATE_OWN_CONTACT_DETAILS = 1025;
    /** Update Own Physical Address */
    public static final long UPDATE_OWN_RESIDENTIAL_ADDRESS = 1026;
    /** Send a tutor interview invitation */
    public static final long SEND_TUTOR_APPLICANT_INTERVIEW_INVITATION = 1027;
    
    /** Create New User */
    public static final long CREATE_NEW_USER_PROFILE = 2000;
    /** Assign User To Profile */
    public static final long ASSIGN_USER_TO_PROFILE = 2001;
    /** Verify Own Profile */
    public static final long VERIFY_OWN_PROFILE = 2008;
    
}
