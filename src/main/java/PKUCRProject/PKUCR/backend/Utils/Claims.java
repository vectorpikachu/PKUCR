package PKUCRProject.PKUCR.backend.Utils;


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
    
    public Claims() {
        this.iat = "2024-11-23 11:11:11";
        this.exp =  "2024-11-23 11:11:11";
        this.jti = "4";
        this.email = "pikachu@126.com";
    }

    public Claims(String sub, String iat, String exp, String jti, String email) {
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
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
