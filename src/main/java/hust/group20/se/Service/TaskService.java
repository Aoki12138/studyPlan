package hust.group20.se.Service;

import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;

import java.sql.Timestamp;
import java.util.List;

public interface TaskService {

    List<Task> getTasksByUserID(Integer userID);

    Integer getTotal();

    Integer getMaxID();

    Task getTaskByTaskID(Integer taskID);

    Integer addOneTaskByClass(Integer userID, Task task);

    Integer addOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Enum<Priority> priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation);

    boolean deleteOneTaskByTaskID(Integer taskID);

    Integer updateOneTask(Task newTask);
}
