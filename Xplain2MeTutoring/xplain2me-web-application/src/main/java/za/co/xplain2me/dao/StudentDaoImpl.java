package za.co.xplain2me.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Student;

public class StudentDaoImpl implements StudentDao {
    
    private static final Logger LOG = 
            Logger.getLogger(StudentDaoImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    
    public StudentDaoImpl() {
    }
    
    @Override
    public List<Student> findStudentsByMatchingFirstNames(String searchValue, 
            Profile profilePerformingAction) throws DataAccessException {
        
        if ((searchValue == null || searchValue.length() < 3) || 
                profilePerformingAction == null) {
            LOG.warning("... either the search value is too short"
                    + " or the profile performing action is null...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            List<Criterion> predicates = new ArrayList<>();
            predicates.add(Restrictions.ilike("person.firstNames", searchValue.toLowerCase(), 
                    MatchMode.ANYWHERE));
            predicates.add(Restrictions.gt("profileType.id", 
                    profilePerformingAction.getProfileType().getId()));
            
            criteria = session.createCriteria(Student.class, "student")
                    .createAlias("student.profile", "profile")
                    .createAlias("profile.profileType", "profileType")
                    .createAlias("profile.person", "person")
                    .add(Restrictions.and(predicates.toArray(
                            new Criterion[predicates.size()])
                    ))
                    .addOrder(Order.asc("person.firstNames"));
            
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
    public Student getStudentById(long id, Profile profilePerformingAction) throws DataAccessException {
        
        if ((id < 0 || id > Long.MAX_VALUE) || 
                profilePerformingAction == null) {
            LOG.warning("... either the ID is invalid or the profile performing action is null...");
            return null;
        }
        
        Student student = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(Student.class, "student")
                    .createAlias("student.profile", "profile")
                    .createAlias("profile.profileType", "profileType")
                    .add(Restrictions.and(
                            Restrictions.eq("student.id", id),
                            Restrictions.gt("profileType.id", 
                                    profilePerformingAction.getProfileType().getId())));
            
            Iterator iterator = criteria.list().iterator();
            while (iterator.hasNext())
                student = (Student)iterator.next();
            
            return student;
            
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
