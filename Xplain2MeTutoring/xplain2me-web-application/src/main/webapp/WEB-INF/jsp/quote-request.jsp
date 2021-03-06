
<%@page import="za.co.xplain2me.entity.Subject"%>
<%@page import="za.co.xplain2me.webapp.component.RequestQuoteForm"%>

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
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="tutoring, private home tuition, tutors">
        <meta name="author" content="Tsepo Maleka">
    
        <title>Request Quote | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
        <script type="text/javascript" 
            src="<%= request.getContextPath() %>/assets/js/quote-request.js">
        </script>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
  
        <div class="container">

            <div class="row">
                <br>
                <h1 class="centered">REQUEST A QUOTE</h1>
                <hr>

                
                
                <div class="col-lg-6 col-lg-offset-3">
                    
                    <p class="text-muted">
                        To request a quote with our rates, please 
                        fill in this form and we will send you 
                        a quote as soon as possible.
                    </p>
                
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

                        <!-- Street Address -->

                        <div id="streetFormGroup" class="form-group">
                            <label>Physical Address:</label>
                            <textarea class="form-control" id="streetAddress"
                                   name="streetAddress" rows="3"
                                   placeholder="Enter the Physical Address"
                                   style="resize: none"><%= (form.getStreetAddress() == null) ? "" : 
                                        form.getStreetAddress()  %></textarea>
                        </div>

                        <!-- Province -->

                        <div id="provinceFormGroup" class="form-group">
                            <label>Province:</label>
                            <select class="form-control" id="province"
                                    name="province">
                                <%
                                    for (Long provinceId : form.getProvinces().keySet()) {
                                        %>
                                        <option value="<%= provinceId %>"
                                                <%= (form.getProvince() != null &&
                                                        form.getProvince().getId() == provinceId) ? "selected" : "" %>>
                                            <%= form.getProvinces().get(provinceId).getDescription() %>
                                        </option>
                                <%
                                    }
                                %>
                            </select>
                        </div>

                        <!-- Academic Level -->

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

                         <!-- Number of Lessons Required -->

                        <div id="numberOfLessonsRequiredFormGroup" class="form-group">
                            <label>Number of Lessons Required:</label>
                            <input type="text" class="form-control" id="numberOfLessonsRequired"
                                   name="numberOfLessonsRequired" maxlength="2"
                                   placeholder="Enter the Number of Lessons"
                                   value="<%= form.getNumberOfLessonsRequired() %>"/>
                        </div>

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
                               name="quoteRequestSubmit" class="btn btn-primary"
                               value="Request Rates Quote"/>

                        <br/>
                        <br/>
                    </form>
                </div>
        </div>
        <br>
        <br>
    </div>
        
        <%@include file="../jspf/template/default-footer.jspf" %> 

         
    </body>
</html>
