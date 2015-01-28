package za.co.xplain2me.bo.impl;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Logger;
import org.bouncycastle.util.encoders.Hex;
import za.co.xplain2me.bo.UserPasswordBO;
import za.co.xplain2me.util.SHA256Encryptor;

public class UserPasswordBOImpl implements UserPasswordBO {
    
    private static final Logger LOG = Logger
            .getLogger(UserPasswordBOImpl.class.getName(), null);
    
    private static final Random random = new SecureRandom();
    
    public UserPasswordBOImpl() {
    }
    
    @Override
    public String generateRandomSaltValue() {
        
        LOG.info("... generating random salt value ..."); 
        
        byte[] byteValue = new byte[16];
        random.nextBytes(byteValue);
        
        return new String(Hex.encode(byteValue));
        
    }

    @Override
    public String generateHashedPassword(String password, String saltValue)
            throws Exception {
        return SHA256Encryptor.computeSHA256(password, saltValue);
    }
    
}
