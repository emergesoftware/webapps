package za.co.emergelets.xplain2me.bo.validation.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import za.co.emergelets.xplain2me.bo.validation.TutorJobApplicationBO;
import za.co.emergelets.xplain2me.model.TutorJobApproval;

public class TutorJobApplicationBOImpl implements TutorJobApplicationBO {

    private static final Logger LOG = 
            Logger.getLogger(TutorJobApplicationBOImpl.class.getName(), null);
    
    private List<String> errors;
    private SimpleDateFormat formatter;
    
    public TutorJobApplicationBOImpl() {
        this.errors = null;
        this.formatter = null;
    }
    
    @Override
    public List<String> validateTutorJobApprovalParameters(TutorJobApproval approval) {
        
        errors = new ArrayList<>();
        
        // check if the date of interview is provided
        if (approval.getDateOfInterview() == null) {
            errors.add("The date of the interview is required. The "
                        + "accepted format is: YYYY-MM-DD.");
        }
        
        else {
           
            // check that the date of interview is after today
            // and not after 90 days.
            Date today = new Date();
            if (approval.getDateOfInterview().before(today) || 
                    Days.daysBetween(new DateTime(today), 
                        new DateTime(approval.getDateOfInterview())).getDays() > 90) {
                errors.add("The date of interview must be after the current time and "
                        + "cannot be made later than 90 days.");
            }
            
        }
        
        // check the time of interview
        if (approval.getTimeOfInterview() == null) {
            errors.add("The time (hour and minute) of the interview is required. ");
        }
        
        // check that the location is provided
        if (approval.getLocation() == null || approval.getLocation().isEmpty()) {
            errors.add("The location of the interview is required.");
        }
        
        return errors;
    }

    @Override
    public String getDefaultEmailBodyForTutorApproval(TutorJobApproval approval) {
        
        StringBuilder builder = new StringBuilder();
        String output = null;
        
        builder.append("\n<html>")
               .append("<head>")
               .append("<title>Interview Invitation</title>")
               .append("</head>")
                
               .append("<body style='font-family:Arial; display: block'>") 
                
               .append("<p><strong>Note: Do not reply to this email. Reply to ")
               .append("info@xplain2me.co.za</strong>.</p>")
                
               .append("<p>Dear [applicant_full_name],</p>")
                
               .append("<p>We would like to inform you that your tutor job <br/>")
               .append("application made with us on the [date_of_submission] was successful <br/>")
               .append("and we would like to invite you for an interview as follows:</p>")
               .append("<p><strong>DATE</strong>: [date_of_interview]<br/>")
               .append("<strong>TIME:<strong> [time_of_interview]<br/>")
               .append("<strong>LOCATION:<strong> [location] </p>")
                
               .append("<p>Please arrive at least 15 minutes before the interview commences.</p>")
               .append("<p>[additional_notes]</p>")
                
               .append("<p>Regards,<br/>")
               .append("Xplain2Me Tutoring | Recruitment<br/>")
               .append("<strong>Email:</strong> info@xplain2me.co.za<br/>")
               .append("<strong>Website:</strong> www.xplain2me.co.za</p>")
               
               .append("</body>")
               .append("</html>\n");
                
        
        output = builder.toString();
        
        Map<String, String> injections = new TreeMap<>();
        DateFormat formatter = null;
        
        // applicant full name
        String applicantFullName = approval.getAttachedJobApplication().getLastName() + 
                ", " + approval.getAttachedJobApplication().getFirstNames();
        injections.put("[applicant_full_name]", applicantFullName);
        
        // date of submission
        formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        injections.put("[date_of_submission]", formatter.format(
                approval.getAttachedJobApplication().getDateSubmitted()
        ));
        
        // date of interview
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        injections.put("[date_of_interview]", formatter.format(approval.getDateOfInterview()));
        
        // time of interview
        formatter = new SimpleDateFormat("HH:mm");
        injections.put("[time_of_interview]", formatter.format(approval.getTimeOfInterview()));
        
        // location
        injections.put("[location]", approval.getLocation().toUpperCase());
        
        // additional notes
        if (approval.getAdditionalNotes() == null)
            approval.setAdditionalNotes(""); 
        injections.put("[additional_notes]", approval.getAdditionalNotes());
        
        for (String key : injections.keySet()) {
            String value = injections.get(key);
            output = output.replace(key, value);
        }
        
        LOG.info("... the default email body : \n" + output + "\n");
        
        return output;
        
    }
    
}
