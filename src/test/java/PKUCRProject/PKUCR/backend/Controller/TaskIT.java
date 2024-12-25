package PKUCRProject.PKUCR.backend.Controller;

import PKUCRProject.PKUCR.backend.Entity.Task;
import PKUCRProject.PKUCR.backend.Service.TaskService;
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
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TaskService taskService;

    private Task task;
    private String token;

    @BeforeEach
    void setUp() {
        // 设置一个假任务
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String formattedDate = sdf.format(timestamp);
        task = new Task(1L, 34L, "Test Task",formattedDate, 0, "description");
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
    void testGetAllTasks() throws Exception {

        mockMvc.perform(get("/task")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray()); // 验证返回值是数组
    }

    @Test
    void testInsertTask() throws Exception {

        mockMvc.perform(post("/task/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task))
                        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists()); // 验证返回的任务是否包含 ID
    }

    @Test
    void testInsertTaskWithoutLogin() throws Exception {
        mockMvc.perform(post("/task/insert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(task)))
                        .andExpect(status().isForbidden());
    }

    @Test
    void testSelectTaskById() throws Exception {

        // 插入任务
        task = taskService.insert(task);
        Long taskId = task.getId();

        mockMvc.perform(get("/task/selectById")
                .param("id", "1")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Task"));
    }

    @Test
    void testUpdateTask() throws Exception {

        // 插入任务
        task = taskService.insert(task);
        Long taskId = task.getId();
        task.setName("Updated Task");

        mockMvc.perform(put("/task/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", token)
                        .content(objectMapper.writeValueAsString(task)))
                .andExpect(status().isOk())
                .andExpect(content().string("update success"));
    }

    @Test
    void testDeleteTask() throws Exception {

        // 插入任务
        Long taskId = 1L;

        mockMvc.perform(delete("/task/delete?id=" + taskId)
        .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(content().string("delete task success by id = "+taskId.toString()));
    }

    @Test
    void testDeleteTaskWithoutLogin() throws Exception {
        mockMvc.perform(delete("/task/delete?id=1"))
                .andExpect(status().isForbidden());
    }
}

