package za.co.emergelets.xplain2me.dao;

import java.util.logging.Logger;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import za.co.emergelets.xplain2me.entity.Person;

public class PersonDAOImpl extends DefaultDataAccessObject implements PersonDAO {
    
    private static Logger LOG = Logger.getLogger(PersonDAOImpl.class.getName(), null);
    
    public PersonDAOImpl(){
        super();
    }

    @Override
    public Person getPerson(String username) throws DataAccessException {
        
        if (username == null || username.isEmpty())
            return null;
        
        Person person = null;
        
        try {
            
            factory = DataRepositoryUtility.configure(null);
            session = factory.openSession();
            
            criteria = session.createCriteria(Person.class, "person");
            criteria.createAlias("user", "user");
            criteria.add(Restrictions.eq("user.username", username));
            iterator = criteria.list().iterator();
            
            while (iterator.hasNext())
                person = (Person)iterator.next();
            
            return person;
        
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
