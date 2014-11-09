<%-- 
    Document   : login
    Created on : 30 Sep 2014, 8:08:34 PM
    Author     : user
--%>

<%@page import="za.co.emergelets.xplain2me.webapp.component.LoginForm"%>
<%
    LoginForm form = (LoginForm)session
            .getAttribute(LoginForm.class.getName());
    
    if (form == null) {
        response.sendRedirect("/login");
        return;
    }
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login | Xplain2Me Tutoring</title>
        
        <link type="text/css" rel="stylesheet" 
              href="<%= request.getContextPath() %>/assets/themes/dark-theme/bootstrap/css/bootstrap.min.css" />
       
        <script type="text/javascript" 
        src="<%= request.getContextPath() %>/assets/themes/dark-theme/js/jquery-1.10.2.min.js"></script>
        
        <script type="text/javascript" 
        src="<%= request.getContextPath() %>/assets/themes/dark-theme/bootstrap/js/bootstrap.min.js"></script>
       
        <script type="text/javascript" 
            src="<%= request.getContextPath() %>/assets/js/login.js"></script>
            
            
        <!-- force reload on back press -->
        <script type="text/javascript">
            window.onpageshow = function(event) {
                if (event.persisted) {
                    document.body.style.display = "none";
                    location.reload();
                }
            };
        </script>
        
    </head>
    <body onload="initialisation()">
        
        <div id="content" class="container">
            
            <h4 class="text-primary">Xplain2Me Tutoring Services | Portal</h4>
            <hr/>
           
            <h3>Login</h3>
            <p class="text-muted">
                To utilise our portal, please provide your
                login credentials below:
            </p>
            
            <form role="form" id="loginForm" name="loginForm" method="post"
                action="<%= request.getContextPath() + "/login" %>"
                onsubmit="return validateLoginForm(this)">
                
                <%
                    // determine if there are any errors
                    boolean hasErrors = !form.getErrorsEncountered().isEmpty();
                %>
                
                <div id="alertBlock" class="alert alert-danger" role="alert"
                     <%= (hasErrors) ? "" : "style=\"display:none\"" %>>
                    
                    <%
                        if (hasErrors) {
                            %> 
                            <p>Some errors were encountered:</p>
                            <ul>
                    <%
                            for (String error : form.getErrorsEncountered()) {
                                %>
                                <li><%= error %></li>
                    <%
                            }
                            
                            %>
                            </ul>
                                <%
                        }
                    %>
                    
                </div>
                
                <%
                        int attemptsLeft = 0;
                        if (form.getLoginAttempts() > LoginForm.MAX_LOGIN_ATTEMPTS)
                            attemptsLeft = 0;
                        else
                            attemptsLeft = LoginForm.MAX_LOGIN_ATTEMPTS - form.getLoginAttempts();
                        
                        String attemptsMessage = "You have " + attemptsLeft + " login attempts left.";
                        if (attemptsLeft == 1)
                            attemptsMessage = "You have 1 login attempt left.";
                %>
                
                <p class="text-info">
                    <i class="glyphicon glyphicon-info-sign"></i>
                    <strong> <%= attemptsMessage %></strong>
                </p>
                
                <br/>
                
                <!-- Username -->
                <div id="usernameFormGroup" class="form-group">
                    <label>Username</label>
                    <input type="text" class="form-control" id="username"
                           name="username" maxlength="16"
                           placeholder="Enter Username"/>
                </div>
                
                <!-- Password -->
                <div id="passwordFormGroup" class="form-group">
                    <label>Password</label>
                    <input type="password" class="form-control" id="password"
                           name="password" maxlength="32"
                           placeholder="Enter Password"/>
                </div>
                
                <br/>
                
                <input type="submit" id="loginButton"
                    name="loginButton" class="btn btn-primary"
                    value="Login"/>
               
            </form>
                            
            <br/>
            <br/>
            
            <hr/>
            
            <%@include file="../jspf/template/default-footer.jspf" %>              
            
        </div>
        
    </body>
</html>
