package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
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

@Tag(name = "UserController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    
    @Autowired
    private UserService userService;

    @Operation(summary = "Insert a user, return a user id")
    @PostMapping("/userInsert")
    public String insert(@RequestBody User user) {
        return userService.insert(user);
    }

    @Operation(summary = "Select a user by id")
    @GetMapping("/userSelectById")
    public User selectById(@RequestBody int id) {
        return userService.selectById(id);
    }

    @Operation(summary = "Update a user")
    @PutMapping("/userUpdate")
    public String update(@RequestBody User user) {
        return userService.update(user);
    }

    @Operation(summary = "Delete a user by id")
    @DeleteMapping("/userDelete")
    public String delete(@RequestBody int id) {
        return userService.delete(id);
    }

}
