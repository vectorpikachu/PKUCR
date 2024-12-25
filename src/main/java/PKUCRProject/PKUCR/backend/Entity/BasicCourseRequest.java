package PKUCRProject.PKUCR.backend.Entity;

public class BasicCourseRequest {
    private String courseId;
    private String courseName;
    private String category;

    public BasicCourseRequest(String courseId, String courseName, String category) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.category = category;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
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
}
