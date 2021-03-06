
<%@page import="za.co.xplain2me.entity.ProfileType"%>
<%@page import="za.co.xplain2me.util.DateTimeUtils"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.xplain2me.entity.Profile"%>
<%@page import="za.co.xplain2me.webapp.component.UserManagementForm"%>

<%
    UserManagementForm form = (UserManagementForm)session
            .getAttribute(UserManagementForm.class.getName());
    
    if (form == null) {
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.BROWSE_EXISTING_USERS + 
                "?rand=" + System.currentTimeMillis() + 
                "&invalid-request=yes");
        return;
    }
    
    TreeMap<Long, Profile> userProfiles = form.getUserProfiles();
    if (userProfiles == null) {
        userProfiles = new TreeMap<Long, Profile>();
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Browse Existing Users</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Browse Existing Users</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <div class="col-md-12">
                        
                        <a  title="Add New User" 
                            class="btn btn-primary btn-sm"
                            href="<%= request.getContextPath() + RequestMappings.ADD_NEW_USER %>">
                            <span class="glyphicon glyphicon-plus"></span>
                            <span>&nbsp;Add New User</span>
                        </a>
                            
                        <a  title="Search Users" 
                            class="btn btn-primary btn-sm"
                            href="<%= request.getContextPath() + RequestMappings.SEARCH_USER %>">
                            <span class="glyphicon glyphicon-search"></span>
                            <span>&nbsp;Search Users</span>
                        </a>
                        
                        <hr/>
                    </div>
                    
                    <%
                        if (userProfiles.isEmpty()) {
                            %>
                            
                            <div class="col-md-6">
                                <div class="alert alert-warning">
                                    <h4>No Users Found</h4>
                                    <p>
                                        No users were found that you are authorised to manage or browse
                                        at this moment.
                                    </p>
                                </div>
                            </div>
                    <%
                        }
                        
                        else {
                            %>
                            <div class="col-md-12">
                                
                                <div class="panel panel-primary">
                                    
                                    <div class="panel-heading">
                                        User Account Profiles 
                                    </div>
                                    
                                    <div class="panel-body">
                                        
                                        <table border="0" class="table table-condensed table-hover" style="font-size: 100%">
                                            <thead>
                                                <tr>
                                                    <th></th>
                                                    <th>User ID</th>
                                                    <th>Username</th>
                                                    <th>Date Added</th>
                                                    <th>Full Names</th>
                                                    <th>Profile Type</th>
                                                    <th>Profile verified</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                            <%
                                    for (Profile profile : userProfiles.values()) {

                                        %>
                                        <tr>
                                            <td>
                                                <%
                                                    // configure the links

                                                    String editUserLink = request.getContextPath() + 
                                                            RequestMappings.EDIT_USER_PROFILE + 
                                                            "?profile-id=" + profile.getId() + 
                                                            "&rand=" + System.currentTimeMillis();

                                                    String viewFullProfile = request.getContextPath() + 
                                                            RequestMappings.VIEW_USER_PROFILE + 
                                                            "?profile-id=" + profile.getId();
                                                    
                                                    String changeUserProfileTypeLink = 
                                                            request.getContextPath() + RequestMappings.CHANGE_USER_PROFILE_TYPE + 
                                                            "?profile-id=" + profile.getId();
                                                    
                                                    String activateOrBlockUser = request.getContextPath() + 
                                                            RequestMappings.ACTIVATE_OR_BLOCK_USER + 
                                                            "?profile-id=" + profile.getId();
                                                    
                                                    String removeUserProfile = request.getContextPath() + 
                                                            RequestMappings.DELETE_USER_PROFILE + 
                                                            "?profile-id=" + profile.getId();
                                                    
                                                    String assignUserAsTutor = request.getContextPath() + 
                                                            RequestMappings.CREATE_TUTOR + "?profile_id=" + 
                                                            profile.getId(); 
                                                    
                                                %>

                                                <div class="btn-group">

                                                    <button type="button" data-toggle="dropdown" 
                                                            class="btn btn-primary btn-sm dropdown-toggle">
                                                        <span class="glyphicon glyphicon-list"></span>
                                                        <span class="caret"></span>
                                                    </button>

                                                    <ul class="dropdown-menu" role="menu" aria-labelledby="dropdownMenu" >
                                                        <li>
                                                            <a  title="View Full Profile"
                                                                href="<%= viewFullProfile %>">
                                                                <span class="glyphicon glyphicon-th-list"></span>
                                                                &nbsp;View Profile
                                                            </a>
                                                            <a  title="Edit user"
                                                                href="<%= editUserLink %>">
                                                                <span class="glyphicon glyphicon-pencil"></span>
                                                                &nbsp;Edit
                                                            </a>
                                                        </li>
                                                         <li>
                                                            <a  title="Change profile type"
                                                                href="<%= changeUserProfileTypeLink %>">
                                                                <span class="glyphicon glyphicon-user"></span>
                                                                &nbsp;Change Profile
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a title="Activate or block user" 
                                                               href="<%= activateOrBlockUser %>">
                                                                <span class="glyphicon glyphicon-lock"></span>
                                                                &nbsp;Activate / Block
                                                            </a>
                                                        </li>
                                                        <li>
                                                            <a title="Remove User" 
                                                               href="<%= removeUserProfile %>">
                                                                <span class="glyphicon glyphicon-remove"></span>
                                                                &nbsp;Remove
                                                            </a>
                                                        </li>
                                                        
                                                        <%
                                                        
                                                        long profileTypeId = profile.getProfileType().getId();
                                                        
                                                            if (profileTypeId == ProfileType.TUTOR_PROFILE || 
                                                                profileTypeId == ProfileType.APP_MANAGER_PROFILE) {
                                                                
                                                                %>
                                                        
                                                        <li>
                                                            <a title="Assign user as tutor" 
                                                               href="<%= assignUserAsTutor %>">
                                                                <span class="glyphicon glyphicon-education"></span>
                                                                &nbsp;Assign user as tutor
                                                            </a>
                                                        </li>
                                                                
                                                        <%
                                                                
                                                            }
                                                        %>
                                                        
                                                    </ul>

                                                </div>
                                                        
                                            </td>
                                            <td>
                                                <%= profile.getPerson().getUser().getId() %>
                                            </td>
                                            <td>
                                                <%= profile.getPerson().getUser().getUsername() %>
                                            </td>
                                            <td>
                                                <%= DateTimeUtils.formatDateTime(profile.getDateCreated()) %>
                                            </td>
                                            <td>
                                                <%= profile.getPerson().getLastName() + 
                                                    ", " + profile.getPerson().getFirstNames()
                                                %>
                                            </td>
                                            <td>
                                                <%= profile.getProfileType().getDescription() %>
                                            </td>
                                            <td>
                                                <%= (profile.isVerified()) ? "Yes" : "No" %>
                                            </td>
                                        </tr>
                                                <%
                                    }
                                    %>
                                            </tbody>
                                        </table>
                                    
                                    </div>
                                </div>
                            </div>
                            <%
                        }
                    %>
                    
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-manager-footer.jspf" %>
            
        </div>
    </body>
</html>
