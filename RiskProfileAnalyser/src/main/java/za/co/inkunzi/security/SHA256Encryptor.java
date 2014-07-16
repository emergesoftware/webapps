/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package za.co.inkunzi.security;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.bouncycastle.util.encoders.Hex;


public final class SHA256Encryptor {
    
    public static final String ALGORITHM_SHA256 = "SHA-256";
    
    public static String computeSHA256(String input) 
            throws NoSuchAlgorithmException, UnsupportedEncodingException {
        
        MessageDigest digest = MessageDigest.getInstance(ALGORITHM_SHA256);
        digest.update(input.getBytes("UTF-8"));
        byte[] hassword = digest.digest();
        
        return new String(Hex.encode(hassword));
    }
    
}
