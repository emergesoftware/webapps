<%@page import="za.co.emergelets.xplain2me.webapp.component.RequestQuoteForm"%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    RequestQuoteForm form = (RequestQuoteForm)session
            .getAttribute(RequestQuoteForm.class.getName());
    if (form == null) {
        response.sendRedirect("/quote-request");
        return;
    }
    
%>

<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request Quote | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript" 
            src="<%= request.getContextPath() %>/assets/js/quote-request.js">
        </script>
        
    </head>
    <body>
        
        <div class="container" id="content">
            
            <%@include file="../jspf/template/default-banner.jspf" %>
            
            <%@include file="../jspf/template/default-nav-bar.jspf" %>
            
            <h3>Request a quote</h3>
            <p class="text-muted">
                To request a quote with our rates, please 
                fill in this form and we will send you 
                a quote as soon as possible.
            </p>
            
            <hr/>
            
            <form id="requestQuoteForm" name="requestQuoteForm" role="form"
                  action="<%= request.getContextPath() %>/quote-request"
                  method="post" onsubmit="return validateRequestQuoteForm(this)">
                
                <%
                    
                    boolean errorsEncountered = false;
                    
                    // Check if there are any errors encountered
                    if (form.getErrorsEncountered() != null && 
                            form.getErrorsEncountered().isEmpty() == false) {
                            
                        errorsEncountered = true;
                    }
                %>
                
                <div id="alertBlock" class="alert alert-danger" role="alert"
                    <%= (!errorsEncountered) ? "style=\"display:none\"" : "" %>>
                    
                    <%
                        if (errorsEncountered) {
                            %>
                            <h4>The following errors were encountered:</h4>
                            <%
                            for (String error : form.getErrorsEncountered()) {
                            %>
                            <span><%= error %></span><br/>                            
                            <%
                            }
                        }
                    %>
                    
                </div>
                     
                <br/>
                <p class="text-primary">
                    <strong>Note: </strong>
                    All fields are mandatory.
                </p>
                
                <!-- Last Name -->
                
                <div id="lastNameFormGroup" class="form-group">
                    <label>Last Name:</label>
                    <input type="text" class="form-control" id="lastName"
                           name="lastName" maxlength="32"
                           placeholder="Enter the Last Name"
                           value="<%= (form.getLastName() == null) 
                           ? "" : form.getLastName() %>"/>
                </div>
                
                <!-- First Names -->
                
                <div id="firstNamesFormGroup" class="form-group">
                    <label>First Names:</label>
                    <input type="text" class="form-control" id="firstNames"
                           name="firstNames" maxlength="32"
                           placeholder="Enter the First Names"
                           value="<%= (form.getFirstNames() == null) 
                                   ? "" : form.getFirstNames() %>"/>
                </div>
                
                <!-- Email Address -->
                
                <div id="emailAddressFormGroup" class="form-group">
                    <label>Email Address:</label>
                    <input type="text" class="form-control" id="emailAddress"
                           name="emailAddress" maxlength="64"
                           placeholder="Enter the Email Address"
                           autocomplete="off"/>
                </div>
                
                <!-- Contact Number -->
                
                <div id="contactNumberFormGroup" class="form-group">
                    <label>Contact Number:</label>
                    <input type="text" class="form-control" id="contactNumber"
                           name="contactNumber" maxlength="10"
                           placeholder="Enter the Contact Number"
                           autocomplete="off"/>
                </div>
                
                <!-- City / Town -->
                
                <div id="cityOrTownFormGroup" class="form-group">
                    <label>City / Town:</label>
                    <input type="text" class="form-control" id="cityOrTown"
                           name="cityOrTown" maxlength="64"
                           placeholder="Enter the City / Town"
                           value= "<%= (form.getCityOrTown() == null)
                                   ? "" : form.getCityOrTown() %>"/>
                </div>
                
                <!-- Province -->
                
                <div id="provinceFormGroup" class="form-group">
                    <label>Province:</label>
                    <select class="form-control" id="province"
                            name="province">
                        <%
                            for (Long provinceId : form.getProvinces().keySet()) {
                                %>
                                <option value="<%= provinceId %>">
                                    <%= form.getProvinces().get(provinceId).getDescription() %>
                                </option>
                        <%
                            }
                        %>
                    </select>
                </div>
                
                <!-- Area Code -->
                
                <div id="academicLevelFormGroup" class="form-group">
                    <label>Academic Level:</label>
                    <select class="form-control" id="academicLevel"
                            name="academicLevel">
                                <%
                                    for (Long academicLevelId : form.getAcademicLevels().keySet()) {
                                    %>
                                    <option value="<%= academicLevelId %>">
                                        <%= form.getAcademicLevels().get(academicLevelId).getDescription() %>
                                    </option>
                        <%
                                    }
                                %>
                    </select>
                </div>
                
                <p>
                    <strong>Complete this challenge - 
                        this verifies that you are really human.
                    </strong>
                </p>

                <!-- reCAPTCHA snippets -->
                <script type="text/javascript"
                    src="http://www.google.com/recaptcha/api/challenge?k=6LfJjvsSAAAAAJuEMZYXdAwjj1sx5nDAPZmmWVO7">
                </script>

                <noscript>
                   <iframe src="http://www.google.com/recaptcha/api/noscript?k=6LfJjvsSAAAAAJuEMZYXdAwjj1sx5nDAPZmmWVO7"
                       height="300" width="500" frameborder="0"></iframe><br>
                   <textarea name="recaptcha_challenge_field" rows="3" cols="40">
                   </textarea>
                   <input type="hidden" name="recaptcha_response_field"
                       value="manual_challenge">
                </noscript>

                <br/>
                
                <p>
                    <strong>Terms of
                        service agreement:
                    </strong>
                </p>
                
                <!-- Agree to terms and conditions -->
                <label style="font-weight: normal">
                    <input type="checkbox" id="agreeToTermsOfService"
                           name="agreeToTermsOfService" path="agreeToTermsOfService"
                           value="false"/>
                    &nbsp;
                    I agree to and understand the 
                    <a href="<%= request.getContextPath() %>/assets/miscellaneous/documents/terms-of-service.pdf"
                       target="_blank" title="Terms of Service Agreement">
                        Terms of Service
                    </a> agreement.
                </label>
                       
                
                <br/><br/>
                        
                <input type="submit" id="submit"
                       name="quoteRequestSubmit" class="btn btn-primary"
                       value="Request Rates Quote"/>
                
                <br/>
                <br/>
            </form>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
        
    </body>
</html>
