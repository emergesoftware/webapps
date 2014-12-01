package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import za.co.emergelets.xplain2me.entity.Gender;

public class GenderDAOImpl extends HibernateConnectionProvider implements GenderDAO {

    private static final Logger LOG = Logger
            .getLogger(GenderDAOImpl.class.getName(), null);
   
    public GenderDAOImpl() {
        super();
    }
    
    @Override
    public List<Gender> getAllGender() throws DataAccessException {
        
        try {
            
            session = getSessionFactory().openSession(); 
            
            criteria = session.createCriteria(Gender.class)
                .addOrder(Order.asc("id"));
            return criteria.list();
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            closeConnection();
        }
    }
    
}
