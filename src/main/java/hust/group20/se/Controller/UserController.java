package hust.group20.se.Controller;

import hust.group20.se.Entity.Priority;
import hust.group20.se.Service.TaskService;
import hust.group20.se.Service.UserService;
import hust.group20.se.Utils.timeMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    //登录之后才能返回用户主页面！
    @GetMapping("/index")
    public String showMainPage(){
        return "index";
    }

    @RequestMapping("/signIn")
    public String showSignInPage(){
        return "signIn";
    }

    @RequestMapping("/signUp")
    public String showSignUpPage(){
        return "signUp";
    }

    @RequestMapping("/taskList")
    public String showTaskListPage(){
        return "taskList";
    }

    @RequestMapping("/userInfo")
    public String showUserInfoPage(){
        return "userInfo";
    }

    @RequestMapping("/analysis/day")
    public String showDayAnalysisPage(){
        return "analysisByDay";
    }

    @RequestMapping("/analysis/week")
    public String showWeekAnalysisPage(){
        return "analysisByWeek";
    }

    @PostMapping("/addTask")
    @ResponseBody
    public String addOneTask(@RequestParam(value = "taskName") String taskName,
                             @RequestParam(value = "taskTheme") String taskTheme,
                             @RequestParam(value = "priority") String priority,
                             @RequestParam(value = "startTime") String startTime,
                             @RequestParam(value = "endTime") String endTime,
                             @RequestParam(value = "description") String description){

        Integer userID = new Integer(1);
        Integer newTaskID = taskService.getMaxID()+1;
        taskService.addOneTaskByAttributes(userID,newTaskID,taskName,taskTheme,Priority.valueOf(priority),timeMachine.toTimeStamp(startTime),timeMachine.toTimeStamp(endTime),description,0);
        return "redirect:/user/index";
    }

    @GetMapping("/addTask")
    public String showAddTaskPage() { return "addTask"; }
}
