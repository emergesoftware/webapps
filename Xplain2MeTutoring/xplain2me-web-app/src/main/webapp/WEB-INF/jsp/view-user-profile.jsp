<%@page import="za.co.emergelets.util.BooleanToText"%>
<%@page import="za.co.emergelets.util.StringUtils"%>
<%@page import="za.co.emergelets.xplain2me.entity.ProfileType"%>
<%@page import="za.co.emergelets.xplain2me.entity.Profile"%>
<%@page import="za.co.emergelets.util.DateTimeUtils"%>

<%
    Profile profile = (Profile)request.getAttribute(Profile.class.getName());
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>View User Profile</title>
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>User Profile Account</h2>
                <p class="text-primary">
                    View the details of the user profile account.
                </p>
                <hr/>
                
                <%
                    if (profile != null) {
                        
                        String editProfileLink = request.getContextPath() + 
                                RequestMappings.EDIT_USER_PROFILE + 
                                "?profile-id=" + profile.getId();
                %>
                <!-- start: Actions -->
                <div class="row">
                    <div class="col-md-6">
                        
                        <a  title="Edit user profile details" 
                            class="btn btn-primary btn-sm"
                            href="<%= editProfileLink %>">
                            <span class="glyphicon glyphicon-pencil"></span>
                            <span>&nbsp;Edit Profile</span>
                        </a>
                            
                        <br/>
                        <hr/>
                    </div>
                </div>
                <!-- end: Actions --> 
                
                <% } %>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <% if (profile != null) { %>
                
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

                                <table border="0" class="table table-stripped" style="font-size: 100%">
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
                                        <tr>
                                            <td><strong>Active</strong></td>
                                            <td><%= BooleanToText.format(profile.getPerson().getUser()
                                                    .isActive(), BooleanToText.YES_NO_FORMAT) %></td>
                                        </tr>
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
                                        <tr>
                                            <td><strong>Profile Verified</strong></td>
                                            <td><%= BooleanToText.format(profile.isVerified(), BooleanToText.YES_NO_FORMAT) %></td>
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
                                    USER ACCOUNT
                                </h3>
                            </div>

                            <div class="panel-body">

                                <table border="0" class="table table-stripped" style="font-size: 100%">
                                    <tbody>
                                        <tr>
                                            <td><strong>Username:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getUser().getUsername() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Date Created:</strong></td>
                                            <td><%= DateTimeUtils.formatDateTime(profile.getPerson().getUser().getAdded()) %></td>
                                        </tr>
                                        <tr>
                                            <td><strong>Status:</strong></td>
                                            <td>
                                                <%
                                                    boolean userActive = profile.getPerson().getUser().isActive();
                                                    if (userActive) {
                                                        %>
                                                        <span>
                                                            <label class="label label-info">ACTIVE</label>
                                                        </span>
                                                        <span>since</span>
                                                        <span>
                                                            <%= DateTimeUtils.formatDateTime(profile.getPerson().getUser().getAdded()) %>
                                                        </span>
                                                        <%
                                                    }
                                                    else {
                                                        %>
                                                        <span>
                                                            <label class="label label-warning">DEACTIVATED</label>
                                                        </span>
                                                        <span>on</span>
                                                        <span>
                                                            <%= DateTimeUtils.formatDateTime(profile.getPerson().getUser().getDeactivated()) %>
                                                        </span>
                                                        <%
                                                    }
                                                %>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>

                        </div>
                        <!-- end: User Account -->
                        
                        <!-- start: Personal Information -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    PERSONAL INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">

                                <table border="0" class="table table-stripped" style="font-size: 100%">
                                    <tbody>
                                        <tr>
                                            <td><strong>Last Name:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getLastName() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>First Names:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getFirstNames() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Date of Birth:</strong></td>
                                            <td>
                                                <%= DateTimeUtils.formatDate(profile.getPerson().getDateOfBirth()) %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Gender:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getGender().getDescription() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Citizenship:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getCitizenship().getDescription() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>ID / Passport Number:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getIdentityNumber() %>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>

                        </div>
                        <!-- end: Personal Information -->
                        
                        <!-- start: Contact Details -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    CONTACT INFORMATION
                                </h3>
                            </div>

                            <div class="panel-body">

                                <table border="0" class="table table-stripped" style="font-size: 100%">
                                    <tbody>
                                        <tr>
                                            <td><strong>Contact Number:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getContactDetail().getCellphoneNumber() %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td><strong>Email Address:</strong></td>
                                            <td>
                                                <%= profile.getPerson().getContactDetail().getEmailAddress() %>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>

                            </div>

                        </div>
                        <!-- end: Contact Details -->
                        
                        <!-- start: Physical Address -->
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    RESIDENTIAL ADDRESS
                                </h3>
                            </div>

                            <div class="panel-body">

                                <table border="0" class="table table-stripped" style="font-size: 100%">
                                    <tbody>
                                        <tr>
                                            <td>
                                                <p>
                                                    <%= profile.getPerson().getPhysicalAddress().toHtmlString() %>
                                                </p>
                                            </td>
                                        </tr>
                                        
                                    </tbody>
                                </table>

                            </div>

                        </div>
                        <!-- end: Physical Address -->
                        <br/>
                    </div>
                    
                </div>
                
                <% } %>
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
