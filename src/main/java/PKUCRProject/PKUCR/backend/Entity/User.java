package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User")
public class User {
    @Schema(name = "id", required = false, example = "1")
    private int id;

    @Schema(name = "email", required = true, example = "pikachu@126.com")
    private String email;

    @Schema(name = "password", required = true, example = "123456")
    private String password;

    @Schema(name = "permission", required = false, example = "1")
    private int permission;

    @Schema(name = "token", required = false, example = "eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA")
    private String token;

    public User() {
        this.id = 1;
        this.email = "pikacu@126.com";
        this.password = "22222";
    }

    public User(int id, String email, String password, int permission) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.permission = permission;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getId() {
        return id;
    }
    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public int getPermission() {
        return permission;
    }
    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setPermission(int permission) {
        this.permission = permission;
    }
    public void setToken(String token) {
        this.token = token;
    }

}
