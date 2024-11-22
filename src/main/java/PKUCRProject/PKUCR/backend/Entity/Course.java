package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Course")
public class Course {

    @Schema(name = "id", required = false, example = "1")
    private Long id;

    @Schema(name = "user_id", required = false, example = "1")
    private Long user_id;

    @Schema(name = "courseID", required = false, example = "04834220")
    private String courseID;

    @Schema(name = "courseName", required = true, example = "软件工程")
    private String courseName;

    @Schema(name = "teacher", required = false, example = "孙艳春")
    private String teacher;

    @Schema(name = "credit", required = false, example = "4")
    private int credit;
    // 这里还要加入课程资源等信息
    public Course(String courseID, String courseName, String teacher, int credit) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.teacher = teacher;
        this.credit = credit;
    }
    public Course() {
        this.courseID = "04834220";
        this.courseName = "软件工程";
        this.teacher = "孙艳春";
        this.credit = 4;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
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
    public int getCredit() {
        return credit;
    }
    public void setCredit(int credit) {
        this.credit = credit;
    }
}
