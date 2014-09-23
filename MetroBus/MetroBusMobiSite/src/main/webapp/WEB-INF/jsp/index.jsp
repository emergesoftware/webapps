<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
    </head>

    <body>

       <!-- wrap the page navigation here -->
       <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

        <!--wrap the page content do not style this-->
        <div id="page-content">

            <div class="container" >
                
                <br/>
                
                <div class="jumbotron">
                    <h1>The MetroBus App</h1>
                    <p>The mobile app designed for MetroBus commuters in Jozi, has landed in town, 
                    first of it's kind - made just for you.</p>
                    <p><a class="btn btn-primary btn-lg" href="#getGoingHeader">Get going</a></p>
                </div>
                
                
                <h3 id="getGoingHeader">Let's get going...</h3>
                
                <p class="text-primary">
                    Here's a few options to explore:
                </p>
                
                <a href="<%= request.getContextPath() %>/time-tables/select-option" 
                   class="btn btn-default btn-lg btn-block">
                    Check bus routes + time tables
                </a>
                <br/>
                
                <a href="<%= request.getContextPath() %>/search/find-available-routes" 
                   class="btn btn-default btn-lg btn-block">
                    Search where you'd like to go
                </a>
                <br/>
                
            </div>

        </div>
 
        <%@include file="../jspf/templating/default-footer.jspf" %>
        
    </body>
</html>
