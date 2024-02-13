package bean;

import java.time.LocalDate;

public class TaskBean extends BaseBean {
    protected String name;
    protected String description;
    protected Integer creatorId;
    protected LocalDate createdTime;
    protected LocalDate dueDate;
    protected Boolean isCompleted;

    public TaskBean() {
    }

    public TaskBean(String name, String description, Integer creatorId, LocalDate dueDate) {
        this.name = name;
        this.description = description;
        this.creatorId = creatorId;
        this.createdTime = LocalDate.now();
        this.dueDate = dueDate;
        isCompleted = false;
    }

    public TaskBean(String name, Integer creatorId, LocalDate dueDate) {
        this.name = name;
        this.creatorId = creatorId;
        this.createdTime = LocalDate.now();
        this.dueDate = dueDate;
        description = "";
        isCompleted = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public LocalDate getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDate createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public Boolean getCompleted() {
        return isCompleted;
    }

    public void setCompleted(Boolean completed) {
        isCompleted = completed;
    }

    @Override
    public String toString() {
        return ("Task {id: %s" +
                ", name: %s" +
                ", description: %s" +
                ", creator Id: %s" +
                ", created time: %s" +
                ", due date: %s" +
                ", || %s }").formatted(id, name, description, creatorId, createdTime, dueDate, isCompleted? "completed" : "not completed");
    }
}
