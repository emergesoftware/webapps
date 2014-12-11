<%-- 
    Document   : edit-own-profile
    Created on : 09 Dec 2014, 9:48:34 PM
    Author     : user
--%>

<%@page import="za.co.emergelets.xplain2me.entity.Citizenship"%>
<%@page import="za.co.emergelets.xplain2me.entity.Gender"%>
<%@page import="za.co.emergelets.util.DateTimeUtils"%>
<%@page import="za.co.emergelets.xplain2me.entity.ProfileType"%>
<%@page import="za.co.emergelets.xplain2me.entity.Profile"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.ProfileManagementForm"%>
<%
    ProfileManagementForm form = (ProfileManagementForm)
            session.getAttribute(ProfileManagementForm.class.getName());
    
    if (form == null || form.getProfile() == null) {
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.DASHBOARD_OVERVIEW);
        return;
    }
    
    Profile profile = form.getProfile();
    ProfileType profileType = profile.getProfileType();
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Edit Own Profile</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/bootstrap-datepicker.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Edit My Profile</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    <div class="col-md-6">
                        
                        <!-- start: Profile Type -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    PROFILE TYPE
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <div class="alert alert-warning">
                                    <h4>PLEASE NOTE:</h4>
                                    <p>You cannot edit this section - please contact your
                                        system administrator to make changes to your
                                        profile type.</p>
                                </div>
                                
                                <table border="0" class="table table-stripped" style="font-size: 95%">
                                    <tbody>
                                        <tr>
                                            <td><strong>Profile Type:</strong></td>
                                            <td>
                                                <%= profile.getProfileType().getDescription() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Date Created:</strong></td>
                                            <td><%= DateTimeUtils.formatDateTime(profile.getDateCreated()) %></td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>

                        </div>
                        <!-- end: Profile Type -->
                        
                        <!-- start: User Account -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    EDIT USER LOGIN INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <div class="alert alert-warning">
                                    <h4>KEEP PASSWORDS SAFE:</h4>
                                    <p>It is your responsibility to keep your
                                        login information both username and passwords
                                        secure and not to share these with anyone.
                                    </p>
                                </div>
                                
                                <form id="userLoginInfoForm" name="userLoginInfoForm"
                                      method="post" 
                                      action="<%= request.getContextPath() + RequestMappings.EDIT_MY_CREDENTIALS %>">
                                    
                                    <div class="form-group">
                                        <label>Change Username:</label>
                                        <input type="text" id="username" name="username" 
                                               value="<%= profile.getPerson().getUser().getUsername() %>"
                                               class="form-control" data-validation="length"
                                               data-validation="alphanumeric" data-validation-length="8-24"
                                               />
                                    </div>
                                    <hr/>
                                    <h4>Change Password:</h4>
                                    
                                    <div class="form-group">
                                        <label>Enter current password:</label>
                                        <input type="password" id="currentPassword" name="currentPassword"
                                               placeholder="Enter the CURRENT password"
                                               class="form-control" data-validation="length"
                                               data-validation="alphanumeric" data-validation-length="8-24"
                                               data-validation-optional="true"/>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Enter new password:</label>
                                        <input type="password" id="newPassword" name="newPassword"
                                               placeholder="Enter the NEW password"
                                               class="form-control" data-validation="length"
                                               data-validation="alphanumeric" data-validation-length="8-24"
                                                data-validation-optional="true"/>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Re-enter new password:</label>
                                        <input type="password" id="reEnterNewPassword" name="reEnterNewPassword"
                                               placeholder="Re-enter the NEW password"
                                               class="form-control" data-validation="length"
                                               data-validation="alphanumeric" data-validation-length="8-24"
                                                data-validation-optional="true"/>
                                    </div>
                                    
                                    <div class="form-group">
                                        <input type="submit" name="changeUserCredentialsRequest"
                                               value="Update Credentials" ondblclick="return false" 
                                               class="btn btn-primary"/>
                                    </div>
                                    
                                </form>

                            </div>

                        </div>
                        <!-- end: User Account -->
                        
                         <!-- start: Personal Information -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    UPDATE PERSONAL INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">
                                
                                <form id="personForm" name="personForm" method="post"
                                      action="<%= request.getContextPath() + RequestMappings.MY_PROFILE_EDIT %>">
                                    
                                    <div class="form-group">
                                        <label>Last Name:</label>
                                        <input type="text" name="lastName" id="lastName"
                                               value="<%= profile.getPerson().getLastName() %>"
                                               class="form-control"
                                               data-validation="length" data-validation-length="3-32" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>First Names:</label>
                                        <input type="text" name="firstNames" id="firstNames"
                                               value="<%= profile.getPerson().getFirstNames() %>"
                                               class="form-control"
                                               data-validation="length" data-validation-length="3-64" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>Date of Birth:</label>
                                        <input type="text" name="dateOfBirth" id="dateOfBirth"
                                               value="<%= DateTimeUtils.formatDate(profile.getPerson().getDateOfBirth()) %>"
                                               class="form-control datepicker"
                                               data-validation="birthdate" data-validation-format="yyyy-mm-dd" />
                                    </div>
                                               
                                    <div class="form-group">
                                        <label>Gender:</label>
                                        <select name="gender" id="gender"
                                               class="form-control">
                                            
                                            <%
                                                String selected = profile.getPerson().getGender().getDescription();
                                                for (Gender gender : form.getGender().values()) {
                                                    %>
                                                    <option value="<%= gender.getId() %>" <%= (selected.equalsIgnoreCase(gender.getDescription())) ? "selected" : "" %>>
                                                        <%= gender.getDescription() %>
                                                    </option>
                                            <%
                                                }
                                            %>
                                            
                                        </select>
                                    </div>
                                    
                                    <div class="form-group">
                                        <label>Citizenship:</label>
                                        <select name="citizenship" id="citizenship"
                                               class="form-control">
                                            
                                            <%
                                                selected = profile.getPerson().getCitizenship().getDescription();
                                                for (Citizenship c : form.getCitizenship().values()) {
                                                    
                                                    %>
                                                    <option value="<%= c.getId() %>" <%= (selected.equalsIgnoreCase(c.getDescription())) ? "selected" : "" %>>
                                                        <%= c.getDescription() %>
                                                    </option>
                                            <%
                                                }
                                            %>
                                            
                                        </select>
                                    </div>
                                            
                                    <div class="form-group">
                                        <input type="submit" name="updatePersonalInfoRequest"
                                               value="Update Personal Details" ondblclick="return false"
                                               class="btn btn-primary" />
                                    </div>
                                    
                                </form>
                                
                               
                            </div>

                        </div>
                        <!-- end: Personal Information -->
                        
                    </div>
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
