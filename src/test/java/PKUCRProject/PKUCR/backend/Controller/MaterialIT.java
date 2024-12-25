package PKUCRProject.PKUCR.backend.Controller;

import PKUCRProject.PKUCR.backend.Service.MaterialService;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;
import PKUCRProject.PKUCR.backend.Entity.Material;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.mock.web.MockMultipartFile;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import com.jayway.jsonpath.JsonPath;
import java.io.File;
import java.util.Arrays;
import java.nio.file.Files;
import java.io.FileInputStream;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional  // 确保每个测试用例后回滚数据库操作，避免影响其他测试
public class MaterialIT {

    @Autowired
    private MockMvc mockMvc;

    private String token;

    private String test_file_path = "src/test/resources/testfile.txt";

    @Autowired
    private MaterialService materialService;  // 直接注入 MaterialService 来验证数据库操作

    
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
    public void testUploadSuccess() throws Exception {
        File file = new File(test_file_path);
        MockMultipartFile mockFile = new MockMultipartFile("file", "testfile.txt", "text/plain", new FileInputStream(file));

        String response = mockMvc.perform(multipart("/api/resource/material/CS101")
                .file(mockFile)
                .param("fileName", "testfile.txt")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value("testfile.txt"))
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();

        // 验证数据库中是否插入了 Material 条目
        Long materialId = Long.valueOf(JsonPath.read(response, "$.id"));
        Material material = materialService.selectByID(materialId);
        assertNotNull(material);  // 断言 Material 对象不为空
        assertEquals("CS101", material.getCourseID());  // 验证 courseID 是否正确

        // 验证文件是否保存到服务器目录
        String filePath = "/data/materials/" + "/" + material.getCourseID() + "/" + material.getID() + "_" + material.getFilename();
        File savedFile = new File(filePath);
        assertTrue(savedFile.exists());  // 断言文件确实存在
    }

    @Test
    public void testUploadTokenerror() throws Exception {
        File file = new File("src/test/resources/testfile.txt");
        MockMultipartFile mockFile = new MockMultipartFile("file", "testfile.txt", "text/plain", new FileInputStream(file));

        mockMvc.perform(multipart("/api/resource/material/CS101")
                .file(mockFile)
                .param("fileName", "testfile.txt")
                .header("Authorization", "token"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testUploadFilenameSpace() throws Exception {
        File file = new File("src/test/resources/testfile.txt");
        MockMultipartFile mockFile = new MockMultipartFile("file", "testfile.txt", "text/plain", new FileInputStream(file));

        String response = mockMvc.perform(multipart("/api/resource/material/CS101")
                .file(mockFile)
                .param("fileName", "")
                .header("Authorization", token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.filename").value(""))
                .andExpect(jsonPath("$.id").exists())
                .andReturn().getResponse().getContentAsString();
        
        // 验证数据库中是否插入了 Material 条目
        Long materialId = Long.valueOf(JsonPath.read(response, "$.id"));
        Material material = materialService.selectByID(materialId);
        assertNotNull(material);  // 断言 Material 对象不为空
        assertEquals("CS101", material.getCourseID());  // 验证 courseID 是否正确

        // 验证文件是否保存到服务器目录
        String filePath = "/data/materials/" + "/" + material.getCourseID() + "/" + material.getID() + "_" + material.getFilename();
        File savedFile = new File(filePath);
        assertTrue(savedFile.exists());  // 断言文件确实存在
    }

    @Test
    public void testDownloadSuccess() throws Exception {
        // 准备测试数据
        String courseID = "CS101";
        Long id = 16L;
        File file = new File(test_file_path);
        
        // 执行请求并验证响应
        MvcResult result = mockMvc.perform(get("/api/resource/material/{courseID}/{id}", courseID, id).header("Authorization", token))
            .andExpect(status().isOk())  // HTTP 200 OK
            .andExpect(header().string("Content-Disposition", "attachment; filename=testfile.txt"))  // 验证文件名
            .andReturn();  
        
        byte[] downloadedFileContent = result.getResponse().getContentAsByteArray();
        byte[] localFileContent = Files.readAllBytes(file.toPath());
        // 比较下载的文件与本地文件是否一致
        assert Arrays.equals(downloadedFileContent, localFileContent) : "Files are not the same!";
   
    }

    @Test
    public void testDownloadFileNotFound() throws Exception {
        // 准备测试数据
        String courseID = "CS101";
        Long id = 999L;  // 假设此 ID 不存

        // 执行请求并验证响应
        mockMvc.perform(get("/api/resource/material/{courseID}/{id}", courseID, id).header("Authorization", token))
            .andExpect(status().isNotFound())  // HTTP 400 Bad Request
            .andExpect(content().string("Material not found in the database"));  // 验证错误消息
    }

    @Test
    public void testDeleteSuccess() throws Exception {
        // 假设文件 ID 为 1，文件存在且是当前用户上传的
        Long fileId = 19L;

        Material material = materialService.selectByID(fileId);
        String filePath = "/data/materials/" + "/" + material.getCourseID() + "/" + material.getID() + "_" + material.getFilename();
        
        // 模拟用户登录和进行删除请求
        MvcResult result = mockMvc.perform(delete("/api/resource/material/{id}", fileId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andExpect(status().isOk()) // 成功请求返回 200
                .andExpect(content().string("Material deleted successfully"))
                .andReturn();
        
        material = materialService.selectByID(fileId);
        assertNull(material);  // 断言 Material 对象为空

        // 验证文件是否保存到服务器目录
        File savedFile = new File(filePath);
        assertFalse(savedFile.exists());  // 断言文件已删除
    }

    @Test
    void testDeleteFileNotFound() throws Exception {
        // 假设文件 ID 为 999，文件不存在
        Long fileId = 999L;

        // 模拟删除请求
        MvcResult result = mockMvc.perform(delete("/api/resource/material/{id}", fileId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andExpect(status().isNotFound()) // 返回 404 文件未找到
                .andExpect(content().string("file not found"))
                .andReturn();
    }

    @Test
    void testDeletePermissionDenied() throws Exception {
        Long fileId = 16L;
        Material material = materialService.selectByID(fileId);
        String filePath = "/data/materials/" + "/" + material.getCourseID() + "/" + material.getID() + "_" + material.getFilename();

        // 模拟删除请求
        MvcResult result = mockMvc.perform(delete("/api/resource/material/{id}", fileId)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", token))
                .andExpect(status().isBadRequest()) // 权限拒绝返回 400
                .andExpect(content().string("Permission denied, users can only delete files they uploaded themselves."))
                .andReturn();
        
        material = materialService.selectByID(fileId);
        assertNotNull(material);  
        
        File savedFile = new File(filePath);
        assertTrue(savedFile.exists()); 
    }
}