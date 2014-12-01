package za.co.emergelets.xplain2me.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.UserSalt;

public class UserSaltDAOImpl extends HibernateConnectionProvider implements UserSaltDAO {

    private static final Logger LOG = 
            Logger.getLogger(UserSaltDAOImpl.class.getName(), null);
    
    public UserSaltDAOImpl() {
        super();
    }
    
    @Override
    public UserSalt getUserSalt(String username) throws DataAccessException {
        
        if ((username == null || username.isEmpty()))
            return null;
        
        UserSalt salt = null;
        
        try {
            
            session = getSessionFactory().openSession();
            
            criteria = session.createCriteria(UserSalt.class)
                .createAlias("user", "user")
                .add(Restrictions.eq("user.username", username));
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                salt = (UserSalt)iterator.next();
            
            return salt;
        
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeConnection();
        }
    }
    
}
