package za.co.xplain2me.dao;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.User;
import za.co.xplain2me.entity.UserSalt;

public class UserDAOImpl implements UserDAO {

    private static final Logger LOG = 
            Logger.getLogger(UserDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public UserDAOImpl() {
    }
    
    @Override
    public boolean isUsernameUnique(String username) throws DataAccessException {
        if (username == null || username.isEmpty()) {
            LOG.warning(" the username is null or empty - cannot check.");
            return false;
        }
        
        long count = 0;
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory()
                    .openSession();
            
            criteria = session.createCriteria(User.class, "user")
                       .setProjection(Projections.count("user.username"))
                       .add(Restrictions.eq("user.username", username.toLowerCase()));
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                count = (Long)iterator.next();
            
            return (count == 0);
        
        }
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "... error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public User updateUser(User user) throws DataAccessException {
        
        if (user == null) {
            LOG.warning(" the user entity are null ");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory()
                    .openSession();
            transaction = session.beginTransaction();
            //update the user entity
            session.merge(user);
            transaction.commit();
            
            return user;
        
        }
        catch (HibernateException e) {
            HibernateConnectionProvider.rollback(transaction);
            
            LOG.log(Level.SEVERE, "... error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public boolean updateUserPassword(String username, String newPassword, String saltValue) throws DataAccessException {
        if (username == null || newPassword == null || saltValue == null) {
            LOG.warning(" either the username, new password or salt value salt is null ");
            return false;
        }
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory()
                    .openSession();
            transaction = session.beginTransaction();
            
            // get the user first
            User user = null;
            criteria = session.createCriteria(User.class, "user")
                    .add(Restrictions.eq("user.username", username));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                user = (User)iterator.next();
            
            if (user == null) {
                LOG.warning(" no such user could be found - password update failed.");
                HibernateConnectionProvider.rollback(transaction);
                return false;
            }
            
            // get the user's salt value
            UserSalt salt = null;
            criteria = session.createCriteria(UserSalt.class, "salt")
                    .createAlias("salt.user", "user")
                    .add(Restrictions.eq("user.username", username));
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                salt = (UserSalt)iterator.next();
            
            if (salt == null) {
                LOG.warning(" no such user salt could be found - password update failed.");
                HibernateConnectionProvider.rollback(transaction);
                return false;
            }
            
            // set the user's new password
            user.setPassword(newPassword);
            // set the salt value
            salt.setValue(saltValue);
            
            //update the user entity
            session.merge(user);
            // update the password salt entity
            session.merge(salt);
            
            transaction.commit();
            
            return true;
        
        }
        catch (HibernateException e) {
            HibernateConnectionProvider.rollback(transaction);
            
            LOG.log(Level.SEVERE, "... error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }
    
}
