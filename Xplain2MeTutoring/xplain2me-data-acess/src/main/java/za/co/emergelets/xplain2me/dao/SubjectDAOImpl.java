package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Subject;

public class SubjectDAOImpl extends DefaultDataAccessObject implements SubjectDAO {

    private static final Logger LOG = 
            Logger.getLogger(SubjectDAOImpl.class.getName(), null);
    
    public SubjectDAOImpl() {
        super();
    }
    
    @Override
    public Subject getSubject(long id) throws DataAccessException {
        if (id < 100)
            return null;
        
        Subject subject = null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Subject.class);
            criteria.add(Restrictions.eq("id", id));
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
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<Subject> getAllSubjects() throws DataAccessException {
        try {
            
            List<Subject> subjects = null;
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Subject.class);
            criteria.addOrder(Order.asc("name"));
            return criteria.list();
            
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public List<Subject> searchSubjects(String keyword) throws DataAccessException {
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Subject.class);
            criteria.add(Restrictions.ilike("name", keyword, MatchMode.ANYWHERE));
            criteria.addOrder(Order.asc("name"));
            return criteria.list();
            
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }
    
}
