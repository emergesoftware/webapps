<%-- 
    Document   : request-a-tutor-submitted
    Created on : 01 Oct 2014, 7:15:12 PM
    Author     : user
--%>

<%@page import="za.co.emergelets.xplain2me.webapp.component.RequestTutorForm"%>
<%
    RequestTutorForm form = (RequestTutorForm)session
            .getAttribute(RequestTutorForm.class.getName());
    if (form == null)
        response.sendRedirect("/request-a-tutor");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Request a Tutor | Xplain2Me Tutoring Services</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
    </head>
    <body>
        <div id="content" class="container">
            
            <%@include file="../jspf/template/default-banner.jspf" %>
            
            <%@include file="../jspf/template/default-nav-bar.jspf" %>
            
            <h3>Results:</h3>
            <hr/>
            
            <%
                boolean hasErrors = (form.getErrorsEncountered() != null && 
                        form.getErrorsEncountered().isEmpty() == false);
            %>
            
            <% if (hasErrors == true) { %>
                <p class="text-danger">
                    The submission encountered the following errors:
                </p>
                <ul>
                <% for (String error : form.getErrorsEncountered()) { %>
                    <li><%= error %></li>
                <% } %>
                </ul>
                <p><strong>Press the Back button from your browser to
                    return to the previous page and fix these.</strong>
                </p>
            <% } else { %>
                <p>Thank you, your submission was successful.</p>
            <% } %>
            
            <br/>
            <br/>
            
            <%@include file="../jspf/template/default-footer.jspf" %>
            
        </div>
    </body>
</html>
