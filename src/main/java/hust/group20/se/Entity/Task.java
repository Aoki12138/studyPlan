package hust.group20.se.Entity;

import java.sql.Date;

public class Task {
    private Integer taskID;
    private String taskName;
    private String taskTheme;
    private Enum<Priority> priority;
    private Integer alarmFrequency;
    private Date startTime;
    private Date endTime;
    private String description;
    private Integer evaluation;
    private Integer userID;

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskTheme() {
        return taskTheme;
    }

    public void setTaskTheme(String taskTheme) {
        this.taskTheme = taskTheme;
    }

    public Enum<Priority> getPriority() {
        return priority;
    }

    public void setPriority(Enum<Priority> priority) {
        this.priority = priority;
    }

    public Integer getAlarmFrequency() {
        return alarmFrequency;
    }

    public void setAlarmFrequency(Integer alarmFrequency) {
        this.alarmFrequency = alarmFrequency;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Integer evaluation) {
        this.evaluation = evaluation;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }
}
