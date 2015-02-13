package za.co.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class StaticContentController extends GenericController implements Serializable {
    
    // the logger
    private static final Logger LOG = 
            Logger.getLogger(StaticContentController.class.getName(), null);
    
    /**
     * Constructor
     */
    public StaticContentController() {
        
    }
    
    /**
     * The HOME page
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/welcome", method = RequestMethod.GET)
    public ModelAndView welcomePage(HttpServletRequest request) {
        LOG.info("... delivering the home page ...");
        return createModelAndView("static/index");
    }
    
    /**
     * The ABOUT page
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView aboutUsPage(HttpServletRequest request) {
        LOG.info(" .. delivering the about us page ...");
        return createModelAndView("static/about");
    }
}
