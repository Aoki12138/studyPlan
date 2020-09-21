package hust.group20.se.Controller;

import hust.group20.se.Entity.Diary;
import hust.group20.se.Entity.Priority;
import hust.group20.se.Entity.Task;
import hust.group20.se.Entity.User;
import hust.group20.se.Service.TaskService;
import hust.group20.se.Service.UserService;
import hust.group20.se.Utils.timeMachine;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.DataInput;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    //登录之后才能返回用户主页面！
    @GetMapping("/index")
    public String showMainPage(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        Integer userID = user.getUserID();
        List<Task> UnfinTasks = taskService.getUnfinTasks(userID);
        List<Task> FinTasks = taskService.getFinTasks(userID);


        Long totaltime1 = taskService.getTotalTime(UnfinTasks);
        Long totaltime2 = taskService.getTotalTime(FinTasks);
        Long totaltime = totaltime1 + totaltime2;
        Task presentTask = taskService.getPresentTask(userID);
        if(presentTask!=null){
            totaltime += presentTask.getEndTime().getTime()-presentTask.getStartTime().getTime();
        }

        model.addAttribute("totaltime",totaltime/60000);
        model.addAttribute("Fintotaltime",totaltime2/60000);
        if(totaltime!=0){
            model.addAttribute("finProportion",totaltime2*100/totaltime);
        }
        else{
            model.addAttribute("finProportion",100);
        }

        model.addAttribute("UnfinTasks",UnfinTasks);
        model.addAttribute("FinTasks",FinTasks);
        return "index";
    }

    @GetMapping("/updatePassword")
    public String showUpdatePassword(){
        return "updatePassword";
    }

    @PostMapping("/updatePassword")
    public String updatePassword(HttpSession session,
                                 @RequestParam(value = "userPassword") String userPassword){

        User user= (User) session.getAttribute("user");

        userService.updateUserPassword(user.getUserNickName(),userPassword);

        return "redirect:/user/userInfo";
    }

    @GetMapping("/diaryList")
    public String showDiaryList(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        Integer userID = user.getUserID();

        List<Diary> diaries = taskService.getAllDiaryByUserID(userID);
        model.addAttribute("diaries",diaries);
        return "diaryList";
    }

    @GetMapping("/addDiary")
    public String showAddDiaryPage(){
        return "addDiary";
    }



    @GetMapping("/userInfo")
    public String showUserInfoPage(){

        return "userInfo";
    }

    @PostMapping("/userInfo")
    public String updateUserInfo(@RequestParam(value = "userNickName") String userNickName,
                                 @RequestParam(value = "userSex") Integer userSex,
                                 @RequestParam(value = "userEmail") String userEmail,
                                 HttpSession session){
        userService.updateUserInfo(userNickName,userSex,userEmail);

        session.removeAttribute("user");

        User user= userService.signInCheck(userNickName);
        session.setAttribute("user",user);

        return "redirect:/user/userInfo";
    }

    @RequestMapping("/analysis/day")
    public String showDayAnalysisPage(HttpSession session){

        Integer num=new Integer(1);
        List<Task> tasks=taskService.getTasksByUserID(num);
        session.setAttribute("tasks",tasks);

        return "analysisByDay";
    }

    @RequestMapping("/analysis/week")
    public String showWeekAnalysisPage(){
        return "analysisByWeek";
    }

    @GetMapping("/taskList")
    public String showTaskListPage(Model model,HttpSession session){
        User user = (User)session.getAttribute("user");
        List<Task> tasks = taskService.getTasksByUserID(user.getUserID());
        model.addAttribute("tasks",tasks);
        return "taskList";
    }

    @GetMapping("/taskList/delete/{id}")
    public  String deleteOneTask(@PathVariable("id") Integer taskID){
        taskService.deleteOneTaskByTaskID(taskID);
        return "redirect:/user/taskList";
    }

    @GetMapping("/evaluation/{id}")
    public String showEvaluationPage(@PathVariable("id")Integer taskID,Model model){
        Task presentTask = taskService.getTaskByTaskID(taskID);
        model.addAttribute("task",presentTask);
        return "evaluation";
    }

    //未完成用户ID
    @PostMapping("/addDiary")
    public String addDiary(@RequestParam(value = "diaryName") String diaryName,
                           @RequestParam(value = "keyword") String keyword,
                           @RequestParam(value = "color") String color,
                           @RequestParam(value = "body") String body,
                           @RequestParam(value = "userID") Integer userID ){
        taskService.addDiary(diaryName,keyword,color,body,userID);
        return "redirect:/user/diaryList";
    }

    @PostMapping("/evaluation")
    public String addEvaluation(@RequestParam(value = "taskID")Integer taskID,
                                @RequestParam(value = "evaluation")Integer evaluation){
        taskService.updateEvaluation(taskID,evaluation);
        return "redirect:/user/index";
    }

    @PostMapping("/taskList/update")
    public String updateOneTask(@RequestParam(value = "userID") Integer userID,
                                @RequestParam(value = "taskID") Integer taskID,
                                @RequestParam(value = "taskName") String taskName,
                                @RequestParam(value = "taskTheme") String taskTheme,
                                @RequestParam(value = "priority") String priority,
                                @RequestParam(value = "startTime") String startTime,
                                @RequestParam(value = "endTime") String endTime,
                                @RequestParam(value = "description") String description,
                                @RequestParam(value = "evaluation") Integer evaluation){
        taskService.updateOneTaskByAttributes(userID,taskID,taskName,taskTheme,Priority.valueOf(priority),timeMachine.toTimeStamp(startTime),timeMachine.toTimeStamp(endTime),description,evaluation);
        return "redirect:/user/taskList";
    }

    @GetMapping("/taskList/update/{id}")
    public String updateOneTask(@PathVariable("id") Integer taskID,Model model){
        Task task = taskService.getTaskByTaskID(taskID);
        model.addAttribute("oldTask",task);
        return "updateTask";
    }

    @PostMapping("/addTask")
    public String addOneTask(@RequestParam(value="userID") Integer userID,
                             @RequestParam(value = "taskName") String taskName,
                             @RequestParam(value = "taskTheme") String taskTheme,
                             @RequestParam(value = "priority") String priority,
                             @RequestParam(value = "startTime") String startTime,
                             @RequestParam(value = "endTime") String endTime,
                             @RequestParam(value = "description") String description){

        Integer newTaskID = taskService.getMaxID()+1;
        taskService.addOneTaskByAttributes(userID,newTaskID,taskName,taskTheme,Priority.valueOf(priority),timeMachine.toTimeStamp(startTime),timeMachine.toTimeStamp(endTime),description,0);
        return "redirect:/user/index";
    }

    @GetMapping("/addTask")
    public String showAddTaskPage() { return "addTask"; }


}
