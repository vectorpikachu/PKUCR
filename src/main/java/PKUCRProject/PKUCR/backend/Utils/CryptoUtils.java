package PKUCRProject.PKUCR.backend.Utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CryptoUtils {
    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private CryptoUtils() {
    }

    
    /** 
     * @param providedPassword
     * @param storedPassword
     * @return boolean
     */
    public static boolean verifyPassword(String providedPassword, String storedPassword) {
        return bCryptPasswordEncoder.matches(providedPassword, storedPassword);
    }

    public static String encodePassword(String providedPassword) {
        return bCryptPasswordEncoder.encode(providedPassword);
    }
}