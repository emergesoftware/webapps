
<%@page import="za.co.xplain2me.webapp.component.TutorJobApplicationForm"%>
<%@page import="za.co.xplain2me.entity.BecomeTutorRequest"%>
<%
    
    TutorJobApplicationForm form = (TutorJobApplicationForm)
            session.getAttribute(TutorJobApplicationForm.class.getName());
    
    if (form == null) {
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.DASHBOARD_OVERVIEW + 
                "?invalid-request=1" + 
                "&rand=" + System.currentTimeMillis());
        return;
    }
    
    BecomeTutorRequest becomeTutorRequest = form.getTutorJobApplication();
    boolean isFound = (becomeTutorRequest != null);
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>View Tutor Job Application Details</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/bootstrap-datepicker.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Send a job approval</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <%
                        if (isFound || becomeTutorRequest != null) {
                            
                    %>
                    
                        <div class="col-md-4">

                            <form role="form" name="approvalForm" id="approvalForm"
                                method="post"
                                action="<%= request.getContextPath() + RequestMappings.APPROVE_TUTOR_JOB_APPLICATION %>"> 

                                <p class="text-primary">
                                    To send this applicant an approval of their tutor job application, 
                                    fill in the information below to set up an interview with this
                                    applicant.
                                </p>

                                <div class="form-group">
                                    <label>The date of the interview:</label>
                                    <input type="text" id="dateOfInterview" name="dateOfInterview"
                                           class="form-control datepicker"
                                           placeholder="Pick a time of the interview (YYYY-MM-DD)" 
                                           data-validation="date" data-validation-format="yyyy-mm-dd"
                                           data-validation-error-msg="Date of interview is required."/>

                                </div>

                                <div class="form-group">
                                    <label>The time of the interview:</label>
                                    <span class="text-muted">
                                        NB: Use 24-hour format.
                                    </span><br/>

                                    <div class="input-group">
                                        
                                        <span class="input-group-addon">Hour:</span>
                                        
                                        <input type="text" id="hourOfInterview" name="hourOfInterview"
                                               class="form-control"
                                               data-validation="number" data-validation-allowing="range[0;23]"
                                               data-validation-error-msg="Hour must be between 00 and 23." />
                                        
                                        <span class="input-group-addon">Minute:</span>
                                        
                                        <input type="text" id="minuteOfInterview" name="minuteOfInterview"
                                               class="form-control"
                                               data-validation="number" data-validation-allowing="range[0;59]"
                                               data-validation-error-msg="Minute must be between 00 and 59." />
                                        
                                        
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label>Location:</label>
                                    <span class="text-muted">
                                        Type the address of the location the
                                        interview will be held at.
                                    </span><br/>
                                    <textarea id="location" name="location"
                                        class="form-control"
                                        data-validation="required" rows="3"
                                        data-validation-error-msg="Location of the interview is required."
                                        placeholder="Enter the location of the interview."></textarea>
                                </div>
                                
                                <div class="form-group">
                                    <label>Default Email Body:</label>
                                    <br/>
                                    <span class="text-muted">
                                        This is the default email body of the
                                        email (preview). You may add any other
                                        additional notes below that you would like
                                        to include for the applicant.
                                    </span>
                                    <br/>
                                    <div class="panel panel-default">
                                        <div class="panel-body">
                                            <br/>
                                            Dear [Applicant Full Names], <br/>
                                            <br/>
                                            We would like to inform you that your tutor job <br/>
                                            application made with us on the [Date] was successful <br/>
                                            and would like to invite you for an interview as follows: <br/>
                                            <br/>
                                            Date: [Date of Interview]<br/>
                                            Time: [Time of Interview]<br/>
                                            Location: [Location of Interview] <br/>
                                            <br/>
                                            Please arrive at least 15 minutes before the interview commences.<br/>
                                            [Your additional notes here]<br/>
                                            <br/>
                                        </div>
                                    </div>
                                </div>
                                
                                <div class="form-group">
                                    <label>Additional Notes:</label>
                                    <textarea id="additionalNotes" name="additionalNotes"
                                        class="form-control" rows="3"
                                        placeholder="Enter your additional notes - if any."></textarea>
                                </div>
                                
                                <div class="panel panel-default">
                                    <div class="panel-body">
                                        <strong>Note: </strong>
                                        The system cannot determine if the email was
                                        delivered to the applicant. Please check the
                                        integrated email account to check this.
                                    </div>
                                </div>
                                
                                <di class="form-group">
                                    <input type="submit" value="Send Approval"
                                           class="btn btn-primary"
                                           ondblclick="return false" />
                                </di>

                            </form>

                        </div>
                    
                    <%
                        }
                    %>
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
