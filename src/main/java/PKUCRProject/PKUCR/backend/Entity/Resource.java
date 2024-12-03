package PKUCRProject.PKUCR.backend.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Resource")
public class Resource {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(name = "resourceID", required = false, example = "1")
    private long resourceID;

    @Schema(name = "courseID", required = true, example = "ics")
    private Long courseID;

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

    public Resource(Long resourceID, Long courseID, Long userID, String fileName, String filePath, int permission, String uploadTime) {
        this.resourceID = resourceID;
        this.courseID = courseID;
        this.userID = userID;
        this.fileName = fileName;
        this.filePath = filePath;
        this.uploadTime = uploadTime;
    }
    public Resource() {}
    public void setResourceID(Long id) {
        this.resourceID = id;
    }
    public Long getResourceID() {
        return resourceID;
    }

    public Long getCourseID() {
        return courseID;
    }
    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }

    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public String getFileName() {
        return fileName;
    }
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }
    public void setFilePath(String filePath) {
        this.filePath=filePath;
    }

    public String getTime() {
        return uploadTime;
    }
    public void setTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }
}
