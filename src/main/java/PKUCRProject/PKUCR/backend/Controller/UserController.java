package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import PKUCRProject.PKUCR.backend.Entity.LoginRequest;
import PKUCRProject.PKUCR.backend.Entity.RegisterRequest;
import PKUCRProject.PKUCR.backend.Entity.TokenResponse;
import PKUCRProject.PKUCR.backend.Entity.User;
import PKUCRProject.PKUCR.backend.Utils.CryptoUtils;
import PKUCRProject.PKUCR.backend.Utils.JwtUtils;

@Tag(name = "UserController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    private final AuthenticationManager authenticationManager;

    public UserController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    /**
     * @param loginRequest 用户登录请求
     * @return ResponseEntity<?> 返回用户的 token
     */
    @Operation(summary = "A user loging in, return the user's token")
    @PostMapping("/api/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 验证用户凭证
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // 获取用户详细信息
            final User user = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

            // 生成 JWT 令牌
            final String token = jwtUtils.getToken(user.getUsername());
            // 返回包含令牌的响应, 包装一下
            TokenResponse response = new TokenResponse(user.getUsernameReal(), token);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @Operation(summary = "A user register, return the user's token")
    @PostMapping("/api/auth/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest) {
        // 验证用户是否存在
        try {
            customUserDetailsService.loadUserByUsername(registerRequest.getEmail());
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        } catch (Exception e) {
            // 创建新用户
            System.out.println("Registering user: " + registerRequest.getEmail());
            System.out.println("Username: " + registerRequest.getUsername());
            String encodedPassword = CryptoUtils.encodePassword(registerRequest.getPassword());
            User user = new User(registerRequest.getEmail(), encodedPassword);
            user.setUsername(registerRequest.getUsername());
            customUserDetailsService.registerUser(user);
            final String token = jwtUtils.getToken(registerRequest.getEmail());
            TokenResponse response = new TokenResponse(user.getUsernameReal(), token);
            return ResponseEntity.ok().body(response);
        }
    }
}
