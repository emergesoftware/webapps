package za.co.xplain2me.dao;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.data.transformation.DataTransformerTool;
import za.co.xplain2me.entity.PhysicalAddress;

public class PhysicalAddressDAOImpl implements PhysicalAddressDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(PhysicalAddressDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    
    public PhysicalAddressDAOImpl(){
    }

    @Override
    public PhysicalAddress getPhysicalAddress(long id) 
            throws DataAccessException {
        
        if (id < 1) {
            LOG.warning(" ... invalid sequence # for physical address ID ...");
            return null;
        }
        
        PhysicalAddress address = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(PhysicalAddress.class)
                    .add(Restrictions.eq("id", id));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                address = (PhysicalAddress)iterator.next();
            
            return address;
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
    public PhysicalAddress updatePhysicalAddress(PhysicalAddress address) throws DataAccessException {
        
        if (address == null || 
                address.getId() < 1) {
            LOG.warning(" ... entity null or invalid sequence # "
                    + "for physical address ID ...");
            return null;
        }
        
        try {
            
            DataTransformerTool
                    .transformStringValuesToUpperCase(address);
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            transaction = session.beginTransaction();
            session.merge(address);
            transaction.commit();
            
            return address;
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
    
    
 }
