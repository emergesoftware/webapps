package za.co.emergelets.xplain2me.dao;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.data.transformation.DataTransformerTool;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.entity.TutorRequestSubject;

public class TutorRequestDAOImpl extends HibernateConnectionProvider
                                implements TutorRequestDAO {

    private static final Logger LOG = Logger.getLogger(
            TutorRequestDAOImpl.class.getName(), null);
   
    public TutorRequestDAOImpl() {
        super();
    }
    
    @Override
    public TutorRequest getTutorRequestById(long id) throws DataAccessException {
        
        if (id < 1) return null;
        
        TutorRequest request = null;
        
        try {
            
            LOG.log(Level.INFO, "... get tutor request by id = {0}", id); 
            
            session = getSessionFactory().openSession();
            
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
            closeConnection();
        }
        
    }

    @Override
    public TutorRequest getTutorRequestByEmailAddress(String emailAddress) throws DataAccessException {
        if (emailAddress == null || emailAddress.isEmpty()) return null;
        
        TutorRequest request = null;
        
        try {
            
            LOG.log(Level.INFO, "... get tutor request by email address = {0}", emailAddress); 
            
            session = getSessionFactory().openSession();
            
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
           closeConnection();
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
            
            session = getSessionFactory().openSession();
            
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
            closeConnection();
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
            
            session = getSessionFactory().openSession();
            
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
            
            rollback(transaction);
            
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeConnection();
        }
    }


    @Override
    public List<TutorRequest> getUnreadTutorRequests() throws DataAccessException {
        
        try {
            
            session = getSessionFactory().openSession();
            
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
            closeConnection();
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
            
            session = getSessionFactory().openSession();
            transaction = session.beginTransaction();
            session.merge(request);
            transaction.commit();
            return request;
        
        }
        
        catch (HibernateException e) {
            
            rollback(transaction);
            
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeConnection();
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
            
            session = getSessionFactory().openSession();
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
            
            rollback(transaction);
            
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            closeConnection();
        }
        
    }
}
