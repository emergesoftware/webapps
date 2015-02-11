
<%@page import="za.co.xplain2me.webapp.component.RequestTutorForm"%>

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
        
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="tutoring, private home tuition, tutors">
        <meta name="author" content="Tsepo Maleka">
        
        <title>Request a Tutor | Xplain2Me Tutoring</title>
        
        <%@include file="../jspf/template/default-header.jspf" %>
        
    </head>
    <body data-spy="scroll" data-offset="0" data-target="#navbar-main">
        
        <%@include file="../jspf/template/default-nav-bar.jspf" %>
        
        <div class="container">
            
            <h1 class="centered">Request Submission Result</h1>
            <hr/>
            
            <div class="row white">
                <div class="col-lg-6">
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
                </div>
            </div>
        </div>
                
        <%@include file="../jspf/template/default-footer.jspf" %>
          
    </body>
</html>
