package hust.group20.se.Controller;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.UserAgent;
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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

            User user=(User) SecurityUtils.getSubject().getPrincipal();

            HttpServletRequest request=((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

            String ua=request.getHeader("User-Agent");
            UserAgent userAgent =UserAgent.parseUserAgentString(ua);
            Browser browser=userAgent.getBrowser();
            OperatingSystem os=userAgent.getOperatingSystem();

            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.sss");//设置日期格式

            String recording=df.format(new Date())+"   User:"+user.getUserNickName()+"   Browser:"+browser.getName()+"   OperatingSystem:"+os.getName()+"\n";

            FileOutputStream fileOutputStream=null;
            File file=new File("D:\\signInLog.txt");

            try{
                if(file.exists()){
                    file.createNewFile();
                }
                fileOutputStream =new FileOutputStream(file,true);
                fileOutputStream.write(recording.getBytes());
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

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
