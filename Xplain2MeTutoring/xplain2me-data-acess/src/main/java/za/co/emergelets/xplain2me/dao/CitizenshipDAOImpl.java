/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import za.co.emergelets.xplain2me.entity.Citizenship;

/**
 *
 * @author user
 */
public class CitizenshipDAOImpl extends DefaultDataAccessObject implements CitizenshipDAO {

    private static Logger LOG = 
            Logger.getLogger(CitizenshipDAOImpl.class.getName(), null);
    
    public CitizenshipDAOImpl(){
        super();
    }
    
    @Override
    public List<Citizenship> getAllCitizenships() throws DataAccessException {
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Citizenship.class);
            criteria.addOrder(Order.asc("id"));
            
            return criteria.list();
        }
        
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
        
    }
    
}
