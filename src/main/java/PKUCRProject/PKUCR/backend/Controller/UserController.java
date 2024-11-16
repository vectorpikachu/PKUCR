package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
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
    @ResponseBody
    public User login(@RequestBody User user) {
        // 向数据库查询用户是否存在
        // 不存在或者密码错误则返回错误信息
        // 否则返回token
        user.setPermission(0); // 默认非管理员权限
        user = userService.login(user);
        Claims claims = new Claims();
        JwtUtils jwtUtils = new JwtUtils(claims);
        String token = jwtUtils.getToken();
        user.setToken(token);
        userService.updateToken(user); // 在数据库里更新token
        return user;
    }

    @Operation(summary = "A user register, return the user's token")
    @PostMapping("/auth/register")
    @ResponseBody
    public User register(@RequestBody User user) {
        user.setPermission(0); // 默认非管理员权限
        userService.insert(user);
        user = userService.login(user);
        Claims claims = new Claims();
        JwtUtils jwtUtils = new JwtUtils(claims);
        String token = jwtUtils.getToken();
        user.setToken(token);
        userService.updateToken(user); // 在数据库里更新token
        return user;
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
