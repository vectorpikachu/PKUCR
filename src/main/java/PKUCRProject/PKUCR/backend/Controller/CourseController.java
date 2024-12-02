package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import PKUCRProject.PKUCR.backend.Entity.Course;
import PKUCRProject.PKUCR.backend.Service.CourseService;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "CourseController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CourseController {
    
    @Autowired
    private CourseService courseService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    
    /** 
     * @return ResponseEntity<?>
     */
    @Operation(summary = "Return a user's all courses")
    @GetMapping("/course")
    public ResponseEntity<?> initByUserID() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Long userId = customUserDetailsService.getUserID(username);
        return ResponseEntity.ok(courseService.selectByUserID(userId));
    }

    @Operation(summary = "Insert a course, return a course id")
    @PostMapping("/course/insert")
    public ResponseEntity<?> insert(@Valid @RequestBody Course course) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Long userId = customUserDetailsService.getUserID(username);

        course.setUser_id(userId);
        course = courseService.insert(course);
        return ResponseEntity.ok(course);
    }

    @Operation(summary = "Select a course by id")
    @GetMapping("/course/selectById")
    public ResponseEntity<?> selectById(@Valid @RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
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
    public ResponseEntity<?> update(@Valid @RequestBody Course course) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Long userId = customUserDetailsService.getUserID(username);
        course.setUser_id(userId);
        return ResponseEntity.ok(courseService.update(course));
    }

    @Operation(summary = "Delete a course by id")
    @DeleteMapping("/course/delete")
    public ResponseEntity<?> delete(@Valid @RequestParam Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        return ResponseEntity.ok(courseService.delete(id));
    }
}
