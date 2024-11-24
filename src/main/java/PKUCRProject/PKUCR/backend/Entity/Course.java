package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Course")
public class Course extends BasicCourse{

    @Schema(name = "user_id", required = false, example = "1")
    private Long user_id;

    // 这里还要加入课程资源等信息
    public Course(String courseID, String courseName, String category, String teacher, int credit) {
        super(courseID, courseName, category, teacher, credit);
    }
    public Course() {
        super();
    }

    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
