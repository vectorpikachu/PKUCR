package PKUCRProject.PKUCR.backend.Entity;

public class TokenResponse {
    private String username;
    private String token;
    
    public TokenResponse(String username, String token) {
        this.username = username;
        this.token = token;
    }

    
    /** 返回当前d的token
     * @return String
     */
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    /** 返回当前的用户名
     * @return String
     */
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
