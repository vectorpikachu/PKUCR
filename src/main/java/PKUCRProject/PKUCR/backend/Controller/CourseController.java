package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import PKUCRProject.PKUCR.backend.Entity.Course;
import PKUCRProject.PKUCR.backend.Service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CourseController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
    
    @Autowired
    private CourseService courseService;

    @Operation(summary = "Insert a course, return a course id")
    @PostMapping("/course/insert")
    @ResponseBody
    public Course insert(@RequestBody Course course) {
        return courseService.insert(course);
    }

    /* 使用的是PathVariable */
    @Operation(summary = "Select a course by id")
    @GetMapping("/course/selectById/{id}")
    public ResponseEntity<Course> selectById(@PathVariable int id) {
        Course course = courseService.selectById(id);
        if (course == null) {
            return ResponseEntity.notFound().build();
        }
        else {
            return ResponseEntity.ok(course);
        }
    }

    @Operation(summary = "Update a course")
    @PutMapping("/course/update")
    public String update(@RequestBody Course course) {
        return courseService.update(course);
    }

    @Operation(summary = "Delete a course by id")
    @DeleteMapping("/course/delete/{id}")
    public String delete(@PathVariable int id) {
        return courseService.delete(id);
    }
}
