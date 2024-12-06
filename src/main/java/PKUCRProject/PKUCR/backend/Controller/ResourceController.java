package PKUCRProject.PKUCR.backend.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import PKUCRProject.PKUCR.backend.Service.ResourceService;
import PKUCRProject.PKUCR.backend.Entity.Resource;
import PKUCRProject.PKUCR.backend.Entity.User;

@Tag(name = "ResourceController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ResourceController {
    @Autowired
    private ResourceService resourceService;

    /**
     * 上传文件
     * @param courseID
     * @param fileName
     * @param file
     * @return URL
     */
    @Operation(summary = "Upload a resource file")
    @PostMapping("/api/resource/material/{courseID}")
    public ResponseEntity<?> uploadResource(
            @PathVariable("courseID") String courseID,
            @RequestParam("fileName") String fileName,
            @RequestParam("file") MultipartFile file) {

        /*// 确认上传用户登录状态
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        //保存上传用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        Long userId = userService.getUserID(userEmail);
        */
        Long userId = 0L; //暂时不考虑作者信息

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        Resource resource = new Resource();
        resource.setCourseID(courseID);
        resource.setUserID(userId);
        resource.setFileName(fileName);
        String filePath = "/data/resources/" + courseID + "/"; //其实是dir
        resource.setFilePath(filePath);
        resource.setTime(LocalDateTime.now().toString());

        resourceService.insertResource(resource);
        String fullFilePath = "/data/resources/" + courseID + "/" + resource.getResourceID() + "_" + resource.getFileName();

        // 保存文件（文件内容需要在 POST BODY 中提供）
        try {
            Path path = Paths.get(fullFilePath);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path); // 文件内容需要在BODY中注明
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("IOException: fail to save file.");
        }

        // 根据文件名生成具体URL并返回
        String fileUrl = "/api/resource/material/"+resource.getCourseID().toString()+"/"+resource.getResourceID().toString();
        Map<String, String> response = Map.of(
            "filename", resource.getFileName(),
            "url", fileUrl
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 下载文件
     * @param courseID
     * @param resourceID
     * @return 文件内容
     */
    @Operation(summary = "Download a resource")
    @GetMapping("/api/resource/material/{courseID}/{resourceID}")
    public ResponseEntity<?> downloadResource(@PathVariable("courseID") String courseID, @PathVariable("resourceID") Long resourceID) {
        PKUCRProject.PKUCR.backend.Entity.Resource resource = resourceService.getResource(courseID, resourceID);
        
        // 检查文件是否存在
        String fileDir = resource.getFilePath();
        String filePath = fileDir + resource.getResourceID().toString()+'_'+resource.getFileName();
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(filePath);
        }

        // 返回文件内容
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; resourceID=\"" + resourceID.toString() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
        }
    }
}
