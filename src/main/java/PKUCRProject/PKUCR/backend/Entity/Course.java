package PKUCRProject.PKUCR.backend.Entity;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Course")
public class Course extends BasicCourse {

    @Schema(name = "user_id", required = false, example = "1")
    private Long user_id;

    // 这里还要加入课程资源等信息
    public Course(String courseID, String courseName, String category,
            String teacher, int credit, String classroom, List<CourseTime> time, String link) {
        super(courseID, courseName, category, teacher, credit, classroom, time, link);
    }

    public Course() {
        super();
    }

    /**
     * @return Long
     */
    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
