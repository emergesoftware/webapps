
<%@page import="za.co.xplain2me.webapp.component.RequestTutorForm"%>

<%
    RequestTutorForm form = (RequestTutorForm)session
            .getAttribute(RequestTutorForm.class.getName());
    if (form == null || form.getTutorRequest() == null)
        response.sendRedirect("/request-a-tutor");
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
        
        <title>Request a Tutor | Xplain2Me Tutoring Services</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript" 
        src="<%= request.getContextPath() %>/assets/js/request-a-tutor-verification.js"></script>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
        <div class="container">
         
            <h1 class="centered">Verification Code:</h1>
            <hr/>
            <p>
                <span>We have sent you an email to </span>
                <span><b><%= form.getTutorRequest().getEmailAddress() %></b>.</span>
                <br/>
                <span>Go to your email inbox to obtain the 4-digit verification code.</span>
                <br/>
                <span>You must do this in the next 5 minutes, or else the code will expire.</span>
            </p> 
           
            <div class="row white">
                <div class="col-lg-6">
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
                          method="post" action="<%= request.getContextPath() %>/verify-tutor-request"
                          onsubmit="return validateVerificationCodeForm(this)">

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
        </div>
        
       <%@include file="../jspf/template/default-footer.jspf" %>
        
    </body>
</html>
