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
    private String classroom;
    private String time;
    private String week;

    // 这里还要加入课程资源等信息
    public BasicCourse(String courseID, String courseName, String category, 
        String teacher, int credit, String classroom, String time, String week) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.category = category;
        this.teacher = teacher;
        this.credit = credit;
        this.classroom = classroom;
        this.time = time;
        this.week = week;
    }

    public BasicCourse() {
        this.courseID = "04834220";
        this.courseName = "软件工程";
        this.category = "专业课";
        this.teacher = "孙艳春";
        this.credit = 4;
        this.classroom = "二教315";
        this.time = "星期二(第3节-第4节)\n" + //
                        "星期四(第5节-第6节)";
        this.week = "1-16";
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

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }
}
