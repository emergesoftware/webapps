<%@page import="za.co.metrobus.hibernate.entity.BusServiceType"%>
<%@page import="za.co.metrobus.hibernate.entity.BusRoute"%>
<%@page import="za.co.metrobus.hibernate.entity.BusDeparture"%>
<%@page import="za.co.metrobus.hibernate.entity.BusArrival"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.List"%>
<%@page import="com.emergelets.metrobus.mobisite.component.TimesTableForm"%>
<%@ page import="za.co.metrobus.hibernate.entity.Location" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    // get the form
    TimesTableForm form = (TimesTableForm)session
            .getAttribute(TimesTableForm.class.getName());
    if (form == null) {
        response.sendRedirect(request.getContextPath() + "/time-tables/select-option?error=true");
    }
        
    // get the list of departures + other information 
    // assuming only one type of departure service is loaded
    List<BusDeparture> departures = form.getBusDepartures();
    
    // bus route information
    BusRoute route = null;
    
    // parameter for service type
    String serviceParam = null;
            
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
        <script type="text/javascript" 
                src="<%= request.getContextPath() %>/resources/javascript/bus-route-time-table.js">
        </script>
        
    </head>

    <body onload="initialise()">

       <!-- wrap the page navigation here -->
       <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

       <!--wrap the page content do not style this-->
       <div id="page-content">

            <div class="container" >
                
                <!-- [1] THE BUS ROUTE INFORMATION / DESCRIPTION -->
                
                <%
                    route = form.getRoute();
                    String title = "No Bus Route Information Found.";
                    if (route != null) {
                        title = route.getRouteNumber() + " - " + 
                                route.getRouteDescription();
                    }
                %>
                
                <h3><%= title %></h3>
                
                <%
                    if (route == null) {
                    %>
                   
                    <br/>
                    
                    <div class="alert alert-warning">
                            <h4>No Time Table Found!</h4>
                            <p>
                                It is either you have entered an incorrect route number or
                                the time table for that bus and the service are not yet
                                available. Please try again.
                            </p>
                    </div>
                    
                <%
                    }
                %>
                
                
                <%
                
                // build the action url
                String actionUrl = null;
                
                if (route == null) {
                    actionUrl = request.getContextPath() + "/time-tables/select-option";
                }
                
                else {
                    actionUrl = request.getContextPath() + "/time-tables/bus-route/" +
                                route.getRouteNumber();
                }
                
                %>
                
                <form role="form" method="get"
                              action="<%= actionUrl %>"
                              id="timesTableForm">
                    
                    <input type="hidden" value="<%= form.getSecondsBeforeNextDeparture() %>"
                           id="secondsBeforeNextDeparture" />
                    
                <% if (route != null && form.getServiceType() != null)  {
                   
                    // determine the type of service
                    String serviceType = null;
                    
                    if (form.getServiceType() == BusServiceType.WeekdayService) {
                        serviceType = "MONDAY - FRIDAY SERVICE";
                        serviceParam = "weekday";
                    }
                    if (form.getServiceType() == BusServiceType.SaturdayService) {
                        serviceType = "SATURDAY SERVICE";
                        serviceParam = "saturday";
                    }
                    if (form.getServiceType() == BusServiceType.SundayService) {
                        serviceType = "SUNDAY SERVICE";
                        serviceParam = "sunday";
                    }
                %>
                    
                <p class="text-muted" style="font-size: 130%">
                    <strong><%= serviceType %></strong>
                </p>
                
                <hr/>
                
                <!-- [2] THE AVAILABILITY LABELS -->
                
                <div class="well">
                
                <%
                    // determine the available services for
                    // this route
                    if (route != null) {
                        
                        %>
                        <span class="text-muted">Availability: </span><br/>
                        <%
                    
                        if (route.isWeekdayService()) {
                            %> <a class="btn btn-success btn-sm"
                                    onclick="changeServiceType('weekday')">
                                <span class="glyphicon glyphicon-list-alt"></span>
                                <span> Monday - Friday</span>    
                                
                                </a> 
                            <%
                        }

                        if (route.isSaturdayService()) {
                            %> 
                            <a class="btn btn-warning btn-sm"
                                    onclick="changeServiceType('saturday')">
                                <span class="glyphicon glyphicon-list-alt"></span>
                                <span> Saturday</span> 
                            </a> 
                            <%
                        }

                        if (route.isSundayService()) {
                            %> <a class="btn btn-danger btn-sm"
                                    onclick="changeServiceType('sunday')">
                                <span class="glyphicon glyphicon-list-alt"></span>
                                <span> Sunday</span> 
                                </a> 
                            <%
                        }
                        
                        %>
                            <br/>
                            <hr/>
                            
                            <span class="text-muted">More bus route options:</span><br/>
                            
                            <div>
                                
                                <a class="btn btn-primary btn-sm"
                                    onclick="openRouteDescription('<%= route.getRouteNumber() %>')">
                                    <span class="glyphicon glyphicon-tasks"></span>
                                    <span> Route Description</span>
                                </a>
                                
                                <a class="btn btn-primary btn-sm"
                                    onclick="openRouteBusStops('<%= route.getRouteNumber() %>')">
                                    <span class="glyphicon glyphicon-map-marker"></span>
                                    <span> Bus Stops</span>
                                </a>
                                
                            </div>
                            
                        <%
                    }
                %>

                <hr/>
                
                
                <%
                    if (form.getLocations() != null &&
                            form.getLocations().isEmpty() == false) {
                        %>
                            <input type="hidden" id="serviceType" name="serviceType" value="<%= serviceParam %>"/>

                            <span class="text-muted">Filter by departure location:</span><br/>
                            <select name="from" id="locations" class="form-control"
                                    onchange="document.getElementById('timesTableForm').submit()">
                                <option value="0">ALL DEPARTURE LOCATIONS</option>
                        <%

                        for (Location location : form.getLocations()) {
                            
                            %>
                                <option value="<%= location.getLocationId() %>"
                                        <%= (form.getSearchLocationId() == location.getLocationId()) ?
                                                "selected='selected'" : "" %>>
                                    <%= location.getShortName() %>
                                </option>
                            <%
                        }

                        %>
                        </select>
                        
                        <%
                    }
                %>

                </div> 
                    
                    <%
                        if (form.getSecondsBeforeNextDeparture()> -1 && 
                                form.isTodaysServiceSearched()) {
                            %>
                            
                        <div class="panel panel-primary">
                            <div class="panel-heading">
                                <h3 class="panel-title">
                                    <i class="glyphicon glyphicon-info-sign"></i>
                                    Next Bus Departure Information</h3>
                        </div>
                            
                        <div class="panel-body">
                            <p>
                                <span class="text-primary" style="font-size: 120%">
                                    <%= form.getNextDepartureAlert() %>
                                </span><br/><br/>

                                <span id="nextDepartureAlert" style="font-size: 95%">
                                    <span class="glyphicon glyphicon-time"></span>
                                    <span> Departing in about </span>
                                    <span id="minutesLeftCounter" class="text-primary" style="font-size: 130%">0 min 0 sec</span>
                                    <span> from now.</span>
                                  </span>
                            </p>
                        </div>
                    </div>
                    <hr/>
                            
                            <%
                        }
                        
                        else if (form.getSecondsBeforeNextDeparture() == -1 && 
                                form.isTodaysServiceSearched()) {
                            %>
                                <span class="text-primary" style="font-size: 120%">
                                    <%= form.getNextDepartureAlert() %>
                                </span><br/>
                                <hr/>
                            <%
                        }
                    %>

                <table border="0" width="100%" class="table">
                    <thead>
                        <th>Time</th>
                        <th>Departs From</th>
                        <th>Stopping At</th>
                    </thead>
                    <tbody>
                    <%
                        
                        if (departures == null || departures.isEmpty()) {
                            %>
                            <tr>
                                <td colspan="3">
                                    <span class="text-warning">
                                        <strong>
                                            No bus time table found for this route, on the
                                            selected day of service.
                                        </strong>
                                    </span>
                                </td>
                            </tr>
                            <%
                        }
                        
                        else {
                            
                        for (BusDeparture departure : departures) {

                           String time = new SimpleDateFormat("HH:mm")
                                   .format(departure.getDepartureTime());
                           String departsFrom = departure.getDepartureLocation().getShortName();
                           StringBuilder arrivals = new StringBuilder();

                           %>
                           
                           <%
                           if (departure.isNextDeparture()) {
                               %>
                               <tr class="active">
                                   <td colspan="3">
                                       <label class="label label-danger">
                                           <i class="glyphicon glyphicon-time"></i>
                                           NEXT BUS
                                       </label>
                                   </td>
                               </tr>
                            <%
                           }
                           %>
                           
                            <tr id="departureItem<%= departure.getBusDepartureId() %>" 
                                <%= ((departure.isNextDeparture()) ?
                                    "class='active' style='font-size:120%'" : "") %>>
                                <td>
                                    <span><%= time %></span> 
                                </td>
                                <td><%= departsFrom %></td>
                           
                           <%
                           
                           int counter = 0;
                           
                           for (BusArrival arrival : departure.getBusArrivals()) {
                               
                               counter++;
                               
                               if (counter == departure.getBusArrivals().size())
                                   arrivals.append(arrival.getArrivalLocation().getShortName())
                                          .append("</br>");
                               
                               else 
                                   arrivals.append(arrival.getArrivalLocation().getShortName())
                                          .append(",</br>");
                           }
                           %>

                                <td> <%= arrivals.toString() %> </td>

                              </tr>
                           <%
                        
                        }
                    }
                    %>
                    </tbody>
                </table>
                
                <hr/>
                
                <label class="label label-primary">
                    PLEASE NOTE:
                </label>
                <br/>
                
                <div class="well" style="font-size: 80%">
                    
                    <ul>
                        <li>
                            This time table for this specified route was correct at
                            the time of capture and is the most updated version. 
                            We try our best to keep the time tables up to
                            date, as often as we can.
                        </li>
                        <li>
                            You may notice a change of times and differences in the
                            actual MetroBus services as well as this time table. MetroBus
                            may cancel, postpone or modify the time table
                            for this route, with or without notice to the developer.
                        </li>
                        <li>
                            This time table assumes that the bus service is running
                            smoothly and on time, all the time.
                            There is no guarantee that this is always the case. Other
                            factors like traffic, broken down buses or disruption
                            within the MetroBus services may cause slight inconvenience.
                        </li>
                    </ul>
                </div>
                
                <% } %>
                
                </form>
            </div>

        </div>    
                
       <%@include file="../jspf/templating/default-footer.jspf" %>
       
    </body>
</html>
