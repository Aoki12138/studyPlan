package hust.group20.se.Utils;

import java.util.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class timeMachine {

    public static Timestamp toTimeStamp(String dateStr){
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        try {
            Date date = simpleDateFormat.parse(dateStr);
//            System.out.println(date);
            return new Timestamp(date.getTime());
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
