/*
    THE FAMOUS CATCH-TRY BLOCK FOR DAO:
    
        try {
            
            session = HibernateConnectionProvider.
                    getSessionFactory().openSession(); 

            // TO-DO HERE
        }
        
        catch (HibernateException e) {
            LOG.log(Level.SEVERE, "Error: {0}", e.getMessage());
            throw new DataAccessException(e);
        }
        finally {
            HibernateConnectionProvider.closeConnection(session);
        }

 */
package za.co.emergelets.xplain2me.dao;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Entity;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.Stoppable;
import org.reflections.Reflections;

public final class HibernateConnectionProvider {
    
    // The Logger
    private static final Logger LOG = 
            Logger.getLogger(HibernateConnectionProvider.class.getName(), null);
    // the entity package to scan 
    private static final String ENTITY_PACKAGE = "za.co.emergelets.xplain2me.entity";
    
    // reflections
    private static Reflections reflections = null;
    // stores the annotated classes
    private static Set<Class<?>> annotatedClasses = null;
    // The session factory
    private static SessionFactory sessionFactory = null;
    
    
    static {
        
        // build the session factory
        try {
            createSessionFactory();
        }
        catch (HibernateException | IllegalAccessException ex) {
            LOG.log(Level.SEVERE, " ... FAILED TO BUILD SESSION FACTORY: {0}", 
                    ex.getMessage());
        }
    }

    /**
     * Gets the current instance of the
     * session factory
     * 
     * @return 
     */
    public static SessionFactory getSessionFactory() {
        try {
            
            if (sessionFactory == null)
                createSessionFactory();
            
            return sessionFactory;
        } 

        catch (HibernateException | IllegalAccessException ex) {
            LOG.log(Level.SEVERE, "... intial session factory "
                    + "creation failed. Error: {0}", ex);
            throw new DataAccessException(ex);
        }
    }
    
    /**
     * Creates an instance of the SessionFactory
     * from the Hibernate configurations
     * 
     */
    private static void createSessionFactory() throws IllegalAccessException {
        
        LOG.info("... scanning the " + ENTITY_PACKAGE + " package for persistent entities ...");
        reflections = new Reflections(ENTITY_PACKAGE);
        annotatedClasses = reflections.getTypesAnnotatedWith(Entity.class);
        
        if (annotatedClasses == null || annotatedClasses.isEmpty())
            throw new IllegalAccessException("Must have at least one annotated class.");
        
        LOG.info("... starting session factory configuration using /hibernate.cfg.xml ...");
        Configuration configuration = new Configuration().configure();
        
        LOG.info("... registering the annotated classes from package ...");
        
        StringBuilder mappings = new StringBuilder();
        mappings.append("\n");
        
        for (Class c : annotatedClasses) {
            
            configuration.addAnnotatedClass(c);
            
            mappings.append(">> Mapped class: ")
                    .append(c.getName())
                    .append("\n");    
        }
        
        LOG.info(mappings.toString());
        
        LOG.info("... building service registry and applying settings ...");
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        
    }
    
    /**
     * Closes the current connection or
     * actively open session.
     * 
     * @param session
     * @return 
     */
    public static boolean closeConnection(Session session) {
        
        if (session != null && session.isOpen()) {
            LOG.info(" ... closing the current session [SUCCESS] ...");
            session.close();
            return true;
        }
        
        else  {
            LOG.info(" ... closing the current session [FAILED] ...");
            return false;
        }
       
    }
    
    /**
     * Destroys all open 
     * connection pools
     * 
     * @return 
     */
    public static boolean destroyConnectionPools() {
        LOG.info(" ... closing connection pool ...");
       
        try {
            
            final SessionFactoryImplementor sessionFactoryImplementor = 
                    (SessionFactoryImplementor)sessionFactory;
            
            ConnectionProvider connectionProvider = 
                    sessionFactoryImplementor.getConnectionProvider();
            
            if (Stoppable.class.isInstance(connectionProvider)) {
                ((Stoppable) connectionProvider).stop();
            }
            
            return true;
        }
        
        catch (HibernateException e) {
            LOG.severe("... error: could not close connection pools... ");
            LOG.log(Level.SEVERE, "ERROR: {0}", e.getMessage());
            return false;
        }
    }
    
    /**
     * Rolls back the currently active
     * transaction
     * 
     * @param transaction 
     */
    public static void rollback(Transaction transaction) {
        try {
            if (transaction != null) {
                transaction.rollback();
                LOG.info(" ... current transaction rolled back ...");
            }
        }
        catch (HibernateException e) {
            LOG.severe(" ... transaction rollback failed ...");
        }
    }
    
}
