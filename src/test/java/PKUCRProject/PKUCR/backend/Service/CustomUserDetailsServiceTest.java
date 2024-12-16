package PKUCRProject.PKUCR.backend.Service;

import PKUCRProject.PKUCR.backend.Dao.UserMapper;
import PKUCRProject.PKUCR.backend.Entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class CustomUserDetailsServiceTest {

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // 初始化Mockito注解
        testUser = new User();
        testUser.setId(19L);
        testUser.setEmail("ima@stu.pku.edu.cn");
        testUser.setPassword("sdffffff");
        testUser.setPermission(1);
    }

    @Test
    void loadUserByUsername() {
        when(userMapper.selectByEmail(testUser.getEmail())).thenReturn(testUser);
        User result = (User) customUserDetailsService.loadUserByUsername(testUser.getEmail());

        assertNotNull(result);
        assertEquals(testUser.getEmail(), result.getEmail());
    }

    @Test
    void registerUser() {
        when(userMapper.selectByEmail(testUser.getEmail())).thenReturn(testUser);

        // 调用registerUser方法，应该抛出RuntimeException
        assertThrows(RuntimeException.class, () -> {
            customUserDetailsService.registerUser(testUser);
        });
    }

    @Test
    void getUserID() {
        // 模拟数据库中查询到该email的用户
        when(userMapper.selectByEmail(testUser.getEmail())).thenReturn(testUser);

        // 调用getUserID方法
        Long result = customUserDetailsService.getUserID(testUser.getEmail());

        // 验证返回的ID
        assertEquals(testUser.getId(), result);
    }

    @Test
    void getUserById() {
        // 模拟数据库中查询到该ID的用户
        when(userMapper.selectById(testUser.getId())).thenReturn(testUser);

        // 调用getUserById方法
        User result = customUserDetailsService.getUserById(testUser.getId());

        // 验证返回的用户
        assertEquals(testUser, result);
    }

    @Test
    void getUserByEmail() {
        // 模拟数据库中查询到该email的用户
        when(userMapper.selectByEmail(testUser.getEmail())).thenReturn(testUser);

        // 调用getUserByEmail方法
        User result = customUserDetailsService.getUserByEmail(testUser.getEmail());

        // 验证返回的用户
        assertEquals(testUser, result);
    }
}