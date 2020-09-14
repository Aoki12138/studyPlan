package hust.group20.se.Mapper;

import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("SELECT * FROM task WHERE task.userID=#{userID}")
    List<Task> getTasksByUserID(Integer userID);

    @Select("SELECT * FROM task WHERE task.taskID=#{taskID}")
    Task getTaskByTaskID(Integer taskID);

    @Insert("INSERT INTO task (task.taskID,task.taskName,task.taskTheme,task.priority,task.alarmFrequency,task.startTime,task.endTime,task.description,task.evaluation,task.userID) VALUES (#{task.taskID},#{task.taskName},#{task.taskTheme},#{task.priority},#{task.alarmFrequency},#{task.startTime},#{task.endTime},#{task.description},#{task.evaluation},#{user.userID})")
    Integer addOneTask(User user,Task task);

    @Delete("DELETE FROM task WHERE task.taskID=#{taskID}")
    boolean deleteOneTaskByTaskID(Integer taskID);

    @Update("UPDATE task SET task.taskID=#{newTask.taskID},task.taskName=#{newTask.taskName},task.taskTheme=#{newTask.taskTheme},task.priority=#{newTask.priority},task.alarmFrequency=#{newTask.alarmFrequency},task.startTime=#{newTask.startTime},task.endTime=#{newTask.endTime},task.description=#{newTask.description},task.evaluation=#{newTask.evaluation},task.userID=#{newTask.userID}")
    Integer updateOneTask(Task newTask);
}
