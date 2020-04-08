package com.bin.bustest.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SecondsTest {
//    public void secondTest() {
//        //秒
//        long second = 1509412775l;
//        //转换为日时分秒
//        String days = secondToTime(second);
//        System.out.println(days);
//        //转换为所需日期格式
//        String dateString = secondToDate(second, "yyyy-MM-dd hh:mm:ss");
//        System.out.println(dateString);
//    }

    /**
     * 秒转换为指定格式的日期
     *
     * @param second
     * @param patten
     * @return
     */
    private String secondToDate(long second, String patten) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(second * 1000);//转换为毫秒
        Date date = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat(patten);
        String dateString = format.format(date);
        return dateString;
    }

    /**
     * 返回日时分秒
     *
     * @param second
     * @return
     */
    public static String secondToTime(long second) {
        long days = second / 86400;//转换天数
        second = second % 86400;//剩余秒数
        long hours = second / 3600;//转换小时数
        second = second % 3600;//剩余秒数
        long minutes = second / 60;//转换分钟
        second = second % 60;//剩余秒数
//        if (0 < days) {
//            return days + "天，" + hours + "小时，" + minutes + "分，" + second + "秒";
//        } else {
//            return hours + "小时，" + minutes + "分，" + second + "秒";
//        }

        if (0 < hours) {
            return hours + "小时" + minutes + "分";
        } else {
            return minutes + "分" ;
        }
    }
}
