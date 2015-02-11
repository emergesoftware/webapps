
<%@page import="za.co.xplain2me.entity.Subject"%>
<%@page import="za.co.xplain2me.entity.AcademicLevel"%>
<%@page import="za.co.xplain2me.webapp.component.RequestTutorForm"%>

<%
    RequestTutorForm form = (RequestTutorForm)session
            .getAttribute(RequestTutorForm.class.getName());
    if (form == null)
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
        
        <title>Request a tutor | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript" 
            src="<%= request.getContextPath() %>/assets/js/request-a-tutor.js"></script>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
        <div class="container">
            
            <div class="row white">
                <br>
                <h1 class="centered">REQUEST A TUTOR</h1>
                <hr>
                <p class="text-muted">
                    Thanks for your interest in our tutoring services. To request a tutor,
                    please fill in the information below and we will get back to you
                    soon.
                </p>
            
                <div class="col-lg-8">
                    <form role="form" id="requestTutorForm" name="requestTutorForm" method="post"
                        action='<%= (request.getContextPath() + "/request-a-tutor") %>'
                        onsubmit="return validateRequestTutorForm(this)">

                        <div id="alertBlock" class="alert alert-danger" role="alert"
                             style="display:none">

                        </div>

                        <!-- The Navigation Tabs -->
                        <ul id="tabs" class="nav nav-tabs">
                            <li class="active">
                                <a href="#firstTab" data-toggle="tab">
                                    <label class="label label-primary">1</label>
                                    &nbsp;Provide Your Personal Details
                                </a>
                            </li>
                            <li>
                                <a href="#secondTab" data-toggle="tab">
                                    <label class="label label-primary">2</label>
                                    &nbsp;Specify Your Academic Needs
                                </a>
                            </li>
                            <li>
                                <a href="#thirdTab" data-toggle="tab">
                                    <label class="label label-primary">3</label>
                                    &nbsp;Submit Your Request
                                </a>
                            </li>
                        </ul>

                        <div id="tabContent" class="tab-content">

                            <!-- first tab content -->
                            <div class="tab-pane fade active in" id="firstTab">
                                <br/>

                                <h3 class="text-primary centered">Personal Information</h3>
                                <hr/>

                                <!-- Last Name -->
                                <div id="lastNameFormGroup" class="form-group">
                                    <label>Last Name:</label>
                                    <input type="text" class="form-control" id="lastName"
                                           name="lastName" maxlength="32"
                                           placeholder="Enter the Last Name"/>
                                </div>
                                <!-- First Names -->
                                <div id="firstNamesFormGroup" class="form-group">
                                    <label>First Names:</label>
                                    <input type="text" class="form-control" id="firstNames"
                                           name="firstNames" maxlength="32"
                                           placeholder="Enter your First Names"/>
                                </div>
                                <!-- Gender -->
                                <div class="form-group">
                                    <label>Gender:</label>
                                    <select class="form-control" id="gender"
                                           name="gender" path="gender">
                                        <option value="true">Male</option>
                                        <option value="false">Female</option>
                                    </select>
                                </div>

                                <br/>
                                <h3 class="text-primary centered">Contact Information</h3>
                                <hr/>

                                <!-- Email Address -->
                                <div id="emailAddressFormGroup" class="form-group">
                                    <label>Email Address:</label>
                                    <input type="text" class="form-control" id="emailAddress"
                                           name="emailAddress" maxlength="64"
                                           placeholder="Enter your Email Address"
                                           autocomplete="off"/>
                                </div>

                                <!-- Contact Number -->
                                <div id="contactNumberFormGroup" class="form-group">
                                    <label>Contact Number:</label>
                                    <input type="text" class="form-control" id="contactNumber"
                                           name="contactNumber" maxlength="10"
                                           placeholder="Enter your Contact Number"
                                           autocomplete="off"/>
                                </div>

                                <br>
                                <h3 class="text-primary centered">Residential Details</h3>
                                <hr/>

                                <!-- Physical Address -->
                                <div id="physicalAddressFormGroup" class="form-group">
                                    <label>Physical Address:</label>
                                    <input type="text" class="form-control" id="physicalAddress"
                                           name="physicalAddress" maxlength="64"
                                           placeholder="Enter Street Address, eg. 2nd Pebbles Avenue"/>

                                </div>
                                <!-- Suburb Name -->
                                <div id="suburbFormGroup" class="form-group">
                                    <label>Town / Suburb:</label>
                                    <input type="text" class="form-control" id="suburb"
                                           name="suburb" maxlength="32"
                                           placeholder="Enter the Suburb or Town Name"/>

                                </div>
                                 <!-- City -->
                                <div id="cityFormGroup" class="form-group">
                                    <label>City:</label>
                                    <input type="text" class="form-control" id="city"
                                           name="city" maxlength="32"
                                           placeholder="Enter City Name, eg. Johannesburg"/>

                                </div>
                                <!-- Area Code -->
                                <div id="areaCodeFormGroup" class="form-group">
                                    <label>Area Code:</label>
                                    <input type="text" class="form-control" id="areaCode"
                                           name="areaCode" maxlength="6"
                                           placeholder="Enter your Area Code"/>
                                </div>


                                <br/>
                                <br/>

                            </div> 
                            <!-- end of first tab -->

                            <!-- second tab content -->
                            <div class="tab-pane fade" id="secondTab">
                                <br/>

                                <h3 class="text-primary centered">Academic Details</h3>
                                <hr/>

                                <!-- Grade Level -->
                                <div class="form-group">
                                    <label>Grade Level:</label>

                                    <select class="form-control" id="gradeLevel"
                                            name="gradeLevel" path="gradeLevel">

                                        <%
                                            for (AcademicLevel level : form.getAcademicLevels().values()) {
                                        %>

                                            <option value="<%= level.getId() %>">
                                                <%= level.getDescription() %>
                                            </option>
                                        <% } %>
                                    </select>
                                </div>
                                <!-- Subjects -->
                                <div id="subjectsFormGroup" class="form-group">
                                    <label>Subjects:</label>
                                    <p>
                                        <span class="glyphicon glyphicon-info-sign"></span>
                                        <span class="text-muted">
                                            To select more than one, press <b>Ctrl</b> key
                                            together with your selection when you click.
                                        </span>
                                    </p>

                                    <select id="subjects" name="subjects"
                                            class="form-control" multiple="multiple"
                                            size="12">

                                        <% for (Subject subject : form.getSubjects().values()) { %>
                                            <option value="<%= subject.getId() %>">
                                                <%= subject.getName() %>
                                            </option>
                                        <% } %>
                                    </select>
                                </div>

                                <!-- Learner problem areas and necessary additional information -->
                                <div class="form-group">
                                    <label>Learner problem areas and necessary additional information:</label>
                                    <textarea class="form-control" id="additionalInformation"
                                           name="additionalInformation"
                                           rows="5"></textarea>
                                </div>

                                <br/>
                                <br/>

                            </div>
                            <!-- end of second tab -->

                            <!-- third tab content -->
                            <div class="tab-pane fade" id="thirdTab">

                                <br/>

                                <h3 class="text-primary centered">Submit Your Request</h3>
                                <hr/>

                                <h3>Prove that you're human</h3>
                                <p>Complete this challenge - this verifies that you are really human.</p>

                                <!-- reCAPTCHA snippets -->
                                <%@include file="../jspf/template/re-captcha-code-snippet.jspf" %>
                                
                                <br>
                                
                                <h3 class="text-primary centered">Terms of Service agreement</h3>
                                <hr/>
                                
                                <p>
                                    You're done. Please verify the information you entered before you submit.
                                </p>

                                <!-- Agree to terms and conditions -->
                                <label style="font-weight: normal">
                                    <input type="checkbox" id="agreeToTermsOfService"
                                           name="agreeToTermsOfService" path="agreeToTermsOfService"
                                           value="false"/>
                                    &nbsp;
                                    I agree to the 
                                    <a href="<%= request.getContextPath() %>/assets/miscellaneous/documents/terms-of-service.pdf"
                                       target="_blank" title="Terms of Service Agreement">
                                        Terms of Service
                                    </a> agreement.
                                </label>


                                <br/>
                                <br/>

                                <input type="submit" id="submit"
                                       name="requestTutor" class="btn btn-primary"
                                       value="Request Tutor"/>

                                <br/>
                                <br/>

                            </div>
                            <!-- end of third tab -->

                        </div>

                    </form>
                </div>
                <br>
            </div>  
        </div>
                                       
        <%@include file="../jspf/template/default-footer.jspf" %>              
           
    </body>
</html>
