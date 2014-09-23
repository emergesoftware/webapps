<%@page import="com.emergelets.metrobus.util.GoogleMapsInfo"%>
<%@page import="za.co.metrobus.hibernate.entity.BusRoute"%>
<%@page import="za.co.metrobus.hibernate.entity.BusStop"%>
<%@page import="com.emergelets.metrobus.mobisite.component.RouteForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    RouteForm form = (RouteForm)session.getAttribute(RouteForm.class.getName());
    if (form == null)
        form = new RouteForm();
    
    // get the bus stops
    List<BusStop> busStops = form.getRouteBusStops();
    // get the bus route information
    BusRoute route = form.getRoute();
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
        <link type="text/css" href='https://api.tiles.mapbox.com/mapbox.js/v2.1.0/mapbox.css' rel='stylesheet' />
        <script type="text/javascript" src='https://api.tiles.mapbox.com/mapbox.js/v2.1.0/mapbox.js'></script>
       
        <script type="text/javascript">
            
            $(document).ready(function(){
                
                // Provide your access token
                L.mapbox.accessToken = 'pk.eyJ1IjoidHNlcG9tYWxla2EiLCJhIjoiRnJNRXUyRSJ9.BLPvl6Zr0QH_V5cu8DizIg';
                // Create a map in the div #map
                var map = L.mapbox.map('map', 'tsepomaleka.ipki7b4m')
                // center the map to the terminus
                        .setView([-26.2041, 28.0473], 12);
                
                // add the bus stops if there is a list of them
                <%
                    if (route != null && 
                            (busStops != null && !busStops.isEmpty())) {
                        %>
                <%= form.generateMapMarkerWithoutJson() %>
                        <%
                    }
                %>
               
            });
           
            
        </script>
        
    </head>

    <body>
        
        <!-- wrap the page navigation here -->
        <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

        <!--wrap the page content do not style this-->
        <div id="page-content">
            <div class="container" >
                <br/>
                
                <!-- bus route information -->
                <%
                    if (route == null) {
                        %>
                
                        <div class="alert alert-danger">
                            <strong>Incorrect bus route found</strong> 
                            We could not find the specified bus route and it's bus stops.
                            Please check your request before trying again.
                        </div>
                        
                        <%
                    }
                    
                    else {
                        %>
                        
                        <h3>
                            <%= route.getRouteNumber() + " - " + route.getRouteDescription() %>
                        </h3>
                        
                        <hr/>
                        
                        <h4>Bus Stops</h4>
                        <p class="text-muted">
                            See all the bus stops covered by this
                            bus route service in the map below.
                        </p>
                        
                        <div class="container" id="map" 
                             style="height: 480px; border-top: 1px solid gray; border-bottom: 1px solid gray">
                            
                        </div>
                        
                    <%
                            
                    }
                       
                %>
                
               
            </div>
        </div>
        </div>
                
        <%@include file="../jspf/templating/default-footer.jspf" %>
              
    </body>
</html>
