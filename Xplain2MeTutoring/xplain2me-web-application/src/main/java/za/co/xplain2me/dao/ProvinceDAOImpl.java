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
import za.co.xplain2me.entity.Province;

public class ProvinceDAOImpl implements ProvinceDAO {

    private static final Logger LOG = 
            Logger.getLogger(ProvinceDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public ProvinceDAOImpl() {
        super();
    }
    
    @Override
    public List<Province> getAllProvinces() throws DataAccessException {
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Province.class)
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
