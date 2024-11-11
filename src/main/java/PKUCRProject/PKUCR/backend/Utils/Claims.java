package PKUCRProject.PKUCR.backend.Utils;


public class Claims {
    // "主题"
    private String sub;

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
        this.sub = "text";
        this.iat = "2024-11-23 11:11:11";
        this.exp =  "2024-11-23 11:11:11";
        this.jti = "4";
        this.email = "pikachu@126.com";
    }

    public Claims(String sub, String iat, String exp, String jti, String email) {
        this.sub = sub;
        this.iat = iat;
        this.exp = exp;
        this.jti = jti;
        this.email = email;
    }    

    String getSub() {
        return sub;
    }
    String getIat() {
        return iat;
    }
    String getExp() {
        return exp;
    }
    String getJti() {
        return jti;
    }
    String getEmail() {
        return email;
    }

    void setSub(String sub) {
        this.sub = sub;
    }
    void setIat(String iat) {
        this.iat = iat;
    }
    void setExp(String exp) {
        this.exp = exp;
    }
    void setJti(String jti) {
        this.jti = jti;
    }
    void setEmail(String email) {
        this.email = email;
    }

}
