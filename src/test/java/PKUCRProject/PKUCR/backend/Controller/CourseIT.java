package PKUCRProject.PKUCR.backend.Controller;

import PKUCRProject.PKUCR.backend.Entity.Course;
import PKUCRProject.PKUCR.backend.Service.CourseService;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseService courseService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    private Course course;
    private String token;

    @BeforeEach
    void setUp() {
        // 设置一个假课程
        course = new Course();
        course.setCourseName("Software Engineering");
        course.setTeacher("Dr. Sun");
        course.setClassroom("EJ-315");
        course.setTime("2024-03-25 08:00");
        course.setWeek("1-15");
    }
    @BeforeEach
    public void loginAndGetToken() throws Exception {
        // 获取 token
        String response = mockMvc.perform(post("/api/auth/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email\":\"user1@user.com\", \"password\":\"password\"}"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        token = JsonPath.read(response, "$.token");
    }
    
    @Test
    void testGetAllCourses() throws Exception {

        mockMvc.perform(get("/course")
        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()); // 验证返回值是数组
    }

    @Test
    void testInsertCourse() throws Exception {

        mockMvc.perform(post("/course/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Software Engineering"))
                .andExpect(jsonPath("$.teacher").value("Dr. Sun"));
    }

    @Test
    void testInsertCourseWithoutLogin() throws Exception {
        mockMvc.perform(post("/course/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isForbidden());
    }

    @Test
    void testSelectCourseById() throws Exception {

        // 插入课程
        course = courseService.insert(course);
        Long courseId = course.getId();

        mockMvc.perform(get("/course/selectById?id=" + courseId)
        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.courseName").value("Software Engineering"));
    }

    @Test
    void testUpdateCourse() throws Exception {

        // 插入课程
        course = courseService.insert(course);
        String courseId = course.getCourseID();
        course.setCourseName("Advanced Software Engineering");

        mockMvc.perform(put("/course/update")
        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(course)))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteCourse() throws Exception {

        // 插入课程
        course = courseService.insert(course);
        String courseId = course.getCourseID();

        mockMvc.perform(delete("/course/delete?id=" + courseId)
        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("delete course success by id = "+courseId));
    }

    @Test
    void testDeleteCourseWithoutLogin() throws Exception {
        mockMvc.perform(delete("/course/delete?id=1"))
                .andExpect(status().isForbidden());
    }
}
