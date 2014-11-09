<%-- 
    Document   : request-a-tutor-submitted
    Created on : 01 Oct 2014, 7:15:12 PM
    Author     : user
--%>

<%@page import="za.co.emergelets.xplain2me.webapp.component.RequestTutorForm"%>
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
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta http-equiv="Pragma" content="no-cache">
        <meta http-equiv="Expires" content="-1">
        
        <title>Request a Tutor | Xplain2Me Tutoring Services</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript" 
        src="<%= request.getContextPath() %>/assets/js/request-a-tutor-verification.js"></script>
        
    </head>
    <body>
        <div id="content" class="container">
            
            <%@include file="../jspf/template/default-banner.jspf" %>
            
            <%@include file="../jspf/template/default-nav-bar.jspf" %>
            
            <h3>Verification Code:</h3>
            <hr/>
            <p>
                <span>We have sent you an email to </span>
                <span><%= form.getTutorRequest().getEmailAddress() %>.</span>
                <br/>
                <span>Go to your email inbox to obtain the 9-digit verification code.</span>
                <br/>
                <span>You must do this in the next 5 minutes, or else the code will expire.</span>
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
                  method="post" action="<%= request.getContextPath() %>/verify-tutor-request"
                  onsubmit="return validateVerificationCodeForm(this)"
                  style="width:60%">
                
                <div id="verificationCodeFormGroup" class="form-group">
                    <label>Verification Code:</label>
                    <input type="text" class="form-control" id="verificationCode"
                           name="verificationCode" maxlength="9" autocomplete="off"
                           placeholder="Enter your Verification Code"/>
                </div>
                <br/>
                <input type="submit" id="submit"
                    name="verify" class="btn btn-primary"
                    value="Verify"/>
   
            </form>
            
            <br/>
            <br/>
            
            <%@include file="../jspf/template/default-footer.jspf" %>
            
        </div>
    </body>
</html>
