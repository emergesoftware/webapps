package za.co.emergelets.test;

import za.co.emergelets.xplain2me.dao.CitizenshipDAO;
import za.co.emergelets.xplain2me.dao.CitizenshipDAOImpl;

public class TestClass {
    
    public static void main(String... args) throws Exception {
        
        CitizenshipDAO dao = new CitizenshipDAOImpl();
        dao.getAllCitizenships();
        
    }
    
}
