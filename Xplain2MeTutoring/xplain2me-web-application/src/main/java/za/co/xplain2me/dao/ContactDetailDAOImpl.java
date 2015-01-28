package za.co.xplain2me.dao;

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
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.ContactDetail;

public class ContactDetailDAOImpl implements ContactDetailDAO {
    
    private static final Logger LOG = 
            Logger.getLogger(ContactDetailDAOImpl.class.getName(), null);
   
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public ContactDetailDAOImpl() {
    }

    @Override
    public ContactDetail getContactDetailById(long contactDetailId) throws DataAccessException {
        
        if (contactDetailId <= 0) {
            LOG.warning(" ... contact detail ID apears wrong ...");
            return null;
        }
        
        ContactDetail contactDetail = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(ContactDetail.class)
                    .add(Restrictions.eq("id", contactDetailId));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                contactDetail = (ContactDetail)iterator.next();
            
            return contactDetail;
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
    public ContactDetail getContactDetailByEmailAddress(String emailAddress) throws DataAccessException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            LOG.warning(" ... email address apears empty ...");
            return null;
        }
        
        ContactDetail contactDetail = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(ContactDetail.class)
                    .add(Restrictions.eq("emailAddress", emailAddress));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                contactDetail = (ContactDetail)iterator.next();
            
            return contactDetail;
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
    public ContactDetail getContactDetailByContactNumber(String contactNumber) throws DataAccessException {
        if (contactNumber == null || contactNumber.isEmpty()) {
            LOG.warning(" ... contact number apears empty ...");
            return null;
        }
        
        ContactDetail contactDetail = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(ContactDetail.class)
                    .add(Restrictions.eq("cellphoneNumber", contactNumber));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                contactDetail = (ContactDetail)iterator.next();
            
            return contactDetail;
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
    public boolean isContactDetailCompletelyUnique(ContactDetail contactDetail, boolean includeInCheck) throws DataAccessException {
        try {
            
            long rowCount = 0;
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            if (includeInCheck == true) {
                criteria = session.createCriteria(ContactDetail.class)
                        .setProjection(Projections.rowCount())
                        .add(Restrictions.or(
                                Restrictions.eq("emailAddress", contactDetail.getCellphoneNumber()), 
                                Restrictions.eq("cellphoneNumber", contactDetail.getCellphoneNumber()))
                        );
            }
            else {
                
                List<Criterion> predicates = new ArrayList<>();
                predicates.add(Restrictions.or(
                        Restrictions.eq("emailAddress", contactDetail.getCellphoneNumber()), 
                        Restrictions.eq("cellphoneNumber", contactDetail.getCellphoneNumber())));
                predicates.add(Restrictions.ne("id", contactDetail.getId()));
                
                criteria = session.createCriteria(ContactDetail.class)
                        .setProjection(Projections.rowCount())
                        .add(Restrictions.and(
                                predicates.toArray(new Criterion[predicates.size()])
                        ));
            }
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                rowCount = (Long)iterator.next();
            
            return (rowCount == 0);
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
    public ContactDetail updateContactDetail(ContactDetail contactDetail) throws DataAccessException {
        
        if (contactDetail == null)
            return null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            transaction = session.beginTransaction();
            session.merge(contactDetail);
            transaction.commit();
            
            return contactDetail;
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
