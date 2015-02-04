
<%@page import="za.co.xplain2me.util.BooleanToText"%>
<%@page import="za.co.xplain2me.entity.User"%>
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
        <title>Activate or Block User</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Activate / Block User</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <% if (!profileIsNull) { 
                    
                    Profile profile = form.getProfile();
                    String actionUrl = request.getContextPath() + 
                            RequestMappings.ACTIVATE_OR_BLOCK_USER;
                
                    User user = profile.getPerson().getUser();
                    
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
                                    <span> is currently set as being: </span>
                                    <span>
                                        <strong>
                                            <%= BooleanToText.format(user.isActive(), BooleanToText.ACTIVE_INACTIVE_FORMAT) %>
                                        </strong>.
                                    </span>
                                    <span> You may only activate or block users below your
                                    privilege level.</span>
                                </p>
                            </div>
                        </div>
                        
                        <%
                            String value = null;
                            if (user.isActive())
                                value = "block";
                            else
                                value = "activate";
                        %>                
                                        
                        <div class="form-group">
                            <div class="checkbox">
                                <label>
                                    <input type="checkbox" id="activateOrBlockUser" name="activateOrBlockUser"
                                           value="<%= value %>"
                                           data-validation="checkbox_group" data-validation-qty="min1">
                                    <%= value.substring(0, 1).toUpperCase() + 
                                            value.substring(1)  %> this user
                                </label>
                            </div>
                        </div>
                                
                        <div class="panel panel-default">
                            <div class="panel-body">
                                <p>
                                    If you <strong>Activate</strong> a user, they will be able
                                    to login into the system.<br/>
                                    However, if you <strong>Block</strong> a user, they will not 
                                    have access into the system.
                                </p>
                            </div>
                        </div>
                                
                        <div class="form-group">
                            <input type="submit" value="Save Change"
                                   class="btn btn-primary" ondblclick="return false" />
                        </div>
                        
                    </form>
                </div>
                
                <% } %>
                
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
