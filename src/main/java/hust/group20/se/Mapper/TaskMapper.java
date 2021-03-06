package hust.group20.se.Mapper;

import hust.group20.se.Entity.Diary;
import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import org.apache.ibatis.annotations.*;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

@Mapper
public interface TaskMapper {

    @Select("SELECT * FROM task WHERE task.userID=#{userID}")
//    @Result(property = "priority",column = "priority",typeHandler= EnumPriorityTypeHandler.class)
    List<Task> getTasksByUserID(Integer userID);

    @Select("SELECT * FROM diary WHERE")
    List<Diary> getAllDiary();

    @Select("SELECT * FROM diary WHERE diary.userID=#{userID}")
    List<Diary> getAllDiaryByUserID(Integer userID);

    @Select("SELECT * FROM task WHERE task.userID=#{userID} and task.endTime between #{todayTime} and #{tomorrowTime}")
    List<Task> getTasksTodayByUserID(Integer userID,Timestamp todayTime,Timestamp tomorrowTime);



    @Select("SELECT count(*) FROM task")
    Integer getTotal();

    @Select("SELECT * FROM TASK")
    List<Task> getAllTasks();

    @Select("SELECT * FROM USER")
    List<User> getAllUser();

    @Select("SELECT * FROM TASK WHERE (task.startTime>#{now} AND task.startTime<#{lasttime} AND task.userID=#{userID}) ")
    List<Task> getUnfinTasks(Integer userID,Timestamp now,Timestamp lasttime);

    @Select("SELECT * FROM TASK WHERE (task.startTime>#{firsttime} AND task.endTime<#{now} AND task.userID=#{userID})")
    List<Task> getFinTasks(Integer userID,Timestamp now,Timestamp firsttime);

    @Select("SELECT * FROM TASK WHERE (task.startTime>#{firsttime} AND task.endTime<#{lasttime})")
    List<Task> getTasksByTimeStamp(Timestamp firsttime,Timestamp lasttime);

    @Select("SELECT * FROM TASK WHERE task.startTime<#{now}  AND task.endTime>#{now} AND task.userID=#{userID}")
    Task getPresentTask(Integer userID,Timestamp now);

    @Select("SELECT max(task.taskID) FROM task")
    Integer getMaxID();

    @Select("SELECT * FROM task WHERE task.taskID=#{taskID}")
    Task getTaskByTaskID(Integer taskID);

    @Insert("INSERT INTO task (task.taskID,task.taskName,task.taskTheme,task.priority,task.startTime,task.endTime,task.description,task.userID) VALUES (#{task.taskID},#{task.taskName},#{task.taskTheme},#{task.priority},#{task.startTime},#{task.endTime},#{task.description},#{userID})")
    Integer addOneTaskByClass(Integer userID,Task task);

    @Insert("INSERT INTO diary(diary.diaryName,diary.keyword,diary.color,diary.body,diary.createTime,diary.userID) VALUES(#{diaryName},#{keyword},#{color},#{body},#{createTime},#{userID})")
    Integer addDiary(String diaryName,String keyword,String color,String body,Timestamp createTime,Integer userID);

    @Insert("INSERT INTO task (task.taskID,task.taskName,task.taskTheme,task.priority,task.startTime,task.endTime,task.description,task.userID) VALUES (#{taskID},#{taskName},#{taskTheme},#{priority},#{startTime},#{endTime},#{description},#{userID})")
    Integer addOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description);

    @Delete("DELETE FROM task WHERE task.taskID=#{taskID}")
    boolean deleteOneTaskByTaskID(Integer taskID);

    @Update("UPDATE task SET task.taskName=#{newTask.taskName},task.taskTheme=#{newTask.taskTheme},task.priority=#{newTask.priority},task.startTime=#{newTask.startTime},task.endTime=#{newTask.endTime},task.description=#{newTask.description},task.userID=#{newTask.userID} WHERE task.taskID=#{newTask.taskID}")
    Integer updateOneTaskByClass(Task newTask);

    @Update("UPDATE task SET task.taskName=#{taskName},task.taskTheme=#{taskTheme},task.priority=#{priority},task.startTime=#{startTime},task.endTime=#{endTime},task.description=#{description},task.userID=#{userID} WHERE task.taskID=#{taskID}")
    Integer updateOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description);

    @Update("UPDATE TASK SET task.evaluation=#{evaluation} WHERE task.taskID=#{taskID}")
    Integer updateEvaluation(Integer taskID,Integer evaluation);

}
