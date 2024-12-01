package main.java.PKUCRProject.PKUCR.backend.Controller;

import java.io.File;
import java.io.IOException;
import java.net.http.HttpHeaders;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;

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

    @Operation(summary = "Upload a resource file")
    @PostMapping("/api/resource/material/{courseId}")
    public ResponseEntity<?> uploadResource(
            @PathVariable("courseId") String courseId, 
            @Valid @RequestBody main.java.PKUCRProject.PKUCR.backend.Entity.Resource resource) {

        // 确认上传用户登录状态
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        //保存上传用户信息
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String userEmail = userDetails.getUsername();
        Long userId = userService.getUserID(userEmail);

        resource.setCourseID(courseId);
        resource.setUserID(userId);
        resource.setTime(LocalDateTime.now().toString());

        //生成唯一的resourceID
        String resourceID = UUID.randomUUID().toString();
        resource.setResourceID(resourceID);

        String savePath = "/data/resources/" + courseId + "/";
        String fullFilePath = "/data/resources/" + courseId + "/" + resource.getID() + "_" + resource.getFilename();

        // 保存文件（文件内容需要在 POST BODY 中提供）
        try {
            Path path = Paths.get(fullFilePath);
            Files.createDirectories(path.getParent());
            Files.write(path, resource.getFileContent().getBytes()); // 文件内容需要在BODY中注明
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save file");
        }
        resource.setSavePath(fullFilePath);

        resourceService.insertResource(resource);

        // 根据文件名生成具体URL并返回
        String fileUrl = "/api/resource/material/"+resource.getCourseID()+"/"+resource.getID();
        Map<String, String> response = Map.of(
            "filename", resource.getFilename(),
            "url", fileUrl
        );

        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Download a resource")
    @GetMapping("/api/resource/material/{courseId}/{resourceID}")
    public ResponseEntity<?> downloadResource(@PathVariable("courseId") String courseID, @PathVariable("resourceID") String resourceID) {
        main.java.PKUCRProject.PKUCR.backend.Entity.Resource resource = resourceService.getResource(courseID, resourceID);
        
        // 检查文件是否存在
        Strng filePath = resource.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("File not found");
        }

        // 返回文件内容
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; resourceID=\"" + resourceID + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
        }
    }
}
