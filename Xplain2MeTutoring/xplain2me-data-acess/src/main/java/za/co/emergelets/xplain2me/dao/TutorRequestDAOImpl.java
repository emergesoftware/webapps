package za.co.emergelets.xplain2me.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.TutorRequest;
import za.co.emergelets.xplain2me.entity.TutorRequestSubject;

public class TutorRequestDAOImpl extends DefaultDataAccessObject implements TutorRequestDAO {

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
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(TutorRequest.class);
            criteria.add(Restrictions.eq("id", id));
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
            DataRepositoryUtility.close();
        }
        
    }

    @Override
    public TutorRequest getTutorRequestByEmailAddress(String emailAddress) throws DataAccessException {
        if (emailAddress == null || emailAddress.isEmpty()) return null;
        
        TutorRequest request = null;
        
        try {
            
            LOG.log(Level.INFO, "... get tutor request by email address = {0}", emailAddress); 
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(TutorRequest.class);
            criteria.add(Restrictions.eq("emailAddress", emailAddress));
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
            DataRepositoryUtility.close();
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
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(TutorRequest.class);
            criteria.add(Restrictions.eq("contactNumber", contactNumber));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                request = (TutorRequest)iterator.next();
            
            return request;
        }
        
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public TutorRequest saveTutorRequest(TutorRequest request) throws DataAccessException {
        
        if (request == null || (request.getSubjects() == null 
                || request.getSubjects().isEmpty()))
            throw new DataAccessException(
                    new NullPointerException("null"));
        
        try {
            
            LOG.info("... save new tutor request "); 
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            // begin the transaction
            tx = session.beginTransaction();
            
            //save the tutor request object
            session.save(request);
            session.persist(request);
            
            // save the tutor request subjects
            // objects
            for (TutorRequestSubject subject : request.getSubjects()) {
                session.save(subject);
                session.persist(subject);
            }
            
            // commit the transaction block
            tx.commit();
            
            return request;
        }
        
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }

    @Override
    public boolean isTutorRequestCompletelyUnique(TutorRequest request) throws DataAccessException {
        if (request == null)
            return false;
        
        long rowCount = 0;
        
        try {
            
            LOG.info("... checking if this applicant is unique");
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(TutorRequest.class, "tutorRequest");
            criteria.setProjection(Projections.rowCount());
            criteria.add(Restrictions.or(
                    Restrictions.eq("contactNumber", request.getContactNumber()), 
                    Restrictions.eq("emailAddress", request.getEmailAddress())));
            rowCount = (Long)criteria.uniqueResult();
            
            return (rowCount == 0);
            
        }
        
        catch (HibernateException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        
        finally {
            DataRepositoryUtility.close();
        }
    }
    
}