package PKUCRProject.PKUCR.backend.Entity;

import java.util.ArrayList;
import java.util.List;

/**
 * 课程基本信息
 */
public class BasicCourse {

    private Long id;
    private String courseID;
    private String courseName;
    private String category;
    private String teacher;
    private int credit;
    private String classroom;
    private List<CourseTime> time = new ArrayList<>();
    private String link;

    // 这里还要加入课程资源等信息
    public BasicCourse(String courseID, String courseName, String category, 
        String teacher, int credit, String classroom, List<CourseTime> time, String link) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.category = category;
        this.teacher = teacher;
        this.credit = credit;
        this.classroom = classroom;
        this.time = time;
        this.link = link;
    }

    public BasicCourse() {
        this.courseID = "04834220";
        this.courseName = "软件工程";
        this.category = "专业课";
        this.teacher = "孙艳春";
        this.credit = 4;
        this.classroom = "二教315";
        this.time = new ArrayList<CourseTime>();
        this.time.add(new CourseTime("2021-09-01", "2021-12-31", "08:00", "10:40", 1, 1L));
        this.link = "/api/resource/04834220";
    }

    /**
     * @return Long 返回当前课程在数据库里的id
     */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public List<CourseTime> getTime() {
        return time;
    }

    public void setTime(List<CourseTime> time) {
        this.time = time;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
