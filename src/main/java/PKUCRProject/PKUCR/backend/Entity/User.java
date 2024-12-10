package PKUCRProject.PKUCR.backend.Entity;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "User")
public class User implements UserDetails {

    @Schema(name = "id", required = false, example = "1")
    private Long id;

    @Schema(name = "email", required = true, example = "pikachu@126.com")
    private String email;

    @Schema(name = "password", required = true, example = "123456")
    private String password;

    @Schema(name = "permission", required = false, example = "1")
    private Integer permission;

    @Schema(name = "token", required = false, example = "eyJhbGciOiJIUzI1NiJ9.SGVsbG8sIHdvcmxkIQ.onO9Ihudz3WkiauDO2Uhyuz0Y18UASXlSc1eS0NkWyA")
    private String token;

    private String username;

    public User() {
        this.email = "pikachu@126.com";
        this.password = "22222";
        this.username = "Veccccccc";
    }

    public User(Long id, String email, String password, int permission, String username) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.permission = permission;
        this.username = username;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    
    /** 
     * @return Long
     */
    public Long getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public Integer getPermission() {
        return permission;
    }

    public String getToken() {
        return token;
    }

    /**
     * 这是一个真的获取用户名的方法
     * @return 真的用户名
     */
    public String getUsernameReal() {
        return username;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPermission(Integer permission) {
        this.permission = permission;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        if (permission != null) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + permission));
        }
        return authorities;
    }

    /**
     * 获取实际上是email
     */
    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}