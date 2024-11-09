package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User")
public class User {
    @Schema(name = "id", required = false, example = "1")
    private int id;

    @Schema(name = "password", required = true, example = "123456")
    private String password;

    @Schema(name = "email", required = true, example = "pikachu@126.com")
    private String email;

    @Schema(name = "permission", required = false, example = "1")
    private int permission;

    public User(int id, String password, String email, int permission) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.permission = permission;
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

}
