package zhou.com.snail.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zhou on 2017/10/21.
 */

public class DateUtil {

    //2007.07.04
    public static String dotDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
        String time = format.format(date);
        return time;
    }

    //2017-12-12
    public static String lineDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String time = format.format(date);
        return time;
    }

    //4th Jul 2017
    public static String blankDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d'th' MMM yyyy");
        String time = format.format(date);
        return time;
    }

    //8th June 12:33
    public static String hourDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("d'th' MMM HH:mm");
        String time = format.format(date);
        return time;
    }

    //20/8/2017
    public static String reverseDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        String time = format.format(date);
        return time;
    }

}
