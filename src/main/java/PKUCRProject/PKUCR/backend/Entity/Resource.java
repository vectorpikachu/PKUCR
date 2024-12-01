package main.java.PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Resource")
public class Resource {
    @Schema(name = "resourceID", required = false, example = "1")
    private long resourceID;

    @Schema(name = "courseID", required = true, example = "ics")
    private String courseID;

    @Schema(name = "userID", required = true, example = "1")
    private Long userID;

    @Schema(name = "fileName", required = true, example = "ics_midterm_exam_2024")
    private String fileName;

    @Schema(name = "filePath", required = false, example = "/data/materials/CS101/1_lecture1.pdf")
    private String filePath;

    @Schema(name = "fileContent", required = false, example = "file content here")
    private String fileContent;

    @Schema(name = "uploadTime", required = true, example = "2024-11-30T12:34:56")
    private String uploadTime;

    public Resource(Long resourceID, String courseID, String userID, String fileName, String filePath, int permission, String uploadTime) {
        this.resourceID = resourceID;
        this.courseID = courseID;
        this.userID = userID;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadTime = uploadTime;
    }
    public void setId(Long id) {
        this.resourceID = id;
    }
    public int getId(Long id) {
        return id;
    }

    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFilename(String fileName) {
        return fileName;
    }
    public void setFilename(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }
    public void SetFilePath(String filePath) {
        this.filePath=filePath;
    }

    public String getTime(String uploadTime) {
        return uploadTime;
    }
    public void setTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
