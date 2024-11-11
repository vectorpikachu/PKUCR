package PKUCRProject.PKUCR.backend.Utils;

import java.security.SecureRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;

public class JwtUtils {

    private JWSObject jwsObject;
    private byte[] sharedSecret;

    public JwtUtils(Claims claims) {
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Payload payload = new Payload(objectMapper.writeValueAsString(claims));
            this.jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
        } catch (JsonProcessingException e) {
                
            e.printStackTrace();
        }            
    }

    public String getToken() {
        // Create HMAC signer
        try {
            JWSSigner signer = new MACSigner(sharedSecret);
            // Apply the HMAC
            try {
                jwsObject.sign(signer);
            } catch (JOSEException e) {
                e.printStackTrace();
            }
        } catch (KeyLengthException e) {
            e.printStackTrace();
        }

        // To serialize to compact form, produces something like
        // eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA
        String s = jwsObject.serialize();

        return s;
    }

    public boolean verify(String token) {
        try {
            JWSVerifier jwsVerifier = new MACVerifier(sharedSecret);
            return jwsObject.verify(jwsVerifier);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return false;
    }
}
