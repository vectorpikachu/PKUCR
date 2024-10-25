package PKUCRProject.PKUCR.backend.Entity;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Task")
public class Task {

    @Schema(name = "id", required = true, example = "1")
    private int id;

    @Schema(name = "name", required = true, example = "Task1")
    private String name;

    @Schema(name = "date", required = true, example = "2021-01-01 10:00:00")
    private String date;

    @Schema(name = "priority", required = true, example = "1")
    private int priority;

    @Schema(name = "description", required = true, example = "This is a task")
    private String description;

    public Task(int id, String name, String date, int priority, String description) {
        this.id = id;
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

    public int getId() {
        return id;
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
        return this.description;
    }
    public void setId(int id) {
        this.id = id;
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
