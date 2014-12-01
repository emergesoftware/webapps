package za.co.emergelets.xplain2me.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.data.transformation.DataTransformerTool;
import za.co.emergelets.xplain2me.entity.AcademicLevelsTutoredBefore;
import za.co.emergelets.xplain2me.entity.BecomeTutorRequest;
import za.co.emergelets.xplain2me.entity.BecomeTutorSupportingDocument;

public class BecomeTutorRequestDAOImpl extends HibernateConnectionProvider
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

            session = getSessionFactory().openSession(); 
            
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
            closeConnection();
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
            
            session = getSessionFactory().openSession(); 
            
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
            rollback(transaction);
            LOG.log(Level.SEVERE, "error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            closeConnection();
        }
    }
    
}
