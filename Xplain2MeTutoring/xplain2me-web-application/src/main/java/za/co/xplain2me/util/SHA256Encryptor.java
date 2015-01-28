package za.co.xplain2me.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.util.encoders.Hex;


public final class SHA256Encryptor {
    
    public static final String ALGORITHM_SHA256 = "SHA-256";
    
    /**
     * Computes the SHA-256 One-way hashing
     * 
     * @param input
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public static String computeSHA256(String input) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA256);
        digest.update(input.getBytes("UTF-8"));
        byte[] hassword = digest.digest();
        
        return new String(Hex.encode(hassword));
    }
    
    /**
     * Computes the SHA-256 One Way Hashing
     * (With a salt value)
     * 
     * @param input
     * @param salt
     * @return
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException 
     */
    public static String computeSHA256(String input, String salt) 
            throws NoSuchAlgorithmException, 
            UnsupportedEncodingException {
        return computeSHA256(input + salt);
    }
    
}
