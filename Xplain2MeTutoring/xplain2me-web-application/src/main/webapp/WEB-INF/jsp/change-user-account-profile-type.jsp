
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
    
    boolean profileIsNull = false;
    
    if (form.getProfile() == null 
            || (form.getProfileTypes() == null ||
            form.getProfileTypes().isEmpty()))
        profileIsNull = true;
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Change User Profile Type</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Change User Profile Type</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <% if (!profileIsNull) { 
                    
                    Profile profile = form.getProfile();
                    String actionUrl = request.getContextPath() + 
                            RequestMappings.CHANGE_USER_PROFILE_TYPE + 
                            "?profile-id=" + profile.getId();
                
                %>
                
                <div class="row">
                    <div class="col-md-6">
                        
                    <form name="profileTypeForm" id="profileTypeForm"
                          method="post"
                          action="<%= actionUrl %>">
                        
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>
                                    <span>
                                        <strong>
                                            <%= profile.getPerson().getUser().getUsername() %>
                                        </strong>
                                    </span>
                                    <span> is currently assigned the </span>
                                    <span>
                                        <strong>
                                            <%= profile.getProfileType().getDescription() %>
                                        </strong>.
                                    </span>
                                    <span> You may only change to user profiles below your
                                    privilege level.</span>
                                </p>
                            </div>
                        </div>
                                        
                        <div class="form-group">
                            <label>Select user profile to change to:</label>
                            <select id="profileType" name="profileType"
                                    class="form-control" >
                                <optgroup label="Available User Profiles to switch to:">
                                <%
                                    for (ProfileType type : form.getProfileTypes().values()) {
                                        if (!type.equals(profile.getProfileType())) {
                                        
                                            %>
                                            
                                            <option value="<%= type.getId() %>">
                                                <%= type.getDescription() %>
                                            </option>       
                                            
                                <%
                                            
                                        }
                                    }
                                %>
                                </optgroup>
                            </select>
                        </div>
                                
                        <div class="form-group">
                            <label>Enter your password:</label>
                            <input type="password" name="password" id="password"
                                   class="form-control" placeholder="Enter your password (Required)"
                                   data-validation="length" data-validation-length="min5" maxlength="24"
                                   data-validation-error-msg="Your password is required." />
                        </div>
                                
                        <div class="form-group">
                            <input type="submit" value="Change Profile Type"
                                   class="btn btn-primary" ondblclick="return false" />
                        </div>
                        
                    </form>
                </div>
                
                <% } %>
                
            </div>
            
            <%@include file="../jspf/template/default-manager-footer.jspf" %>
            
        </div>
    </body>
</html>
