package za.co.emergelets.xplain2me.dao;

import java.util.Iterator;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/**
 * Manages the Hibernate Session Factory
 * and database connection sessions
 * 
 * @author Tsepo Maleka
 */
public abstract class DefaultDataAccessObject {
    
    protected static SessionFactory factory;
    
    protected Session session;
    protected Transaction tx;
    protected Criteria criteria;
    protected Query query;
    protected Iterator iterator;
    
    /**
     * Default constructor
     */
    protected DefaultDataAccessObject()  {
        
        factory = null;
        
        this.session = null;
        this.tx = null;
        this.criteria = null;
        this.query = null;
        this.iterator = null;
    }
    
   
    
}
