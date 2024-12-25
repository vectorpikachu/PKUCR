package PKUCRProject.PKUCR.backend.Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.core.io.InputStreamResource;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import PKUCRProject.PKUCR.backend.Service.MaterialService;
import PKUCRProject.PKUCR.backend.Entity.Material;
import PKUCRProject.PKUCR.backend.Service.CustomUserDetailsService;

@Tag(name = "MaterialController")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

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

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        if (authentication.getPrincipal() instanceof String){
            return ResponseEntity.badRequest().body("AuthenticationError: fail to get authentication token. Do you log in first?");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        Long userID = customUserDetailsService.getUserID(username);

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
            "id", material.getID().toString()
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
        if (material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material not found in the database");
        }
        // 检查文件是否存在
        String fileDir = material.getFiledir();
        String filePath = fileDir + material.getID().toString()+'_'+material.getFilename();
        FileInputStream fileInputStream;
        try {
            fileInputStream = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error reading filefile not found");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + material.getFilename());

        return ResponseEntity.ok()
                .headers(headers)
                .body(new InputStreamResource(fileInputStream));
    }
    /**
     * 删除文件
     * @param id
     * @return 
     */
    @Operation(summary = "Delete a material file")
    @DeleteMapping("/api/resource/material/{id}")
    public ResponseEntity<?> deleteMaterial(
            @PathVariable("id") Long id) {
        //获取用户信息
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.badRequest().body("Please login first");
        }
        if (authentication.getPrincipal() instanceof String){
            return ResponseEntity.badRequest().body("AuthenticationError: fail to get authentication token. Do you log in first?");
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        Long userID = customUserDetailsService.getUserID(username);

        Material material = materialService.selectByID(id);
        if (material == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Material not found in the database");
        }
        Long fileuserID = material.getUserID();
        if(userID!=fileuserID){
            return ResponseEntity.badRequest().body("Permission denied, users can only delete files they uploaded themselves.");
        }

        String fileDir = material.getFiledir();
        String filePath = fileDir + material.getID().toString()+'_'+material.getFilename();
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("file not found");
        }
        
        if (file.delete()) {
            materialService.deleteMaterial(id);
            return ResponseEntity.ok("Material deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete material file");
        }
    }
}
