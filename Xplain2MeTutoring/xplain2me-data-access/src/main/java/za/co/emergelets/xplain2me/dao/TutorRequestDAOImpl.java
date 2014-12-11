package za.co.emergelets.xplain2me.dao;

import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.data.transformation.DataTransformerTool;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.entity.TutorRequestSubject;

public class TutorRequestDAOImpl implements TutorRequestDAO {

    private static final Logger LOG = Logger.getLogger(
            TutorRequestDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
   
    public TutorRequestDAOImpl() {
        super();
    }
    
    @Override
    public TutorRequest getTutorRequestById(long id) throws DataAccessException {
        
        if (id < 1) return null;
        
        TutorRequest request = null;
        
        try {
            
            LOG.log(Level.INFO, "... get tutor request by id = {0}", id); 
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(TutorRequest.class, "tutorRequest")
                       .add(Restrictions.eq("tutorRequest.id", id));
                       
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (TutorRequest)iterator.next();
            
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
    public TutorRequest getTutorRequestByEmailAddress(String emailAddress) throws DataAccessException {
        if (emailAddress == null || emailAddress.isEmpty()) return null;
        
        TutorRequest request = null;
        
        try {
            
            LOG.log(Level.INFO, "... get tutor request by email address = {0}", emailAddress); 
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(TutorRequest.class)
                .add(Restrictions.eq("emailAddress", emailAddress));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (TutorRequest)iterator.next();
            
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
    public TutorRequest getTutorRequestByContactNumber(String contactNumber) throws DataAccessException {
        if (contactNumber == null || contactNumber.isEmpty()) 
            return null;
        
        TutorRequest request = null;
        
        try {
            
            LOG.log(Level.INFO, "... get tutor request by contact number = {0}", 
                    contactNumber); 
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(TutorRequest.class)
                .add(Restrictions.eq("contactNumber", contactNumber));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (TutorRequest)iterator.next();
            
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
    public TutorRequest saveTutorRequest(TutorRequest request) throws DataAccessException {
        
        if (request == null || (request.getSubjects() == null 
                || request.getSubjects().isEmpty()))
            throw new DataAccessException("null");
        
        try {
            
            DataTransformerTool.transformStringValuesToUpperCase(request);
            
            LOG.info("... save new tutor request "); 
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            // begin the transaction
            transaction = session.beginTransaction();
            
            //save the tutor request object
            session.save(request);
            session.persist(request);
            
            // save the tutor request subjects
            // objects
            for (TutorRequestSubject subject : request.getSubjects()) {
                session.save(subject);
                session.persist(subject);
            }
            
            // generate a reference number
            request.setReferenceNumber(TutorRequest
                    .generateReferenceNumber(request));
            
            // update the tutor request
            session.update(request);
            
            // commit the transaction block
            transaction.commit();
            
            return request;
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
    public List<TutorRequest> getUnreadTutorRequests() throws DataAccessException {
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            
            criteria = session.createCriteria(TutorRequest.class)
                .add(Restrictions.eq("received", false))
                .addOrder(Order.asc("id"));
            
            return criteria.list();
        }
        
        catch (HibernateException ex) {
            LOG.log(Level.SEVERE, "Error: {0}", ex.getMessage()); 
            throw new DataAccessException(ex);
        }
        
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
        
    }
    
    @Override
    public TutorRequest updateTutorRequest(TutorRequest request) throws DataAccessException {
        
        if (request == null) {
            LOG.warning("... Request is not valid ...");
            return null;
        }
        
        try {
            
            LOG.info(" ... updating tutor request entity ...");
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.merge(request);
            transaction.commit();
            return request;
        
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
    public TutorRequest deleteTutorRequest(TutorRequest request) throws DataAccessException {
        
        if (request == null) { 
            LOG.warning("tutor request to delete is null...");
            return null;
        }
        
        try {
            
            LOG.log(Level.INFO, " ... deleting tutor request: {0} ...", request.getId()); 
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            transaction = session.beginTransaction();
            
            List<TutorRequestSubject> tutorRequestSubjects = 
                    request.getSubjects();
            
            if (tutorRequestSubjects != null && 
                    tutorRequestSubjects.isEmpty() == false) {
                
                for (TutorRequestSubject subject : tutorRequestSubjects)
                    session.delete(subject);
                
            }
            
            session.delete(request);
            
            transaction.commit();
            
            return request;
        
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
    
    /**
     * CREATES A SEARCH CRITERION
     * OBJECT FROM THE PROVIDED SESSION
     * 
     * @param value
     * @param searchType
     * @param session
     * @return 
     */
    private Criteria createSearchTutorRequestCriteria(Object value, int searchType, Session session) {
        
        if (searchType == TutorRequest.SEARCH_BY_REFERENCE_NUMBER)
            return session.createCriteria(TutorRequest.class, "request")
                    .add(Restrictions.eq("request.referenceNumber", value.toString()));
        
        else if (searchType == TutorRequest.SEARCH_BY_REQUEST_ID)
            return session.createCriteria(TutorRequest.class, "request")
                    .add(Restrictions.eq("request.id", (Long)value));
        
        else if (searchType == TutorRequest.SEARCH_BY_EMAIL_ADDRESS)
            return session.createCriteria(TutorRequest.class, "request")
                    .add(Restrictions.eq("request.emailAddress", value.toString()))
                    .addOrder(Order.desc("request.dateReceived"));
        
        else if (searchType == TutorRequest.SEARCH_BY_CONTACT_NUMBER)
            return session.createCriteria(TutorRequest.class, "request")
                    .add(Restrictions.eq("request.contactNumber", value.toString()))
                    .addOrder(Order.desc("request.dateReceived"));
        
        else return null;
        
    }

    @Override
    public TutorRequest searchTutorRequestByReferenceNumber(String referenceNumber) throws DataAccessException {
        
        if (referenceNumber == null || referenceNumber.isEmpty()) {
            LOG.warning(" ... reference number is empty ...");
            return null;
        }
        
        TutorRequest result = null;
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            criteria = createSearchTutorRequestCriteria(referenceNumber,
                                    TutorRequest.SEARCH_BY_REFERENCE_NUMBER, session);
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                result = (TutorRequest)iterator.next();
            
            return result;
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
    public TutorRequest searchTutorRequestById(long tutorRequestId) throws DataAccessException {
        
        if (tutorRequestId < 0) {
            LOG.warning(" ... tutor request ID is empty ...");
            return null;
        }
        
        TutorRequest result = null;
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            criteria = createSearchTutorRequestCriteria(tutorRequestId,
                                    TutorRequest.SEARCH_BY_REQUEST_ID, session);
            
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                result = (TutorRequest)iterator.next();
            
            return result;
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
    public List<TutorRequest> searchTutorRequestByEmailAddress(String emailAddress) throws DataAccessException {
        
        if (emailAddress == null || emailAddress.isEmpty()) {
            LOG.warning(" ... email address is empty ...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            criteria = createSearchTutorRequestCriteria(emailAddress,
                                    TutorRequest.SEARCH_BY_EMAIL_ADDRESS, session);
            
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
    public List<TutorRequest> searchTutorRequestByContactNumber(String contactNumber) throws DataAccessException {
    
        if (contactNumber == null || contactNumber.isEmpty()) {
            LOG.warning(" ... contact number is empty ...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.getSessionFactory().openSession();
            criteria = createSearchTutorRequestCriteria(contactNumber,
                                    TutorRequest.SEARCH_BY_CONTACT_NUMBER, session);
            
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
