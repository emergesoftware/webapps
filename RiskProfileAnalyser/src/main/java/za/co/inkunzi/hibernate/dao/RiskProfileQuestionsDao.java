/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.inkunzi.hibernate.entity.RiskProfileQuestionItem;

/**
 *
 * @author user
 */
public class RiskProfileQuestionsDao extends DefaultFactory {
    
    public RiskProfileQuestionsDao() throws IllegalAccessException{
        
        super();
        
        addAnnotatedClass(RiskProfileQuestionItem.class);
 
        constructSessionFactory();
    }
    
    /**
     * Gets a list of the risk profile questions
     * 
     * @param questionNumber
     * @return 
     */
    public List<RiskProfileQuestionItem> getRiskProfileQuestionItems(int questionNumber) {
        
        List<RiskProfileQuestionItem> list = null;
        
        openSession();
        
        criteria = session.createCriteria(RiskProfileQuestionItem.class);
        
        if (questionNumber > 0)
            criteria.add(Restrictions.eq("riskProfileQuestionNumber", questionNumber));
        
        criteria.addOrder(Order.asc("riskProfileQuestionNumber"));
        
        Iterator iterator = criteria.list().iterator();
        list = new ArrayList<RiskProfileQuestionItem>();
        
        while (iterator.hasNext()) {
            Object[] object = (Object[])iterator.next();
            list.add((RiskProfileQuestionItem)object[0]);
        }
        
        return list;
        
    }
    
}
