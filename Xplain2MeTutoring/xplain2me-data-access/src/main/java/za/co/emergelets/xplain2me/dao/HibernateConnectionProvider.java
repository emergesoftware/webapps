package za.co.emergelets.xplain2me.dao;

import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Query;
import org.hibernate.Criteria;
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

/**
 * Hibernate connection provider
 * class
 * 
 */
public abstract class HibernateConnectionProvider {
    
    // The Logger
    private static final Logger LOG = 
            Logger.getLogger(HibernateConnectionProvider.class.getName(), null);
    
    // The session factory
    private static SessionFactory sessionFactory;
    
    static {
        createSessionFactory();
    }
  
    protected Session session;
    protected Criteria criteria;
    protected Transaction transaction;
    protected Iterator iterator;
    protected Query query;
    
    /**
     * Constructor
     */
    protected HibernateConnectionProvider() {
        this.session = null;
        this.criteria = null;
        this.iterator = null;
        this.query = null;
    }
    
    /**
     * Gets the current instance of the
     * session factory
     * 
     * @return 
     */
    protected final SessionFactory getSessionFactory() {
        try {
            
            if (sessionFactory == null)
                createSessionFactory();
            
            return sessionFactory;
        } 

        catch (HibernateException ex) {
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
    private static void createSessionFactory() {
        
        
        Configuration configuration = new Configuration().configure();
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        
    }
    
    /**
     * Closes the current connection or
     * actively open session.
     * 
     * @return 
     */
    protected final boolean closeConnection() {
        
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
    protected final boolean destroyConnectionPools() {
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
    protected final void rollback(Transaction transaction) {
        try {
            if (transaction != null)
                transaction.rollback();
        }
        catch (HibernateException e) {
            LOG.severe(" ... transaction rollback failed ...");
        }
    }
    
}
