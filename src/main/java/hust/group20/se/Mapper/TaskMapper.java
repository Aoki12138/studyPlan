package hust.group20.se.Mapper;

import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("SELECT * FROM task WHERE task.userID=#{userID}")
    List<Task> getTasksByUserID(Integer userID);

    @Select("SELECT count(*) FROM task")
    Integer getTotal();

    @Select("SELECT max(task.taskID) FROM task")
    Integer getMaxID();

    @Select("SELECT * FROM task WHERE task.taskID=#{taskID}")
    Task getTaskByTaskID(Integer taskID);

    @Insert("INSERT INTO task (task.taskID,task.taskName,task.taskTheme,task.priority,task.startTime,task.endTime,task.description,task.evaluation,task.userID) VALUES (#{task.taskID},#{task.taskName},#{task.taskTheme},#{task.priority},#{task.startTime},#{task.endTime},#{task.description},#{task.evaluation},#{userID})")
    Integer addOneTaskByClass(Integer userID,Task task);

    @Insert("INSERT INTO task (task.taskID,task.taskName,task.taskTheme,task.priority,task.startTime,task.endTime,task.description,task.evaluation,task.userID) VALUES (#{taskID},#{taskName},#{taskTheme},#{priority},#{startTime},#{endTime},#{description},#{evaluation},#{userID})")
    Integer addOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Enum<Priority> priority, Timestamp startTime, Timestamp endTime, String description, Integer evaluation);

    @Delete("DELETE FROM task WHERE task.taskID=#{taskID}")
    boolean deleteOneTaskByTaskID(Integer taskID);

    @Update("UPDATE task SET task.taskID=#{newTask.taskID},task.taskName=#{newTask.taskName},task.taskTheme=#{newTask.taskTheme},task.priority=#{newTask.priority},task.startTime=#{newTask.startTime},task.endTime=#{newTask.endTime},task.description=#{newTask.description},task.evaluation=#{newTask.evaluation},task.userID=#{newTask.userID}")
    Integer updateOneTask(Task newTask);
}
