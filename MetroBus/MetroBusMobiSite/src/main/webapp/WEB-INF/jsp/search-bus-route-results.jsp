<%@page import="java.util.Set"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
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
    
    Map<BusRoute, List<BusStop>> map = new HashMap<BusRoute, List<BusStop>>();
    
    if (searchForm.getBusStops().size() > 0) {
        
        List<BusStop> list = null;
        BusRoute key = null;
        
        for (BusStop stop : searchForm.getBusStops()) {
            
            key = stop.getRoute();
            
            if (map.containsKey(key)){
                list = map.get(key);
                list.add(stop);
            }
            
            else {
                list = new ArrayList<BusStop>();
                list.add(stop);
                map.put(key, list);
            }
        }
    }
    
    int foundCount = map.size();
    
%>

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
                <p class="text-muted">
                    <strong>
                        <%= (foundCount == 1) 
                                ? ("Found: " + foundCount + " bus route going past " + searchForm.getSearchQuery()) : 
                                ("Found: " + foundCount + " bus routes going past " + searchForm.getSearchQuery()) %>
                    </strong>
                </p>
                <hr/>
                
                <%
                    if (map.isEmpty()) {
                        
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

                        Set<BusRoute> keySet = map.keySet();

                        for (BusRoute route : keySet) {
                            
                            String link = request.getContextPath() + 
                                    "/time-tables/find-by-bus-route?"
                                    + "routeNumber=" + route.getRouteNumber();
                            
                            %>
                            <a id="busRouteItem<%= route.getRouteNumber() %>" 
                                href="<%= link %>" class="list-group-item" style="font-weight: bold">
                                
                                <span class="label label-primary"><%= route.getRouteNumber() %></span>
                                <span>&nbsp;</span>
                                <span><%= route.getRouteDescription() %></span>
                                <hr/>
                                
                                <span class="text-primary">Going past: </span><br/>
                            <%
                            
                            for (BusStop stop : map.get(route)) {
                                
                                %>
                                
                                <span class="glyphicon glyphicon-circle-arrow-right"></span>
                                <span class="text-muted"><%= stop.getFullDescription() %></span>
                                <br/>
                                
                                <%
                                
                            }
                            %>

                        </a>
                            <%
                        }
                            %>
                    </div>

                <% } %>
                
            </div>
        </div>

        <%@include file="../jspf/templating/default-footer.jspf" %>
        
    </body>
</html>
