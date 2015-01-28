package za.co.xplain2me.dao;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.ProfileType;

public class ProfileTypeDAOImpl implements ProfileTypeDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(ProfileTypeDAOImpl.class.getName(), null);
    
    public ProfileTypeDAOImpl() {
    }

    @Override
    public List<ProfileType> getAllProfileTypes() throws DataAccessException {
        
        Session session = null;
        Criteria criteria = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(ProfileType.class)
                    .add(Restrictions.eq("active", true))
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
    public ProfileType getProfileTypeById(long id) throws DataAccessException {
        
        if (id < 1 || id > Long.MAX_VALUE) {
            LOG.warning(" ... invalid value for ID ... ");
            return null;
        }
        
        Session session = null;
        Criteria criteria = null;
        Iterator iterator = null;
        
        ProfileType type = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            criteria = session.createCriteria(ProfileType.class)
                    .add(Restrictions.and(
                            Restrictions.eq("id", id), 
                            Restrictions.eq("active", true)));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                type = (ProfileType)iterator.next();
            
            return type;
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
    public List<ProfileType> getProfileTypesAuthorisedByProfile(Profile profile) throws DataAccessException {
        
        if (profile == null || profile.getProfileType() == null) {
            LOG.warning(" .. either the Profile or ProfileType entity is null ...");
            return null;
        }
        
        Session session = null;
        Criteria criteria = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(ProfileType.class)
                    .add(Restrictions.and( 
                            Restrictions.eq("active", true),
                            Restrictions.gt("id", profile.getProfileType().getId())))
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
