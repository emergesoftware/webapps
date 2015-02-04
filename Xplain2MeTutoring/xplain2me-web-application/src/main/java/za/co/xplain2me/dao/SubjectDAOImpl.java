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
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.Subject;

public class SubjectDAOImpl implements SubjectDAO {

    private static final Logger LOG = 
            Logger.getLogger(SubjectDAOImpl.class.getName(), null);
   
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public SubjectDAOImpl() {
        super();
    }
    
    @Override
    public Subject getSubject(long id) throws DataAccessException {
        if (id < 100)
            return null;
        
        Subject subject = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Subject.class)
                .add(Restrictions.eq("id", id));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                subject = (Subject)iterator.next();
        
            return subject;
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
    public List<Subject> getAllSubjects() throws DataAccessException {
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Subject.class)
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
    public List<Subject> searchSubjects(String keyword) throws DataAccessException {
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Subject.class)
                .add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE))
                .addOrder(Order.asc("name"));
            
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
