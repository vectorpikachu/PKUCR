package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Task")
public class Task {

    @Schema(name = "id", required = false, example = "1")
    private Long id;

    @Schema(name = "user_id", required = false, example = "1")
    private Long user_id;

    @Schema(name = "name", required = true, example = "Task1")
    private String name;

    @Schema(name = "date", required = false, example = "2021-01-01 10:00:00")
    private String date;

    @Schema(name = "priority", required = false, example = "1")
    private int priority;

    @Schema(name = "description", required = false, example = "This is a task")
    private String description;

    public Task(Long id, Long user_id, String name, String date, int priority, String description) {
        this.id = id;
        this.user_id = user_id;
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.description = description;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                ", priority=" + priority +
                ", description='" + description + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }
    public Long getUser_id() {
        return user_id;
    }
    public String getName() {
        return name;
    }
    public String getDate() {
        return date;
    }
    public int getPriority() {
        return priority;
    }
    public String getDescription() {
        return description;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
}
