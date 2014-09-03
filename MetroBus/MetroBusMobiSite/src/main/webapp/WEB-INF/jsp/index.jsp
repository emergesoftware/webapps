<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
    </head>

    <body>

        <%@include file="../jspf/templating/loadingPanel.jspf" %>

       <!-- wrap the page navigation here -->
       <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

        <!--wrap the page content do not style this-->
        <div id="page-content">

            <div class="container" >
                
                <br/>
                
                <!-- table border="0" style="width: 100%">
                    <tr>
                        <td><img src="/resources/images/metrobus-logo-jpeg.jpg" 
                         alt="" style="height: 64px"/></td>
                        <td>
                            <div style="margin-left: 18px">
                                <h3 class="text-primary">
                                    Johannesburg MetroBus App
                                </h3>
                            </div> 
                        </td>
                    </tr>
                </table>
                
                <hr/ -->
                
                
                <br/>
                
                <a class="btn btn-primary form-control" 
                   href="<%= request.getContextPath() %>/time-tables/select-option">
                    <span class="glyphicon glyphicon-th-large"></span>
                    <span>&nbsp;</span>
                    <span>Time Tables</span>
                </a>
                <br/>
                <br/>
                    
                <a class="btn btn-primary form-control" 
                   href="<%= request.getContextPath() %>/search/find-available-routes">
                    <span class="glyphicon glyphicon-search"></span>
                    <span>&nbsp;</span>
                    <span>Search</span>
                </a>
                <br/>
                <br/>
                
                <a class="btn btn-primary form-control" 
                   href="#">
                    <span class="glyphicon glyphicon-tags"></span>
                    <span>&nbsp;</span>
                    <span>Fares</span>
                </a>
                <br/>
                
               
                <br/>
                
            </div>

        </div>
 

        
    </body>
</html>
