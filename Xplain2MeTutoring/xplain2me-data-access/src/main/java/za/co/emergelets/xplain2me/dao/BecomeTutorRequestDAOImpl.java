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
import za.co.emergelets.data.transformation.DataTransformerTool;
import za.co.emergelets.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;

public class BecomeTutorRequestDAOImpl implements BecomeTutorRequestDAO {

    private static final Logger LOG = 
            Logger.getLogger(BecomeTutorRequestDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public BecomeTutorRequestDAOImpl() {
    }
    
    @Override
    public boolean isTutorApplicationUnique(BecomeTutorRequest request) 
            throws DataAccessException {
        
        if (request == null || 
                (request.getContactNumber() == null || request.getContactNumber().isEmpty()) || 
                (request.getEmailAddress() == null || request.getEmailAddress().isEmpty()) || 
                (request.getIdentityNumber() == null || request.getIdentityNumber().isEmpty())) {
            
            LOG.warning("tutor application request is empty");
            return false;
        }
        
        try {

            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            List<Criterion> criterion = new ArrayList<>();
            criterion.add(Restrictions.eq("emailAddress", request.getEmailAddress()));
            criterion.add(Restrictions.eq("contactNumber", request.getContactNumber()));
            criterion.add(Restrictions.eq("identityNumber", request.getIdentityNumber()));
            
            criteria = session.createCriteria(BecomeTutorRequest.class)
                .add(Restrictions.or(criterion.toArray(
                    new Criterion[criterion.size()])));
            
            return criteria.list().isEmpty();
            
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
    public BecomeTutorRequest saveBecomeTutorRequest(BecomeTutorRequest request, 
            List<AcademicLevelsTutoredBefore> academicLevelsTutoredBefore, 
            List<BecomeTutorSupportingDocument> documents) 
            
            throws DataAccessException {
        
        if (request == null) {
            LOG.warning("become a tutor request object is null"); 
            return null;
        }
        
        try {
            
            DataTransformerTool.transformStringValuesToUpperCase(request);
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            Transaction tx = session.beginTransaction();
            
            session.save(request);
            session.persist(request);
            
            if (academicLevelsTutoredBefore != null &&
                    academicLevelsTutoredBefore.isEmpty() == false) {
                
                for (AcademicLevelsTutoredBefore item : academicLevelsTutoredBefore) {
                    
                    if (item == null) continue;
                    
                    session.save(item);
                    session.persist(item);
                }
            }
            
            if (documents != null && documents.isEmpty() == false) {
                
                for (BecomeTutorSupportingDocument item : documents) {
                    
                    if (item == null) continue;
                    
                    session.save(item);
                    session.persist(item);
                }
            }
            
            tx.commit();
            
            return request;
            
        }
        
        catch (HibernateException e) {
            HibernateConnectionProvider.rollback(transaction);
            
            LOG.log(Level.SEVERE, "error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
    }

    @Override
    public List<BecomeTutorRequest> getBecomeTutorRequests(int pageNumber) throws DataAccessException {
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(BecomeTutorRequest.class, "request")
                    .addOrder(Order.desc("request.id"))
                    .setFirstResult((pageNumber - 1) * 10)
                    .setMaxResults(10);
            
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
    public BecomeTutorRequest getBecomeTutorRequest(long id) throws DataAccessException {
        if (id < 1) {
            LOG.warning(" the ID for the become a tutor request is not valid.");
            return null;
        }
        
        BecomeTutorRequest request = null;
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory()
                    .openSession();
            request = (BecomeTutorRequest)session.createCriteria(BecomeTutorRequest.class, "request")
                    .add(Restrictions.eq("request.id", id))
                    .uniqueResult();
            
            return request;
            
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
    public List<BecomeTutorSupportingDocument> getBecomeTutorSupportingDocuments(long requestId) throws DataAccessException {
        if (requestId < 1) {
            LOG.warning("... the request ID does not appear authentic ...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            criteria = session.createCriteria(BecomeTutorSupportingDocument.class, "document")
                    .createAlias("document.request", "request")
                    .add(Restrictions.eq("request.id", requestId))
                    .addOrder(Order.asc("document.id"));
            
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
    public BecomeTutorRequest searchBecomeTutorRequestByIdNumber(String identityNumber) throws DataAccessException {
        if (identityNumber == null || identityNumber.isEmpty()) {
            LOG.warning(" the ID number is not provided ...");
            return null;
        }
        
        BecomeTutorRequest request = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(BecomeTutorRequest.class)
                    .add(Restrictions.eq("identityNumber", identityNumber.toUpperCase()));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (BecomeTutorRequest)iterator.next();
            
            return request;
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
    public BecomeTutorRequest searchBecomeTutorRequestByEmailAddress(String emailAddress) throws DataAccessException {
        if (emailAddress == null || emailAddress.isEmpty()) {
            LOG.warning(" the email address is not provided ...");
            return null;
        }
        
        BecomeTutorRequest request = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(BecomeTutorRequest.class)
                    .add(Restrictions.eq("emailAddress", emailAddress.toUpperCase()));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (BecomeTutorRequest)iterator.next();
            
            return request;
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
    public BecomeTutorRequest searchBecomeTutorRequestByContactNumber(String contactNumber) throws DataAccessException {
        if (contactNumber == null || contactNumber.isEmpty()) {
            LOG.warning(" the contact number is not provided ...");
            return null;
        }
        
        BecomeTutorRequest request = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(BecomeTutorRequest.class)
                    .add(Restrictions.eq("contactNumber", contactNumber.toUpperCase()));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (BecomeTutorRequest)iterator.next();
            
            return request;
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
