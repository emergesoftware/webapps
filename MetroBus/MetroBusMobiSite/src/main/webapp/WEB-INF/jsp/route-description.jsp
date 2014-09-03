<%@page import="za.co.metrobus.hibernate.entity.RouteDescription"%>
<%@page import="com.emergelets.metrobus.mobisite.component.RouteForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    RouteForm form = (RouteForm)session.getAttribute(RouteForm.class.getName());
    if (form == null)
        response.sendRedirect("/index");
    
    // Get the list of route descriptions
    List<RouteDescription> descriptions = form.getRouteDescriptions();
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
        <script type="text/javascript">
            
            function scrollTo(elementId) {
                
                $('html, body').animate({
                    scrollTop: $("#" + elementId).offset().top
                }, 500);
                
            }
            
        </script>
        
    </head>

    <body>
        
        <!-- the loading panel -->
        <%@include file="../jspf/templating/loadingPanel.jspf" %>

        <!-- wrap the page navigation here -->
        <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

        <!--wrap the page content do not style this-->
        <div id="page-content">
            <div class="container" >
                
                <br/>
                
                <%
                    if (descriptions == null || descriptions.isEmpty()) {
                        
                        %>
                        <div class="alert alert-warning">
                                <h4>No Route Description Found</h4>
                                <p>
                                    It looks like we could not find the route description
                                    for the requested bus route. Please also check that
                                    you have specified the correct bus route number.
                                </p>
                        </div>
                        <%
                        
                    }
                    
                    else {
                        
                        %>
                        <p class="text-muted">Jump to route description:</p>
                        <div class="list-group">
  
                        <%
                        
                        for (RouteDescription description : descriptions) {
                            
                            %>
                            <a href="#" class="list-group-item"
                               onclick="scrollTo('routeDescription<%= description.getId() %>')">
                                From <%= description.getStartLocation().getShortName() %>
                                to <%= description.getEndLocation().getShortName() %>
                            </a>
                            <%
                            
                        }
                        
                        %>
                        </div><br/>
                        
                        <%
                        
                        for (RouteDescription description : descriptions) {
                            
                            %>
                                <div id="routeDescription<%= description.getId() %>" 
                                     class="panel panel-primary">
                                    
                                    <div class="panel-heading">
                                        <h3 class="panel-title">
                                            From <%= description.getStartLocation().getShortName() %>
                                            to <%= description.getEndLocation().getShortName() %>
                                        </h3>
                                    </div>
                                    
                                    <div class="panel-body">
                                        <ul class="list-group">
                                            
                                        <%
                                            String[] lines = description.getText().split(";");
                                            
                                            int counter = 0;
                                            for (String line : lines) {
                                                counter++;
                                                
                                                %>
                                                    
                                                        <li class="list-group-item">
                                                            <span class="badge">
                                                                <%= counter %>
                                                            </span>
                                                            <%= line.replace("\n", "") %>
                                                        </li>
                                                    
                                                <%
                                                
                                            }
                                        %>
                                        </ul>
                                    </div>
                                </div> <br/>
                            <%
                            
                        }
                        
                    }
                %>
                
            </div>
        </div>

              
    </body>
</html>
