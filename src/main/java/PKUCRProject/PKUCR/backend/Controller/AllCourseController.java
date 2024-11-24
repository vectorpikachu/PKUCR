package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import PKUCRProject.PKUCR.backend.Service.AllCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "CourseController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AllCourseController {
    
    @Autowired
    private AllCourseService allCourseService;

    @Operation(summary = "Return all courses")
    @GetMapping("/allcourse")
    public ResponseEntity<?> selectAll() {
        return ResponseEntity.ok(allCourseService.selectAll());
    }
}
