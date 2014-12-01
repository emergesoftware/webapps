package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import za.co.emergelets.xplain2me.entity.Citizenship;

public class CitizenshipDAOImpl extends HibernateConnectionProvider implements CitizenshipDAO {

    private static final Logger LOG = 
            Logger.getLogger(CitizenshipDAOImpl.class.getName(), null);
   
    public CitizenshipDAOImpl(){
        super();
    }
    
    @Override
    public List<Citizenship> getAllCitizenships() throws DataAccessException {
        
        try {
            
            session = getSessionFactory().openSession();
            
            criteria = session.createCriteria(Citizenship.class)
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
