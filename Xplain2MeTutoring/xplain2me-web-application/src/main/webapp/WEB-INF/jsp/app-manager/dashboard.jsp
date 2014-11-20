<%-- 
    Document   : dashboard
    Created on : 30 Sep 2014, 8:08:34 PM
    Author     : user
--%>

<%
    UserContext userContext = (UserContext)session
                .getAttribute(UserContext.class.getName());
    
    if (userContext == null) {
        response.sendRedirect("/login?session_timeout=true");
    }
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <title>Dashboard | Xplain2Me Tutoring</title>
        
        <%@include file="../../jspf/template/default-manager-header.jspf" %>
        
    </head>
    <body>
        
        <div id="wrapper">
            
            <%@include file="../../jspf/template/default-manager-navigation.jspf" %>
            
            <div id="page-wrapper">
                
                <h2>Dashboard</h2>
                
                <!-- new tutor requests -->
                
            </div>
             
            <%@include file="../../jspf/template/default-footer.jspf" %>              
            
        </div>
        
    </body>
</html>
