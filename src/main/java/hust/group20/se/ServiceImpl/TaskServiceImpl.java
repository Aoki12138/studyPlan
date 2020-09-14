package hust.group20.se.ServiceImpl;

import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import hust.group20.se.Mapper.TaskMapper;
import hust.group20.se.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Task getTaskByTaskID(Integer taskID) {
        return taskMapper.getTaskByTaskID(taskID);
    }

    @Override
    public Integer addOneTask(User user, Task task) {
        return taskMapper.addOneTask(user,task);
    }

    @Override
    public boolean deleteOneTaskByTaskID(Integer taskID) {
        return taskMapper.deleteOneTaskByTaskID(taskID);
    }

    @Override
    public Integer updateOneTask(Task newTask) {
        return taskMapper.updateOneTask(newTask);
    }
}
