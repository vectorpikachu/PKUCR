package PKUCRProject.PKUCR.backend.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import PKUCRProject.PKUCR.backend.Entity.Comment;
import PKUCRProject.PKUCR.backend.Entity.Material;
import PKUCRProject.PKUCR.backend.Service.AllCourseService;
import PKUCRProject.PKUCR.backend.Service.CommentService;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import PKUCRProject.PKUCR.backend.Service.MaterialService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;

@Tag(name = "CourseController")
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
            jsonObject.set(course.getCourseID(), courseObject);
        });
        return ResponseEntity.ok(jsonObject);
    }

    // 获取某门课的详情
    @Operation(summary = "Return the detail of a course")
    @GetMapping("/api/resource/{courseId}") // 前端拼错了
    public ResponseEntity<?> selectOne(@PathVariable("courseId") String courseId) {
        // 返回一个JSON
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode jsonObject = mapper.createObjectNode();
        /*
         * {
         *  "comments": [
         * {"user": "Alice", "content": "Good course!"},
         * {"user": "Bob", "content": "Bad course!"}
         * ],
         * "materials": [
         * {"name": "PPT", "url": "/api/resource/material/{courseId}/{filename}"},
         * {"name": "Video", "url": "/api/resource/material/{courseId}/{filename}"}
         * ]
         * }
         */
        //BasicCourse course = allCourseService.selectByCourseID(courseId);
        // 获取这门课的所有评论
        List<Comment> comments = commentService.selectByCourseID(courseId);
        ArrayNode commentsArray = mapper.createArrayNode();
        comments.forEach(comment -> {
            ObjectNode commentObject = mapper.createObjectNode();
            Long userId = comment.getUserID();
            String userEmail = userService.getUserById(userId).getEmail();
            commentObject.put("user", userEmail);
            commentObject.put("content", comment.getContent());
            commentsArray.add(commentObject);
        });
        jsonObject.set("comments", commentsArray);
        // 获取这门课的所有资料
        List<Material> materials = materialService.selectByCourseID(courseId);
        ArrayNode materialsArray = mapper.createArrayNode();
        materials.forEach(material -> {
            ObjectNode materialObject = mapper.createObjectNode();
            materialObject.put("name", material.getName());
            materialObject.put("url", "/api/resource/material/" + courseId + "/" + material.getName());
            materialsArray.add(materialObject);
        });
        jsonObject.set("materials", materialsArray);
        return ResponseEntity.ok(jsonObject);
    }
}
