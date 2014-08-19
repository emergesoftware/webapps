/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.emergelets.util;

import java.security.SecureRandom;
import org.bouncycastle.util.encoders.Hex;

/**
 *
 * @author user
 */
public final class SecureRandomKeyGenerator {
    
    /**
     * Generates a random key securely
     * @return 
    */
    public static String generateSecureRandomKey() {
        
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[32];
        random.nextBytes(salt);
        return new String(Hex.encode(salt));
    }
    
}
