
<%@page import="za.co.xplain2me.entity.ProfileType"%>
<%@page import="za.co.xplain2me.webapp.component.UserManagementForm"%>
<%@page import="za.co.xplain2me.entity.Profile"%>

<%
    UserManagementForm form = (UserManagementForm)session
            .getAttribute(UserManagementForm.class.getName());

    if (form == null) {
        
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.BROWSE_EXISTING_USERS + 
                "?rand=" + System.currentTimeMillis());
        return;
    }
    
    boolean profileIsNull = (form.getProfile() == null);
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Delete User Confirmation</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Delete User Confirmation</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <% if (!profileIsNull) { 
                    
                    Profile profile = form.getProfile();
                    String actionUrl = request.getContextPath() + 
                            RequestMappings.DELETE_USER_PROFILE;
                
                %>
                
                <div class="row">
                    <div class="col-md-6">
                        
                    <form name="profileForm" id="profileForm"
                          method="post"
                          action="<%= actionUrl %>">
                        
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>
                                    <span>
                                        You are about to permanently remove 
                                        <strong>
                                            <%= profile.getPerson().getUser().getUsername() %>
                                        </strong>
                                    </span>
                                    <span> from the system. Confirm below by providing 
                                        your password and ticking the check box.
                                    </span>
                                   
                                </p>
                            </div>
                        </div>
                                        
                        <div class="form-group">
                            <label>Are you sure you want to permanently remove this user?</label><br/>
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="deleteUserProfile" name="deleteUserProfile"
                                           value="yes"
                                           data-validation="checkbox_group" data-validation-qty="min1">Yes
                                </label>
                            </div>
                        </div>
                                
                        <div class="form-group">
                            <label>Enter your password:</label>
                            <input type="password" name="password" id="password"
                                   class="form-control" placeholder="Enter your password (Required)"
                                   data-validation="length" data-validation-length="min5" maxlength="24"
                                   data-validation-error-msg="Your password is required." />
                        </div>
                                
                        <div class="form-group">
                            <input type="submit" value="Delete User Profile"
                                   class="btn btn-primary" ondblclick="return false" />
                        </div>
                        
                    </form>
                </div>
                
                <% } %>
                
            </div>
            
            <%@include file="../jspf/template/default-manager-footer.jspf" %>
            
        </div>
            
        </div>
    </body>
</html>
