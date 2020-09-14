package hust.group20.se.Service;

import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;

import java.util.List;

public interface TaskService {

    List<Task> getTasksByUserID(Integer userID);

    Task getTaskByTaskID(Integer taskID);

    Integer addOneTask(User user, Task task);

    boolean deleteOneTaskByTaskID(Integer taskID);

    Integer updateOneTask(Task newTask);
}
