package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.ProfileType;
import za.co.emergelets.xplain2me.entity.ProfileTypeUrlPermissions;

public class ProfileTypeUrlPermissionsDAOImpl implements ProfileTypeUrlPermissionsDAO {

    private static final Logger LOG = 
            Logger.getLogger(ProfileTypeUrlPermissionsDAOImpl.class.getName(), null);
    
    private Session session;
    private Criteria criteria;
    
    public ProfileTypeUrlPermissionsDAOImpl() {
    }
    
    @Override
    public List<ProfileTypeUrlPermissions> getUserProfileUrlPermissions(ProfileType profileType) 
            throws DataAccessException {
        
        if (profileType == null) {
            LOG.warning("... profile type is NULL ...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider
                    .getSessionFactory().openSession();
            
            criteria = session.createCriteria(ProfileTypeUrlPermissions.class, "permissions")
                    .createAlias("permissions.profileType", "profileType")
                    .createAlias("permissions.menuItem", "menuItem")
                    .add(Restrictions.eq("profileType.id", profileType.getId()))
                    .addOrder(Order.asc("menuItem.id"));
            
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
