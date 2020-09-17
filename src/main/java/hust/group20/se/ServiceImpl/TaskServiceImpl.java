package hust.group20.se.ServiceImpl;

import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import hust.group20.se.Mapper.TaskMapper;
import hust.group20.se.Service.TaskService;
import javafx.util.converter.TimeStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
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
    public List<Task> getAllTasks(){
        return taskMapper.getAllTasks();
    }

    @Override
    public List<Task> getTasksByTimeStamp(Timestamp firsttime,Timestamp endtime){
        return taskMapper.getTasksByTimeStamp(firsttime, endtime);
    }

    @Override
    public Task getPresentTask(){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return taskMapper.getPresentTask(now);
    }

    @Override
    public List<Task> getUnfinTasks(){
        //现在的时间戳
        Timestamp now = new Timestamp(System.currentTimeMillis());
        //今天23:59:59的时间戳
        Calendar lastmidnight = Calendar.getInstance();
        lastmidnight.setTime(new Date());
        lastmidnight.set(Calendar.HOUR_OF_DAY,23);
        lastmidnight.set(Calendar.MINUTE,59);
        lastmidnight.set(Calendar.SECOND,59);
        lastmidnight.set(Calendar.MILLISECOND,59);
        Timestamp lasttime = new Timestamp(lastmidnight.getTimeInMillis());
        return taskMapper.getUnfinTasks(now,lasttime);
    }

    @Override
    public List<Task> getFinTasks(){
        //现在的时间戳
        Timestamp now = new Timestamp(System.currentTimeMillis());
        //今天零点的时间戳
        Calendar midnight = Calendar.getInstance();
        midnight.setTime(new Date());
        midnight.set(Calendar.HOUR_OF_DAY,0);
        midnight.set(Calendar.MINUTE,0);
        midnight.set(Calendar.SECOND,0);
        midnight.set(Calendar.MILLISECOND,0);
        Timestamp firsttime = new Timestamp(midnight.getTimeInMillis());
        return taskMapper.getFinTasks(now,firsttime);
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
    public Integer updateEvaluation(Integer taskID,Integer evaluation){
        return taskMapper.updateEvaluation(taskID,evaluation);
    }

    @Override
    public Integer updateOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation) {
        return taskMapper.updateOneTaskByAttributes(userID,taskID,taskName,taskTheme,priority,startTime,endTime,description,evaluation);
    }
}
