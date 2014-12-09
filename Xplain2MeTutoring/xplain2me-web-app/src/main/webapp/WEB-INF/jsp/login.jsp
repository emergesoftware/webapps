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
        form = new LoginForm();
    }
    
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Login | Xplain2Me Tutoring</title>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate" />
        <meta http-equiv="Pragma" content="no-cache" />
        <meta http-equiv="Expires" content="0" />

        <!-- Bootstrap: Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://bootswatch.com/cosmo/bootstrap.min.css" />

        <!-- Bootstrap Glyphicons -->
        <link type="text/css" rel="stylesheet" href="http://netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap-glyphicons.css" />

        <!-- Latest version of jQuery -->
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
        
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
                onsubmit="return validateLoginForm(this)"
                style="width:60%">
                
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
                    <span class="glyphicon glyphicon-info-sign"></span>
                    <span><strong> <%= attemptsMessage %></strong></span>
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
               
                <a href="<%= request.getContextPath() %>/index"
                   class="btn btn-default">
                    Go Back
                </a>
                
            </form>
                            
            <br/>
            <br/>
            
            <hr/>
            
            <%@include file="../jspf/template/default-footer.jspf" %>              
            
        </div>
        
    </body>
</html>
