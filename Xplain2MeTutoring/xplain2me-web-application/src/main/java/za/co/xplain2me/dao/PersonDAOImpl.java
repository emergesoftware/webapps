package za.co.xplain2me.dao;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import za.co.xplain2me.data.transformation.DataTransformerTool;
import za.co.xplain2me.entity.Person;

public class PersonDAOImpl implements PersonDAO {
    
    private static final Logger LOG = Logger
            .getLogger(PersonDAOImpl.class.getName(), null);
    
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    public PersonDAOImpl(){
        super();
    }

    @Override
    public Person getPerson(String username) throws DataAccessException {
        
        if (username == null || username.isEmpty())
            return null;
        
        Person person = null;
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession();
            
            criteria = session.createCriteria(Person.class, "person")
                    .createAlias("user", "user")
                    .add(Restrictions.eq("user.username", username));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                person = (Person)iterator.next();
            
            return person;
        
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
    public Person updatePerson(Person person) throws DataAccessException {
        if (person == null) {
            return null;
        }
        
        try {
            
            DataTransformerTool
                    .transformStringValuesToUpperCase(person);
            
            session = HibernateConnectionProvider.getSessionFactory()
                    .openSession();
            
            transaction = session.beginTransaction();
            session.merge(person);
            transaction.commit();
            
            return person;
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
    public boolean isPersonCompletelyUnique(Person person, boolean includeInCheck) throws DataAccessException {
        
        if (person == null) {
            LOG.warning("... the Person entity is null ...");
            return false;
        }
        
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            criteria = session.createCriteria(Person.class, "person")
                    .setProjection(Projections.count("person.id"));
            if (!includeInCheck)
                criteria.add(Restrictions.and(
                        Restrictions.eq("person.identityNumber", person.getIdentityNumber()),
                        Restrictions.ne("person.id", person.getId())));
            else
                criteria.add(Restrictions.eq("person.identityNumber", person.getIdentityNumber()));
            
            iterator = criteria.list().iterator();
            long count = 0;
            
            while (iterator.hasNext())
                count = (Long)iterator.next();
            
            return (count == 0);
            
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
