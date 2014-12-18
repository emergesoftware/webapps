package za.co.emergelets.xplain2me.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
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
    
}
