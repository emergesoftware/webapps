package za.co.metrobus.test;

import java.util.List;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;
import za.co.metrobus.hibernate.entity.BusStop;

/**
 *
 * @author Tsepo Maleka
 */
public class Test {
    
    public static void main(String... args) {
        
        BusServiceDAO data = new BusServiceDAOImpl();
        List<BusStop> list = data.getBusRoutesGoingTo("parkhurst");
        System.out.println("results: " + list.toString()); 
       
     
    }
    
  
    
}
