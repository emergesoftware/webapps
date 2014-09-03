<%@page import="za.co.metrobus.hibernate.entity.BusStop"%>
<%@page import="za.co.metrobus.hibernate.entity.BusRoute"%>
<%@page import="com.emergelets.metrobus.mobisite.component.SearchForm"%>
<%@ page import="java.util.List" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    SearchForm searchForm = (SearchForm)session.getAttribute(SearchForm.class.getName());
    if (searchForm == null || 
            searchForm.getBusStops() == null)
        response.sendRedirect(request.getContextPath() + "/search/find-available-routes");
    
    int foundCount = searchForm.getBusStops().size();
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
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
                <p class="text-muted">
                    <strong>
                        <%= (foundCount == 1) ? ("Found: " + foundCount + " route.") : 
                                ("Found: " + foundCount + " routes.") %>
                    </strong>
                </p>
                <hr/>
                
                <%
                    if (searchForm.getBusStops() == null || 
                            searchForm.getBusStops().isEmpty()) {
                        
                        %>
                        
                        <div class="alert alert-warning">
                            <h4>Nothing Found!</h4>
                            <p>
                                We could not find a bus route that has such a 
                                destination location name. Please try again.
                            </p>
                        </div>
                        
                        <%
                        
                    }
                    
                    else {
                        
                        %><div class="list-group"> <%

                        List<BusStop> busStops = searchForm.getBusStops();

                        for (BusStop stop : busStops) {
                            
                            BusRoute route = stop.getRoute();
                            String link = request.getContextPath() + 
                                    "/time-tables/find-by-bus-route?"
                                    + "routeNumber=" + route.getRouteNumber();
                    %>

                    <a id="busRouteItem<%= route.getRouteNumber() %>" 
                       href="<%= link %>" class="list-group-item" style="font-weight: bold">
                        <span class="label label-primary"><%= route.getRouteNumber() %></span>
                        <span>&nbsp;</span>
                        <span><%= route.getRouteDescription() %></span><br/>
                        <span class="text-muted"><%= stop.getFullDescription() %></span>
                    </a>
                    <%
                        }
                    %>
                    </div>
                    
                    <% } %>
                
            </div>
        </div>

              
    </body>
</html>
