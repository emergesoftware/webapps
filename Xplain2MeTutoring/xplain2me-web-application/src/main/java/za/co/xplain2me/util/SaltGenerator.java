package za.co.xplain2me.util;

import java.security.SecureRandom;
import org.bouncycastle.util.encoders.Hex;

public final class SaltGenerator {
    
    /**
     * Generates a salt value
     * @return 
    */
    public static String generateSaltValue() {
        
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return new String(Hex.encode(salt));
    }
    
}
