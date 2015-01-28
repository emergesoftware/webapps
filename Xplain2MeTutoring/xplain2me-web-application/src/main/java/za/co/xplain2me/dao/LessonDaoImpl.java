package za.co.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import za.co.xplain2me.entity.LessonStatus;

public class LessonDaoImpl implements LessonDao {
    
    private static final Logger LOG = 
            Logger.getLogger(LessonDaoImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    
    public LessonDaoImpl() {
    }

    @Override
    public List<LessonStatus> getAllLessonStatuses() throws DataAccessException {
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(LessonStatus.class)
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
