package za.co.emergelets.xplain2me.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;

/**
 *
 * @author user
 */
public class BecomeTutorRequestDAOImpl extends DefaultDataAccessObject
                                        implements BecomeTutorRequestDAO {

    private static final Logger LOG = 
            Logger.getLogger(BecomeTutorRequestDAOImpl.class.getName(), null);
    
    public BecomeTutorRequestDAOImpl() {
        super();
    }
    
    @Override
    public boolean isTutorApplicationUnique(BecomeTutorRequest request) throws DataAccessException {
        
        if (request == null || 
                (request.getContactNumber() == null || request.getContactNumber().isEmpty()) || 
                (request.getEmailAddress() == null || request.getEmailAddress().isEmpty()) || 
                (request.getIdentityNumber() == null || request.getIdentityNumber().isEmpty())) {
            
            LOG.warning("tutor application request is empty");
            return false;
        }
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            List<Criterion> criterion = new ArrayList<>();
            criterion.add(Restrictions.eq("emailAddress", request.getEmailAddress()));
            criterion.add(Restrictions.eq("contactNumber", request.getContactNumber()));
            criterion.add(Restrictions.eq("identityNumber", request.getIdentityNumber()));
            
            criteria = session.createCriteria(BecomeTutorRequest.class);
            criteria.add(Restrictions.or(criterion.toArray(
                    new Criterion[criterion.size()])));
            
            return criteria.list().isEmpty();
            
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
    public BecomeTutorRequest saveBecomeTutorRequest(BecomeTutorRequest request, 
            List<AcademicLevelsTutoredBefore> academicLevelsTutoredBefore, 
            List<BecomeTutorSupportingDocument> documents) 
            
            throws DataAccessException {
        
        if (request == null) {
            LOG.warning("become a tutor request object is null"); 
            return null;
        }
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            tx = session.beginTransaction();
            
            session.save(request);
            session.persist(request);
            
            for (AcademicLevelsTutoredBefore item : academicLevelsTutoredBefore) {
                session.save(item);
                session.persist(item);
            }
            
            for (BecomeTutorSupportingDocument item : documents) {
                session.save(item);
                session.persist(item);
            }
            
            tx.commit();
            
            return request;
            
        }
        
        catch (HibernateException e) {
            LOG.severe("error: " + e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            DataRepositoryUtility.close();
        }
    }
    
}
