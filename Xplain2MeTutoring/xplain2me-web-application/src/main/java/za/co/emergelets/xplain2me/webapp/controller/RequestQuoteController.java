package za.co.emergelets.xplain2me.webapp.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAO;
import za.co.emergelets.xplain2me.dao.AcademicLevelDAOImpl;
import za.co.emergelets.xplain2me.dao.ProvinceDAO;
import za.co.emergelets.xplain2me.dao.ProvinceDAOImpl;
import za.co.emergelets.xplain2me.entity.AcademicLevel;
import za.co.emergelets.xplain2me.entity.Province;
import za.co.emergelets.xplain2me.webapp.component.RequestQuoteForm;

@Controller
public class RequestQuoteController extends GenericController implements Serializable {
    
    private static final Logger LOG = Logger.getLogger(
            RequestQuoteController.class.getName(), null);
    
    // data access objects
    private AcademicLevelDAO academicLevelDAO;
    private ProvinceDAO provinceDAO;
    
    public RequestQuoteController() {
        this.academicLevelDAO = new AcademicLevelDAOImpl();
        this.provinceDAO = new ProvinceDAOImpl();
    }
    
    @RequestMapping(value = "/quote-request", method = RequestMethod.GET)
    public ModelAndView displayQuoteRequestPage(HttpServletRequest request) {
        
        // invalidate any existing session
        invalidateCurrentSession(request);
        
        // create the form
        RequestQuoteForm form = new RequestQuoteForm();
        
        // get the academic levels
        List<AcademicLevel> academicLevels = academicLevelDAO.getAllAcademicLevels();
        for (AcademicLevel level : academicLevels) {
            form.getAcademicLevels().put(level.getId(), level);
        }
        
        // get the provinces
        List<Province> provinces = provinceDAO.getAllProvinces();
        for (Province province : provinces) {
            form.getProvinces().put(province.getId(), province);
        }
        
        // save the form to the session
        saveToSessionScope(request, form);
        
        return createModelAndView("quote-request");
        
    }
    
}
