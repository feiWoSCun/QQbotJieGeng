package cn.mikulink.luobin.util;

import java.util.Calendar;

public class DateUtils {
    public static int getNowTime() {
        Calendar cal = Calendar.getInstance();
        int hour = cal.get(Calendar.HOUR_OF_DAY);
        if (6 <= hour && hour <= 9) {
            return 1;
        } else if (11 <= hour && hour <= 17) {
            return 2;
        } else if (18 <= hour && hour <= 22) {
            return 3;
        }
        else return 4;
    }
}
