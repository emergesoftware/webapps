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

        <%@include file="../jspf/templating/loadingPanel.jspf" %>
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
                    
                <p class="text-muted"><strong><%= serviceType %></strong></p>
                
                <hr/>
                
                    <!-- [2] THE AVAILABILITY LABELS -->
                    
                <%
                    // determine the available services for
                    // this route
                    if (route != null) {
                        
                        %>
                        <span class="text-muted"><b>Availability: </b></span><br/>
                        <%
                    
                        if (route.isWeekdayService()) {
                            %> <label class="label label-success" onclick="changeServiceType('weekday')">MON - FRI</label> <%
                        }

                        if (route.isSaturdayService()) {
                            %> <label class="label label-warning" onclick="changeServiceType('saturday')">SATURDAY</label> <%
                        }

                        if (route.isSundayService()) {
                            %> <label class="label label-danger" onclick="changeServiceType('sunday')">SUNDAY</label> <%
                        }
                        
                        %>
                            <br/><br/>
                            <span class="text-muted"><strong>More bus route options:</strong></span><br/>
                            <div style="margin-top: 8px; width: 100%">
                                <label class="label label-default" style="margin: 5px; padding: 5px; float: left " 
                                       onclick="openRouteDescription('<%= route.getRouteNumber() %>')">
                                    ROUTE DESCRIPTION
                                </label>
                                <label class="label label-default" style="margin: 5px; padding: 5px; float: left" 
                                       onclick="openRouteBusStops('<%= route.getRouteNumber() %>')">
                                    BUS STOPS
                                </label>
                                <label class="label label-default" style="margin: 5px; padding: 5px; float: left" 
                                       onclick="openRouteAreaCoverage('<%= route.getRouteNumber() %>')">
                                    AREA COVERAGE
                                </label>
                            </div>
                            <br/>
                            
                        <%
                    }
                %>

                <br/>
                <hr/>
                
                
                <%
                    if (form.getLocations() != null &&
                            form.getLocations().isEmpty() == false) {
                        %>
                            <input type="hidden" id="serviceType" name="serviceType" value="<%= serviceParam %>"/>

                            <label
                                   for="locations">Show only buses from:</label><br/>
                            <select name="from" id="locations" class="form-control"
                                    onchange="document.getElementById('timesTableForm').submit()">
                                <option value="0">ALL DEPARTURE LOCATIONS</option>
                        <%

                        for (Location location : form.getLocations()) {
                            %>
                                <option value="<%= location.getLocationId() %>"
                                        <%= (form.getSearchLocationId() == location.getLocationId()) ?
                                                "selected='selected'" : "" %>>
                                    <%= location.getFullName() %>
                                </option>
                            <%
                        }

                        %>
                        </select>
                        <hr/>
                        <%
                    }
                %>

                    
                    
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
                        <th>Departure Time</th>
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

                            <tr id="departureItem<%= departure.getBusDepartureId() %>" <%= ((departure.isNextDeparture()) ?
                                    "class='text-primary' style='font-weight: bold; font-size: 110%'" : "") %>>
                                <td style="font-size: 120%"><%= time %></td>
                                <td><%= departsFrom %></td>
                           
                           <%

                           for (BusArrival arrival : departure.getBusArrivals()) {

                               if (arrival.isViaLocation())
                                   arrivals.append("<b>via</b> ")
                                          .append(arrival.getArrivalLocation().getShortName())
                                          .append("</br>");
                               if (arrival.isTurnAroundLocation())
                                   arrivals.append("<b>to</b> ")
                                          .append(arrival.getArrivalLocation().getShortName())
                                          .append("</br>");
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
                
                
                <% } %>
                
                </form>
            </div>

        </div>       
    </body>
</html>
