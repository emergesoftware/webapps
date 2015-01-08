<%@page import="za.co.emergelets.xplain2me.entity.Gender"%>
<%@page import="za.co.emergelets.xplain2me.entity.Citizenship"%>
<%@page import="za.co.emergelets.xplain2me.entity.ProfileType"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.emergelets.xplain2me.entity.Profile"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.UserManagementForm"%>

<%
    UserManagementForm form = (UserManagementForm)session
            .getAttribute(UserManagementForm.class.getName());
    
    if (form == null || (form.getProfileTypes() == null || 
            form.getProfileTypes().isEmpty())) {
        
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.ADD_NEW_USER + 
                "?rand=" + System.currentTimeMillis() + 
                "&invalid-request=yes");
        return;
    }

%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Add New User</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Create New User</h2>
                <p class="text-muted">
                    To create or add a new user, complete all the information required
                    under all the tabs provided below in their respective order. Users need
                    to verify themselves first to be able to log onto the system.
                </p>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <form role="form" name="userProfileForm" id="userProfileForm"
                          method="post" 
                          action="<%= request.getContextPath() + RequestMappings.ADD_NEW_USER %>">
                    
                        <div class="col-md-12">

                            <!-- The Navigation Tabs -->
                            <ul id="tabs" class="nav nav-tabs">
                                <li class="active">
                                    <a href="#firstTab" data-toggle="tab">
                                        <label class="label label-primary">1</label>
                                        &nbsp;Profile Type
                                    </a>
                                </li>
                                <li>
                                    <a href="#secondTab" data-toggle="tab">
                                        <label class="label label-primary">2</label>
                                        &nbsp;Personal Information
                                    </a>
                                </li>
                                <li>
                                    <a href="#thirdTab" data-toggle="tab">
                                        <label class="label label-primary">3</label>
                                        &nbsp;Contact Details
                                    </a>
                                </li>
                                <li>
                                    <a href="#fourthTab" data-toggle="tab">
                                        <label class="label label-primary">4</label>
                                        &nbsp;Residential Address
                                    </a>
                                </li>
                                <li>
                                    <a href="#fifthTab" data-toggle="tab">
                                        <label class="label label-primary">5</label>
                                        &nbsp;User Login + Submission
                                    </a>
                                </li>
                            </ul>

                            <div id="tabContent" class="tab-content">

                                <!-- START: Profile type selection -->
                                <div class="tab-pane fade active in" id="firstTab">
                                    <div class="row">
                                        <div class="col-md-6">

                                            <br/>

                                            <h3 class="text-primary">Profile Type Selection</h3>
                                            <hr/>
                                            
                                            <div class="form-group">
                                                <label>Profile Type</label>
                                                <p class="text-muted">
                                                    Select the profile type for this new
                                                    user you are creating.
                                                </p>
                                                
                                                <select id="profileType" name="profileType"
                                                    class="form-control">
                                                        <%
                                                            for (ProfileType type : form.getProfileTypes().values()) {
                                                                %>
                                                                <option value="<%= type.getId() %>">
                                                                    <%= type.getDescription() %>
                                                                </option>
                                                        <%
                                                            }
                                                        %>    
                                                </select>
                                            </div>
                                            
                                            <br/>
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <p>
                                                        <strong>
                                                            Please move onto the second tab to add 
                                                            personal details of the user.
                                                        </strong>
                                                    </p>
                                                </div>
                                            </div>
                                            <br/>

                                        </div>
                                    </div>
                                </div>
                                <!-- END: Profile type selection -->
                                
                                <!-- START: Personal Details -->
                                <div class="tab-pane fade" id="secondTab">
                                    <div class="row">
                                        <div class="col-md-6">

                                            <br/>

                                            <h3 class="text-primary">Personal Information</h3>
                                            <hr/>
                                            
                                            <div class="form-group">
                                                <label>Last Name:</label>
                                                <input type="text" id="lastName" name="lastName"
                                                       class="form-control" data-validation="length" data-validation-length="min2"
                                                       maxlength="32" 
                                                       placeholder="Enter the Last Name (Required)"
                                                       data-validation-error-msg="Last Name is required."/>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>First Names:</label>
                                                <input type="text" id="firstNames" name="firstNames"
                                                       class="form-control" data-validation="length" data-validation-length="min2"
                                                       maxlength="64" 
                                                       placeholder="Enter the First Names (Required)"
                                                       data-validation-error-msg="First Names is required."/>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>Date Of Birth:</label>
                                                <input type="text" id="dateOfBirth" name="dateOfBirth"
                                                       class="form-control datepicker" 
                                                       placeholder="Enter the Date of Birth [Use format: YYYY-MM-DD] (Required)"
                                                       maxlength="10"
                                                       data-validation="date" data-validation-format="yyyy-mm-dd"
                                                       data-validation-error-msg="Date of Birth is required."/>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>Identity / Passport Number:</label>
                                                <input type="text" id="idNumber" name="idNumber"
                                                       class="form-control" maxlength="24"
                                                       placeholder="Enter ID or Passport Number (Required)"
                                                       data-validation="length" data-validation-length="min6" 
                                                       data-validation-error-msg="Identity or Passport Number is required."/>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>Gender:</label>
                                                <select id="gender" name="gender"
                                                    class="form-control">
                                                   
                                                    <%
                                                            for (Gender gender : form.getGender().values()) {
                                                                %>
                                                                <option value="<%= gender.getId() %>">
                                                                    <%= gender.getDescription() %>
                                                                </option>
                                                    <%
                                                            }
                                                    %>
                                                    
                                                </select>
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>Citizenship</label>
                                                <select id="citizenship" name="citizenship"
                                                    class="form-control">
                                                    
                                                    <%
                                                            for (Citizenship citizenship : form.getCitizenships().values()) {
                                                                %>
                                                                <option value="<%= citizenship.getId() %>">
                                                                    <%= citizenship.getDescription() %>
                                                                </option>
                                                    <%
                                                            }
                                                    %>
                                                    
                                                </select>
                                            </div>
                                                    
                                            <br/>
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <p>
                                                        <strong>
                                                            Please move onto the third tab to add 
                                                            contact details of the user.
                                                        </strong>
                                                    </p>
                                                </div>
                                            </div>
                                            <br/>
                                            
                                        </div>
                                    </div>
                                </div>
                                <!-- END: Personal Details -->
                                
                                <!-- START: Contact Details -->
                                <div class="tab-pane fade" id="thirdTab">
                                    <div class="row">
                                        <div class="col-md-6">

                                            <br/>

                                            <h3 class="text-primary">Contact Details</h3>
                                            <hr/>
                                            
                                            <div class="form-group">
                                                <label>Contact Number:</label>
                                                <div class="input-group">
                                                    <span class="input-group-addon">+27</span>
                                                    <input type="text" id="contactNumber" name="contactNumber" 
                                                           class="form-control" maxlength="13"
                                                           placeholder="Enter the Contact Number (Required)"
                                                           data-validation="length" data-validation-length="min9" 
                                                           data-validation-error-msg="Contact Number is required."/>
                                                </div>
                                                 
                                            </div>
                                            
                                            <div class="form-group">
                                                <label>Email Address:</label>
                                                <input type="text" id="emailAddress" name="emailAddress" 
                                                       class="form-control" maxlength="64"
                                                       placeholder="Enter the Email Address (Required)"
                                                       data-validation="length" data-validation-length="min8" 
                                                       data-validation-error-msg="Email Address is required."/>
                                                
                                            </div>
                                            
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <p>
                                                        <strong>
                                                            Please move onto the fourth tab to add 
                                                            residential address of the user.
                                                        </strong>
                                                    </p>
                                                </div>
                                            </div>
                                            <br/>
                                            
                                        </div>
                                    </div>
                                </div>
                                <!-- END: Contact Details -->
                                
                                <!-- START: Residential Address -->
                                <div class="tab-pane fade" id="fourthTab">
                                    <div class="row">
                                        <div class="col-md-6">
                                            
                                            <br/>
                                            
                                            <h3 class="text-primary">Residential Address</h3>
                                            <hr/>
                                            
                                            <!-- Physical Address Line 1 to 4 -->
                                            <div class="form-group">
                                                <label>Physical Address:</label>

                                                <input type="text" id="physicalAddressLine1" name="physicalAddressLine1"
                                                       class="form-control" placeholder="Enter street number / street name / unit number etc. (Required)"
                                                       data-validation="length" data-validation-length="min8"
                                                       data-validation-error-msg="Street Number / Street Name / Unit Number / Address Line 1 is required."
                                                       max-length="36" />

                                                <input type="text" id="physicalAddressLine2" name="physicalAddressLine2"
                                                       class="form-control" placeholder="Enter complex name / section / extension etc. (Optional)"
                                                       max-length="36" />

                                                <input type="text" id="suburb" name="suburb"
                                                       class="form-control" placeholder="Enter suburb / town (Required)"
                                                       data-validation="length" data-validation-length="min3"
                                                       data-validation-error-msg="Suburb / Town is required."
                                                       max-length="36" />

                                                <input type="text" id="city" name="city"
                                                       class="form-control" placeholder="Enter city / municipality (Required)"
                                                       data-validation-error-msg="City / Municipality is required."
                                                       data-validation="length" data-validation-length="min3"
                                                       max-length="36" />
                                            </div>

                                            <!-- Area Code -->
                                            <div class="form-group">
                                                <label>Area Code:</label>
                                                <input type="text" id="areaCode" name="areaCode"
                                                       class="form-control" placeholder="Enter the area code (Required)"
                                                       data-validation="length" data-validation-length="min4"
                                                       data-validation-error-msg="Area Code is required."
                                                       max-length="6" />
                                            </div>
                                            
                                            <br/>
                                            
                                            <div class="panel panel-default">
                                                <div class="panel-body">
                                                    <p>
                                                        <strong>
                                                            Please move onto the fifth tab to add 
                                                            user login credentials of the user and then
                                                            submit.
                                                        </strong>
                                                    </p>
                                                </div>
                                            </div>
                                            
                                            <br/>
                                            
                                        </div>
                                    </div>
                                </div>
                                <!-- END: Residential Address-->
                                
                                <!-- START: User Login + Submission -->
                                <div class="tab-pane fade" id="fifthTab">
                                    <div class="row">
                                        <div class="col-md-6">
                                            
                                            <br/>
                                            
                                            <h3 class="text-primary">User Login + Submission</h3>
                                            <hr/>
                                            
                                            <div class="form-group">
                                                <label>Username</label>
                                                <p class="text-muted">
                                                    Provide the username for this user - they will use this
                                                    username to login. This user will have the choice to change
                                                    this username later.
                                                </p>
                                                <input type="text" id="username" name="username"
                                                       class="form-control"
                                                       placeholder="Enter the Username (Required)"
                                                       data-validation="length" data-validation-length="min8" 
                                                       data-validation-error-msg="Username is required."/>
                                            </div>
                                            
                                            <div class="alert alert-info" role="alert">
                                                <p>
                                                    <span><strong>Note:</strong></span><br/>
                                                    <span>
                                                        The user will be added as not active and their
                                                        profile account will be created as not verified.
                                                        The user will also be allocated a default password.
                                                        An email will be sent to the user to verify themselves and
                                                        only then will they be able to log in.
                                                    </span>
                                                </p>
                                            </div>
                                            
                                            <div class="form-group">
                                                <input type="submit" name="createNewUserRequest"
                                                       value="Create new user" class="btn btn-primary"
                                                       ondblclick="return false" />
                                            </div>
                                            
                                            <br/>
                                            
                                        </div>
                                    </div>
                                </div>
                                <!-- END: User Login + Submission -->
                            </div>

                        </div>
                    
                    </form>
                          
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
