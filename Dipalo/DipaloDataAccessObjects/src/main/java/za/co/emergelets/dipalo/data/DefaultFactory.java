package za.co.emergelets.dipalo.data;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 * Manages the Hibernate Session Factory
 * and database connection sessions
 * 
 * @author Tsepo Maleka
 */
public abstract class DefaultFactory {
    
    protected List<Class> annotatedClasses;
    
    private Configuration config;
    protected static SessionFactory factory;
    
    protected Session session;
    protected Transaction tx;
    protected Criteria criteria;
    
    /**
     * Default constructor
     */
    protected DefaultFactory()  {
        // set the annotated classes list
        annotatedClasses = new ArrayList<>();
        
    }
    
    /**
     * Adds a Hibernate 
     * Annotated Entity class
     * 
     * @param c - The annotated class
     */
    protected void addAnnotatedClass(Class c) {
        
        if (!annotatedClasses.contains(c))
            annotatedClasses.add(c);
        
    }
    
    /**
     * Constructs a new session factory
     * from the current Hibernate configuration
     * 
     * @throws IllegalAccessException 
     */
    protected void constructSessionFactory() throws IllegalAccessException {
        
        if (annotatedClasses == null || annotatedClasses.isEmpty())
            throw new IllegalAccessException("Must have at least one annotated class.");
        
        config = new Configuration();
        for (Class c : annotatedClasses) 
            config.configure().addAnnotatedClass(c);
        
        factory = config.buildSessionFactory();

    }
  
    /**
     * Opens a new session from
     * the session factory
     * 
     */
    protected void openSession() {
        if (factory != null)
            session = factory.openSession();
    }
    
    /**
     * Saves and persists the object
     * to the current instance of the session
     * 
     * @param object 
     */
    protected void persistObject(Object object) {
        session.save(object);
        session.persist(object);
    }
    
    /**
     * Closes the current session
     */
    protected void closeSession() {
        /*if (session != null)
            session.close(); */
    }
    
}
