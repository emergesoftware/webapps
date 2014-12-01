package za.co.emergelets.xplain2me.dao;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Person;

public class PersonDAOImpl extends HibernateConnectionProvider implements PersonDAO {
    
    private static final Logger LOG = Logger
            .getLogger(PersonDAOImpl.class.getName(), null);
    
    public PersonDAOImpl(){
        super();
    }

    @Override
    public Person getPerson(String username) throws DataAccessException {
        
        if (username == null || username.isEmpty())
            return null;
        
        Person person = null;
        
        try {
            
            
            session = getSessionFactory().openSession();
            
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
            closeConnection();
        }
        
    }
    
    
    
}
