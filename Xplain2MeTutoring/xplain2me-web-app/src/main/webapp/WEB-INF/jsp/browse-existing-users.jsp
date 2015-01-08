<%@page import="za.co.emergelets.util.DateTimeUtils"%>
<%@page import="java.util.TreeMap"%>
<%@page import="za.co.emergelets.xplain2me.entity.Profile"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.UserManagementForm"%>

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
                                <table border="0" class="table table-stripped">
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
                                        Actions
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
                            <%
                        }
                    %>
                    
                </div>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
