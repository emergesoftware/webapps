package za.co.metrobus.hibernate.dao;

/**
 *
 * @author Tsepo Maleka
 */
public class DataAccessException extends RuntimeException {
    
    public DataAccessException(String message) {
        super(message);
    }
    
    public DataAccessException(Throwable t) {
        super(t);
    }
}
