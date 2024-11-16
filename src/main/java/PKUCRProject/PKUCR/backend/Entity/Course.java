package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Course")
public class Course {

    @Schema(name = "id", required = false, example = "1")
    private int id;

    @Schema(name = "courseID", required = false, example = "04834220")
    private String courseID;

    @Schema(name = "courseName", required = true, example = "软件工程")
    private String courseName;

    @Schema(name = "teacher", required = false, example = "孙艳春")
    private String teacher;

    @Schema(name = "time", required = false, example = "2024-11-1 8:00:00")
    private String time;

    @Schema(name = "location", required = false, example = "二教315")
    private String location;

    @Schema(name = "credit", required = false, example = "4")
    private int credit;
    // 这里还要加入课程资源等信息
    public Course(String courseID, String courseName, String teacher, String time, String location, int credit) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacher = teacher;
        this.time = time;
        this.location = location;
        this.credit = credit;
    }
    public Course() {
        this.courseID = "04834220";
        this.courseName = "软件工程";
        this.teacher = "孙艳春";
        this.time = "2024-11-1 8:00:00";
        this.location = "二教315";
        this.credit = 4;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public String getTeacher() {
        return teacher;
    }
    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
}
