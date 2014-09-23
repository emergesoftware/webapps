package za.co.metrobus.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.CacheMode;
import org.hibernate.Criteria;
import org.hibernate.Query;
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
    protected Query query;
    
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
    protected void constructSessionFactory() 
            throws IllegalAccessException {
        constructSessionFactory(null);
    }
    
    /**
     * Constructs a new session factory
     * from the current Hibernate configuration
     * 
     * @param configFileName The XML hibernate configuration file
     * @throws IllegalAccessException 
     */
    protected void constructSessionFactory(String configFileName) 
            throws IllegalAccessException {
           
        if (annotatedClasses == null || annotatedClasses.isEmpty())
            throw new IllegalAccessException("Must have at least one annotated class.");
        
        config = null;
        
        if (configFileName == null || configFileName.isEmpty())
            config = new Configuration().configure();
        else
            config = new Configuration()
                .configure(configFileName);
        
        for (Class c : annotatedClasses) 
            config.addAnnotatedClass(c);
        
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
            session.disconnect();*/
    }
    
}
