<%-- 
    Document   : generic-error-page
    Created on : 04 Dec 2014, 1:38:17 PM
    Author     : user
--%>

<%@page import="za.co.emergelets.xplain2me.webapp.controller.RequestMappings"%>
<%@page import="za.co.emergelets.xplain2me.webapp.component.ErrorForm"%>
<%
    ErrorForm form = (ErrorForm)request
            .getAttribute(ErrorForm.class.getName());
    
    if (form == null) {
        response.sendRedirect(request.getContextPath() + 
                "/assets/html/error-pages/default.html");
        return;
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title><%= form.getErrorTitle() %></title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <!-- Bootstrap: Latest compiled and minified CSS -->
        <link rel="stylesheet" href="http://bootswatch.com/cosmo/bootstrap.min.css" />

        <!-- Latest version of jQuery -->
        <script src="https://code.jquery.com/jquery-1.11.1.min.js"></script>

        <!-- Latest compiled and minified JavaScript -->
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>

    </head>
    
    <body>
        <div id="content" class="container">
            
            <br/>
            
            <div class="jumbotron">
                <h1><%= form.getErrorTitle() %></h1>
                <p>
                    <%= form.getErrorDescription() %>
                </p>
                <hr/>
                <a href="<%= request.getContextPath() + RequestMappings.DASHBOARD_OVERVIEW %>"
                   class="btn btn-primary">
                    Return to dashboard
                </a>
            </div>
            
        </div>
    </body>
</html>
