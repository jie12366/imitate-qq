package Model;

import javafx.scene.control.TextField;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * 根据生日计算年龄（生日是字符串）
 */
public class cacuAge {
    public cacuAge(){

    }
    public String getAge(String birth){
        LocalDate date = LocalDate.now();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String Age = "";
        try{
            Date date1 = sdf.parse(birth);
            Instant instant = date1.toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDate localDate = instant.atZone(zoneId).toLocalDate();
            int age = localDate.until(date).getYears();
            Age = age + "";
        }catch (ParseException e){
            e.printStackTrace();
        }
        return Age;
    }
}
