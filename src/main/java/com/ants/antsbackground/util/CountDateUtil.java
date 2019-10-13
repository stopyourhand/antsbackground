package com.ants.antsbackground.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 计算两个日期相差多少天
 *
 * @Author czd
 * @Date:createed in 2019/9/27
 * @Version: V1.0
 */
public class CountDateUtil {

    /**
     * 获取两个时间的相差的天数
     *
     * @param startTimes
     * @param endTimes
     * @return
     */
    public static Long getDatePoor(String startTimes, String endTimes) {
        //设置日期格式
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date start;
        Date end;
        //初始化天数，小时，分钟以及秒数
        long day = 0;
        try {
            start = dateFormat.parse(startTimes);
            end = dateFormat.parse(endTimes);
            //获取开始时间和结束时间
            long startTime = start.getTime();
            long endTime = end.getTime();
            long differentDay;
            if (startTime < endTime) {
                differentDay = endTime - startTime;
            } else {
                differentDay = startTime - endTime;
            }
            //将时间转化为天数
            day = differentDay / (24 * 60 * 60 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return day;

    }

    /**
     * 获取与当前天数相差一定天数的前日期，即通过现在的日期获取之前的日期
     *
     * @param number
     * @return
     */
    public static String getBeforeDay(int number) {
        //设置时间格式
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //获取一个时间对象
        Calendar calendar = Calendar.getInstance();
        //利用calender的增加天数的方法，变相的和当前天数减去一定天数获取之前的天数
        calendar.add(Calendar.DATE, number);
        //获取与当前天数相差一定天数的之前的日期
        Date beforeDate = calendar.getTime();
        return simpleDateFormat.format(beforeDate);
    }

    public static void main(String[] args) {

    }


}
