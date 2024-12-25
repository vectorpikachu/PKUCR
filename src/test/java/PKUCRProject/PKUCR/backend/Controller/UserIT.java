package PKUCRProject.PKUCR.backend.Controller;

import PKUCRProject.PKUCR.backend.Entity.LoginRequest;
import PKUCRProject.PKUCR.backend.Entity.TokenResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // 初始化工作，例如清理数据库等（如果需要）
    }

    @Test
    void testLoginSuccess() throws Exception {
        // 假设已经注册了该用户，提供登录请求的数据
        LoginRequest loginRequest = new LoginRequest("user1@user.com", "password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());  // 验证返回的 token 是否存在
    }

    @Test
    void testLoginInvalidCredentials() throws Exception {
        // 提供错误的凭证
        LoginRequest loginRequest = new LoginRequest("user1@user.com", "wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Invalid credentials")); 
    }

    @Test
    void testLoginUnknownUser() throws Exception {
        // 提供错误的凭证
        LoginRequest loginRequest = new LoginRequest("user99@user.com", "password");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isForbidden())
                .andExpect(content().string("Invalid credentials")); 
    }

    @Test
    void testRegisterSuccess() throws Exception {
        // 提供注册请求的数据
        LoginRequest loginRequest = new LoginRequest("newuser1@user.com", "password");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());  // 验证返回的 token 是否存在
    }

    @Test
    void testRegisterEmailAlreadyTaken() throws Exception {
        // 假设该邮箱已被注册
        LoginRequest loginRequest = new LoginRequest("user1@user.com", "password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Error: Email is already taken!")); 
    }
}
