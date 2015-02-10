package za.co.xplain2me.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.entity.Lesson;
import za.co.xplain2me.entity.LessonStatus;
import za.co.xplain2me.entity.Profile;
import za.co.xplain2me.entity.Subject;
import za.co.xplain2me.entity.Tutor;
import za.co.xplain2me.entity.TutorSubject;

public class TutorDaoImpl implements TutorDao {
    
    private static final Logger LOG = 
            Logger.getLogger(TutorDaoImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction tx;
    protected Iterator iterator;
    
    public TutorDaoImpl() {
    }

    @Override
    public Tutor createTutor(Tutor tutor, Profile profilePerformingAction) throws DataAccessException {
        
        if (tutor == null || profilePerformingAction == null) {
            LOG.warning("... either the tutor is null or the "
                    + "profile performing action is null ...");
            return null;
        }
        
        if (tutor.getProfile().getProfileType().getId() < 
                    profilePerformingAction.getProfileType().getId()) {
                
            LOG.warning("... action not permitted - insufficient authority ...");
            return null;
                
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            tx = session.beginTransaction();
            session.save(tutor);
            session.persist(tutor);
            tx.commit();
            
            return tutor;
            
        }
        
        catch (HibernateException e) {
            
            HibernateConnectionProvider.rollback(tx);
            
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
        
    }

    @Override
    public boolean assignSubjectsToTutor(List<TutorSubject> tutorSubjects, boolean appendTo, Profile profilePerformingAction)
            throws DataAccessException {
        
        if (tutorSubjects == null || tutorSubjects.isEmpty() || 
                profilePerformingAction == null) {
            
            LOG.warning("... either no tutor subjects are provided or "
                    + "the profile performing action is null ...");
            return false;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            tx = session.beginTransaction();
            
            long count = 0L;
            
            // if appendTo is TRUE
            if (appendTo) {
                
                // check first if the tutor is not assigned the subjects
                for (TutorSubject tutorSubject : tutorSubjects) {
                    
                    criteria = session.createCriteria(TutorSubject.class, "tutorSubject")
                            .createAlias("tutorSubject.tutor", "tutor")
                            .createAlias("tutorSubject.subject", "subject")
                            .setProjection(Projections.count("tutorSubject.id"))
                            .add(Restrictions.and(
                                    Restrictions.eq("tutor.id", tutorSubject.getTutor().getId()),
                                    Restrictions.eq("subject.id", tutorSubject.getSubject().getId())));
                    
                    iterator = criteria.list().iterator();
                    while (iterator.hasNext())
                        count = (Long)iterator.next();
                    
                    // if this entry does not exist then insert
                    if (count == 0) {
                        session.save(tutorSubject);
                        session.persist(tutorSubject);
                    }
                    
                }
            }
            
            else {
                
                // delete all the entries from the table - if they
                // exist and insert new entries
                for (TutorSubject tutorSubject : tutorSubjects) {
                    
                    criteria = session.createCriteria(TutorSubject.class, "tutorSubject")
                            .createAlias("tutorSubject.tutor", "tutor")
                            .createAlias("tutorSubject.subject", "subject")
                            .setProjection(Projections.count("tutorSubject.id"))
                            .add(Restrictions.and(
                                    Restrictions.eq("tutor.id", tutorSubject.getTutor().getId()),
                                    Restrictions.eq("subject.id", tutorSubject.getSubject().getId())));
                    
                    iterator = criteria.list().iterator();
                    while (iterator.hasNext())
                        count = (Long)iterator.next();
                    
                    // if this entry does not exist then insert
                    if (count == 0) {
                        session.save(tutorSubject);
                        session.persist(tutorSubject);
                    }
                    
                    // else then delete then insert
                    else {
                        
                        session.delete(tutorSubject);
                        
                        session.save(tutorSubject);
                        session.persist(tutorSubject);
                        
                    }
                    
                }
                
            }
            
            tx.commit();
            
            return true;
        }
        
        catch (HibernateException e) {
            
            HibernateConnectionProvider.rollback(tx);
            
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            return false;
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }
        
    }

    @Override
    public List<Tutor> searchTutor(int tutorSearchCriteria, Object value, Profile profilePerformingAction) throws DataAccessException {
        
        if (profilePerformingAction == null) {
            LOG.warning(" the profile performing the action is not provided ...");
            return null;
        }
        
        List<Tutor> results = new ArrayList<>();
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            switch (tutorSearchCriteria) {
                case SEARCH_BY_SUBJECT_TUTORING:
                    
                    if (value != null && value instanceof Subject) {
                        
                        // case the object value
                        Subject subject = (Subject)value;
                        
                        // create the criteria
                        criteria = session.createCriteria(TutorSubject.class, "tutoringSubject")
                                .createAlias("tutoringSubject.tutor", "tutor")
                                .createAlias("tutoringSubject.subject", "subject")
                                .createAlias("tutor.profile", "profile")
                                .createAlias("profile.profileType", "profileType")
                                .setProjection(Projections.distinct(Projections.property("tutor")))
                                .add(Restrictions.and(
                                        Restrictions.eq("subject.id", subject.getId()), 
                                        Restrictions.gt("profileType.id", profilePerformingAction.getProfileType().getId())))
                                .addOrder(Order.asc("tutor.id"));
                        
                        results = criteria.list();
                        
                    }
                    
                    break;
                    
                case SEARCH_BY_TUTOR_ID:
                    
                    if (value != null && value instanceof Long) {
                        
                        long tutorId = (Long)value;
                        
                        List<Criterion> predicates = new ArrayList<>();
                        
                        predicates.add(Restrictions.eq("tutor.id", tutorId));
                        predicates.add(Restrictions.gt("profileType.id", 
                                profilePerformingAction.getProfileType().getId()));
                        
                        criteria = session.createCriteria(Tutor.class, "tutor")
                                .createAlias("tutor.profile", "profile")
                                .createAlias("profile.profileType", "profileType")
                                .add(Restrictions.and(
                                        predicates.toArray(new Criterion[predicates.size()])
                                ));
                                
                        results = criteria.list();
                        
                    }
                    
                    break;
            }
            
            return results;
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
    public Tutor updateTutor(Tutor tutor, Profile profilePerformingAction) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet.");
        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean assignLessonsToTutor(List<Lesson> lessons, Profile profilePerformingAction) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean detachUserAsTutor(Tutor tutor, Profile profilePerformingAction) throws DataAccessException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Lesson> getTutorLessons(Tutor tutor, LessonStatus status, 
            Profile profilePerformingAction) 
            throws DataAccessException {
        
        if (tutor == null || status == null || 
                profilePerformingAction == null) {
            
            LOG.warning("... either the tutor is null or the status is null or "
                    + "the profile performing action is null...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
             List<Criterion> predicates = new ArrayList<>();
            
            predicates.add(Restrictions.eq("tutor.id", tutor.getId()));
            predicates.add(Restrictions.eq("lessonStatus.id", status.getId()));
            predicates.add(Restrictions.gt("profileType.id", 
                    profilePerformingAction.getProfileType().getId()));
            

            criteria = session.createCriteria(Lesson.class, "lesson")
                    .createAlias("lesson.tutor", "tutor")
                    .createAlias("lesson.lessonStatus", "lessonStatus")
                    .createAlias("tutor.profile", "profile")
                    .createAlias("profile.profileType", "profileType")
                    .add(Restrictions.and(
                            predicates.toArray(new Criterion[predicates.size()])
                    ))
                    .addOrder(Order.desc("lesson.id"));
            
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
    public List<Subject> getTutorSubjects(Tutor tutor, Profile profilePerformingAction) 
            throws DataAccessException {
        
        if (tutor == null || profilePerformingAction == null) {
            LOG.warning(" ... either the tutor or profile performing "
                    + "action is null ...");
            return null;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 
            
            List<Criterion> predicates = new ArrayList<>();
            
            predicates.add(Restrictions.eq("tutor.id", tutor.getId()));
            predicates.add(Restrictions.gt("profileType.id", 
                    profilePerformingAction.getProfileType().getId()));

            criteria = session.createCriteria(TutorSubject.class, "tutorSubject")
                    .createAlias("tutorSubject.tutor", "tutor")
                    .createAlias("tutor.profile", "profile")
                    .createAlias("profile.profileType", "profileType")
                    .createAlias("tutorSubject.subject", "subject")
                    .setProjection(Projections.distinct(Projections.property("subject"))) 
                    .add(Restrictions.and(predicates
                            .toArray(new Criterion[predicates.size()])))
                    .addOrder(Order.asc("tutorSubject.id"));
            
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
    public boolean isTutorAlready(Profile profile, Profile profilePerformingAction) 
            throws DataAccessException {
        
        if (profile == null || profilePerformingAction == null) {
            LOG.warning("... the profile or the profile performing action is null ...");
            return true;
        }
        
        if (profile.getProfileType().getId() < 
                    profilePerformingAction.getProfileType().getId()) {
                
            LOG.warning("... action not permitted - insufficient authority ...");
            return true;     
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(Tutor.class, "tutor")
                    .createAlias("tutor.profile", "profile")
                    .setProjection(Projections.count("profile.id"))
                    .add(Restrictions.eq("profile.id", profile.getId()));
            
            long count = 0L;
            
            Iterator iterator = criteria.list().iterator();
            while(iterator.hasNext())
                count = (Long)iterator.next();
            
            return (count > 0);
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
