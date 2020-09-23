package hust.group20.se.ServiceImpl;

import hust.group20.se.Entity.Diary;
import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import hust.group20.se.Mapper.TaskMapper;
import hust.group20.se.Service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;

    @Override
    public List<Diary> getAllDiary(){
        return taskMapper.getAllDiary();
    }

    @Override
    public Long getTotalTime(List<Task> tasks){
        Long totaltime = 0L;
        for(int i=0;i<tasks.size();i++){
            totaltime += tasks.get(i).getEndTime().getTime()-tasks.get(i).getStartTime().getTime();
        }
        return totaltime;
    }

    @Override
    public List<Diary> getAllDiaryByUserID(Integer userID){
        return taskMapper.getAllDiaryByUserID(userID);
    }

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
    public Task getPresentTask(Integer userID){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return taskMapper.getPresentTask(userID,now);
    }

    @Override
    public List<Task> getTasksTodayByUserID(Integer userID,Timestamp todayTime,Timestamp tomorrowTime){
        return taskMapper.getTasksTodayByUserID(userID,todayTime,tomorrowTime);
    }

    @Override
    public List<Task> getTasksFromChosenByTheme(List<Task> list,String theme){
        List<Task> chosenList = new ArrayList<Task>();
        int length=list.size();
        int i=0;
        for(i=0;i<=length-1;i++){
            if(list.get(i).getTaskTheme().equals(theme)){
                chosenList.add(list.get(i));
            }
        }
        return chosenList;
    }

    @Override
    public List<Task> getTasksFromChosenByTime(List<Task> list,Timestamp starttime,Timestamp endtime){
        List <Task> chosenList = new ArrayList <Task>();
        Long start = starttime.getTime();
        Long end = endtime.getTime();
        int length = list.size();
        int i=0;
        for(i=0;i<=length-1;i++){
            if((list.get(i).getEndTime().getTime()>=start)&&
                    (list.get(i).getEndTime().getTime()<=end)){
                chosenList.add(list.get(i));
            }
        }
        return chosenList;
    }

    @Override
    public List<Integer> gatherTime(List<Task> list){
        List <Integer> timeList = new ArrayList <Integer>();
        Integer fulltime= new Integer(0);
        Integer efftime= new Integer(0);
        int length=list.size();

        for(int i=0;i<length;i++){
            Long starttime=list.get(i).getStartTime().getTime();
            Long endtime=list.get(i).getEndTime().getTime();
            int times=(int)((endtime-starttime)/1000);
            int efftimes=times*(list.get(i).getEvaNum())/100;
            fulltime=fulltime+Integer.valueOf(times);
            efftime=efftime+Integer.valueOf(efftimes);
        }

        timeList.add(fulltime);
        timeList.add(efftime);
        return timeList;
    }

    @Override
    public List<Task> getUnfinTasks(Integer userID){
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
        return taskMapper.getUnfinTasks(userID,now,lasttime);
    }

    @Override
    public List<Task> getFinTasks(Integer userID){
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
        return taskMapper.getFinTasks(userID,now,firsttime);
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
    public Integer addOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description) {
        return taskMapper.addOneTaskByAttributes(userID, taskID, taskName, taskTheme, priority, startTime, endTime, description);
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
    public Integer addDiary(String diaryName,String keyword,String color,String body,Integer userID){
        Timestamp now = new Timestamp(System.currentTimeMillis());
        return taskMapper.addDiary(diaryName,keyword,color,body,now,userID);
    }



    @Override
    public Integer updateOneTaskByAttributes(Integer userID, Integer taskID, String taskName, String taskTheme, Priority priority, Timestamp startTime, Timestamp endTime, String description) {
        return taskMapper.updateOneTaskByAttributes(userID,taskID,taskName,taskTheme,priority,startTime,endTime,description);
    }

    @Override
    public List<User> getAllUser(){
        return taskMapper.getAllUser();
    }

    @Override
    public List<Long> getTimeOfTheme(List<Task> tasks){
        List<Long> time = new ArrayList<Long>(5);
        for(int i=0;i<5;i++){
            time.add(0L);
        }
        for(int i=0;!tasks.isEmpty() && i < tasks.size();i++){
            switch (tasks.get(i).getTaskTheme()){
                case"数学":
                    time.set(0,time.get(0)+(tasks.get(i).getEndTime().getTime()-tasks.get(i).getStartTime().getTime()));
                    break;
                case"英语":
                    time.set(1,time.get(1)+(tasks.get(i).getEndTime().getTime()-tasks.get(i).getStartTime().getTime()));
                    break;
                case"政治":
                    time.set(2,time.get(2)+(tasks.get(i).getEndTime().getTime()-tasks.get(i).getStartTime().getTime()));
                    break;
                case"专业课":
                    time.set(3,time.get(3)+(tasks.get(i).getEndTime().getTime()-tasks.get(i).getStartTime().getTime()));
                    break;
                case"其他":
                    time.set(4,time.get(4)+(tasks.get(i).getEndTime().getTime()-tasks.get(i).getStartTime().getTime()));
                    break;
                default:
                    System.out.println("未知主题");
            }
        }
        return time;
    }
}
