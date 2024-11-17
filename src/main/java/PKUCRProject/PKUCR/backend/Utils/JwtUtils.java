package PKUCRProject.PKUCR.backend.Utils;

import java.security.SecureRandom;
import java.text.ParseException;
import java.util.Base64;
import java.util.Date;


import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.*;
import com.nimbusds.jwt.JWTClaimsSet;

@Component
public class JwtUtils {

    private JWSObject jwsObject;

    private static final byte[] sharedSecret;

    static {
        // Generate random 256-bit (32-byte) shared secret
        SecureRandom random = new SecureRandom();
        sharedSecret = new byte[32];
        random.nextBytes(sharedSecret);
        System.out.println("Generated shared secret: " + Base64.getEncoder().encodeToString(sharedSecret));
    }

    public String getToken(String email) {
        Claims claims = new Claims(email);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Payload payload = new Payload(objectMapper.writeValueAsString(claims));
            this.jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), payload);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
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

    public Boolean verify(String token) {
        try {
            JWSVerifier jwsVerifier = new MACVerifier(sharedSecret);
            return jwsObject.verify(jwsVerifier);
        } catch (JOSEException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getSubject(String token) {
        try {
            // Parse the token into a JWSObject
            JWSObject jwsObject = JWSObject.parse(token);
            // Verify the signature with the shared secret
            JWSVerifier verifier = new MACVerifier(sharedSecret);
            if (!jwsObject.verify(verifier)) {
                return null; // Signature verification failed
            }
            // Extract the payload and parse it as a JWTClaimsSet
            return (String) jwsObject.getPayload().toJSONObject().get("sub");
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Boolean isTokenExpired(String token) {
        try {
            // Parse the token into a JWSObject
            JWSObject jwsObject = JWSObject.parse(token);
            // Verify the signature with the shared secret
            JWSVerifier verifier = new MACVerifier(sharedSecret);
            if (!jwsObject.verify(verifier)) {
                return true; // Signature verification failed, consider token expired
            }
            // Extract the payload and parse it as a JWTClaimsSet
            JWTClaimsSet claimsSet = jwsObject.getPayload().toSignedJWT().getJWTClaimsSet();
            // Get the expiration time from the claims set
            Date expirationTime = claimsSet.getExpirationTime();
            if (expirationTime == null) {
                return false; // No expiration time found, assume not expired
            }
            // Check if the current time is after the expiration time
            return new Date().after(expirationTime);
        } catch (ParseException | JOSEException e) {
            e.printStackTrace();
        }
        return true; // Default to expired if any exception occurs
    }
}
