
<%@page import="za.co.xplain2me.entity.ProfileType"%>
<%@page import="za.co.xplain2me.webapp.controller.UserManagementController"%>
<%@page import="za.co.xplain2me.webapp.component.UserManagementForm"%>

<%
    UserManagementForm form = (UserManagementForm)session
            .getAttribute(UserManagementForm.class.getName());
    
    if (form == null) {
        response.sendRedirect(request.getContextPath() + 
                RequestMappings.SEARCH_USER + 
                "?mode=" + UserManagementController.SEARCH_MODE_FORM);
        return;
    }
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" "http://www.w3.org/TR/REC-html40/loose.dtd">
<html>
    <head>
        <title>Search Users</title>
        
        <%@include file="../jspf/template/default-manager-header.jspf" %>
        <%@include file="../jspf/template/form-validation-script.jspf" %>
        
        <script type="text/javascript">
            
            function createTextElement() {
                var element = document.createElement('input');
                
                element.setAttribute('id', 'searchValue');
                element.setAttribute('type', 'text');
                element.setAttribute('name', 'search-value');
                $(element).addClass('form-control');
                
                return element;
            }
            
            function createSelectElement() {
                var element = document.createElement('select');
                
                element.setAttribute('id', 'searchValue');
                element.setAttribute('name', 'search-value');
                $(element).addClass('form-control');
                
                return element;
            }
            
        </script>
        
        <script type="text/javascript">
            
            $(document).ready(function() {
                
                // the searchType element
                var searchType = document.getElementById("searchType");
                
                // assign the searchType dropbox box an onChange event
                $(searchType).change(function(event) {
                    
                    // other elements
                    var infoPanel = document.getElementById("infoPanel");
                    var searchValueWrapper = document.getElementById("searchValueWrapper");
                    
                    // hide the info panel
                    $(infoPanel).css({ display : 'none' });
                    
                    // get the value selected
                    var value = $(this).val();
                    
                    // if the username is selected 
                    if (value === "1") {
                        
                        $(searchValueWrapper).html('');
                        var searchValue = createTextElement();
                     
                        $(searchValue).val('');
                        $(searchValue).attr({ 'placeholder' : 'Enter the Username (Required)' });
                        
                        $(searchValueWrapper).append(searchValue);
                    }
                    
                    // if the id / passport number is selected 
                    else if (value === "2") {
                        
                        $(searchValueWrapper).html('');
                        var searchValue = createTextElement();
                        
                        $(searchValue).val('');
                        $(searchValue).attr({ 'placeholder' : 'Enter ID / Passport Number (Required)' });
                        
                        $(searchValueWrapper).append(searchValue);
                    }
                    
                    // if the email address is selected
                     if (value === "3") {
                        
                        $(searchValueWrapper).html('');
                        var searchValue = createTextElement();
                        
                        $(searchValue).val('');
                        $(searchValue).attr({ 'placeholder' : 'Enter the Email Address (Required)' });
                        
                        $(searchValueWrapper).append(searchValue);
                    }
                    
                    // if profile type was selected
                    if (value === "4") {
                        
                        $(searchValueWrapper).html('');
                        var searchValue = createSelectElement();
                        
                        var types = document.getElementsByName("profile_types");
                        
                        for (var i = 0; i <types.length; i++) {
                            
                            var array = $(types[i]).val().split(".");
                            
                            var option = document.createElement('option');
                            $(option).val(array[0]);
                            $(option).html(array[1]);
                            
                            $(searchValue).append(option);
                        }
                        
                        $(searchValueWrapper).append(searchValue);
                        
                    }
                    
                    // if value is from 5 to 7
                    if (value === "5" || value === "6" || value === "7") {
                        
                        $(searchValueWrapper).html('');
                        var searchValue = createTextElement();
                        
                        $(searchValue).val('');
                        $(searchValue).attr({ 'placeholder' : 'No value is required. Leave field blank' });
                        $(searchValue).prop('readonly', true);
                        
                        $(searchValueWrapper).append(searchValue);
                    }
                    
                });
                
            });
            
        </script>
        
    </head>
    <body>
        <div id="wrapper">
            
            <%@include file="../jspf/template/default-manager-navigation.jspf" %>
            
            <div class="container-fluid" id="page-wrapper">
                
                <h2>Search Users</h2>
                <hr/>
                
                <%@include file="../jspf/template/default-alert-block.jspf" %>
                
                <div class="row">
                    
                    <div class="col-md-6">
                        
                        <%
                            for (ProfileType type : form.getProfileTypes().values()) {
                                
                                %>
                        <input type="hidden" name="profile_types" 
                               value="<%= type.getId() + "." + type.getDescription() %>" />
                        <%
                                
                            }
                        %>
                        
                        <form role="search" name="searchForm" id="searchForm"
                              method="post"
                              action="<%= request.getContextPath() + RequestMappings.SEARCH_USER %>">
                            
                            <div class="form-group">
                                <label for="searchType">Search Using:</label>
                                <select id="searchType" name="search-type" 
                                        class="form-control">
                                    <option value="<%= UserManagementController.SEARCH_TYPE_USERNAME %>" selected>
                                        Username
                                    </option>
                                    <option value="<%= UserManagementController.SEARCH_TYPE_ID_NUMBER %>">
                                        Identity / Passport Number
                                    </option>
                                    <option value="<%= UserManagementController.SEARCH_TYPE_EMAIL_ADDRESS %>">
                                        Email Address
                                    </option>
                                    <option value="<%= UserManagementController.SEARCH_TYPE_PROFILE_TYPE %>">
                                        Profile Type
                                    </option>
                                    <option value="<%= UserManagementController.SEARCH_TYPE_ACTIVE_USERS %>">
                                        Active Users
                                    </option>
                                    <option value="<%= UserManagementController.SEARCH_TYPE_BLOCKED_USERS %>">
                                        Blocked Users
                                    </option>
                                    <option value="<%= UserManagementController.SEARCH_TYPE_UNVERIFIED_USERS %>">
                                        Unverified User Profiles
                                    </option>
                                </select>
                            </div>
                            
                            <div id="infoPanel" class="panel panel-primary" style="display:none">
                            </div>
                                        
                            <div class="form-group">
                                <label for="searchValue">Search Value:</label>
                                <div id="searchValueWrapper">
                                    <input type="text" id="searchValue" name="search-value"
                                           class="form-control" value=""
                                           placeholder="Enter the Username (Required)" />
                                </div>
                            </div>
                            
                            <div class="form-group">
                                <input type="submit" value="Search Users"
                                       ondblclick="return false" class="btn btn-primary"/>
                            </div>
                            
                        </form>
                        
                    </div>
                    
                </div>
            </div>
            
            <%@include file="../jspf/template/default-footer.jspf" %> 
            
        </div>
    </body>
</html>
