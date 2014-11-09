package za.co.emergelets.xplain2me.dao;

import java.util.Set;
import java.util.logging.Logger;
import javax.persistence.Entity;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.spi.Stoppable;
import org.reflections.Reflections;

/**
 * Hibernate configuration
 * 
 * @author tsepo maleka
 */
public final class DataRepositoryUtility {
    
    private static Logger LOG = Logger.getLogger
        (DataRepositoryUtility.class.getName(), null);
    
    private static final String PERISTANCE_ENTITIES_PACKAGE = 
            "za.co.emergelets.xplain2me.entity";
    
    // stores the annotated classes
    private static Set<Class<?>> annotatedClasses;
    // the session factory / jbdc connection
    private static SessionFactory factory;
    // the hinernate configuration
    private static Configuration config;
 
    
    /**
     * Begins the hibernate configuration process
     * 
     * @param xmlConfigurationFile Hibernate configuration file 
     * @return  
     */
    public static SessionFactory configure(String xmlConfigurationFile) throws HibernateException {
        
        LOG.info("... starting Hibernate configuration...");
        
        try {
            Reflections reflections = new Reflections(PERISTANCE_ENTITIES_PACKAGE);
            annotatedClasses = reflections.getTypesAnnotatedWith(Entity.class);
            
            return constructSessionFactory(xmlConfigurationFile);
        }
        catch (IllegalAccessException e) {
            LOG.severe("Error: " + e.getMessage());
            throw new HibernateException(e);
        }
    }
    
    /**
     * Constructs the session factory
     * from th Hibernate configuration and the 
     * annotated entities
     * 
     * @param xmlConfigurationFile
     * @throws IllegalAccessException 
     */
    private static SessionFactory constructSessionFactory(String xmlConfigurationFile) 
            throws IllegalAccessException {
        
        LOG.info("... constructing the session factory... ");
        
        if (annotatedClasses == null || annotatedClasses.isEmpty())
            throw new IllegalAccessException("Must have at least one annotated class.");
        
        // create new configuration using the default xml file or the
        // specified xml file
        if (xmlConfigurationFile == null || xmlConfigurationFile.isEmpty())
            config = new Configuration().configure();
        else
            config = new Configuration().configure(xmlConfigurationFile);
        
        // register the annotated Hibernate classes
        for (Class c : annotatedClasses) 
            config.addAnnotatedClass(c);
        
        // configure the service registry properties
        StandardServiceRegistryBuilder serviceRegistry = 
                new StandardServiceRegistryBuilder().applySettings(config.getProperties());
        
        // build the session factory
        factory = config.buildSessionFactory(serviceRegistry.build());
        
        // return the session factory
        return factory;

    }
    
    /**
     * Cleans up the connection pools
     * 
     * @return 
     */
    public static boolean close() {
        
        LOG.info(" ... closing connection pool ...");
        
        try {
            
            final SessionFactoryImplementor sessionFactoryImplementor = 
                    (SessionFactoryImplementor)factory;
            
            ConnectionProvider connectionProvider = sessionFactoryImplementor.getConnectionProvider();
            
            if (Stoppable.class.isInstance(connectionProvider)) {
                ((Stoppable) connectionProvider).stop();
            }
            
            return true;
        }
        
        catch (Exception e) {
            LOG.severe("... error: could not close connection pools... ");
            LOG.severe("ERROR: " + e.getMessage());
            return false;
        }
    }
    
}
