package PKUCRProject.PKUCR.backend.Entity;

public class Material {
    
    private Long id;
    private Long userID;
    private Long courseID;
    private String name;
    private String url;
    private String time;

    public Material() {
    }

    public Material(Long courseID, String name, String url, String time) {
        this.courseID = courseID;
        this.name = name;
        this.url = url;
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
}
