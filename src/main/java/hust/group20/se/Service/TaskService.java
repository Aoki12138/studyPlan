package hust.group20.se.Service;

import hust.group20.se.Entity.Diary;
import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {
    List<Diary> getAllDiary();

    List<Diary> getAllDiaryByUserID(Integer userID);

    List<Task> getTasksByUserID(Integer userID);

    List<Task> getAllTasks();

    Long getTotalTime(List<Task> tasks);

    List<Task> getUnfinTasks();

    List<Task> getFinTasks();

    List<Task> getTasksByTimeStamp(Timestamp firsttime,Timestamp endtime);

    Task getPresentTask();

    Integer getTotal();

    Integer getMaxID();

    Task getTaskByTaskID(Integer taskID);

    Integer addOneTaskByClass(Integer userID, Task task);

    Integer addOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation);

    boolean deleteOneTaskByTaskID(Integer taskID);

    Integer updateOneTaskByClass(Task newTask);


    Integer updateEvaluation(Integer taskID,Integer evaluation);

    Integer addDiary(String diaryName,String keyword,String color,String body);

    Integer updateOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation);
}
