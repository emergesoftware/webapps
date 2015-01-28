
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Xplain2Me Tutoring Services</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
    </head>

    <body>
        
        <div class="container" id="content">
            
            <%@include file="../jspf/template/default-banner.jspf" %>
            
            <%@include file="../jspf/template/default-nav-bar.jspf" %>
            
            <h3>Hello there</h3>
            <p class="text-muted">
                Welcome to our customer portal - from here you
                can choose an option from below to continue:
            </p>
            
            <a href="<%= request.getContextPath() %>/request-a-tutor" 
               class="btn btn-primary btn-lg">
                <span class="glyphicon glyphicon-bullhorn"></span>
                <span>&nbsp;Request a tutor</span>
            </a>
            
            
            <a href="<%= request.getContextPath() %>/quote-request"
               class="btn btn-primary btn-lg">
                <span class="glyphicon glyphicon-credit-card"></span>
                <span>&nbsp;Request a quote</span>
            </a>
            
            
            <a href="<%= request.getContextPath() %>/become-a-tutor"
               class="btn btn-primary btn-lg">
                <span class="glyphicon glyphicon-user"></span>
                <span>&nbsp;Become a tutor</span>
            </a>
            
               
            <br/>
            <br/>
            
            <%@include file="../jspf/template/default-footer.jspf" %>
            
        </div>
        
    </body>
</html>
