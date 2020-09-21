package hust.group20.se.Controller;

import hust.group20.se.Entity.User;
import hust.group20.se.Service.UserService;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.Map;


@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/signIn")
    public String signInPage(){
        return "signIn";
    }

    @PostMapping("/signIn")
    public String signIn(@RequestParam("userNickName") String userNickName,
                         @RequestParam("userPassword") String userPassword, Model model){

        UsernamePasswordToken token=new UsernamePasswordToken(userNickName,userPassword);

        Subject subject= SecurityUtils.getSubject();

        try{
            subject.login(token);
            return "redirect:/";
        }catch (UnknownAccountException e) {

            model.addAttribute("msg",e.getMessage());

            return "signIn";
        }catch (IncorrectCredentialsException e) {
            model.addAttribute("msg",e.getMessage());
            model.addAttribute("userNickName",userNickName);

            return "signIn";
        }

    }

    @GetMapping("/signUp")
    public String signUpPage(){
        return "signUp";
    }

    @PostMapping("/signUp")
    public String addUser(User user,Model model){

        User usercheck=userService.signUpCheck(user.getUserNickName());

        if (usercheck!=null){
            model.addAttribute("msg","用户名已存在");
            return "signUp";
        }

        Integer i=userService.addUser(user);

            return "redirect:/signUpSuccess";
    }

    @GetMapping("/signUpSuccess")
    public String signUpSuccessPage(){
        return "signUpSuccess";
    }

    @RequestMapping("/")
    public String redirectIndex(HttpSession session) {
        User user=(User) SecurityUtils.getSubject().getPrincipal();
        session.setAttribute("user",user);
        return "redirect:/user/index";
    }

}
