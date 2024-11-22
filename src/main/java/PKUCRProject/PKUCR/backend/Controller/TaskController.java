package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;

import PKUCRProject.PKUCR.backend.Entity.Task;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import PKUCRProject.PKUCR.backend.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "TaskController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Operation(summary = "Return a user's all tasks")
    @GetMapping("/task")
    public ResponseEntity<?> init() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        int userId = customUserDetailsService.getUserID(username);
        return ResponseEntity.ok(taskService.selectByUserID(userId));
    }

    @Operation(summary = "Insert a task, return a task id")
    @PostMapping("/task/insert")
    public ResponseEntity<?> insert(@Valid @RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        if (task.getName() == null) {
            return ResponseEntity.badRequest().body("Please provide the task's name");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        int userId = customUserDetailsService.getUserID(username);
        task.setUser_id(userId);
        // System.out.println(task);
        task = taskService.insert(task);
        return ResponseEntity.ok(task);
    }

    @Operation(summary = "Select a task by id")
    @GetMapping("/task/selectById")
    public ResponseEntity<?> selectById(@Valid @RequestParam int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        Task task = taskService.selectById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(task);
        }
    }

    @Operation(summary = "Update a task")
    @PutMapping("/task/update")
    public ResponseEntity<?> update(@Valid @RequestBody Task task) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        int userId = customUserDetailsService.getUserID(username);
        task.setUser_id(userId);
        return ResponseEntity.ok(taskService.update(task));
    }

    @Operation(summary = "Delete a task by id")
    @DeleteMapping("/task/delete")
    public ResponseEntity<?> delete(@Valid @RequestParam int id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        return ResponseEntity.ok(taskService.delete(id));
    }

}
