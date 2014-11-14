package za.co.emergelets.util;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class VerificationCodeGenerator {
    
    private static final Logger LOG = 
            Logger.getLogger(VerificationCodeGenerator.class.getName(),
                    null);
    
    private static final Random random = 
            new Random(System.currentTimeMillis());
    
    private static final int MAXIMUM_RANGE = 9999;
    private static final int MINIMUM_RANGE = 1000;
    
    public static final int VERIFICATION_CODE_LENGTH = 4;
    
    /**
     * Generates a 4-digit verification code
     * (between 1000 and 9999)
     * @return 
     */
    public static int generateVerificationCode() {
        int code = random.nextInt(MAXIMUM_RANGE - MINIMUM_RANGE) 
                + MINIMUM_RANGE;
        LOG.log(Level.INFO, "... verification code generated: {0}", code);
        return code;
    }
    
}
