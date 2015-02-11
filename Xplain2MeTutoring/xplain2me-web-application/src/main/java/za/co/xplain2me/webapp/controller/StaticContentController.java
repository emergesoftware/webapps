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
        return sendRedirect("/web-app/index.html");
    }
    
    /**
     * The ABOUT US page
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/about-us", method = RequestMethod.GET)
    public ModelAndView aboutUsPage(HttpServletRequest request) {
        return sendRedirect("/web-app/index.html#about");
    }
    
    /**
     * The CONTACT US page
     * @param request
     * @return 
     */
    @RequestMapping(value = "/contact-us", method = RequestMethod.GET)
    public ModelAndView contactUsPage(HttpServletRequest request) {
        return sendRedirect("/web-app/index.html#contact");
    }
    
    /**
     * The RATES page
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/our-rates", method = RequestMethod.GET)
    public ModelAndView ratesPage(HttpServletRequest request) {
        return sendRedirect("/web-app/index.html#about");
    }
    
    /**
     * The SUBJECTS page 
     * 
     * @param request
     * @return 
     */
    @RequestMapping(value = "/subjects-offered", method = RequestMethod.GET)
    public ModelAndView subjectsPage(HttpServletRequest request) {
        return sendRedirect("/web-app/index.html#subjects");
    }
}
