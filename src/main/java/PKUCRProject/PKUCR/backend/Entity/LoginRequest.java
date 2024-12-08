package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "LoginRequest")
public class LoginRequest {

    @Schema(name = "email", required = true, example = "pikachu@126.com")
    private String email;

    @Schema(name = "password", required = true, example = "123456")
    private String password;

    // 默认构造函数
    public LoginRequest() {}

    // 全参数构造函数
    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
    /** 
     * @return String
     */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
