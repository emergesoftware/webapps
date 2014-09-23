package com.emergelets.metrobus.mobisite.controller;

import com.emergelets.metrobus.mobisite.component.RouteForm;
import com.emergelets.metrobus.mobisite.component.WebPageInfo;
import java.io.Serializable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;

@Controller
@RequestMapping(value = RouteController.MAPPING)
public class RouteController extends GenericController implements Serializable {
    
    private static Logger LOG = Logger.getLogger(RouteController.class.getName(), null);
    
    // the root mapping
    public static final String MAPPING = "/route/*";
    
    // the JSP views
    public static final String ROUTE_DESCRIPTION_PAGE = "route-description";
    public static final String ROUTE_BUS_STOPS_PAGE = "route-bus-stops";
    public static final String ROUTE_AREA_COVERAGE_PAGE = "route-area-coverage";
    
    // data repos
    private BusServiceDAO repo;
    
    // forms
    private RouteForm form;
    
    /**
     * Constructor
     */
    public RouteController() {
        this.repo = new BusServiceDAOImpl();
    }
    
    /**
     * Sets the title of the web page
     * then serialises to the request scope
     * 
     * @param request
     * @param title 
     */
    private void setTitle(HttpServletRequest request, String title) {
        
        // create instance of the web page info object
        webPageInfo = new WebPageInfo();
        webPageInfo.setTitle(title);
        
        // save to request scope
        saveToRequestScope(request, webPageInfo);
        
    }
    
    @RequestMapping(value = "describe/{routeNumber}", method = RequestMethod.GET)
    public ModelAndView displayRouteDescription(HttpServletRequest request,
                        @PathVariable("routeNumber")String routeNumber) {
        
        // check the route number
        if (routeNumber == null || routeNumber.isEmpty()) {
            return sendRedirect("/index?error=yes");
        }
        
        // trim and pad the route number
        routeNumber = routeNumber.trim();
        if (routeNumber.length() == 1) {
            routeNumber = "0" + routeNumber;
        }
        
        // get the form or create if not in
        // the session scope
        form = (RouteForm)getFromSessionScope(request, RouteForm.class);
        if (form == null)
            form = new RouteForm();
        
        // get the route description for this route
        form.setRouteDescriptions(repo.getRouteDescriptions(routeNumber));
        
        // save the form to the session
        // scope
        saveToSessionScope(request, form);
        
        // set the title on the page info
        // and serialise to request scope
        setTitle(request, "Route Description");
        
        return createModelAndView(ROUTE_DESCRIPTION_PAGE);
    }
     
    @RequestMapping(value = "bus-stops/{routeNumber}", method = RequestMethod.GET)
    public ModelAndView displayRouteBusStops(HttpServletRequest request,
            @PathVariable("routeNumber")String routeNumber) {
        
        // check if the route number is not null
        if (routeNumber == null || routeNumber.isEmpty()) {
            LOG.warning("No route number value in the path variable...");
            return sendRedirect("/index?error=yes");
        }
        
        // get the form from session - or
        // recreate if null
        form = (RouteForm)getFromSessionScope(request, RouteForm.class);
        if (form == null)
            form = new RouteForm();
        
        // pad the route number if its digit is one
        routeNumber = routeNumber.trim();
        if (routeNumber.length() == 1)
            routeNumber = "0" + routeNumber;
        
        // pull the data from the repo
        form.setRouteBusStops(repo.getBusStopsByRouteNumber(routeNumber));
        
        // pull the route information
        form.setRoute(repo.getBusRouteByRouteNumber(routeNumber));
        
        // serialise to the session
        saveToSessionScope(request, form);
        
        // set the title
        setTitle(request, "Bus Stops");
        
        return createModelAndView(ROUTE_BUS_STOPS_PAGE);
        
    }
    
    @RequestMapping(value = "area-coverage/{routeNumber}", method = RequestMethod.GET)
    public ModelAndView displayRouteAreaCoverage(HttpServletRequest request, 
            @PathVariable("routeNumber")String routeNumber) {
        
        return createModelAndView(ROUTE_AREA_COVERAGE_PAGE);
        
    }
    
}
