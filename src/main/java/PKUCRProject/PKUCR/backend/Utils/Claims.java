package PKUCRProject.PKUCR.backend.Utils;

import java.util.Date;
import java.util.UUID;


public class Claims {
    // "签发时间"
    private String iat;

    // 过期时间
    private String exp;

    // JWT的ID
    private String jti;

    // "email"
    private String email;

    // "用户拥有的权限"
    //private List<String> authorities;
    
    public Claims(String email) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        this.iat = now.toString();
        
        long expMillis = nowMillis + 3600000;
        Date expDate = new Date(expMillis);
        this.exp = expDate.toString();

        this.jti = UUID.randomUUID().toString();
        this.email = email;
    }
   
    public String getIat() {
        return iat;
    }
    public String getExp() {
        return exp;
    }
    public String getJti() {
        return jti;
    }
    public String getEmail() {
        return email;
    }

    public void setIat(String iat) {
        this.iat = iat;
    }
    public void setExp(String exp) {
        this.exp = exp;
    }
    public void setJti(String jti) {
        this.jti = jti;
    }
    public void setEmail(String email) {
        this.email = email;
    }

}
