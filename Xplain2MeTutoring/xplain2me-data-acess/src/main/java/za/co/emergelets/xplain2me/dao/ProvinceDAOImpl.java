/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import za.co.emergelets.xplain2me.entity.Province;

/**
 *
 * @author user
 */
public class ProvinceDAOImpl extends DefaultDataAccessObject implements ProvinceDAO {

    private static final Logger LOG = 
            Logger.getLogger(ProvinceDAOImpl.class.getName(), null);
    
    public ProvinceDAOImpl() {
        super();
    }
    
    @Override
    public List<Province> getAllProvinces() throws DataAccessException {
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Province.class);
            criteria.addOrder(Order.asc("id"));
            return criteria.list();
            
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
    }
    
}
