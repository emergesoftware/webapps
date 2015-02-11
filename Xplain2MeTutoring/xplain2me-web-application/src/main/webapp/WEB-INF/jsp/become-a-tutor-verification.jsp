
<%@page import="za.co.xplain2me.webapp.component.BecomeTutorForm"%>

<%
    BecomeTutorForm form = (BecomeTutorForm)session
            .getAttribute(BecomeTutorForm.class.getName());
    if (form == null || form.getBecomeTutorRequest() == null) {
        response.sendRedirect("/become-a-tutor");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="tutoring, private home tuition, tutors">
        <meta name="author" content="Tsepo Maleka">
        
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        
        <title>Become a Tutor | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript">
            
            function validateVerificationCodeForm(form) {
                
                if (form === null) {
                    form = document.getElementById("verificationCodeForm");
                }

                if (form.verificationCode.value === null || 
                        form.verificationCode.value.length !== 4 || 
                        isNaN(form.verificationCode.value) === true) {

                    $("#verificationCodeFormGroup").addClass("has-error");
                    return false;
                }

                else {

                    $("#verificationCodeFormGroup").removeClass("has-error");
                    return true;
                }

            }
            
        </script>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
        <div class="container">
            
            <h3 class="centered">Verification Code:</h3>
            <hr/>
            
            <div class="row white">
                <div class="col-lg-6">
            
                    <p>
                        <span>We have sent you an email to </span>
                        <span><strong><%= form.getBecomeTutorRequest().getEmailAddress() %></strong>.</span>
                        <br/>
                        <span>Go to your email inbox to obtain the 4-digit verification code.</span>
                        <br/>
                        <span>You must do this in the next 5 minutes, 
                            or else the code will expire.</span>
                    </p> 

                    <%
                        if (form.getErrorsEncountered().isEmpty() == false) {
                            %>
                            <div id="alertBlock" class="alert alert-danger" role="alert">
                                <%
                                    for (String error : form.getErrorsEncountered()) {
                                        %>
                                        <li><%= error %></li>
                                    <%
                                    }
                                %>
                            </div>
                            <%
                        }
                    %>

                    <form id="verificationCodeForm" name="verificationCodeForm"
                          method="post" action="<%= request.getContextPath() %>/verify-become-a-tutor-request"
                          onsubmit="return validateVerificationCodeForm(this)"
                          style="width:60%">

                        <div id="verificationCodeFormGroup" class="form-group">
                            <label>Verification Code:</label>
                            <input type="text" class="form-control" id="verificationCode"
                                   name="verificationCode" maxlength="4" autocomplete="off"
                                   placeholder="Enter your Verification Code"/>
                        </div>
                        <br/>
                        <input type="submit" id="submit"
                            name="verify" class="btn btn-primary"
                            value="Verify"/>

                    </form>
            
                <br/>
                <br/>
            </div>
        </div>
            
        <%@include file="../jspf/template/default-footer.jspf" %>
            
        </div>
    </body>
</html>
