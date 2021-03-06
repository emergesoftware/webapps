
<%@page import="za.co.xplain2me.entity.Subject"%>
<%@page import="za.co.xplain2me.entity.AcademicLevel"%>
<%@page import="za.co.xplain2me.entity.BecomeTutorRequest"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="za.co.xplain2me.entity.Gender"%>
<%@page import="za.co.xplain2me.entity.Citizenship"%>
<%@page import="za.co.xplain2me.webapp.component.BecomeTutorForm"%>
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
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="tutoring, private home tuition, tutors">
        <meta name="author" content="Tsepo Maleka">
        
        <title>Become a tutor | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        <%@include file="../jspf/template/bootstrap-datepicker.jspf" %>
        
        <script type="text/javascript" 
            src="<%= request.getContextPath() %>/assets/js/become-a-tutor.js">
        </script>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
        <div class="container">
                   
            <h1 class="centered">BECOME A TUTOR</h1>
            <hr/>
            

            <div class="row">
                <div class="col-lg-8 col-lg-offset-2">
                    
                    <p class="text-muted centered">
                        Thanks for your interest in joining our wonderful, vibrant, driven
                        and passionate tutors. To join, please fill in the form below
                        and we will get back to you shortly. Good luck.
                    </p>

                    <form id="becomeTutorForm" name="becomeTutorForm" role="form"
                          action="<%= request.getContextPath() %>/become-a-tutor"
                          method="post" onsubmit="return validateBecomeTutorForm(this)"
                          enctype="multipart/form-data">

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
                            
                        <p class="text-primary">
                            <strong>Note:&nbsp;</strong>
                            All fields are mandatory.
                        </p>

                        <!-- The Navigation Tabs -->
                        <ul id="tabs" class="nav nav-tabs">
                            <li class="active">
                                <a href="#firstTab" data-toggle="tab">
                                    <label class="label label-primary">1</label>
                                    &nbsp;Personal Details
                                </a>
                            </li>
                            <li>
                                <a href="#secondTab" data-toggle="tab">
                                    <label class="label label-primary">2</label>
                                    &nbsp;Contact Information
                                </a>
                            </li>
                            <li>
                                <a href="#thirdTab" data-toggle="tab">
                                    <label class="label label-primary">3</label>
                                    &nbsp;Tell Us About Your Experience
                                </a>
                            </li>
                        </ul>

                        <!-- All Tabs -->
                        <div id="tabContent" class="tab-content">

                            <!-- first tab content -->
                            <div class="tab-pane fade active in" id="firstTab">
                                <br/>

                                <h3 class="text-primary centered">Personal Information</h3>
                                <p class="text-muted centered">
                                    Fill in your personal information.
                                    All fields are mandatory.
                                </p>
                                <hr/>

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
                                    <input type="text" class="form-control datepicker" id="dateOfBirth"
                                           name="dateOfBirth" placeholder="Use Format: YYYY-MM-DD"
                                           value="<%= (dateOfbirth == null) ? "" : dateOfbirth %>"/> 
                                </div>

                                <script type="text/javascript">

                                    $(document).ready(function(){
                                        $(".datepicker").datepicker({
                                            format: "yyyy-mm-dd",
                                            endDate: "01-01-2000",
                                            autoclose : true
                                        });
                                    });

                                </script>

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

                                        <option value="<%= gender.getId() %>"
                                                <%= (form.getBecomeTutorRequest().getGender() != null
                                                  && form.getBecomeTutorRequest().getGender().getId() == gender.getId()) 
                                                ? "" : "selected"%>>
                                            <%= gender.getDescription() %>
                                        </option>   

                                        <%
                                            }
                                        }
                                        %>

                                    </select>
                                </div>    

                                <br/>
                                <p class="text-primary">
                                    <span class="glyphicon glyphicon-arrow-right"></span>
                                    <span>&nbsp;</span>
                                    <span>Now, move onto the 2nd tab to fill in your 
                                        contact and address information.</span>
                                </p>

                                <br/>
                                <br/>
                            </div>
                            <!-- first tab content ends -->

                            <!-- second tab content -->
                            <div class="tab-pane fade" id="secondTab">
                                <br/>

                                <h3 class="text-primary centered">Contact Information</h3>
                                <p class="text-muted centered">
                                    Fill in your contact and address information.
                                    All fields are mandatory.
                                </p>
                                <hr/>

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

                                <br/>
                                <p class="text-primary">
                                    <span class="glyphicon glyphicon-arrow-right"></span>
                                    <span>&nbsp;</span>
                                    <span>Now, move onto the 3rd tab to fill in your 
                                        experience in tutoring and submit 
                                        supporting documents.</span>
                                </p>

                                <br/>
                                <br/>

                            </div>
                            <!-- second tab content ends -->

                            <!-- third tab content -->
                            <div class="tab-pane fade" id="thirdTab">
                                <br/>

                                <h3 class="text-primary centered">Prior Tutoring Experience</h3>
                                <p class="text-muted centered">
                                    Tell us about your previous tutoring experience and
                                    motivate why we should consider you.
                                    All fields are mandatory.
                                </p>
                                <hr/>

                                <!-- Have You Tutored Before -->

                                <div id="tutoredBeforeFormGroup" class="form-group">
                                    <label>Have You Tutored Before? </label><br/>
                                    &nbsp;&nbsp;
                                    <div class="radio-inline" >
                                        <label style="font-weight: normal">
                                            <input type="radio" name="tutoredBefore"
                                                      value="Yes"
                                            <%= (form.getBecomeTutorRequest().isTutoredBefore()) ? "checked" : "" %>>
                                            Yes
                                        </label>
                                    </div>
                                    <div class="radio-inline" >
                                        <label style="font-weight: normal">
                                            <input type="radio" name="tutoredBefore"
                                                      value="No"
                                            <%= (!form.getBecomeTutorRequest().isTutoredBefore()) ? "checked" : "" %>>
                                            No
                                        </label>
                                    </div>

                                </div>

                                <!-- If yes, select the levels you tutored -->
                                <div id="academicLevelFormGroup" class="form-group">
                                    <label>If Yes, select the subjects you have tutored before:</label>
                                    <br>
                                    <%
                                        for (Subject subject : form.getSubjects().values()) {
                                        %>
                                        <label class="checkbox-inline">
                                            <input type="checkbox" name="subject_<%= subject.getId() %>"
                                                   value="<%= subject.getId() %>" />
                                            <%= subject.getName() %>
                                        </label><br/>
                                    <% } %>
                                    
                                </div>
                                

                                <!-- Upload documents -->

                                <div id="documentsFormGroup" class="form-group">
                                    <label>Upload your supporting documents: </label>

                                    <table id="supportingDocumentsTable" class="table table-bordered" 
                                           border="0" width="100%">
                                        <tr>
                                            <td colspan="2">
                                                <p class="text-primary">
                                                    <span>The maximum upload size is 5MB for 
                                                        all your documents in total size. Only PDF format
                                                        is an acceptable file format.</span>
                                                </p>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    <span><strong>Curriculum Vitae:</strong></span><br/>
                                                    <span class="text-primary">This document is required.</span>
                                                </p>
                                            </td>
                                            <td>
                                                <input type="file" id="curriculumVitaeFile" name="curriculumVitaeFile"
                                                       value=""/>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    <span><strong>Matric Certificate:</strong></span><br/>
                                                    <span class="text-primary">This document is required.</span>
                                                </p>
                                            </td>
                                            <td>
                                                <input type="file" id="matricCertificateFile" name="matricCertificateFile"
                                                       value=""/>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    <span><strong>Copy of ID or Passport:</strong></span><br/>
                                                    <span class="text-primary">This document is required.</span>
                                                </p>
                                            </td>
                                            <td>
                                                <input type="file" id="copyOfIDorPassportFile" name="copyOfIDorPassportFile"
                                                       value=""/>

                                            </td>
                                        </tr>
                                        <tr>
                                            <td>
                                                <p>
                                                    <span><strong>Copy of highest obtained qualification:</strong></span><br/>
                                                    <span class="text-primary">Upload only if you have obtained any 
                                                        other qualification after high school.</span>
                                                </p>
                                            </td>
                                            <td>
                                                <input type="file" name="highestObtainedQualificationFile"
                                                       value=""/>

                                            </td>
                                        </tr>
                                         <tr>
                                            <td>
                                                <p>
                                                    <span><strong>Copy of latest academic transcripts:</strong></span><br/>
                                                    <span class="text-primary">Upload only if you have studied at a
                                                        tertiary institution for more than 6 months.</span>
                                                </p>
                                            </td>
                                            <td>
                                                <input type="file" name="academicTranscriptsFile"
                                                       value=""/>

                                            </td>
                                        </tr>
                                    </table>


                                </div>

                                <br/>

                                <!-- Why should Xplain2me hire you -->
                                <div class="form-group" id="motivationFormGroup">

                                    <label>In not more than 5 lines, MOTIVATE why
                                    Xplain2Me should hire you?</label>

                                    <textarea class="form-control" id="motivation"
                                              name="motivation" rows="5"
                                              placeholder="Type your motivational text here"
                                              style="resize: none"><%= (form.getBecomeTutorRequest().getMotivationalText() == null) ? "" 
                                                : form.getBecomeTutorRequest().getMotivationalText() %></textarea>

                                </div>

                                <br>
                                <h3 class="text-primary centered">Almost Finished</h3>
                                <p class="text-muted centered">
                                    You're almost done. Please complete the human-test
                                    challenge and accept our Terms of Service.
                                </p>
                                <hr/>

                                <p>
                                    <strong>Complete this challenge - 
                                        this verifies that you are really human.
                                    </strong>
                                </p>

                                <!-- reCAPTCHA snippets -->
                                <%@include file="../jspf/template/re-captcha-code-snippet.jspf" %>

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

                            </div>
                            <!-- third tab content ends -->

                        </div>

                        <br/>
                        <br/>
                    </form>
            
                </div>
            </div>
            
        </div>
                               
        <%@include file="../jspf/template/default-footer.jspf" %> 
    </body>
</html>
