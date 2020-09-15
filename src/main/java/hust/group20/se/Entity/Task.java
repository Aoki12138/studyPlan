package hust.group20.se.Entity;

import java.sql.Timestamp;

public class Task {
    private Integer taskID;
    private String taskName;
    private String taskTheme;
    private Enum<Priority> priority;
//    private Integer alarmFrequency;
    private Timestamp startTime;
    private Timestamp endTime;
    private String description;
    private Integer evaluation;
    private Integer userID;

    public Task(Integer taskID, String taskName, String taskTheme, Enum<Priority> priority, /*Integer alarmFrequency,*/ Timestamp startTime, Timestamp endTime, String description, Integer evaluation, Integer userID) {
        this.taskID = taskID;
        this.taskName = taskName;
        this.taskTheme = taskTheme;
        this.priority = priority;
//        this.alarmFrequency = alarmFrequency;
        this.startTime = startTime;
        this.endTime = endTime;
        this.description = description;
        this.evaluation = evaluation;
        this.userID = userID;
    }

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

//    public Integer getAlarmFrequency() {
//        return alarmFrequency;
//    }
//
//    public void setAlarmFrequency(Integer alarmFrequency) {
//        this.alarmFrequency = alarmFrequency;
//    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
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
