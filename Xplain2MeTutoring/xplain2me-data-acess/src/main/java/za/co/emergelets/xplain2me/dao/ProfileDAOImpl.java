package za.co.emergelets.xplain2me.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Profile;

public class ProfileDAOImpl extends DefaultDataAccessObject implements ProfileDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(ProfileDAOImpl.class.getName(), null);
    
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
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Profile.class, "profile");
            criteria.createAlias("profile.profileType", "profileType");
            criteria.createAlias("profile.user", "user");
            criteria.createAlias("user.role", "role");
            
            criteria.add(Restrictions.eq("profile.verified", true));
            criteria.add(Restrictions.eq("profileType.active", true));
            criteria.add(Restrictions.eq("user.active", true));
            criteria.add(Restrictions.eq("role.active", true));
            criteria.add(Restrictions.eq("role.allowedLogin", true));
            
            criteria.add(Restrictions.eq("user.username", username));
            criteria.add(Restrictions.eq("user.password", password));
            
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
            DataRepositoryUtility.close();
        }
        
    }
    
}
