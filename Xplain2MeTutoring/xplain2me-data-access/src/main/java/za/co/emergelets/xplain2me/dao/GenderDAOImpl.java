package za.co.emergelets.xplain2me.dao;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import za.co.emergelets.xplain2me.entity.Gender;

public class GenderDAOImpl implements GenderDAO {

    private static final Logger LOG = Logger
            .getLogger(GenderDAOImpl.class.getName(), null);
   
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public GenderDAOImpl() {
        super();
    }
    
    @Override
    public List<Gender> getAllGender() throws DataAccessException {
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            criteria = session.createCriteria(Gender.class)
                .addOrder(Order.asc("id"));
            return criteria.list();
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }
    
}
