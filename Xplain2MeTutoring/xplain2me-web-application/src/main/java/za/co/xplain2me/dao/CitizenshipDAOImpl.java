package za.co.xplain2me.dao;

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
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.Citizenship;

public class CitizenshipDAOImpl implements CitizenshipDAO {

    private static final Logger LOG = 
            Logger.getLogger(CitizenshipDAOImpl.class.getName(), null);
   
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public CitizenshipDAOImpl(){
    }
    
    @Override
    public List<Citizenship> getAllCitizenships() throws DataAccessException {
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Citizenship.class)
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

    @Override
    public Citizenship getCitizenship(long id) throws DataAccessException {
        
        if (id < 1 || id > Long.MAX_VALUE) {
            LOG.warning(" ... invalid value for ID ...");
            return null;
        }
        
        Citizenship citizenship = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            criteria = session.createCriteria(Citizenship.class)
                    .add(Restrictions.eq("id", id));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                citizenship = (Citizenship)iterator.next();
            
            return citizenship;
            
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
