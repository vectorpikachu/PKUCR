package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import PKUCRProject.PKUCR.backend.Entity.BasicCourse;
import PKUCRProject.PKUCR.backend.Entity.BasicCourseRequest;
import PKUCRProject.PKUCR.backend.Entity.Comment;
import PKUCRProject.PKUCR.backend.Entity.CommentRequest;
import PKUCRProject.PKUCR.backend.Entity.Material;
import PKUCRProject.PKUCR.backend.Entity.User;
import PKUCRProject.PKUCR.backend.Service.AllCourseService;
import PKUCRProject.PKUCR.backend.Service.CommentService;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import PKUCRProject.PKUCR.backend.Service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "AllCourseController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class AllCourseController {
    
    @Autowired
    private AllCourseService allCourseService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private CustomUserDetailsService userService;


    /** 返回所有课程 + 相关信息 (评论, 资料)
     * @return ResponseEntity<?>
     */
    @Operation(summary = "Return all courses")
    @GetMapping("/api/resource")
    public ResponseEntity<?> selectAll() {
        // 返回一个JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        /*
         * {
         * "courseId": {
         *   "name": "courseName",
         *   "category": "courseCategory",
         * },
         *  "04834220": {
         *   "name": "软件工程",
         *   "category": "计算机",
         * }
         * }
         */
        allCourseService.selectAll().forEach(course -> {
            ObjectNode courseObject = mapper.createObjectNode();
            courseObject.put("name", course.getCourseName());
            courseObject.put("category", course.getCategory());
            courseObject.put("ID", course.getId());
            jsonObject.set(course.getCourseID(), courseObject);
        });
        return ResponseEntity.ok(jsonObject);
    }

    // 获取某门课的详情
    @Operation(summary = "Return the detail of a course")
    @GetMapping("/api/resource/{courseId}")
    public ResponseEntity<?> selectOne(@PathVariable("courseId") String courseId) {
        // 返回一个JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        /*
         * {
         *  "comments": [
         * {"user": "Alice", "comment": "Good course!", "ID": 1},
         * {"user": "Bob", "comment": "Bad course!", "ID": 2}
         * ],
         * "materials": [
         * {"filename": "PPT", "url": "/api/resource/material/{courseId}/{filename}"},
         * {"filename": "Video", "url": "/api/resource/material/{courseId}/{filename}"}
         * ]
         * }
         */
        
        // 获取这门课的所有评论
        List<Comment> comments = commentService.selectByCourseID(courseId);
        ArrayNode commentsArray = mapper.createArrayNode();
        comments.forEach(comment -> {
            ObjectNode commentObject = mapper.createObjectNode();
            Long userId = comment.getUserID();
            String userEmail = userService.getUserById(userId).getEmail();
            commentObject.put("user", userEmail);
            commentObject.put("comment", comment.getContent());
            commentObject.put("ID", comment.getID());
            commentsArray.add(commentObject);
        });
        jsonObject.set("comments", commentsArray);
        
        // 获取这门课的所有资料
        List<Material> materials = materialService.selectByCourseID(courseId);
        ArrayNode materialsArray = mapper.createArrayNode();
        materials.forEach(material -> {
            ObjectNode materialObject = mapper.createObjectNode();
            materialObject.put("filename", material.getFilename());
            materialObject.put("url", "/api/resource/material/" + courseId + "/" + material.getFilename());
            materialObject.put("ID", material.getID());
            materialsArray.add(materialObject);
        });
        jsonObject.set("materials", materialsArray);
        return ResponseEntity.ok(jsonObject);
    }

    @Operation(summary = "Add a basic course")
    @PostMapping("/api/resource/{courseId}")
    public ResponseEntity<?> insert(@PathVariable String courseId, @Valid @RequestBody BasicCourseRequest basicCourseRequest) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }

        User user = (User) authentication.getPrincipal();
        if (user.getPermission() != 1) {
            return ResponseEntity.badRequest().body("You are not a teacher");
        }
        
        BasicCourse course = new BasicCourse();
        course.setCourseID(courseId);
        course.setCourseName(basicCourseRequest.getCourseName());
        course.setCategory(basicCourseRequest.getCategory());
        allCourseService.insert(course);
        return ResponseEntity.ok(course);
    }

    /**
     * 添加评价
     * @param courseId 一个路径变量 courseId = 课程的 ID
     * @param commentRequest 一个请求体 CommentRequest
     * @return
     */
    @Operation(summary = "Add a comment")
    @PostMapping("/api/resource/comment/{courseId}")
    public ResponseEntity<?> addComment(@PathVariable("courseId") String courseId, @Valid @RequestBody CommentRequest commentRequest) {
        
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
    
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        Comment comment = new Comment();
        comment.setCourseID(courseId);
        comment.setContent(commentRequest.getComment());
        comment.setUserID(userService.getUserID(commentRequest.getUser()));
        
        LocalDateTime date = LocalDateTime.now();
        comment.setTime(date.toString());

        commentService.insertComment(comment);
        jsonObject.put("status", "success");
        // 返回一个 ID of Comment
        jsonObject.put("ID", comment.getID());
        return ResponseEntity.ok(jsonObject);
    }
    
    /**
     * 删除评论
     * @param id 一个路径变量 ID = 评论的 ID
     */
    @Operation(summary = "Delete a comment")
    @DeleteMapping("/api/resource/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        commentService.deleteComment(id);
        jsonObject.put("status", "delete success");
        return ResponseEntity.ok(jsonObject);
    }
}


