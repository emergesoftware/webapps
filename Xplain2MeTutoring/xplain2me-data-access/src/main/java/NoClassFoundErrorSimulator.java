
import za.co.emergelets.xplain2me.dao.HibernateConnectionProvider;


public class NoClassFoundErrorSimulator {
    
    public static void main(String[] args) throws Exception {
        
        System.out.println("... starting NoClassFoundErrorSimulator ...");
        HibernateConnectionProvider.getSessionFactory();
        HibernateConnectionProvider.destroyConnectionPools();
        
    }
    
}
