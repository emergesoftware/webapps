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
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.AcademicLevel;

public class AcademicLevelDAOImpl implements AcademicLevelDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(AcademicLevelDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public AcademicLevelDAOImpl() {
    }
    
    @Override
    public AcademicLevel getAcademicLevel(long id) throws DataAccessException {
        
        if (id == 0)
            return null;
        
        AcademicLevel academicLevel = null;
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(AcademicLevel.class)
                .add(Restrictions.eq("id", id));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext()) {
                academicLevel = (AcademicLevel)iterator.next();
            }
            
            return academicLevel;
            
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
    public List<AcademicLevel> getAllAcademicLevels() throws DataAccessException {
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(AcademicLevel.class)
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
