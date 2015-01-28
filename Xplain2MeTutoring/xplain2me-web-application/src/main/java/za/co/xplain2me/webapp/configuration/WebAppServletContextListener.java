package za.co.xplain2me.webapp.configuration;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import org.springframework.web.context.ContextLoaderListener;
import za.co.xplain2me.dao.HibernateConnectionProvider;

public class WebAppServletContextListener extends ContextLoaderListener {
    
    private static final Logger LOG = 
            Logger.getLogger(WebAppServletContextListener.class.getName(), null);
    
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        super.contextInitialized(sce);
        
        LOG.info(" ... WEB APP: context initialisation ...."); 
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        super.contextDestroyed(sce);
        
        LOG.info(" ... WEB APP: context destroying ...."); 
        
        // destroy Hibernate connection pool factory
        destroyHibernateSessionFactory();
        
        // de-register all JDBC drivers
        deregisterJdbcDrivers();
        
    }
    
    /**
     * De-registers all registered
     * JDBC drivers.
     * 
     */
    protected final void deregisterJdbcDrivers() {
        
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        
        while (drivers.hasMoreElements()) {
            Driver driver = null;
            
            try {
                
                driver = drivers.nextElement();
                LOG.log(Level.INFO, "... deregistering jdbc driver: {0}", driver);
                
                DriverManager.deregisterDriver(driver); 
                
            } 
            
            catch (SQLException e) {
                LOG.log(Level.SEVERE, "... error deregistering driver"
                        + " {0}", e.getMessage());
            }

        }
        
    }
    
    /**
     * Destroys the hibernate session factory and
     * all active connection pools.
     * 
     */
    protected final void destroyHibernateSessionFactory() {
        
        LOG.info(" ... started destroying hibernate session factory conn. pools ...");
        HibernateConnectionProvider.destroyConnectionPools();
        
    }
}
