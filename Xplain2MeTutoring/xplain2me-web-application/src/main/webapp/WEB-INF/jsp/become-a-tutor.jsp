<%@page import="za.co.emergelets.xplain2me.entity.BecomeTutorRequest"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="za.co.emergelets.xplain2me.entity.Gender"%>
<%@page import="za.co.emergelets.xplain2me.entity.Citizenship"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.BecomeTutorForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    BecomeTutorForm form = (BecomeTutorForm)session
            .getAttribute(BecomeTutorForm.class.getName());
    
    if (form == null) 
        form = new BecomeTutorForm();
    
    if (form.getBecomeTutorRequest() == null)
        form.setBecomeTutorRequest(new BecomeTutorRequest());
    
%>

<!DOCTYPE html>
<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Become a tutor | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript" 
            src="<%= request.getContextPath() %>/assets/js/become-a-tutor.js">
        </script>
        
    </head>
    <body>
        
        <div class="container" id="content">
            
            <%@include file="../jspf/template/default-banner.jspf" %>
            
            <%@include file="../jspf/template/default-nav-bar.jspf" %>
            
            <h3>Become a tutor</h3>
            <p class="text-muted">
                Thanks for your interest in joining our wonderful, vibrant, driven
                and passionate tutors. To join, please fill in the form below
                and we will get back to you shortly. Good luck.
            </p>
            
            <hr/>
            
            <form id="becomeTutorForm" name="becomeTutorForm" role="form"
                  action="<%= request.getContextPath() %>/become-a-tutor"
                  method="post" onsubmit="return validateBecomeTutorForm(this)">
                
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
                           value="<%= (form.getBecomeTutorRequest().getLastName() == null) 
                           ? "" : form.getBecomeTutorRequest().getLastName() %>"/>
                </div>
                
                <!-- First Names -->
                
                <div id="firstNamesFormGroup" class="form-group">
                    <label>First Names:</label>
                    <input type="text" class="form-control" id="firstNames"
                           name="firstNames" maxlength="32"
                           placeholder="Enter the First Names"
                           value="<%= (form.getBecomeTutorRequest().getFirstNames() == null) 
                                   ? "" : form.getBecomeTutorRequest().getFirstNames() %>"/>
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
                
                <!-- ID / Passport -->
                
                <div id="idNumberFormGroup" class="form-group">
                    <label>Identity Number:</label>
                    <input type="text" class="form-control" id="idNumber"
                           name="idNumber" maxlength="24"
                           placeholder="Enter the ID / Passport Number"
                           autocomplete="off"/>
                </div>
                
                 <!-- DOB -->
                
                <%
                    String dateOfbirth = null;
                    if (form.getBecomeTutorRequest().getDateOfBirth() != null)
                        dateOfbirth = new SimpleDateFormat("yyyy-MM-dd")
                                .format(form.getBecomeTutorRequest().getDateOfBirth());
                %>
                 
                <div id="dateOfBirthFormGroup" class="form-group">
                    <label>Date Of Birth:</label>
                    <input type="text" class="form-control" id="dateOfBirth"
                           name="dateOfBirth" maxlength="10"
                           placeholder="Enter the Date of birth (YYYY-MM-DD)"
                           value="<%= (dateOfbirth == null) ? "" : dateOfbirth %>"/> 
                </div>
                 
                <!-- Citizenship -->
                
                <div id="citizenshipFormGroup" class="form-group">
                    <label>Citizenship</label>
                    <select class="form-control" id="citizenship"
                           name="citizenship">
                        
                        <%
                            if (form.getCitizenships() != null && 
                                    form.getCitizenships().isEmpty() == false) {
                                
                                for (Citizenship citizenship : form.getCitizenships()) {
                        %>
                        
                        <option value="<%= citizenship.getId() %>">
                            <%= citizenship.getDescription() %>
                        </option>   
                        
                        <%
                                }
                            }
                        %>
                    </select>
                </div>
                
                <!-- Gender -->
                
                <div id="genderFormGroup" class="form-group">
                    <label>Gender:</label>
                    <select class="form-control" id="gender"
                            name="gender">
                        
                        <%
                        
                            if (form.getGender() != null && 
                                    form.getGender().isEmpty() == false) {
                                
                                for (Gender gender : form.getGender()) {
                        %>
                        
                        <option value="<%= gender.getId() %>">
                            <%= gender.getDescription() %>
                        </option>   
                        
                        <%
                            }
                        }
                        %>
                        
                    </select>
                </div>    
                    
                <!-- Street Address -->
                
                <div id="streetAddressFormGroup" class="form-group">
                    <label>Street Address:</label>
                    <input type="text" class="form-control" id="streetAddress"
                           name="streetAddress" maxlength="64"
                           placeholder="Enter the Street Address"
                           value="<%= (form.getBecomeTutorRequest().getStreetAddress() == null) 
                           ? "" : form.getBecomeTutorRequest().getStreetAddress() %>"/>
                </div>
                
                <!-- Suburb / Town -->
                
                <div id="suburbFormGroup" class="form-group">
                    <label>Suburb / Town:</label>
                    <input type="text" class="form-control" id="suburb"
                           name="suburb" maxlength="64"
                           placeholder="Enter the Suburb / Town"
                           value= "<%= (form.getBecomeTutorRequest().getSuburb() == null)
                                   ? "" : form.getBecomeTutorRequest().getSuburb() %>"/>
                </div>
                
                <!-- City -->
                
                <div id="cityFormGroup" class="form-group">
                    <label>City:</label>
                    <input type="text" class="form-control" id="city"
                           name="city" maxlength="64"
                           placeholder="Enter the City"
                           value="<%= (form.getBecomeTutorRequest().getCity() == null) 
                           ? "" : form.getBecomeTutorRequest().getCity() %>"/>
                </div>
                
                <!-- Area Code -->
                
                <div id="areaCodeFormGroup" class="form-group">
                    <label>Area Code: </label>
                    <input type="text" class="form-control" id="areaCode"
                           name="areaCode" maxlength="6"
                           placeholder="Enter the Area Code"
                           value="<%= (form.getBecomeTutorRequest().getAreaCode() == null) 
                           ? "" : form.getBecomeTutorRequest().getAreaCode() %>"/>
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
                       name="becomeTutorSubmit" class="btn btn-primary"
                       value="Become a Tutor"/>
                
                <br/>
                <br/>
            </form>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
        
    </body>
</html>
