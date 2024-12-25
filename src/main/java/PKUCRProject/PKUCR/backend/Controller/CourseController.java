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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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
     * 返回一个用户的所有课程, 在 Clendar里面比较有用
     * 格式为: {"name": "SE", "teacher":"Sun", "classroom":"EJ-315",
     * "time": {"date": }}
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

        ObjectMapper mapper = new ObjectMapper();
        ArrayNode courseList = mapper.createArrayNode();
        for (var course : courseService.selectByUserID(userId)) {
            ObjectNode courseObject = mapper.createObjectNode();
            courseObject.put("id", course.getId());
            courseObject.put("name", course.getCourseName());
            courseObject.put("teacher", course.getTeacher());
            courseObject.put("classroom", course.getClassroom());
            ArrayNode timeArray = mapper.createArrayNode();
            for (var time : course.getTime()) {
                ObjectNode timeObject = mapper.createObjectNode();
                timeObject.put("startDate", time.getStartDate());
                timeObject.put("endDate", time.getEndDate());
                timeObject.put("startTime", time.getStartTime());
                timeObject.put("endTime", time.getEndTime());
                timeObject.put("frequency", time.getFrequency());
                timeArray.add(timeObject);
            }
            courseObject.set("time", timeArray);
            courseObject.put("link", course.getLink());
            String link = "/api/resource/material/" + course.getCourseID();
            courseObject.put("link", link);
            courseList.add(courseObject);
        }
        return ResponseEntity.ok(courseList);
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

        // System.out.println(course.toString());

        course.setUser_id(userId);
        course.setLink("api/resource/" + course.getCourseID());
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
