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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private TaskService taskService;

    @GetMapping("/TaskList")
    public String showAllTaskList(Model model){
        List<Task> allTasks = taskService.getAllTasks();
        model.addAttribute("tasks",allTasks);
        return "allTaskList";
    }

    @GetMapping("/UserList")
    public String showAllUserList(Model model){
        List<User> allUsers = taskService.getAllUser();
        model.addAttribute("users",allUsers);
        return "allUserList";
    }

    @GetMapping("/TaskList/delete/{id}")
    public  String deleteOneTask(@PathVariable("id") Integer taskID){
        taskService.deleteOneTaskByTaskID(taskID);
        return "redirect:/admin/TaskList";
    }

    @PostMapping("/TaskList/update")
    public String updateOneTask(@RequestParam(value = "userID") Integer userID,
                                @RequestParam(value = "taskID") Integer taskID,
                                @RequestParam(value = "taskName") String taskName,
                                @RequestParam(value = "taskTheme") String taskTheme,
                                @RequestParam(value = "priority") String priority,
                                @RequestParam(value = "startTime") String startTime,
                                @RequestParam(value = "endTime") String endTime,
                                @RequestParam(value = "description") String description){
        taskService.updateOneTaskByAttributes(userID,taskID,taskName,taskTheme,Priority.valueOf(priority),timeMachine.toTimeStamp(startTime),timeMachine.toTimeStamp(endTime),description);
        return "redirect:/admin/TaskList";
    }

    @GetMapping("/TaskList/update/{id}")
    public String updateOneTask(@PathVariable("id") Integer taskID,Model model){

        Task task = taskService.getTaskByTaskID(taskID);
        model.addAttribute("oldTask",task);

        String startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(task.getStartTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(task.getEndTime());
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        return "updateTask";
    }

    @GetMapping("/evaluation/{id}")
    public String showEvaluationPage(@PathVariable("id")Integer taskID,Model model){
        Task presentTask = taskService.getTaskByTaskID(taskID);
        model.addAttribute("task",presentTask);

        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(presentTask.getStartTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(presentTask.getEndTime());
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
        return "evaluation";
    }

    @PostMapping("/evaluation")
    public String addEvaluation(@RequestParam(value = "taskID")Integer taskID,
                                @RequestParam(value = "evaluation")Integer evaluation){
        taskService.updateEvaluation(taskID,evaluation);
        return "redirect:/admin/TaskList";
    }
}
