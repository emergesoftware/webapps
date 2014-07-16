<%--
    Document   : login
    Created on : 06 Jul 2014, 11:21:39 PM
    Author     : Tsepo Maleka
--%>

<%@page import="za.co.inkunzi.web.form.UserLoginForm"%>

<%
    UserLoginForm form = (UserLoginForm)session.getAttribute(UserLoginForm.class.getCanonicalName());
    if (form == null)
        form = new UserLoginForm();
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
    <head>
        
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Risk Profile Analyzer | Login | Inkunzi Investments</title>
        
        <%@include file="../jspf/header.jspf" %>
        
        <script type="text/javascript" src="<%= CONTEXT %>/scripts/custom/login.js"></script>
        
    </head>
    <body>
        
        <form id="loginForm" name="loginForm" method="post" action="<%= CONTEXT %>/Login"
              onsubmit="return validateForm(this)">
            
            <div class="container">
     
                <h3>.</h3>
                <h4>Login</h4>
                
                <hr/>
                
                <p><strong>Please enter credentials to gain access to the system.</strong></p>
                
                <% if (form.getErrorMessage() == null)  { %>
                    <div id="alertBox" class="alert alert-danger" style="display:none"></div>
                <% } 
                   
                else {
                %>
                <div id="alertBox" class="alert alert-danger" style="display:block">
                    <p>
                        <strong>Note:</strong> You will be blocked on your third failed attempt.<br/><br/>
                        
                            <%= form.getErrorMessage() %>
                    </p>
                </div>
                <%
                }
                %>
                
                <table id="loginTable" border="0" cellspacing="2" cellpadding="2" width="100%">
                    <tr>
                        <td style="width: 120px"><label>Username:</label></td>
                        <td><input name="username" type="text" id="usernameTextBox" 
                                   placeholder="Enter username"/></td>
                    </tr>
                    <tr>
                        <td style="width: 120px"><label>Password:</label></td>
                        <td><input name="password" type="password" id="passwordTextBox"
                                   placeholder="Enter password"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>
                            <% if (!form.isLocked()) { %>
                            <input type="submit" class="btn btn-primary btn-lg" 
                                   name="submitLoginRequest" value="Login">
                            <% } %>
                        </td>
                    </tr>
                </table>

                <hr/>
                
                <p><strong>More Options</strong></p>
                
                <ul class="list-group">
                    <li><a href="#" class="list-group-item">Reset password</a></li>
                    <li><a href="#" class="list-group-item">Request account registration</a></li>
                 
                </ul>
            </div>
        </form>
        
    </body>
</html>
