package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import PKUCRProject.PKUCR.backend.Entity.Task;
import PKUCRProject.PKUCR.backend.Service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "TaskController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class TaskController {
    
    @Autowired
    private TaskService taskService;

    @Operation(summary = "Insert a task, return a task id")
    @PostMapping("/insert")
    public String insert(@RequestBody Task task) {
        return taskService.insert(task);
    }

    /* 使用的是PathVariable */
    @Operation(summary = "Select a task by id")
    @GetMapping("/selectById/{id}")
    public ResponseEntity<Task> selectById(@PathVariable int id) {
        Task task = taskService.selectById(id);
        if (task == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(task);
        }
    }

    @Operation(summary = "Update a task")
    @PutMapping("/update")
    public String update(@RequestBody Task task) {
        return taskService.update(task);
    }

    @Operation(summary = "Delete a task by id")
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        return taskService.delete(id);
    }

}
