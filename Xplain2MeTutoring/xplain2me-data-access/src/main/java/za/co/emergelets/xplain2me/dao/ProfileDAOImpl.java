package za.co.emergelets.xplain2me.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Profile;
import za.co.emergelets.xplain2me.entity.UserSalt;

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
                .createAlias("profile.person", "person")
                .createAlias("person.user", "user")
                    
                .add(Restrictions.eq("profile.verified", true))
                .add(Restrictions.eq("profileType.active", true))
                .add(Restrictions.eq("user.active", true))
            
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

    @Override
    public List<Profile> getUserAuthorisedProfiles(long currentUserProfileId) throws DataAccessException {
        
        if (currentUserProfileId < 1 || currentUserProfileId > Long.MAX_VALUE) {
            LOG.warning("... the value for the current user "
                    + "profile ID is not valid ..."); 
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(Profile.class, "profile")
                    .createAlias("profile.person", "person")
                    .createAlias("person.user", "user")
                    .add(Restrictions.gt("profile.id", currentUserProfileId))
                    .add(Restrictions.eq("profile.verified", true))
                    .add(Restrictions.eq("user.active", true))
                    .addOrder(Order.asc("user.id"));
            
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
    public Profile persistProfile(Profile profile, UserSalt userSalt) throws DataAccessException {
        
        if (profile == null || profile.getProfileType() == null || 
                profile.getPerson() == null || 
                profile.getPerson().getUser() == null || 
                profile.getPerson().getPhysicalAddress() == null || 
                profile.getPerson().getContactDetail() == null || 
                userSalt == null || 
                userSalt.getUser() == null) {
            
            LOG.warning(" ... all entities for persistence were not found - some are null ...");
            return null;
            
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            transaction = session.beginTransaction();
            
            session.save(profile.getPerson().getUser());
            session.save(userSalt);
            
            session.save(profile.getPerson().getPhysicalAddress());
            session.save(profile.getPerson().getContactDetail());
            
            session.save(profile.getPerson());
            session.save(profile);
            
            transaction.commit();
            
            return profile;
        }
        
        catch (HibernateException e) {
            HibernateConnectionProvider.rollback(transaction);
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public Profile verifyOwnUserProfile(String identityNumber, String emailAddress, long verificationCode) throws DataAccessException {
        
        if (identityNumber == null || identityNumber.isEmpty() || 
                emailAddress == null || emailAddress.isEmpty() || 
                verificationCode < 1000) {
            LOG.warning(" ... incorrect parameters found ...");
            return null;
        }
        
        Profile profile = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            transaction = session.beginTransaction();
            
            // get the profile that contains the ID number,
            // the email address and the verification code
            
            List<Criterion> predicates = new ArrayList<>();
            predicates.add(Restrictions.eq("profile.verificationCode", verificationCode));
            predicates.add(Restrictions.eq("person.identityNumber", identityNumber.toUpperCase()));
            predicates.add(Restrictions.eq("contactDetail.emailAddress", emailAddress.toUpperCase()));
            predicates.add(Restrictions.eq("profile.verified", false));
            
            criteria = session.createCriteria(Profile.class, "profile")
                    .createAlias("profile.person", "person")
                    .createAlias("person.contactDetail", "contactDetail")
                    .add(Restrictions.and(predicates.toArray(
                            new Criterion[predicates.size()])));
            
            iterator = criteria.list().iterator();
            while (iterator.hasNext())
                profile = (Profile)iterator.next();
            
            if (profile == null) 
                throw new DataAccessException("The profile does not exist or has been "
                        + "verified before.");
            
            // update the profile account to verified
            // and the user to being active.
            profile.setVerified(true);
            profile.getPerson().getUser().setActive(true);
            
            session.merge(profile.getPerson().getUser());
            session.merge(profile);
            
            transaction.commit();
            
            return profile;
        }
        
        catch (HibernateException e) {
            HibernateConnectionProvider.rollback(transaction);
            
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            return null;
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
        
    }
    
}
