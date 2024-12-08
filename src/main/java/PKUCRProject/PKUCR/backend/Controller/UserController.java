package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import PKUCRProject.PKUCR.backend.Entity.LoginRequest;
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
     * @param loginRequest
     * @return ResponseEntity<?>
     */
    @Operation(summary = "A user loging in, return the user's token")
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // 验证用户凭证
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );

            // 获取用户详细信息
            final UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getEmail());

            // 生成 JWT 令牌
            final String token = jwtUtils.getToken(userDetails.getUsername());
            // 返回包含令牌的响应, 包装一下
            TokenResponse response = new TokenResponse(token);
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid credentials");
        }
    }

    @Operation(summary = "A user register, return the user's token")
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody LoginRequest loginRequest) {
        // 验证用户是否存在
        try {
            customUserDetailsService.loadUserByUsername(loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Error: Email is already taken!");
        } catch (Exception e) {
            // 创建新用户
            String encodedPassword = CryptoUtils.encodePassword(loginRequest.getPassword());
            User user = new User(loginRequest.getEmail(), encodedPassword);
            customUserDetailsService.registerUser(user);
            final String token = jwtUtils.getToken(loginRequest.getEmail());
            TokenResponse response = new TokenResponse(token);
            return ResponseEntity.ok().body(response);
        }
    }
}
