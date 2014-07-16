/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.app.webservice;

import java.util.List;
import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import za.co.inkunzi.hibernate.dao.RiskProfileQuestionsDao;
import za.co.inkunzi.hibernate.entity.RiskProfileQuestionItem;

/**
 *
 * @author user
 */
@WebService(serviceName = "RiskProfileWebService")
public class RiskProfileWebService {
    
    /**
     * Web service operation
     * @param questionNumber
     * @return 
     * @throws java.lang.IllegalAccessException 
     */
    @WebMethod(operationName = "getRiskProfileQuestions")
    public List<RiskProfileQuestionItem> getRiskProfileQuestions(
            @WebParam(name = "questionNumber")Integer questionNumber) throws IllegalAccessException {
        
        List<RiskProfileQuestionItem> list = null;
        RiskProfileQuestionsDao dao = new RiskProfileQuestionsDao();
        list = dao.getRiskProfileQuestionItems(questionNumber);
        
        return list;
    }
}
