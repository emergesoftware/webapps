package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.AcademicLevel;

public class AcademicLevelDAOImpl extends DefaultDataAccessObject implements AcademicLevelDAO {
    
    private static Logger LOG = 
            Logger.getLogger(AcademicLevelDAOImpl.class.getName(), null);
    
    public AcademicLevelDAOImpl() {
        super();
    }

    @Override
    public AcademicLevel getAcademicLevel(long id) throws DataAccessException {
        
        if (id == 0)
            return null;
        
        AcademicLevel academicLevel = null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(AcademicLevel.class);
            criteria.add(Restrictions.eq("id", id));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext()) {
                academicLevel = (AcademicLevel)iterator.next();
            }
            
            return academicLevel;
            
        }
        
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public List<AcademicLevel> getAllAcademicLevels() throws DataAccessException {
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(AcademicLevel.class);
            criteria.addOrder(Order.asc("id"));
            
            return criteria.list();
            
        }
        
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }
    
    
    
}
