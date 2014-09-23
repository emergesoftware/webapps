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
                <h2>Oops, something went wrong</h2>
                <hr/>
                <p>
                    We have detected that something must have gone wrong
                    or you tried to access a non-existent resource.
                </p>
                
            </div>

        </div>
 
        <%@include file="../jspf/templating/default-footer.jspf" %>
        
    </body>
</html>
