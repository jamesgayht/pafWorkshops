package vttp.paf.pafMockAssessment.models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    public enum Priority {LOW, MED, HIGH}; 

    private String taskId;
    private String description;
    private Priority priority;
    private Date dueDate;
    private String userId; 

    @Override
    public String toString() {
        return "Description >>> %s, Priority >>> %s, DueDate >>>> %s, userId >>> %s\n".formatted(description, priority, dueDate.toString(), userId);
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }
    
    public void setPriority(String priority) {
        this.priority = Priority.valueOf(priority);
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    // public void setDueDate(String dueDate) {
    //     try {
    //         this.dueDate = new SimpleDateFormat("yyyy")
    //     } catch (Exception e) {
    //         // TODO: handle exception
    //     }
    // }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
