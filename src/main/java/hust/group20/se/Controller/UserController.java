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
    public String showDayAnalysisPage(HttpSession session,Model model){
        Integer num = new Integer(1);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MILLISECOND,0);


        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.set(Calendar.SECOND, 59);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MILLISECOND,0);

        Timestamp today = new Timestamp(cal1.getTimeInMillis());
        Timestamp tomorrow = new Timestamp(cal2.getTimeInMillis());

        List<Task> tasks = taskService.getTasksTodayByUserID(num,today,tomorrow);

        //DONUT 和 BAR 需要的各科目的时间统计
        List<Task> mathTasks = taskService.getTasksFromChosenByTheme(tasks,"数学");
        Integer mathtime = taskService.gatherTime(mathTasks).get(0);
        Integer effmathtime = taskService.gatherTime(mathTasks).get(1);
//        session.setAttribute("mathtime",mathtime);
        model.addAttribute("mathtime",mathtime);
//        session.setAttribute("effmathtime",effmathtime);
        model.addAttribute("effmathtime",effmathtime);

        List<Task> EnglishTasks = taskService.getTasksFromChosenByTheme(tasks,"英语");
        Integer englishtime = taskService.gatherTime(EnglishTasks).get(0);
        Integer effenglishtime = taskService.gatherTime(EnglishTasks).get(1);
//        session.setAttribute("englishtime",englishtime);
        model.addAttribute("englishtime",englishtime);
//        session.setAttribute("effenglishtime",effenglishtime);
        model.addAttribute("effenglishtime",effenglishtime);

        List<Task> PoliticsTasks = taskService.getTasksFromChosenByTheme(tasks,"政治");
        Integer politicstime = taskService.gatherTime(PoliticsTasks).get(0);
        Integer effpoliticstime = taskService.gatherTime(PoliticsTasks).get(1);
//        session.setAttribute("politicstime",politicstime);
        model.addAttribute("politicstime",politicstime);
//        session.setAttribute("effpoliticstime",effpoliticstime);
        model.addAttribute("effpoliticstime",effpoliticstime);

        List<Task> proTasks = taskService.getTasksFromChosenByTheme(tasks,"专业课");
        Integer protime = taskService.gatherTime(proTasks).get(0);
        Integer effprotime = taskService.gatherTime(proTasks).get(1);
//        session.setAttribute("protime",protime);
        model.addAttribute("protime",protime);
//        session.setAttribute("effprotime",effprotime);
        model.addAttribute("effprotime",effprotime);

        List<Task> otherTasks = taskService.getTasksFromChosenByTheme(tasks,"其他");
        Integer othertime = taskService.gatherTime(otherTasks).get(0);
        Integer effothertime = taskService.gatherTime(otherTasks).get(1);
//        session.setAttribute("othertime",othertime);
        model.addAttribute("othertime",othertime);
//        session.setAttribute("effothertime",effothertime);
        model.addAttribute("effothertime",effothertime);

        //总时间与有效总时间的统计
        Integer alltime= mathtime+ englishtime+ politicstime+ protime+ othertime;
//        Integer allefftime= effmathtime+ effenglishtime+ effpoliticstime+ effprotime+ effothertime;
//        session.setAttribute("alltime",alltime);
//        session.setAttribute("allefftime",allefftime);

        //不同科目分配时间的占比
        int mathscale =(int) (mathtime*100/(alltime+1));
//        session.setAttribute();
        model.addAttribute("mathscale",mathscale);
        int englishscale =(int) (englishtime*100/(alltime+1));
//        session.setAttribute();
        model.addAttribute("englishscale",englishscale);
        int politicsscale =(int) (politicstime*100/(alltime+1));
//        session.setAttribute("politicsscale",politicsscale);
        model.addAttribute("politicsscale",politicsscale);
        int proscale =(int) (protime*100/(alltime+1));
//        session.setAttribute("proscale",proscale);
        model.addAttribute("proscale",proscale);
        int otherscale =(int) (othertime*100/(alltime+1));
//        session.setAttribute("otherscale",otherscale);
        model.addAttribute("otherscale",otherscale);

        //在科目上提供建议
        Integer[]a= new Integer[]{effmathtime,effenglishtime,effpoliticstime,effprotime,effothertime};
        Arrays.sort(a);

        if(a[0]==effmathtime){
//            session.setAttribute("smallest","今天数学学习时间较少，明天应着重学习");
//            session.setAttribute("SMALLEST","本日学习时间最短的科目为：数学");
            model.addAttribute("smallest","今天数学学习时间较少，明天应着重学习");
        }
        else if(a[0]==effenglishtime){
//            session.setAttribute("smallest","今天英语学习时间较少，明天应着重学习");
//            session.setAttribute("SMALLEST","本日学习时间最短的科目为：英语");
            model.addAttribute("smallest","今天英语学习时间较少，明天应着重学习");
        }
        else if(a[0]==effpoliticstime){
//            session.setAttribute("smallest","今天政治学习时间较少，明天应着重学习");
//            session.setAttribute("SMALLEST","本日学习时间最短的科目为：政治");
            model.addAttribute("smallest","今天政治学习时间较少，明天应着重学习");
        }
        else if(a[0]==effprotime){
//            session.setAttribute("smallest","今天专业课学习时间较少，明天应着重学习");
//            session.setAttribute("SMALLEST","本日学习时间最短的科目为：专业课");
            model.addAttribute("smallest","今天专业课学习时间较少，明天应着重学习");
        }
        else if(a[0]==effothertime){
//            session.setAttribute("smallest","今天其他学习时间较少，明天应着重学习");
//            session.setAttribute("SMALLEST","本日学习时间最短的科目为：其他");
            model.addAttribute("smallest","今天其他学习时间较少，明天应着重学习");
        }

        //给时间分段
        cal1.set(Calendar.HOUR_OF_DAY, 6);
        Timestamp morning = new Timestamp(cal1.getTimeInMillis());

        cal1.set(Calendar.HOUR_OF_DAY, 12);
        Timestamp noon = new Timestamp(cal1.getTimeInMillis());

        cal1.set(Calendar.HOUR_OF_DAY, 18);
        Timestamp evening = new Timestamp(cal1.getTimeInMillis());

        //每段的学习时间及有效学习时间
        List <Task> firstTasks = taskService.getTasksFromChosenByTime(tasks,today,morning);
        if(firstTasks.size() != 0) {
            Long todayStart = today.getTime();
            Long firststart = firstTasks.get(0).getStartTime().getTime();
            if (firststart < todayStart) {
                firstTasks.get(0).setStartTime(today);
            }
        }


        List <Task> secondTasks = taskService.getTasksFromChosenByTime(tasks,morning,noon);
        Integer firstEndtime = new Integer(1);
        Integer efffirstEndtime = new Integer(1);
        if (secondTasks.size()!=0) {
            Long morningStart = morning.getTime();
            Long secondstart = secondTasks.get(0).getStartTime().getTime();
            if (secondstart < morningStart) {
                secondTasks.get(0).setStartTime(morning);
                int firstEnd = (int) ((morningStart - secondstart) / 1000);
                int efffirstEnd = firstEnd * (secondTasks.get(0).getEvaluation()) / 100;
                firstEndtime = Integer.valueOf(firstEnd);
                efffirstEndtime = Integer.valueOf(efffirstEnd);
            }
        }

        List <Task> thirdTasks = taskService.getTasksFromChosenByTime(tasks,noon,evening);
        Integer secondEndtime = new Integer(1);
        Integer effsecondEndtime = new Integer(1);
        if(thirdTasks.size()!=0) {
            Long noonStart = noon.getTime();
            Long thirdstart = thirdTasks.get(0).getStartTime().getTime();
            if (thirdstart < noonStart) {
                thirdTasks.get(0).setStartTime(noon);
                int secondEnd = (int) ((noonStart - thirdstart) / 1000);
                int effsecondEnd = secondEnd * (secondTasks.get(0).getEvaluation()) / 100;
                secondEndtime = Integer.valueOf(secondEnd);
                effsecondEndtime = Integer.valueOf(effsecondEnd);
            }
        }

        List <Task> fourthTasks = taskService.getTasksFromChosenByTime(tasks,evening,tomorrow);
        Integer thirdEndtime = new Integer(1);
        Integer effthirdEndtime = new Integer(1);
        if (fourthTasks.size()!=0) {
            Long eveningStart = evening.getTime();
            Long fourthstart = fourthTasks.get(0).getStartTime().getTime();
            if (fourthstart < eveningStart) {
                fourthTasks.get(0).setStartTime(evening);
                int thirdEnd = (int) ((eveningStart - fourthstart) / 1000);
                int effthirdEnd = thirdEnd * (thirdTasks.get(0).getEvaluation()) / 100;
                thirdEndtime = Integer.valueOf(thirdEnd);
                effthirdEndtime = Integer.valueOf(effthirdEnd);
            }
        }

        Integer firsttime = (firstTasks!=null)?taskService.gatherTime(firstTasks).get(0)+ firstEndtime:firstEndtime;
        Integer efffirsttime =(firstTasks!=null)?taskService.gatherTime(firstTasks).get(1)+ efffirstEndtime:efffirstEndtime;
//        session.setAttribute("firsttime",firsttime);
        model.addAttribute("firsttime",firsttime);
//        session.setAttribute("efffirsttime",efffirsttime);
        model.addAttribute("efffirsttime",efffirsttime);


        Integer secondtime = taskService.gatherTime(secondTasks).get(0)+ secondEndtime;
        Integer effsecondtime = taskService.gatherTime(secondTasks).get(1)+ effsecondEndtime;
//        session.setAttribute("secondtime",secondtime);
        model.addAttribute("secondtime",secondtime);
//        session.setAttribute("effsecondtime",effsecondtime);
        model.addAttribute("effsecondtime",effsecondtime);

        Integer thirdtime = taskService.gatherTime(thirdTasks).get(0)+ thirdEndtime;
        Integer effthirdtime = taskService.gatherTime(thirdTasks).get(1)+ effthirdEndtime;
//        session.setAttribute("thirdtime",thirdtime);
        model.addAttribute("thirdtime",thirdtime);
//        session.setAttribute("effthirdtime",effthirdtime);
        model.addAttribute("effthirdtime",effthirdtime);

        Integer fourthtime = taskService.gatherTime(fourthTasks).get(0);
        Integer efffourthtime = taskService.gatherTime(fourthTasks).get(1);
//        session.setAttribute("fourthtime",fourthtime);
        model.addAttribute("fourthtime",fourthtime);
//        session.setAttribute("efffourthtime",efffourthtime);
        model.addAttribute("efffourthtime",efffourthtime);

        //在时间段上提供建议
//        double firstscale = (double)(efffirsttime/firsttime+1);
//        double secondscale = (double)(effsecondtime/secondtime+1);
//        double thirdscale = (double)(effthirdtime/thirdtime+1);
//        double fourthscale = (double)(efffourthtime/fourthtime+1);
//        double[]b= new double[]{firstscale,secondscale,thirdscale,fourthscale};
//        Arrays.sort(b);
//
//        if(b[0]==firstscale){session.setAttribute("worst","今天清晨（0:00~6:00）学习效率较低，请注意");
//            session.setAttribute("WORST","本日学习效率最低的时间为：清晨（0:00~6:00）");}
//        else if(b[0]==secondscale){session.setAttribute("worst","今天上午（6:00~12:00）学习效率较低，请注意");
//            session.setAttribute("WORST","本日学习效率最低的时间为：上午（6:00~12:00）");}
//        else if(b[0]==thirdscale){session.setAttribute("worst","今天下午（12:00~18:00）学习效率较低，请注意");
//            session.setAttribute("WORST","本日学习效率最低的时间为：下午（12:00~18:00）");}
//        else if(b[0]==fourthscale){session.setAttribute("worst","今天夜间（18:00~24:00）学习效率较低，请注意");
//            session.setAttribute("WORST","本日学习效率最低的时间为：夜间（18:00~24:00）");}

        int[] scale=new int[]{firsttime,secondtime,thirdtime,fourthtime};
        int[] scale_eff=new int[]{efffirsttime,effsecondtime,effthirdtime,efffourthtime};

        int scale_min=0;
        double scale_min_value=1.0;

        for (int i=0;i<scale.length;i++){
            if (scale[i]!=0){
                if ((double)(scale_eff[i]/scale[i])<scale_min_value){
                    scale_min_value=(double)(scale_eff[i]/scale[i]);
                    scale_min=i+1;
                }
            }
        }

        switch (scale_min){
            case 0:model.addAttribute("worst","无");break;
            case 1:model.addAttribute("worst","本日学习效率最低的时间为：清晨（0:00~6:00）。请注意提高效率哦 !");break;
            case 2:model.addAttribute("worst","本日学习效率最低的时间为：上午（6:00~12:00）。请注意提高效率哦 !");break;
            case 3:model.addAttribute("worst","本日学习效率最低的时间为：下午（12:00~18:00）。请注意提高效率哦 !");break;
            case 4:model.addAttribute("worst","本日学习效率最低的时间为：夜间（18:00~24:00）。请注意提高效率哦 !");break;
            default:break;

        }

        model.addAttribute("testForTime",firsttime);

        return "analysisByDay";
    }

    @RequestMapping("/analysis/week")
    public String showWeekAnalysisPage(Model model){
        Integer num = new Integer(1);
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(new Date());
        cal1.setFirstDayOfWeek(Calendar.MONDAY);
        cal1.set(Calendar.DAY_OF_WEEK,cal1.getFirstDayOfWeek());
        cal1.set(Calendar.SECOND, 0);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.HOUR_OF_DAY, 0);
        cal1.set(Calendar.MILLISECOND,0);

        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(new Date());
        cal2.setFirstDayOfWeek(Calendar.MONDAY);
        cal2.set(Calendar.DAY_OF_WEEK,cal2.getFirstDayOfWeek() + 6);
        cal2.set(Calendar.SECOND, 59);
        cal2.set(Calendar.MINUTE, 59);
        cal2.set(Calendar.HOUR_OF_DAY, 23);
        cal2.set(Calendar.MILLISECOND,0);

        Timestamp startWeek = new Timestamp(cal1.getTimeInMillis());
        Timestamp endWeek = new Timestamp(cal2.getTimeInMillis());

        List<Task> taskWeek = taskService.getTasksTodayByUserID(num,startWeek,endWeek);
        //session.setAttribute("taskWeek",taskWeek);
        model.addAttribute("taskWeek",taskWeek);

        Integer plus = new Integer(1);

        //DONUT 和 BAR 需要的各科目的时间统计
        List<Task> mathTaskWeek = taskService.getTasksFromChosenByTheme(taskWeek,"数学");
        Integer mathtimeWeek = taskService.gatherTime(mathTaskWeek).get(0)+plus;
        Integer effmathtimeWeek = taskService.gatherTime(mathTaskWeek).get(1)+plus;
        model.addAttribute("mathtimeWeek",mathtimeWeek);
        model.addAttribute("effmathtimeWeek",effmathtimeWeek);

        List<Task> EnglishTaskWeek = taskService.getTasksFromChosenByTheme(taskWeek,"英语");
        Integer englishtimeWeek = taskService.gatherTime(EnglishTaskWeek).get(0)+plus;
        Integer effenglishtimeWeek = taskService.gatherTime(EnglishTaskWeek).get(1)+plus;
        model.addAttribute("englishtimeWeek",englishtimeWeek);
        model.addAttribute("effenglishtimeWeek",effenglishtimeWeek);

        List<Task> PoliticsTaskWeek = taskService.getTasksFromChosenByTheme(taskWeek,"政治");
        Integer politicstimeWeek = taskService.gatherTime(PoliticsTaskWeek).get(0)+plus;
        Integer effpoliticstimeWeek = taskService.gatherTime(PoliticsTaskWeek).get(1)+plus;
        model.addAttribute("politicstimeWeek",politicstimeWeek);
        model.addAttribute("effpoliticstimeWeek",effpoliticstimeWeek);

        List<Task> proTaskWeek = taskService.getTasksFromChosenByTheme(taskWeek,"专业课");
        Integer protimeWeek = taskService.gatherTime(proTaskWeek).get(0)+plus;
        Integer effprotimeWeek = taskService.gatherTime(proTaskWeek).get(1)+plus;
        model.addAttribute("protimeWeek",protimeWeek);
        model.addAttribute("effprotimeWeek",effprotimeWeek);

        List<Task> otherTaskWeek = taskService.getTasksFromChosenByTheme(taskWeek,"其他");
        Integer othertimeWeek = taskService.gatherTime(otherTaskWeek).get(0);
        Integer effothertimeWeek = taskService.gatherTime(otherTaskWeek).get(1);
        model.addAttribute("othertimeWeek",othertimeWeek);
        model.addAttribute("effothertimeWeek",effothertimeWeek);

        //总时间与有效总时间的统计
        Integer alltimeWeek= mathtimeWeek+ englishtimeWeek+ politicstimeWeek+ protimeWeek+ othertimeWeek;
        Integer allefftimeWeek= effmathtimeWeek+ effenglishtimeWeek+ effpoliticstimeWeek+ effprotimeWeek+ effothertimeWeek;
        model.addAttribute("alltimeWeek",alltimeWeek);
        model.addAttribute("allefftimeWeek",allefftimeWeek);

        //不同科目分配时间的占比
        int mathscaleWeek =(int) (mathtimeWeek*100/alltimeWeek);
        model.addAttribute("mathscaleWeek",mathscaleWeek);
        int englishscaleWeek =(int) (englishtimeWeek*100/alltimeWeek);
        model.addAttribute("englishscaleWeek",englishscaleWeek);
        int politicsscaleWeek =(int) (politicstimeWeek*100/alltimeWeek);
        model.addAttribute("politicsscaleWeek",politicsscaleWeek);
        int proscaleWeek =(int) (protimeWeek*100/alltimeWeek);
        model.addAttribute("proscaleWeek",proscaleWeek);
        int otherscaleWeek =(int) (othertimeWeek*100/alltimeWeek);
        model.addAttribute("otherscaleWeek",otherscaleWeek);

        //给时间分段
        cal1.add(Calendar.DATE, +1);
        Timestamp Tuesday = new Timestamp(cal1.getTimeInMillis());

        cal1.add(Calendar.DATE, +1);
        Timestamp Wednesday = new Timestamp(cal1.getTimeInMillis());

        cal1.add(Calendar.DATE, +1);
        Timestamp Thursday = new Timestamp(cal1.getTimeInMillis());

        cal1.add(Calendar.DATE, +1);
        Timestamp Friday = new Timestamp(cal1.getTimeInMillis());

        cal1.add(Calendar.DATE, +1);
        Timestamp Saturday = new Timestamp(cal1.getTimeInMillis());

        cal1.add(Calendar.DATE, +1);
        Timestamp Sunday = new Timestamp(cal1.getTimeInMillis());


        //每天的学习时间及有效学习时间
        List <Task> MondayTesks = taskService.getTasksFromChosenByTime(taskWeek,startWeek,Tuesday);
        Integer Mondaytime = taskService.gatherTime(MondayTesks).get(0);
        Integer effMondaytime = taskService.gatherTime(MondayTesks).get(1);
        model.addAttribute("Mondaytime",Mondaytime);
        model.addAttribute("effMondaytime",effMondaytime);

        List <Task> TuesdayTesks = taskService.getTasksFromChosenByTime(taskWeek,Tuesday,Wednesday);
        Integer Tuesdaytime = taskService.gatherTime(TuesdayTesks).get(0);
        Integer effTuesdaytime = taskService.gatherTime(TuesdayTesks).get(1);
        model.addAttribute("Tuesdaytime",Tuesdaytime);
        model.addAttribute("effTuesdaytime",effTuesdaytime);

        List <Task> WednesdayTesks = taskService.getTasksFromChosenByTime(taskWeek,Wednesday,Thursday);
        Integer Wednesdaytime = taskService.gatherTime(WednesdayTesks).get(0);
        Integer effWednesdaytime = taskService.gatherTime(WednesdayTesks).get(1);
        model.addAttribute("Wednesdaytime",Wednesdaytime);
        model.addAttribute("effWednesdaytime",effWednesdaytime);

        List <Task> ThursdayTesks = taskService.getTasksFromChosenByTime(taskWeek,Thursday,Friday);
        Integer Thursdaytime = taskService.gatherTime(ThursdayTesks).get(0);
        Integer effThursdaytime = taskService.gatherTime(ThursdayTesks).get(1);
        model.addAttribute("Thursdaytime",Thursdaytime);
        model.addAttribute("effThursdaytime",effThursdaytime);

        List <Task> FridayTesks = taskService.getTasksFromChosenByTime(taskWeek,Friday,Saturday);
        Integer Fridaytime = taskService.gatherTime(FridayTesks).get(0);
        Integer effFridaytime = taskService.gatherTime(FridayTesks).get(1);
        model.addAttribute("Fridaytime",Fridaytime);
        model.addAttribute("effFridaytime",effFridaytime);

        List <Task> SaturdayTesks = taskService.getTasksFromChosenByTime(taskWeek,Saturday,Sunday);
        Integer Saturdaytime = taskService.gatherTime(SaturdayTesks).get(0);
        Integer effSaturdaytime = taskService.gatherTime(SaturdayTesks).get(1);
        model.addAttribute("Saturdaytime",Saturdaytime);
        model.addAttribute("effSaturdaytime",effSaturdaytime);

        List <Task> SundayTesks = taskService.getTasksFromChosenByTime(taskWeek,Sunday,endWeek);
        Integer Sundaytime = taskService.gatherTime(SundayTesks).get(0);
        Integer effSundaytime = taskService.gatherTime(SundayTesks).get(1);
        model.addAttribute("Sundaytime",Sundaytime);
        model.addAttribute("effSundaytime",effSundaytime);

        //在科目上提供建议
        double[]b= new double[]{mathscaleWeek,englishscaleWeek,politicsscaleWeek,proscaleWeek,otherscaleWeek};
        Arrays.sort(b);

        if(b[0]==mathscaleWeek){
            model.addAttribute("WORSTWEEK","本周学习时间最短的科目为：数学");}
        else if(b[0]==englishscaleWeek){
            model.addAttribute("WORSTWEEK","本周学习时间最短的科目为：英语");}
        else if(b[0]==politicsscaleWeek){
            model.addAttribute("WORSTWEEK","本周学习时间最短的科目为：政治");}
        else if(b[0]==proscaleWeek){
            model.addAttribute("WORSTWEEK","本周学习时间最短的科目为：专业课");}
        else if(b[0]==otherscaleWeek){
            model.addAttribute("WORSTWEEK","本周学习时间最短的科目为：其他");}

        int[] times = new int[]{Mondaytime,Tuesdaytime,Wednesdaytime,Thursdaytime,Fridaytime,Saturdaytime,Sundaytime};
        int[] efftimes = new int[]{effMondaytime,effTuesdaytime,effWednesdaytime,effThursdaytime,effFridaytime,effSaturdaytime,effSundaytime};

        int time_min = 0;
        double time_min_value = 1.0;

        for(int i=0;i<times.length;i++){
            if (times[i]!=0){
                if ((double) (efftimes[i] / times[i]) < time_min_value) {
                    time_min_value=(double)(efftimes[i] / times[i]);
                    time_min=i+1;
                }
            }
        }

        switch(time_min){
            case 0:model.addAttribute("SMALLESTWEEK","其他");   break;
            case 1:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期一"); break;
            case 2:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期二"); break;
            case 3:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期三"); break;
            case 4:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期四"); break;
            case 5:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期五"); break;
            case 6:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期六"); break;
            case 7:model.addAttribute("SMALLESTWEEK","本周学习效率最低的时间为：星期日"); break;

            default:            break;
        }

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

        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(presentTask.getStartTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(presentTask.getEndTime());
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
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
        return "redirect:/user/taskList";
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

        String startTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(task.getStartTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm").format(task.getEndTime());
        model.addAttribute("startTime",startTime);
        model.addAttribute("endTime",endTime);
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
