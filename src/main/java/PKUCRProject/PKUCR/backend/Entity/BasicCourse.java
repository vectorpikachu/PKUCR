package PKUCRProject.PKUCR.backend.Entity;


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
    // 这里还要加入课程资源等信息
    public BasicCourse(String courseID, String courseName, String category, String teacher, int credit) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.category = category;
        this.teacher = teacher;
        this.credit = credit;
    }
    public BasicCourse() {
        this.courseID = "04834220";
        this.courseName = "软件工程";
        this.category = "专业课";
        this.teacher = "孙艳春";
        this.credit = 4;
    }

    
    /** 
     * @return Long
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

}
