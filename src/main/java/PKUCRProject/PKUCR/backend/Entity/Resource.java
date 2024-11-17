package main.java.PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Resource")
public class Resource {
    @Schema(name = "id", required = false, example = "1")
    private int id;

    @Schema(name = "course", required = false, example = "ics")
    private String course;

    @Schema(name = "file_name", required = true, example = "ics_midterm_exam_2024")
    private String file_name;

    @Schema(name = "file_path", required = false, example = ".")
    private String file_path;

    @Schema(name = "file_type", required = true, example = "TEXT")
    private String file_type;

    @Schema(name = "permission", required = false, example = "1")
    private int permission;

    @Schema(name = "updated_at", required = false, example = "2021-01-01 10:00:00")
    private int updated_at;

    public Resource(int id, String course, String file_name, String file_path, String file_type, int permission, int updated_at) {
        this.id = id;
        this.course = course;
        this.file_name = file_name;
        this.file_path = file_path;
        this.file_type = file_type;
        this.permission = permission;
        this.updated_at = updated_at;
    }

    
    public int getId() {
        return id;
    }
    public String getCourse() {
        return course;
    }
    public String getFile_name() {
        return file_name;
    }
    public String getFile_path() {
        return file_path;
    }
    public String getFile_type() {
        return file_type;
    }
    public int getPermission() {
        return permission;
    }
    public int getUpdated_at() {
        return updated_at;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void SetCourse(String course) {
        this.course = course;
    }
    public void SetFile_name(String file_name) {
        this.file_name=file_name;
    }
    public void SetFile_path(String file_path) {
        this.file_path=file_path;
    }
    public void SetFile_type(String file_type) {
        this.file_type=file_type;
    }
    public void setPermission(int permission) {
        this.permission = permission;
    }
    public void setUpdated_at(int updated_at) {
        this.updated_at = updated_at;
    }
}
