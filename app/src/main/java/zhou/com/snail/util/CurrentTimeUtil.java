package zhou.com.snail.util;

/**
 * Created by zhou on 2017/8/10.
 */

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
//改为24小时制
public class CurrentTimeUtil {
    public static String nowTime(){
        Calendar now;
        SimpleDateFormat fmt;

        now = Calendar.getInstance();
        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return fmt.format(now.getTime());
    }
}
