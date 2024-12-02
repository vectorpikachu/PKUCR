package PKUCRProject.PKUCR.backend.Entity;

public class Material {
    
    private Long id;
    private Long userID;
    private String courseID;
    private String filename;
    private String url;
    private String time;

    public Material() {
    }

    public Material(String courseID, String filename, String url, String time) {
        this.courseID = courseID;
        this.filename = filename;
        this.url = url;
        this.time = time;
    }

    
    /** 
     * @return Long
     */
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
    public String getCourseID() {
        return courseID;
    }
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
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
