
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="tutoring, private home tuition, tutors">
        <meta name="author" content="Tsepo Maleka">
        
        <title>Verify Own User Profile</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
        <div class="container" id="content">

            <h3 class="centered">Verify new user profile</h3>
            <hr/>
            
            <div class="row">
                <div class="col-lg-6 col-lg-offset-2">
                    
                    <p class="text-muted centered">
                        To verify your new user profile, please
                        enter the details required below, including
                        the verification code that was emailed to you.
                    </p>
                    
                    <%@include file="../jspf/template/default-alert-block.jspf" %>
                    
                    <form role="form" name="verificationForm" id="verificationForm"
                          method="post">

                        <div class="form-group">
                            <label>Enter your ID / Passport Number:</label>
                            <input type="text" id="idNumber" name="idNumber"
                                class="form-control" maxlength="24"
                                placeholder="Enter ID or Passport Number (Required)"
                                data-validation="length" data-validation-length="min6" 
                                data-validation-error-msg="Identity or Passport Number is required."/>
                        </div>

                        <div class="form-group">
                            <label>Enter your Email Address:</label>
                            <input type="text" id="emailAddress" name="emailAddress" 
                                class="form-control" maxlength="64"
                                placeholder="Enter the Email Address (Required)"
                                data-validation="length" data-validation-length="min8" 
                                data-validation-error-msg="Email Address is required."/>
                        </div>

                        <div class="form-group">
                            <label>Enter the Verification Code sent to you:</label>
                            <input type="text" id="verificationCode" name="verificationCode" 
                                class="form-control" maxlength="4"
                                placeholder="Enter the Verification Code (Required)"
                                data-validation="length" data-validation-length="min4" 
                                data-validation-error-msg="Verification Code is required."/>
                        </div>
                        
                        <div class="form-group">
                            <label>Enter new password:</label>
                            <input type="password" id="newPassword" name="newPassword" 
                                class="form-control" maxlength="24"
                                placeholder="Enter new password (Required)"
                                data-validation="length" data-validation-length="min8" 
                                data-validation-error-msg="New Password is required."/>
                        </div>
                        
                        <div class="form-group">
                            <label>Re-enter new password:</label>
                            <input type="password" id="reEnterNewPassword" name="reEnterNewPassword" 
                                class="form-control" maxlength="24"
                                placeholder="Re-enter new password (Required)"
                                data-validation="length" data-validation-length="min8" 
                                data-validation-error-msg="New Re-Entered Password is required."/>
                        </div>

                        <br/>
                        
                        <p>
                            <span><strong>Complete this human test challenge:</strong></span>
                            <span class="text-muted">Type the text you see on the image below:</span>
                        </p>
                        
                        <%@include file="../jspf/template/re-captcha-code-snippet.jspf" %>

                        <br/>

                        <input type="submit" id="verifyAccountButton"
                               class="btn btn-primary" ondblclick="return false"
                               value="Verify and Activate Profile" />

                    </form>
                        
                </div>
            </div>

            <br/> 
        </div>

        <%@include file="../jspf/template/default-footer.jspf" %>
   
    </body>
</html>
