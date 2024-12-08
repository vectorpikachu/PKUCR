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

import PKUCRProject.PKUCR.backend.Service.MaterialService;
import PKUCRProject.PKUCR.backend.Entity.Material;
import PKUCRProject.PKUCR.backend.Entity.User;

@Tag(name = "MaterialController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    /**
     * 上传文件
     * @param courseID
     * @param fileName
     * @param file
     * @return URL
     */
    @Operation(summary = "Upload a material file")
    @PostMapping("/api/resource/material/{courseID}")
    public ResponseEntity<?> uploadMaterial(
            @PathVariable("courseID") String courseID,
            @RequestParam("fileName") String filename,
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
        Long userID = 0L; //暂时不考虑作者信息

        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("No file uploaded");
        }

        Material material = new Material();
        material.setCourseID(courseID);
        material.setUserID(userID);
        material.setFilename(filename);
        String filedir = "/data/materials/" + courseID + "/"; 
        material.setFiledir(filedir);
        material.setTime(LocalDateTime.now().toString());

        materialService.insertMaterial(material);
        String fullFilePath = "/data/materials/" + courseID + "/" + material.getID() + "_" + material.getFilename();

        // 保存文件（文件内容需要在 POST BODY 中提供）
        try {
            Path path = Paths.get(fullFilePath);
            Files.createDirectories(path.getParent());
            Files.copy(file.getInputStream(), path); // 文件内容需要在BODY中注明
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("IOException: fail to save file.");
        }

        // 根据文件名生成具体URL并返回
        String fileUrl = "/api/resource/material/"+material.getCourseID()+"/"+material.getID().toString();
        Map<String, String> response = Map.of(
            "filename", material.getFilename(),
            "url", fileUrl
        );

        return ResponseEntity.ok(response);
    }

    /**
     * 下载文件
     * @param courseID
     * @param id
     * @return 文件内容
     */
    @Operation(summary = "Download a material")
    @GetMapping("/api/resource/material/{courseID}/{id}")
    public ResponseEntity<?> downloadMaterial(@PathVariable("courseID") String courseID, @PathVariable("id") Long id) {
        Material material = materialService.selectByID(id);
        
        // 检查文件是否存在
        String fileDir = material.getFiledir();
        String filePath = fileDir + material.getID().toString()+'_'+material.getFilename();
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(filePath);
        }

        // 返回文件内容
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; id=\"" + id.toString() + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(fileContent);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error reading file");
        }
    }
}
