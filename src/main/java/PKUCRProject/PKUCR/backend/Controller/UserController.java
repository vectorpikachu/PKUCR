package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import PKUCRProject.PKUCR.backend.Service.UserService;
import PKUCRProject.PKUCR.backend.Entity.User;
import PKUCRProject.PKUCR.backend.Utils.Claims;
import PKUCRProject.PKUCR.backend.Utils.JwtUtils;

@Tag(name = "UserController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "A user loging in, return the user's token")
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody User user) {
        // 向数据库查询用户是否存在
        // 不存在或者密码错误则返回错误信息
        // 否则返回token
        if (user.getEmail() == null || user.getPassword() == null) {
            return ResponseEntity.status(500).body("User email or password is empty");
        }
        if (user.getPermission() == null) {
            user.setPermission(0); // 默认非管理员权限
        }
        try {
            user = userService.login(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed");
        }

        Claims claims = new Claims(user.getEmail());
        JwtUtils jwtUtils = new JwtUtils(claims);
        String token = jwtUtils.getToken();

        user.setToken(token);
        userService.updateToken(user); // 在数据库里更新token

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "A user register, return the user's token")
    @PostMapping("/auth/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        user.setPermission(0); // 默认非管理员权限

        try {
            userService.insert(user);
        } catch (Exception e) {
            /* 返回一个错误, 报告已经注册过 */
            return ResponseEntity.status(500).body("User already exists");
        }

        /* 为这个用户新建tasks数据表 */
        try {
            userService.createTaskTable("tasks" + user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Create task table failed");
        }

        /* 为这个用户建立courses数据表 */
        try {
            userService.createCourseTable("courses" + user.getId());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Create course table failed");
        }

        try {
            user = userService.login(user);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Login failed");
        }

        Claims claims = new Claims(user.getEmail());
        JwtUtils jwtUtils = new JwtUtils(claims);
        String token = jwtUtils.getToken();

        user.setToken(token);
        userService.updateToken(user); // 在数据库里更新token

        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Select a user by id")
    @GetMapping("/user/selectById")
    public User selectById(@RequestBody int id) {
        return userService.selectById(id);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/user/update")
    public String update(@RequestBody User user) {
        return userService.update(user);
    }

    @Operation(summary = "Delete a user by id")
    @DeleteMapping("/user/delete")
    public String delete(@RequestBody int id) {
        return userService.delete(id);
    }

}
