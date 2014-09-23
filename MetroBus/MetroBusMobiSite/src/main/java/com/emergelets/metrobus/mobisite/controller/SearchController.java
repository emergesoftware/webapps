package com.emergelets.metrobus.mobisite.controller;

import com.emergelets.metrobus.mobisite.component.SearchForm;
import com.emergelets.metrobus.mobisite.component.WebPageInfo;
import java.io.Serializable;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;

@Controller
@RequestMapping(value = SearchController.MAPPING)
public class SearchController extends GenericController implements Serializable {
    
    // the root request mapping
    public static final String MAPPING = "/search/*";
    
    // the JSP views
    public static final String SEARCH_BUS_ROUTES_MAIN_PAGE = "search-bus-route-main";
    public static final String SEARCH_BUS_ROUTES_RESULTS_PAGE = "search-bus-route-results";
   
    // the controller form
    private SearchForm searchForm;
    
    // data repository object
    private BusServiceDAO repo;
    
    /**
     * Default constructor
     */
    public SearchController() {
        
        // instantiate the bus service data repo
        this.repo = new BusServiceDAOImpl();
        
    }
    
    /**
     * Displays the search main page
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "find-available-routes", method = RequestMethod.GET)
    public ModelAndView displayMainSearchPage(HttpServletRequest request) {
        
        // get the search form from the session
        searchForm = (SearchForm)getFromSessionScope(request, SearchForm.class);
        if (searchForm == null)
            searchForm = new SearchForm();
        
        // reset if no errors
        searchForm.setErrors(null);
        
        // save the form to the session
        // scope
        saveToSessionScope(request, searchForm);
        
        // set the web page info
        webPageInfo = new WebPageInfo();
        webPageInfo.setTitle("Search Available Routes");
        
        // add the web page info to the
        // request scope
        saveToRequestScope(request, webPageInfo);
        
        return createModelAndView(SEARCH_BUS_ROUTES_MAIN_PAGE);
        
    }
    
    /**
     * Receives and then processes the search form request
     * and sends a redirect to another control method
     * to display the results
     * 
     * @param request
     * @param submitSearchRequest
     * @return 
     */
    @RequestMapping(value = "retrieve-results", method = RequestMethod.POST, 
            params = {"submitSearchRequest"})
    public ModelAndView processSearchForAvailableRoutesRequest(
            HttpServletRequest request,
            @RequestParam("submitSearchRequest")String submitSearchRequest) {
        
        // get the search form from the session
        searchForm = (SearchForm)getFromSessionScope(request, SearchForm.class);
        if (searchForm == null)
            searchForm = new SearchForm();
        
        // gather the required parameters
        boolean providedAtLeastOneSearchParameter = true;
        String to = getParameterValue(request, "to");
        
        // check for all required parameters
        if ((to == null || to.isEmpty())) {
            
            providedAtLeastOneSearchParameter = false;
            searchForm.setErrors("Please provide all "
                    + "search fields below.");
        }
        
        // if not all required parameters are missing,
        // send a redirect back to the main page
        if (submitSearchRequest == null || 
                providedAtLeastOneSearchParameter == false) {
            
            saveToSessionScope(request, searchForm);
            return sendRedirect("/search/find-available-routes");
        }
        
        // save the search query
        searchForm.setSearchQuery(to.toUpperCase().trim());
        
        // search the repository for bus routes 
        // according to the given criteria
        if (to != null && !to.isEmpty()) {
            searchForm.setBusStops(repo.getBusRoutesGoingTo(to));
        }
        
        // serialise to the session scope
        saveToSessionScope(request, searchForm);
        
        // redirect
        return sendRedirect("/search/show-search-results");
    }
    
    /**
     * Displays the processed search results
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "show-search-results", method = RequestMethod.GET)
    public ModelAndView displaySearchResultsPage(HttpServletRequest request) {
        
        // check if there is a session
        // and if found - check if there was
        // any search performed
        
        searchForm = (SearchForm)
                getFromSessionScope(request, SearchForm.class);
        
        if (searchForm == null ||
                searchForm.getBusStops() == null) {
            return sendRedirect("/search/find-available-routes");
        }
        
        // set the web page info
        webPageInfo = new WebPageInfo();
        webPageInfo.setTitle("Search Results");
        
        // add the web page info to the
        // request scope
        saveToRequestScope(request, webPageInfo);
        
        return createModelAndView(SEARCH_BUS_ROUTES_RESULTS_PAGE);
    }
    
    /**
     * Checks if the list supplied as the 
     * parameter is:
     * i. Not empty and not null
     * ii. None of the elements are null.
     * 
     * @param list
     * @return 
     */
    private boolean checkList(List<?> list) {
        
        if (list == null || list.isEmpty()) 
            return false;
        else {
            for (Object object : list) {
                if (object == null) return false;
            }
        }
        
        return true;
        
    }
    
}
