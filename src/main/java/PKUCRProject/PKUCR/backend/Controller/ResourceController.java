package main.java.PKUCRProject.PKUCR.backend.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
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

    @Operation(summary = "Store a file and insert file metadata, return file id and file path")
    @PostMapping("/resourceUpload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        // 处理文件上传
        String filePath = resourceService.storeFile(file);
        // 将文件信息记录到数据库
        return resourceService.insertFileMetadata(file, filePath);
    }

    @Operation(summary = "Select a resource by id")
    @GetMapping("/resourceSelectById")
    public Resource selectById(@RequestBody int id) {
        return resourceService.selectById(id);
    }

    @Operation(summary = "Delete a resource by id")
    @DeleteMapping("/resourceDelete")
    public String delete(@RequestBody int id) {
        return resourceService.delete(id);
    }
}
