package hust.group20.se.ServiceImpl;

import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import hust.group20.se.Mapper.TaskMapper;
import hust.group20.se.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Task> getTasksByUserID(Integer userID) {
        return taskMapper.getTasksByUserID(userID);
    }

    @Override
    public Integer getTotal() {
        return taskMapper.getTotal();
    }

    @Override
    public Integer getMaxID() {
        return taskMapper.getMaxID();
    }

    @Override
    public Task getTaskByTaskID(Integer taskID) {
        return taskMapper.getTaskByTaskID(taskID);
    }

    @Override
    public Integer addOneTaskByClass(Integer userID, Task task) {
        return taskMapper.addOneTaskByClass(userID,task);
    }

    @Override
    public Integer addOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation) {
        return taskMapper.addOneTaskByAttributes(userID, taskID, taskName, taskTheme, priority, startTime, endTime, description, evaluation);
    }

    @Override
    public boolean deleteOneTaskByTaskID(Integer taskID) {
        return taskMapper.deleteOneTaskByTaskID(taskID);
    }

    @Override
    public Integer updateOneTaskByClass(Task newTask) {
        return taskMapper.updateOneTaskByClass(newTask);
    }

    @Override
    public Integer updateOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation) {
        return taskMapper.updateOneTaskByAttributes(userID,taskID,taskName,taskTheme,priority,startTime,endTime,description,evaluation);
    }
}
