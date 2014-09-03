package com.emergelets.metrobus.mobisite.controller;

import com.emergelets.metrobus.mobisite.component.TimesTableForm;
import com.emergelets.metrobus.mobisite.component.WebPageInfo;
import com.emergelets.metrobus.util.Utils;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;
import za.co.metrobus.hibernate.entity.BusArrival;
import za.co.metrobus.hibernate.entity.BusDeparture;
import za.co.metrobus.hibernate.entity.BusServiceType;

/**
 *
 * @author tsepo maleka
 */
@Controller
@RequestMapping(TimeTablesController.MAPPING)
public class TimeTablesController extends GenericController 
                                  implements Serializable {

    private static final Logger LOG =
            Logger.getLogger(TimeTablesController.class.getName(), null);

    // the number of milliseconds in a day
    private static final long MILLISECONDS_IN_DAY = 86400000;

    // ------------- start URL MAPPINGS -------------------
    public static final String MAPPING = "/time-tables/*";

    // ------------- end URL MAPPINGS ---------------------

    // ------------- start JSP VIEWS ----------------------
    public static final String TIMES_TABLE_DETOUR_PAGE = "time-tables-select-option";
    public static final String DISPLAY_SINGLE_SERVICE_TIMES_TABLE_PAGE = "bus-route-time-table";
    // --------------- end JSP VIEWS ------------------------

    // the forms + data access objects
    private TimesTableForm form;
    private BusServiceDAO data;
    
    /**
     * Constructor
     */
    public TimeTablesController() {
        this.form = new TimesTableForm();
        this.data = new BusServiceDAOImpl();
    }


    @RequestMapping(value = "select-option", method = RequestMethod.GET)
    public ModelAndView displayDetourPage(HttpServletRequest request) {

        // check the form data
        form = (TimesTableForm)getFromSessionScope(request, TimesTableForm.class);
        if (form == null)
            form = new TimesTableForm();
        
        // get the list of all
        // available bus routes
        form.setRoutes(data.getAllBusRoutes(null));
        
        // save the form to the session
        saveToSessionScope(request, form);
        
        // set the title of the page
        webPageInfo = new WebPageInfo();
        webPageInfo.setTitle("Time Tables - Select Option");

        // serialise the web page details to the request
        saveToRequestScope(request, webPageInfo);

        return createModelAndView(TIMES_TABLE_DETOUR_PAGE);
    }

    @RequestMapping(value = "find-by-bus-route", method = RequestMethod.GET)
    public ModelAndView processBusRouteTimeTableRequest(HttpServletRequest request,
                     @RequestParam(value = "routeNumber") String routeNumber) {
        
        // check for the route number first
        if (routeNumber == null || routeNumber.isEmpty()) {
            LOG.log(Level.WARNING, "No route number was specified");
            return sendRedirect("/time-tables/detour");
        }

        // fix the route number to pad a zero if its length is one
        if (routeNumber.trim().length() == 1) {
            routeNumber = "0" + routeNumber.trim();
        }

        // get the day of the week service type
        String serviceType = null;
        BusServiceType type = Utils.getServiceTypeByCurrentDayOfWeek();

        switch (type) {
            case WeekdayService:
                serviceType = "weekday";
                break;
            case SaturdayService:
                serviceType = "saturday";
                break;
            case SundayService:
            case HolidayService:
                serviceType = "sunday";
                break;
        }

        // send a redirect to another url to process the
        // request
        return sendRedirect("/time-tables/bus-route/" +
                routeNumber + "?serviceType=" + serviceType);

    }

    @RequestMapping(value = "bus-route/{routeNumber}",
            method = RequestMethod.GET)
    public ModelAndView displayBusRouteTimeTableRequest(HttpServletRequest request,
                        @PathVariable(value = "routeNumber")String routeNumber) {

        String serviceType = getParameterValue(request, "serviceType");
        
        // check the route number and service type - 
        // neither should be null
        if ((routeNumber == null || routeNumber.isEmpty()) ||
                (serviceType == null || serviceType.isEmpty())) {
            LOG.log(Level.WARNING, "Either route number / service type is not specified.");
            return sendRedirect("/time-tables/detour?error=true");
        }
        
        // check if the from location ID is set
        String locationId = getParameterValue(request, "from");

        // create a new form or
        // get from session scope
        form = (TimesTableForm)getFromSessionScope(request, TimesTableForm.class);
        if (form == null)
            form = new TimesTableForm();
        
        // gather bus route information
        form.setRoute(data.getBusRouteByRouteNumber(routeNumber));

        // resolve the service type
        BusServiceType type  = null;
        if (serviceType.equalsIgnoreCase("weekday"))
            type = BusServiceType.WeekdayService;
        else if (serviceType.equalsIgnoreCase("saturday"))
            type = BusServiceType.SaturdayService;
        else
            type = BusServiceType.SundayService;
        
        form.setServiceType(type);
        
        // determine if the service type
        // being requested is today's service or
        // not
        BusServiceType todaysServiceType = Utils.getServiceTypeByCurrentDayOfWeek();
        if (type == todaysServiceType)
            form.setIsTodaysServiceSearched(true);
        else
            form.setIsTodaysServiceSearched(false);

        // fetch the bus departures - using parameters passed in
        if (locationId == null || Integer.parseInt(locationId) == 0) {
            form.setBusDepartures(data.getBusDeparturesByBusRoute(routeNumber, type));
            form.setSearchLocationId(0);
        }
        
        else {
            int location = Integer.parseInt(locationId);
            form.setBusDepartures(data
                    .getBusDeparturesLeavingFromLocationWithinBusRoute(routeNumber, type, location));
            form.setSearchLocationId(location);
        }
        
        // filter the unique locations in the
        // bus departure
        form.setLocations(data.getDepartureLocationsFromBusRoute(routeNumber));

        // determine the next bus departure from this current
        // moment
        Date date = new Date();
        boolean foundNextDeparture = false;

        if (form.getBusDepartures() != null && !form.getBusDepartures().isEmpty()) {
            for (BusDeparture departure : form.getBusDepartures()) {
                
                // get the first departure item that is next 
                // after the current time
                
                long nextDeparture = departure.getDepartureTime().getTime() % MILLISECONDS_IN_DAY;
                long currentTime = date.getTime() % MILLISECONDS_IN_DAY;
                
                if (nextDeparture > currentTime && foundNextDeparture == false) {

                    foundNextDeparture = true;
                    departure.setNextDeparture(true);
                    
                    // set the departure message

                    StringBuilder arrivals = new StringBuilder();
                    int counter = 0;
                    for (BusArrival arrival : departure.getBusArrivals()) {
                        if (counter == departure.getBusArrivals().size() - 1)
                            arrivals.append(arrival.getArrivalLocation().getShortName());
                        else
                            arrivals.append(arrival.getArrivalLocation().getShortName()).append(", ");

                        counter++;
                    }

                    form.setNextDepartureAlert("Next bus from " +
                         departure.getDepartureLocation().getShortName() + " is leaving at " +
                    new SimpleDateFormat("HH:mm").format(departure.getDepartureTime()) + " " +
                            "stopping at " + arrivals.toString());
                    
                    // determine the minutes left before next
                    // departure
                    form.setSecondsBeforeNextDeparture((int)(((nextDeparture - currentTime)
                            / 1000)));
           
                }

                else {
                    departure.setNextDeparture(false);
                }
            }
        }
        
        // reset values - if no next departure was not found
        if (foundNextDeparture == false) {
            form.setNextDepartureAlert("No more buses for today.");
            form.setSecondsBeforeNextDeparture(-1);
        }

        // save the form to the session
        // scope
        saveToSessionScope(request, form);
        
        // set the page title
        webPageInfo = new WebPageInfo();
        webPageInfo.setTitle("Bus Route - Times Table");
         
        // save the page info to the request scope
        saveToRequestScope(request, webPageInfo);

        // return view
        return createModelAndView(DISPLAY_SINGLE_SERVICE_TIMES_TABLE_PAGE);
    }
    
}
