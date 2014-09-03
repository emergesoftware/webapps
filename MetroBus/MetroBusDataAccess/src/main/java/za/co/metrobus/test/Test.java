package za.co.metrobus.test;

import java.util.List;
import za.co.metrobus.hibernate.dao.BusServiceDAO;
import za.co.metrobus.hibernate.dao.BusServiceDAOImpl;
import za.co.metrobus.hibernate.entity.BusRoute;
import za.co.metrobus.hibernate.entity.BusStop;
import za.co.metrobus.hibernate.entity.NavigationBarLink;
import za.co.metrobus.hibernate.entity.RouteDescription;

/**
 *
 * @author Tsepo Maleka
 */
public class Test {
    
    public static void main(String... args) {
        
        BusServiceDAO data = new BusServiceDAOImpl();
        List<BusStop> list = data.getBusRoutesGoingTo("rosebank");
        System.out.println("results: " + list.toString()); 
       
     
    }
    
  
    
}
