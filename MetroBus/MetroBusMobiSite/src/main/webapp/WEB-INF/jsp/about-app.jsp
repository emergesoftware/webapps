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
                
                <div id="aboutPanel" class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            About
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p>
                            The <strong>MetroBus App</strong> is an application designed
                            for Johannesburg MetroBus commuters. From this application a 
                            commuter can view the available bus routes, their time tables,
                            view the locations of the bus route bus stops and a comprehensive
                            route description, and search for bus services going to a specific
                            locations.
                        </p>
                        <p>
                            This application was developed, maintained and freely distributed within the
                            Republic of South Africa by <strong>Emerge Software (Pty) Ltd</strong>.
                        </p>
                    </div>
                 </div>
                
                <div class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            Disclaimer
                        </h3>
                    </div>
                    <div class="panel-body">
                        <p>
                            This application is not affiliated, managed or directly
                            associated with the Johannesburg MetroBus company and its
                            stakeholders, if any, or the City of Joburg Municipality.
                            Thereof, any trademarks that may have been used in this
                            application, that are not owned by <strong>Emerge Software (Pty) Ltd</strong>
                            belong to their respective parties, as stated in the previous 
                            statement.
                        </p>
                        
                        <p>
                            By using this application, you agree to use it only for its
                            sole purpose, as described under the <a href="#aboutPanel">About</a> 
                            panel. <strong>Emerge Software (Pty) Ltd</strong> cannot be held
                            responsible for any form of harm caused by using this application
                            for any other purpose.
                        </p>
                        
                    </div>
                </div>
                
                <div id="aboutPanel" class="panel panel-primary">
                    <div class="panel-heading">
                        <h3 class="panel-title">
                            App Details
                        </h3>
                    </div>
                    <div class="panel-body">
                        <table class="table table-bordered" border="0" style="width:100%">
                            <tr>
                                <td><b>Developer:</b></td>
                                <td>
                                    <span class="text-muted">Emerge Software (Pty) Ltd</span><br/>
                                    <span class="text-primary">Email us at: 
                                        <a href="mailto:support@emergesoftware.co.za" target="_blank">
                                            support@emergesoftware.co.za</a>
                                    </span>
                                </td>
                            </tr>
                            <tr>
                                <td><b>Version:</b></td>
                                <td>1.1.9 - Beta</td>
                            </tr>
                            <tr>
                                <td><b>Last updated:</b></td>
                                <td>09 September 2014 15:17 (South African Time)</td>
                            </tr>
                            <tr>
                                <td><b>Recent Change Log:</b></td>
                                <td>Several bug fixes and improvements.</td>
                            </tr>
                        </table>
                    </div>
                 </div>
                
            </div>
        </div>
        
        <%@include file="../jspf/templating/default-footer.jspf" %>
              
    </body>
</html>
