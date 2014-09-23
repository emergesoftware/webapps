<%@page import="java.util.ArrayList"%>
<%@page import="za.co.metrobus.hibernate.entity.BusRoute"%>
<%@page import="java.util.List"%>
<%@page import="com.emergelets.metrobus.mobisite.component.TimesTableForm"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    TimesTableForm form = (TimesTableForm)session.getAttribute(TimesTableForm.class.getName());
    if (form == null)
        form = new TimesTableForm();
    
    List<BusRoute> routes = form.getRoutes();
    if (routes == null)
        routes = new ArrayList<BusRoute>();
    
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        
        <%@include file="../jspf/templating/header.jspf" %>
        
        <script type="text/javascript"
                src="<%= request.getContextPath() %>/resources/javascript/time-tables-detour.js">
        </script>
        
    </head>

    <body>

        <!-- wrap the page navigation here -->
        <%@include file="../jspf/templating/responsive-navigation-bar.jspf" %>

        <!--wrap the page content do not style this-->
        <div id="page-content">
            <div class="container" >
                
                <br/><br/>
                
                <ul class="nav nav-tabs">
                    <li class="active"><a href="#firstTab" data-toggle="tab">Browse routes</a></li>
                    <li><a href="#secondTab" data-toggle="tab">I know the bus route</a></li>
                </ul>
                
                <div id="tabContent" class="tab-content">
                    
                    <div class="tab-pane fade active in" id="firstTab">
                        
                        <form id="browseRoutesForm" role="form">
                    
                                <h4>Browse the bus routes:</h4>
                                <p class="text-muted">Select your preferred bus route below:</p>

                                <input type="text" class="form-control" id="filterBusRoutes"
                                       placeholder="Type here to filter the bus routes" 
                                       onkeyup="filterAvailableBusRoutesList(this)" 
                                       autocomplete="off"/>
                                <br/>

                                <div id="availableBusRoutesListGroup" class="list-group">

                                    <%
                                        for (BusRoute route : routes) {

                                            String link = request.getContextPath() + 
                                                    "/time-tables/find-by-bus-route?"
                                                    + "routeNumber=" + route.getRouteNumber();
                                    %>

                                    <a id="busRouteItem<%= route.getRouteNumber() %>" 
                                       href="<%= link %>" class="list-group-item" style="font-weight: bold">
                                        <span class="label label-primary"><%= route.getRouteNumber() %></span>
                                        <span>&nbsp;</span>
                                        <span><%= route.getRouteDescription() %></span>
                                    </a>
                                    <%
                                        }
                                    %>
                                </div>
                                <br/>
                            </form>
                        
                    </div>
                    
                    <div class="tab-pane fade" id="secondTab">
                        
                        <form role="form" method="get" id="routeNumberForm"
                            action="<%= request.getContextPath() %>/time-tables/find-by-bus-route"
                            onsubmit="return checkBusRouteNumberForm()">

                          <h4>I know the bus route number:</h4>

                          <p class="text-muted">If you know the bus route number,
                              then enter it below:</p>

                          <div id="routeNumberFormGroup" class="form-group">

                              <input type="search" placeholder="Enter Bus Route No." 
                                     name="routeNumber" maxlength="4"
                                     class="form-control" id="routeNumber"
                                     autocomplete="off" />
                              <br/>
                              <p id="routeNumberError" class="text-danger" style="display: none">
                                  <span class="glyphicon glyphicon-warning-sign"></span>
                                  <span> Please provide the bus route number first.</span>
                              </p>

                          </div>

                          <div class="form-group">
                              <input type="submit" class="btn btn-primary form-control" 
                                     value="Show Time Table"
                                     name="requestTimesTableByRouteNumber"
                                     ondblclick="return false" />
                          </div>
                      </form>

                        
                    </div>
                    
                </div>
               
            </div>

        </div>      
                                
        <%@include file="../jspf/templating/default-footer.jspf" %>
        
    </body>
</html>
