/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.web.servlet.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import za.co.inkunzi.app.webservice.RiskProfileWebService;
import za.co.inkunzi.web.form.RiskProfileForm;

@Controller
@Scope("session")
@RequestMapping(value = RiskProfileAnalyserController.MAPPING)
public class RiskProfileAnalyserController extends GenericController {
    
    // the main mapping
    public static final String MAPPING = "/client/riskProfile.htm";
    
    // the JSP views
    public static final String RISK_PROFILE_ANALYSIS_PAGE = "riskProfileAnalysis";
    public static final String RISK_PROFILE_ANALYSIS_SUBMITTED_PAGE = "riskProfileSubmitted";
    
    // form data
    private RiskProfileForm form;
    
    // the web services
    private RiskProfileWebService webService;
    
    /**
     * Constructor
     */
    public RiskProfileAnalyserController() {
        this.form = null;
        this.webService = null;
    }
    
    /**
     * Simply returns the risk profile analysis page
     * 
     * @param request
     * @param model
     * @return 
     */
    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView displayRiskProfileAnalysisPage(HttpServletRequest request, 
            Model model) {
        
        // check if the session has already
        // been created
        form = (RiskProfileForm)
                getFromSession(request, RiskProfileForm.class);
        if (form == null) {
            
            form = new RiskProfileForm();
            try {
                webService = new RiskProfileWebService();
                form.setQuestions((ArrayList)webService.getRiskProfileQuestions(0));
            }
            
            catch (Exception e) {
            }
            
        }
        
        // add the object to the session
        saveToSession(request, form);
         
        return createModelAndView(RISK_PROFILE_ANALYSIS_PAGE);
    }
    
    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView submitRiskProfileAnalysis(HttpServletRequest request) {
        return createModelAndView(RISK_PROFILE_ANALYSIS_SUBMITTED_PAGE);
    }
    
}
