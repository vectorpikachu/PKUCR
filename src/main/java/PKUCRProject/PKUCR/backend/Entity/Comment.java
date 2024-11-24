package PKUCRProject.PKUCR.backend.Entity;

public class Comment {
    private Long id;
    private Long userID;
    private Long courseID;
    private String content;
    private String time;
    // private String userEmail;

    public Comment() {
    }

    public Comment(Long userID, Long courseID, String content, String time) {
        this.userID = userID;
        this.courseID = courseID;
        this.content = content;
        this.time = time;
    }

    public Long getID() {
        return id;
    }
    public void setID(Long id) {
        this.id = id;
    }
    public Long getUserID() {
        return userID;
    }
    public void setUserID(Long userID) {
        this.userID = userID;
    }
    public Long getCourseID() {
        return courseID;
    }
    public void setCourseID(Long courseID) {
        this.courseID = courseID;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    
}

