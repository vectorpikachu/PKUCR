package PKUCRProject.PKUCR.backend.Entity;

public class Material {
    
    private Long id;
    private Long userID;
    private String courseID;
    private String filename;
    private String filedir;
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

    public Material(Long id,Long userID, String courseID, String filename, String filedir, String url, String time) {
        this.id = id;
        this.courseID = courseID;
        this.userID = userID;
        this.filename = filename;
        this.filedir = filedir;
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
    public String getFiledir() {
        return filedir;
    }
    public void setFiledir(String filedir) {
        this.filedir = filedir;
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
