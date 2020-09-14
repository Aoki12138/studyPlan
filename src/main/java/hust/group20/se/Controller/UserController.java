package hust.group20.se.Controller;

import hust.group20.se.Service.TaskService;
import hust.group20.se.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    //登录之后才能返回用户主页面！
    @RequestMapping("/index")
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

}
