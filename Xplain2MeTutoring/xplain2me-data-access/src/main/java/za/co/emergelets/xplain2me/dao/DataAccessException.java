package za.co.emergelets.xplain2me.dao;

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
