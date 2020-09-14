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


}
