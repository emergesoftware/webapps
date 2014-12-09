package za.co.emergelets.xplain2me.dao;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Profile;

public class ProfileDAOImpl implements ProfileDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(ProfileDAOImpl.class.getName(), null);

    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public ProfileDAOImpl() {
        super();
    }
    
    @Override
    public Profile authenticateProfile(String username, String password) throws DataAccessException {
        
        if ((username == null || username.isEmpty()) || 
                (password == null || password.isEmpty()))
            return null;
        
        Profile profile = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Profile.class, "profile")
                .createAlias("profile.profileType", "profileType")
                .createAlias("profile.user", "user")
                .createAlias("user.role", "role")
            
                .add(Restrictions.eq("profile.verified", true))
                .add(Restrictions.eq("profileType.active", true))
                .add(Restrictions.eq("user.active", true))
                .add(Restrictions.eq("role.active", true))
                .add(Restrictions.eq("role.allowedLogin", true))
            
                .add(Restrictions.eq("user.username", username))
                .add(Restrictions.eq("user.password", password));
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                profile = (Profile)iterator.next();
            
            return profile;
        
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
