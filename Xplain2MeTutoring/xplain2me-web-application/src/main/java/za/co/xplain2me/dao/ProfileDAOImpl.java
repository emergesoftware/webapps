package za.co.xplain2me.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.data.transformation.DataTransformerTool;
import za.co.xplain2me.entity.Audit;
import za.co.xplain2me.entity.ContactDetail;
import za.co.xplain2me.entity.PhysicalAddress;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.User;
import za.co.xplain2me.entity.UserSalt;
import za.co.xplain2me.model.SearchUserProfileType;

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
    public Profile authenticateProfile(String username, String password) 
            throws DataAccessException {
        
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
    public List<Profile> getUserAuthorisedProfiles(long currentProfileTypeId) 
            throws DataAccessException {
        
        if (currentProfileTypeId < 100 || currentProfileTypeId > Long.MAX_VALUE) {
            LOG.warning("... the value for the current user "
                    + "profile type ID is not valid ..."); 
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(Profile.class, "profile")
                    .createAlias("profile.person", "person")
                    .createAlias("person.user", "user")
                    .createAlias("profile.profileType", "profileType")
                    .add(Restrictions.gt("profileType.id", currentProfileTypeId))
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
    public Profile persistProfile(Profile profile, UserSalt userSalt) 
            throws DataAccessException {
        
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
            
            DataTransformerTool.transformStringValuesToUpperCase(profile
                    .getPerson().getPhysicalAddress());
            DataTransformerTool.transformStringValuesToUpperCase(profile
                    .getPerson().getContactDetail()); 
            DataTransformerTool.transformStringValuesToUpperCase(profile.getPerson());
            
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
    public Profile verifyOwnUserProfile(String identityNumber, String emailAddress, 
            String verificationCode) throws DataAccessException {
        
        if (identityNumber == null || identityNumber.isEmpty() || 
                emailAddress == null || emailAddress.isEmpty() || 
                verificationCode == null || verificationCode.isEmpty()) {
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

    @Override
    public Profile getProfileById(long profileId, long currentProfileTypeId) 
            throws DataAccessException {
        if ((currentProfileTypeId < 100 || currentProfileTypeId > Long.MAX_VALUE) || 
                (profileId < 1 || profileId > Long.MAX_VALUE)) {
            LOG.warning("... either the value for the queried user or current user "
                    + "profile ID is not valid ..."); 
            return null;
        }
        
        Profile profile = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(Profile.class, "profile")
                    .createAlias("profile.profileType", "profileType")
                    .add(Restrictions.and(
                            Restrictions.gt("profileType.id", currentProfileTypeId), 
                            Restrictions.eq("profile.id", profileId))); 
            
            iterator =  criteria.list().iterator();
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
    public Profile mergeProfile(Profile profile) throws DataAccessException {
        
        if (profile == null || profile.getProfileType() == null) {
            LOG.warning(" the profile entity is null "); 
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            transaction = session.beginTransaction();
            session.merge(profile);
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
    public boolean deleteUserProfile(Profile profileToDelete, Profile profilePerformingAction) throws DataAccessException {
        
        if (profileToDelete == null || profilePerformingAction == null) {
            LOG.warning(" ... either the profile to be deleted or the profile "
                    + "performing action is null ...");
            return false;
        }
        
        if (profilePerformingAction.equals(profileToDelete)) {
            LOG.warning(" ... cannot remove the current context profile ... ");
            return false;
        }
        
        if (profilePerformingAction.getProfileType().getId() >= 
                profileToDelete.getProfileType().getId()) {
            LOG.warning(" ... cannot delete this profile ... ");
            return false;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            transaction = session.beginTransaction();
            
            // get the entities
            User user = profileToDelete.getPerson().getUser();
            ContactDetail contact = profileToDelete.getPerson().getContactDetail();
            PhysicalAddress address = profileToDelete.getPerson().getPhysicalAddress();
            
            // remove the password salt
            criteria = session.createCriteria(UserSalt.class, "salt")
                    .createAlias("salt.user", "user")
                    .add(Restrictions.eq("user.username", user.getUsername()));
            
            for (Object salt : criteria.list()) 
                session.delete((UserSalt)salt);
            
            // remove all audits with this user
            criteria = session.createCriteria(Audit.class, "audit")
                    .createAlias("audit.user", "user")
                    .add(Restrictions.eq("user.username", user.getUsername()));
            
            for (Object audit : criteria.list())
                session.delete((Audit)audit);
            
            // remove the profile
            session.delete(profileToDelete);
            
            // remove the person
            session.delete(profileToDelete.getPerson()); 
            
            // remove the contact details
            session.delete(contact);
            
            // remove the physical address
            session.delete(address);
            
            // remove the user
            session.delete(user);
            
            transaction.commit();

            return true;
        }
        
        catch (HibernateException e) {
            HibernateConnectionProvider.rollback(transaction);
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            return false;
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public List<Profile> searchUserProfile(SearchUserProfileType type, Object searchValue, 
            Profile profilePerformingAction) 
            
            throws DataAccessException {
        
        if (type == null || profilePerformingAction == null) {
            LOG.warning(" either the search by type or the profile"
                    + " performing aciton is null ...");
            return null;
        }
     
        try {
            
            long profileTypeId = profilePerformingAction
                    .getProfileType().getId();
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            switch (type) {
                
                case Username:
                    if (searchValue != null && searchValue instanceof String 
                            && !((String)searchValue).isEmpty()) {
                        
                        criteria = session.createCriteria(Profile.class, "profile")
                                .createAlias("profile.profileType", "profileType")
                                .createAlias("profile.person", "person")
                                .createAlias("person.user", "user")
                                .add(Restrictions.and(
                                        Restrictions.eq("user.username", searchValue.toString()),
                                        Restrictions.gt("profileType.id", profileTypeId))); 
                        
                        return criteria.list();
                    }
                
                break;
                
                case IdentityOrPassportNumber:
                    if (searchValue != null && searchValue instanceof String &&
                            !((String)searchValue).isEmpty()) {
                        
                        criteria = session.createCriteria(Profile.class, "profile")
                                .createAlias("profile.profileType", "profileType")
                                .createAlias("profile.person", "person")
                                .add(Restrictions.and(
                                        Restrictions.eq("person.identityNumber", searchValue.toString()), 
                                        Restrictions.gt("profileType.id", profileTypeId)));
                        
                        return criteria.list();
                    }
                    
                    break;
                
                case EmailAddress:
                    if (searchValue != null && searchValue instanceof String &&
                            !((String)searchValue).isEmpty()) {
                        
                        criteria = session.createCriteria(Profile.class, "profile")
                                .createAlias("profile.profileType", "profileType")
                                .createAlias("profile.person", "person")
                                .createAlias("person.contactDetail", "contactDetail")
                                .add(Restrictions.and(
                                        Restrictions.eq("contactDetail.emailAddress", searchValue.toString()),
                                        Restrictions.gt("profileType.id", profileTypeId)));
                         
                        return criteria.list();
                    }
                    
                    break;
                
                case ProfileType:
                    if (searchValue != null && searchValue instanceof Long
                            && ((Long)searchValue) >= 100) {
                        
                        criteria = session.createCriteria(Profile.class, "profile")
                                .createAlias("profile.profileType", "profileType")
                                .add(Restrictions.and(
                                        Restrictions.eq("profileType.id", (Long)searchValue),
                                        Restrictions.gt("profileType.id", profileTypeId))) 
                                .addOrder(Order.desc("profile.id"));
                        
                        return criteria.list();
                    }
                    
                    break;
                
                case ActiveUserProfiles:
                    
                    criteria = session.createCriteria(Profile.class, "profile")
                            .createAlias("profile.profileType", "profileType")
                            .createAlias("profile.person", "person")
                            .createAlias("person.user", "user")
                            .add(Restrictions.and(
                                    Restrictions.eq("user.active", true), 
                                    Restrictions.gt("profileType.id", profileTypeId)))
                            .addOrder(Order.desc("profile.id"));
                    
                    return criteria.list();
                    
                case BlockedUserProfiles:
                    
                    criteria = session.createCriteria(Profile.class, "profile")
                            .createAlias("profile.profileType", "profileType")
                            .createAlias("profile.person", "person")
                            .createAlias("person.user", "user")
                            .add(Restrictions.and(
                                    Restrictions.eq("user.active", false), 
                                    Restrictions.gt("profileType.id", profileTypeId)))
                            .addOrder(Order.desc("profile.id"));
                    
                    return criteria.list();
                    
                case UnverifiedUserProfiles:
                    
                    criteria = session.createCriteria(Profile.class, "profile")
                            .createAlias("profile.profileType", "profileType")
                            .add(Restrictions.and(
                                    Restrictions.eq("profile.verified", false), 
                                    Restrictions.gt("profileType.id", profileTypeId)))
                            .addOrder(Order.desc("profile.id"));
                    
                    return criteria.list();
                    
                case MatchFirstNames:
                    
                    if (searchValue != null && 
                        searchValue instanceof String && 
                        ((String)searchValue).isEmpty() == false) {
                    
                        criteria = session.createCriteria(Profile.class, "profile")
                                .createAlias("profile.profileType", "profileType")
                                .createAlias("profile.person", "person")
                                .add(Restrictions.and(
                                        Restrictions.ilike("person.firstNames", 
                                                searchValue.toString().trim().toLowerCase(),
                                                MatchMode.ANYWHERE), 
                                        Restrictions.gt("profileType.id", profileTypeId)))
                                .addOrder(Order.asc("person.firstNames"));
                        
                        return criteria.list();
                    }
                    
                    break;
            }
            
            return null;
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
