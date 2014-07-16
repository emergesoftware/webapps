/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.hibernate.dao;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author user
 */
public abstract class DefaultFactory {
    
    protected List<Class> annotatedClasses;
    
    protected static SessionFactory factory;
    
    protected Session session;
    protected Transaction tx;
    protected Criteria criteria;
    
    protected DefaultFactory() throws NullPointerException {
        // set the annotated classes list
        annotatedClasses = new ArrayList<>();
        
    }
    
    protected void addAnnotatedClass(Class c) {
        
        if (!annotatedClasses.contains(c))
            annotatedClasses.add(c);
        
    }
    
    protected void constructSessionFactory() throws IllegalAccessException {
        
        if (annotatedClasses == null || annotatedClasses.isEmpty())
            throw new IllegalAccessException("Must have at least one annotated class.");
        
        Configuration config = new Configuration();
        for (Class c : annotatedClasses) 
            config.configure().addAnnotatedClass(c);
        
        factory = config.buildSessionFactory();

    }
  
    protected void openSession() {
        if (factory != null)
            session = factory.openSession();
    }
    
    protected void beginTransaction() {
        if (session != null)
            tx = session.beginTransaction();
    }
    
    protected void commitTransaction() {
        if (tx != null)
            tx.commit();
    }
    
}
